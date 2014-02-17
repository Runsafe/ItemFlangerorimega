package no.runsafe.itemflangerorimega.bows.enchants;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.minecraft.Firework;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchant;

public class FireworkTest extends CustomBowEnchant
{
	@Override
	public boolean onArrowShoot(ILivingEntity entity)
	{
		Firework.Random().Fire(entity.getLocation());
		return false;
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
