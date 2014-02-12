package no.runsafe.itemflangerorimega.armour;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.entity.IEntityDamageEvent;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityEvent;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.HashMap;
import java.util.List;

public class CustomArmourEnchantHandler implements IEntityDamageEvent
{
	public CustomArmourEnchantHandler(ICustomArmourEnchant[] enchants)
	{
		for (ICustomArmourEnchant enchant : enchants)
			this.enchants.put(enchant.getSimpleName(), enchant);
	}

	@Override
	public void OnEntityDamage(RunsafeEntityDamageEvent event)
	{
		RunsafeEntity entity = event.getEntity();
		if (entity instanceof ILivingEntity)
		{
			RunsafeEntityEquipment equipment = ((ILivingEntity) entity).getEquipment();

			processEnchants(equipment.getLeggings(), event);
			processEnchants(equipment.getBoots(), event);
			processEnchants(equipment.getChestplate(), event);
			processEnchants(equipment.getHelmet(), event);
		}
	}

	private void processEnchants(RunsafeMeta item, RunsafeEntityEvent entityEvent)
	{
		if (item == null)
			return;

		List<String> lore = item.getLore();
		if (lore == null)
			return;

		for (String loreString : lore)
		{
			loreString = ENCHANT_TAG + loreString;
			if (enchants.containsKey(loreString))
			{
				ICustomArmourEnchant enchant = enchants.get(loreString);
				if (entityEvent instanceof RunsafeEntityDamageEvent)
				{
					if (entityEvent instanceof RunsafeEntityDamageByEntityEvent)
						enchant.entityDamageByEntityEvent((RunsafeEntityDamageByEntityEvent) entityEvent);
					else
						enchant.entityDamageEvent((RunsafeEntityDamageEvent) entityEvent);
				}
			}
		}

	}

	private HashMap<String, ICustomArmourEnchant> enchants = new HashMap<String, ICustomArmourEnchant>(0);
	private static final String ENCHANT_TAG = "§r§7";
}
