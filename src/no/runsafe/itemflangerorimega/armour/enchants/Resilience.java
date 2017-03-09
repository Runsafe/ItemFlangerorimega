package no.runsafe.itemflangerorimega.armour.enchants;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchant;

import java.util.Random;

public class Resilience extends CustomArmourEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Resilience I";
	}

	@Override
	public String getSimpleName()
	{
		return "resilience";
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeMeta item, RunsafeEntityDamageByEntityEvent event)
	{
		if (random.nextFloat() < 0.8F)
			item.setDurability((short) (item.getDurability() - 5));

		boolean wasPlayerAttacking = false;
		RunsafeEntity attacker = event.getDamageActor();
		if (attacker instanceof IPlayer)
		{
			wasPlayerAttacking = true;
		}
		else if (attacker instanceof RunsafeProjectile)
		{
			IPlayer shooter = ((RunsafeProjectile) attacker).getShootingPlayer();
			if (shooter != null)
				wasPlayerAttacking = true;
		}

		if (wasPlayerAttacking)
		{
			double damage = event.getDamage();
			event.setDamage(damage - (damage / 3));
		}
	}

	private final Random random = new Random();
}