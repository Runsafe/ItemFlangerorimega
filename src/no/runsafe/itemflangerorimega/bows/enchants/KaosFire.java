package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.projectiles.IProjectile;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.Sound;
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

		int radius = 6; // Scalable sphere
		int radiusSquared = radius * radius;

		for (double x = -radius; x <= radius; x++)
			for (double y = -radius; y <= radius; y++)
				for (double z = -radius; z <= radius; z++)
				{
					// Checks if the point falls inside the spherical radius
					if (x * x + y * y + z * z > radiusSquared)
						continue;

					ILocation fireLoc = loc.clone();
					fireLoc.offset(x, y, z);

					if (!fireLoc.getBlock().isAir())
						continue;

					fireLoc.getBlock().set(Item.Unavailable.Fire);
				}

		loc.playSound(Sound.Environment.Explode);
		projectile.remove();
	}
}
