package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.entity.ILivingEntity;

public abstract class CustomBowEnchant implements ICustomBowEnchant
{
	@Override
	public boolean onArrowShoot(ILivingEntity entity, CustomArrow arrow)
	{
		// Placeholder for extended classes.
		return true;
	}
}
