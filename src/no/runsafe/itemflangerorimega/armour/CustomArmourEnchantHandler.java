package no.runsafe.itemflangerorimega.armour;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.entity.IEntityDamageEvent;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityEvent;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CustomArmourEnchantHandler implements IEntityDamageEvent, IEntityDamageByEntityEvent, IPlayerMove
{
	public CustomArmourEnchantHandler(ICustomArmourEnchant[] enchants)
	{
		for (ICustomArmourEnchant enchant : enchants)
			this.enchants.put(enchant.getSimpleName(), enchant);
	}

	public void enchantArmour(RunsafeMeta meta, ICustomArmourEnchant enchant)
	{
		meta.addLore(ENCHANT_TAG + enchant.getEnchantText());
	}

	public ICustomArmourEnchant getEnchant(String name)
	{
		return enchants.get(name);
	}

	public Set<String> getEnchants()
	{
		return enchants.keySet();
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		checkEnchants(event);
	}

	@Override
	public void OnEntityDamage(RunsafeEntityDamageEvent event)
	{
		checkEnchants(event);
	}

	@Override
	public boolean OnPlayerMove(IPlayer player, ILocation from, ILocation to)
	{
		RunsafeEntityEquipment equipment = player.getEquipment();
		checkMoveEnchants(equipment.getLeggings(), player, from, to);
		checkMoveEnchants(equipment.getBoots(), player, from, to);
		checkMoveEnchants(equipment.getChestplate(), player, from, to);
		checkMoveEnchants(equipment.getHelmet(), player, from, to);
		return true;
	}

	private void checkMoveEnchants(RunsafeMeta item, IPlayer player, ILocation from, ILocation to)
	{
		if (item == null)
			return;

		List<String> lore = item.getLore();
		if (lore == null || lore.isEmpty())
			return;

		for (ICustomArmourEnchant enchant : enchants.values())
			if (lore.contains(ENCHANT_TAG + enchant.getEnchantText()))
				enchant.playerMove(player, from, to);
	}

	private void checkEnchants(RunsafeEntityEvent event)
	{
		RunsafeEntity entity = event.getEntity();
		if (!(entity instanceof ILivingEntity))
			return;

		RunsafeEntityEquipment equipment = ((ILivingEntity) entity).getEquipment();

		processEnchants(equipment.getLeggings(), event);
		processEnchants(equipment.getBoots(), event);
		processEnchants(equipment.getChestplate(), event);
		processEnchants(equipment.getHelmet(), event);
	}

	private void processEnchants(RunsafeMeta item, RunsafeEntityEvent entityEvent)
	{
		if (item == null)
			return;

		List<String> lore = item.getLore();
		if (lore == null)
			return;

		for (ICustomArmourEnchant enchant : enchants.values())
		{
			if (!lore.contains(ENCHANT_TAG + enchant.getEnchantText()))
				continue;

			if (!(entityEvent instanceof RunsafeEntityDamageEvent))
				continue;

			if (entityEvent instanceof RunsafeEntityDamageByEntityEvent)
			{
				enchant.entityDamageByEntityEvent(item, (RunsafeEntityDamageByEntityEvent) entityEvent);
				continue;
			}

			enchant.entityDamageEvent(item, (RunsafeEntityDamageEvent) entityEvent);
		}
	}

	private final HashMap<String, ICustomArmourEnchant> enchants = new HashMap<>(0);
	private static final String ENCHANT_TAG = "§r§7";
}
