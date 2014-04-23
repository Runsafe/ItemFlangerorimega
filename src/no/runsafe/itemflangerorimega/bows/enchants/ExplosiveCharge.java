package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.itemflangerorimega.bows.CustomArrow;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchant;

public class ExplosiveCharge extends CustomBowEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Explosive Charge";
	}

	@Override
	public String getSimpleName()
	{
		return "explosive";
	}

	@Override
	public boolean onArrowShoot(ILivingEntity entity, CustomArrow arrow)
	{
		arrow.setExplosive(true);
		return true;
	}
}
