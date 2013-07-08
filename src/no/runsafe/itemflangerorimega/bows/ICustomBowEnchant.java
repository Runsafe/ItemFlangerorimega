package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.minecraft.block.RunsafeBlock;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;

public interface ICustomBowEnchant
{
	public String getEnchantText();
	public void onArrowCollideBlock(RunsafeProjectile projectile, RunsafeBlock block);
	public void onArrowCollideEntity(RunsafeProjectile projectile, RunsafeEntity entity);
}
