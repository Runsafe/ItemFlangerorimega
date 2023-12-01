package no.runsafe.itemflangerorimega.weapons;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CustomWeaponEnchantHandler implements IEntityDamageByEntityEvent
{
	public CustomWeaponEnchantHandler(ICustomWeaponEnchant[] enchants)
	{
		for (ICustomWeaponEnchant enchant : enchants)
			this.enchants.put(enchant.getSimpleName(), enchant);
	}

	public void enchantWeapon(RunsafeMeta meta, ICustomWeaponEnchant enchant)
	{
		meta.addLore(ENCHANT_TAG + enchant.getEnchantText());
	}

	public ICustomWeaponEnchant getEnchant(String name)
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
		RunsafeEntity attacker = event.getDamageActor();

		if (!(attacker instanceof ILivingEntity) || !(event.getEntity() instanceof ILivingEntity))
			return;

		ILivingEntity livingAttacker = (ILivingEntity) attacker;
		RunsafeMeta item = livingAttacker.getEquipment().getItemInHand();

		if (item == null || item.is(Item.Unavailable.Air))
			return;

		List<String> lore = item.getLore();
		if (lore == null)
			return;

		for (ICustomWeaponEnchant enchant : enchants.values())
		{
			if (!lore.contains(ENCHANT_TAG + enchant.getEnchantText()))
				continue;

			boolean allow = enchant.onDamageEntity((ILivingEntity) event.getEntity());
			if (!allow)
				event.cancel();
		}
	}

	private final HashMap<String, ICustomWeaponEnchant> enchants = new HashMap<>(0);
	private static final String ENCHANT_TAG = "§r§7";
}
