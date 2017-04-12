package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.entity.ProjectileEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchant;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkTest extends CustomBowEnchant
{
	@Override
	public boolean onArrowShoot(ILivingEntity entity, IEntity arrow)
	{
		ILocation location = entity.getLocation();

		if (location != null)
		{
			IEntity fireworkEntity = location.getWorld().spawnCreature(location, ProjectileEntity.Firework.getName());
			Firework firework = (Firework) ((RunsafeEntity) fireworkEntity).getRaw();

			FireworkMeta meta = firework.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.OLIVE).withFade(Color.RED).flicker(true).trail(true).build();

			meta.addEffect(effect);
			meta.setPower(3);
			firework.setFireworkMeta(meta);

			arrow.setPassenger(fireworkEntity);
		}

		return true;
	}

	@Override
	public String getEnchantText()
	{
		return "FireworkTest I";
	}

	@Override
	public String getSimpleName()
	{
		return "firework_test_1";
	}
}
