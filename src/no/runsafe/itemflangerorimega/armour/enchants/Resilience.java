package no.runsafe.itemflangerorimega.armour.enchants;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchant;

public class Resilience extends CustomArmourEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Resilience";
	}

	@Override
	public String getSimpleName()
	{
		return "resilience";
	}

	@Override
	public void entityDamageByEntityEvent(RunsafeEntityDamageByEntityEvent event)
	{
		boolean wasPlayerAttacking = false;
		RunsafeEntity attacker = event.getDamageActor();
		if (attacker instanceof IPlayer)
		{
			wasPlayerAttacking = true;
		}
		else if (attacker instanceof RunsafeProjectile)
		{
			IPlayer shooter = ((RunsafeProjectile) attacker).getShooterPlayer();
			if (shooter != null)
				wasPlayerAttacking = true;
		}

		if (wasPlayerAttacking)
		{
			double damage = event.getDamage();
			event.setDamage(damage - (damage / 10));
		}
	}
}