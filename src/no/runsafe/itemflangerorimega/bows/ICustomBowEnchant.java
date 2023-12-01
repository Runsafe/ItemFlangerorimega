package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.projectiles.IProjectile;

public interface ICustomBowEnchant
{
	String getEnchantText();
	String getSimpleName();
	void onArrowCollideBlock(IProjectile projectile, IBlock block);
	void onArrowCollideEntity(IProjectile projectile, IEntity entity);
	void onArrowCollide(IProjectile projectile);
	boolean onArrowShoot(ILivingEntity shooter, IEntity arrow);
}
