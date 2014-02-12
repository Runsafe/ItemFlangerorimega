package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.itemflangerorimega.bows.ICustomBowEnchant;

public abstract class CustomBowEnchant implements ICustomBowEnchant
{
	@Override
	public void onArrowCollideBlock(RunsafeProjectile projectile, IBlock block)
	{
		// Placeholder for extended classes.
	}

	@Override
	public void onArrowCollideEntity(RunsafeProjectile projectile, RunsafeEntity entity)
	{
		// Placeholder for extended classes.
	}

	@Override
	public void onArrowCollide(RunsafeProjectile projectile)
	{
		// Placeholder for extended classes.
	}
}
