package no.runsafe.itemflangerorimega.weapons.enchants;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.itemflangerorimega.weapons.CustomWeaponEnchant;
import org.bukkit.util.Vector;

public abstract class SlappingEnchant extends CustomWeaponEnchant
{
	public SlappingEnchant(double power)
	{
		this.power = power;
	}

	@Override
	public String getEnchantText()
	{
		return "Slapping I";
	}

	@Override
	public String getSimpleName()
	{
		return "slapping_1";
	}

	@Override
	public boolean onDamageEntity(ILivingEntity entity)
	{
		entity.setVelocity(new Vector(0, power, 0));
		return false;
	}

	private final double power;
}

