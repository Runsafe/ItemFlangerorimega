package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.projectiles.IProjectile;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchant;

public class KaosFire extends CustomBowEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Kaosfire";
	}

	@Override
	public String getSimpleName()
	{
		return "kaos";
	}

	@Override
	public void onArrowCollide(IProjectile projectile)
	{
		ILocation loc = projectile.getLocation();
		if (loc == null)
			return;

		loc.getWorld().createExplosion(loc, 10, true, false);

		projectile.remove();
	}
}
