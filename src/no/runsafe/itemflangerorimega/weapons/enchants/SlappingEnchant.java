package no.runsafe.itemflangerorimega.weapons.enchants;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.itemflangerorimega.weapons.CustomWeaponEnchant;
import org.bukkit.util.Vector;

public abstract class SlappingEnchant extends CustomWeaponEnchant
{
	public SlappingEnchant(double power, String text, String name)
	{
		this.power = power;
		this.text = text;
		this.name = name;
	}

	@Override
	public String getEnchantText()
	{
		return text;
	}

	@Override
	public String getSimpleName()
	{
		return name;
	}

	@Override
	public boolean onDamageEntity(ILivingEntity entity)
	{
		entity.setVelocity(new Vector(0, power, 0));
		return false;
	}

	private final double power;
	private final String text;
	private final String name;
}

