package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
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
	public void onArrowCollide(RunsafeProjectile projectile)
	{
		ILocation loc = projectile.getLocation();
		loc.getWorld().createExplosion(loc, 3.0F, true, true);
	}
}
