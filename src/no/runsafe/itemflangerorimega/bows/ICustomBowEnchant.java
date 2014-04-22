package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.entity.ILivingEntity;

public interface ICustomBowEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public boolean onArrowShoot(ILivingEntity shooter, CustomArrow arrow);
}
