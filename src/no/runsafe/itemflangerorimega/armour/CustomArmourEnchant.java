package no.runsafe.itemflangerorimega.armour;

import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public abstract class CustomArmourEnchant implements ICustomArmourEnchant
{
	@Override
	public void entityDamageEvent(RunsafeMeta item, RunsafeEntityDamageEvent event)
	{
		// Placeholder method to over-ride!
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeMeta item, RunsafeEntityDamageByEntityEvent event)
	{
		// Placeholder method to over-ride!
	}
}
