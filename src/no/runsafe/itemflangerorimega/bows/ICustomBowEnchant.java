package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;

public interface ICustomBowEnchant
{
	String getEnchantText();
	String getSimpleName();
	void onArrowCollideBlock(RunsafeProjectile projectile, IBlock block);
	void onArrowCollideEntity(RunsafeProjectile projectile, RunsafeEntity entity);
	void onArrowCollide(RunsafeProjectile projectile);
	boolean onArrowShoot(ILivingEntity shooter, IEntity arrow);
}
