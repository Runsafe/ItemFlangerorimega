package no.runsafe.itemflangerorimega.armour.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchant;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class DergonThunder extends CustomArmourEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Dergon Thunder";
	}

	@Override
	public String getSimpleName()
	{
		return "dergon_thunder";
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeEntityDamageByEntityEvent event)
	{
		if (random.nextInt(100) <= 80)
		{
			ILivingEntity entity = (ILivingEntity) event.getEntity();
			List<IEntity> entities = entity.getNearbyEntities(10, 10, 10);

			ILocation entityLocation = entity.getLocation();
			for (IEntity closeEntity : entities)
			{
				if (closeEntity instanceof ILivingEntity)
				{
					ILivingEntity livingCloseEntity = (ILivingEntity) closeEntity;
					livingCloseEntity.strikeWithLightning(true);
					livingCloseEntity.damage(2D);

					if (entityLocation != null)
					{
						ILocation closeEntityLocation = livingCloseEntity.getLocation();
						if (closeEntityLocation != null)
						{
							Vector velocity = entityLocation.toVector().subtract(closeEntityLocation.toVector()).normalize();
							livingCloseEntity.setVelocity(velocity.multiply(20D));
						}
					}
				}
			}

			entity.explode(0F, false, false); // Boom!
			entity.setHealth(entity.getMaxHealth()); // Heal to full.
		}
	}

	private final Random random = new Random();
}
