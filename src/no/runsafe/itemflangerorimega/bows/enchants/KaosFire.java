package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.projectiles.IProjectile;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.Sound;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.Config;
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

		int radius = Config.kaosFireRadius; // Scalable sphere
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

		loc.playSound(Sound.Creature.Wither.Hurt, 20, 1);
		projectile.remove();
	}

	@Override
	public boolean onArrowShoot(ILivingEntity entity, IEntity arrow)
	{
		ILocation source = entity.getLocation();
		if (source == null)
			return false;

		if (!(entity instanceof IPlayer))
			return false;

		// Reduce bow's durability
		IPlayer shooter = (IPlayer) entity;
		RunsafeMeta itemMainHand = shooter.getItemInMainHand();
		RunsafeMeta itemOffHand = shooter.getItemInOffHand();

		if (itemMainHand != null && itemMainHand.is(Item.Combat.Bow))
		{
			itemMainHand.setDurability((short) (itemMainHand.getDurability() + Config.kaosFireDurabilityLoss));
			shooter.setItemInMainHand(itemMainHand);
		}

		if (itemOffHand != null && itemOffHand.is(Item.Combat.Bow))
		{
			itemOffHand.setDurability((short) (itemOffHand.getDurability() + Config.kaosFireDurabilityLoss));
			shooter.setItemInOffHand(itemOffHand);
		}

		source.playSound(Sound.Creature.Illager.Illusion.CastSpell);
		return true;
	}
}
