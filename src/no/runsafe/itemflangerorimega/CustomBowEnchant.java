package no.runsafe.itemflangerorimega;

import no.runsafe.framework.minecraft.block.RunsafeBlock;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.itemflangerorimega.bows.ICustomBowEnchant;

public abstract class CustomBowEnchant implements ICustomBowEnchant
{
	@Override
	public void onArrowCollideBlock(RunsafeProjectile projectile, RunsafeBlock block)
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
