package no.runsafe.itemflangerorimega.armour;

import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;

public abstract class CustomArmourEnchant implements ICustomArmourEnchant
{
	@Override
	public void entityDamageEvent(RunsafeEntityDamageEvent event)
	{
		// Placeholder method to over-ride!
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeEntityDamageByEntityEvent event)
	{
		// Placeholder method to over-ride!
	}
}
