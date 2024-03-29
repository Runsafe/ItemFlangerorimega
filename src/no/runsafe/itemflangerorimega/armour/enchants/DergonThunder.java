package no.runsafe.itemflangerorimega.armour.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorldEffect;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.WorldBlockEffect;
import no.runsafe.framework.minecraft.WorldBlockEffectType;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchant;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class DergonThunder extends CustomArmourEnchant
{
	public DergonThunder(IScheduler scheduler)
	{
		this.scheduler = scheduler;
		effect = new WorldBlockEffect(WorldBlockEffectType.BLOCK_DUST, Item.Unavailable.StationaryLava);
	}

	@Override
	public String getEnchantText()
	{
		return "Dergon Thunder I";
	}

	@Override
	public String getSimpleName()
	{
		return "dergon_thunder";
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeMeta item, final RunsafeEntityDamageByEntityEvent event)
	{
		if (random.nextInt(100) > 3)
			return;

		scheduler.startSyncTask(() ->
		{
			if (event.isCancelled())
				return;

			ILivingEntity entity = (ILivingEntity) event.getEntity();
			List<IEntity> entities = entity.getNearbyEntities(10, 10, 10);

			ILocation entityLocation = entity.getLocation();
			if (entityLocation != null)
				entityLocation.playEffect(effect, 0.3F, 100, 50);

			for (IEntity closeEntity : entities)
			{
				if (!(closeEntity instanceof ILivingEntity))
					continue;

				ILivingEntity livingCloseEntity = (ILivingEntity) closeEntity;
				livingCloseEntity.strikeWithLightning(true);
				livingCloseEntity.damage(2D);
				livingCloseEntity.setFireTicks(20 * 5); // Set them on fire for 5 seconds.

				if (entityLocation == null)
					continue;

				ILocation closeEntityLocation = livingCloseEntity.getLocation();
				if (closeEntityLocation == null)
					continue;

				Vector velocity = entityLocation.toVector().add(closeEntityLocation.toVector()).normalize();
				livingCloseEntity.setVelocity(velocity.multiply(6D));
			}
			entity.setHealth(entity.getMaxHealth()); // Heal to full.
		}, 10L);
	}

	private final Random random = new Random();
	private final IWorldEffect effect;
	private final IScheduler scheduler;
}
