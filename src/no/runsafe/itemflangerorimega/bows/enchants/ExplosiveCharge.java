package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.ITNTPrimed;
import no.runsafe.framework.api.entity.projectiles.IProjectile;
import no.runsafe.framework.minecraft.entity.ProjectileEntity;
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
	public void onArrowCollide(IProjectile projectile)
	{
		ILocation loc = projectile.getLocation();
		if (loc == null)
			return;

		ITNTPrimed primedTNT = (ITNTPrimed) ProjectileEntity.PrimedTNT.spawn(loc);
		primedTNT.setIsIncendiary(true);
		primedTNT.setFuseTicks(1);

		projectile.remove();
	}
}
