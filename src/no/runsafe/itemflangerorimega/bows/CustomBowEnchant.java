package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.projectiles.IProjectile;

public abstract class CustomBowEnchant implements ICustomBowEnchant
{
	@Override
	public void onArrowCollideBlock(IProjectile projectile, IBlock block)
	{
		// Placeholder for extended classes.
	}

	@Override
	public void onArrowCollideEntity(IProjectile projectile, IEntity entity)
	{
		// Placeholder for extended classes.
	}

	@Override
	public void onArrowCollide(IProjectile projectile)
	{
		// Placeholder for extended classes.
	}

	@Override
	public boolean onArrowShoot(ILivingEntity entity, IEntity arrow)
	{
		// Placeholder for extended classes.
		return true;
	}
}
