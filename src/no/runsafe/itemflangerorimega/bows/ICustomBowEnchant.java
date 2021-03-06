package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;

public interface ICustomBowEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public void onArrowCollideBlock(RunsafeProjectile projectile, IBlock block);
	public void onArrowCollideEntity(RunsafeProjectile projectile, RunsafeEntity entity);
	public void onArrowCollide(RunsafeProjectile projectile);
	public boolean onArrowShoot(ILivingEntity shooter, IEntity arrow);
}
