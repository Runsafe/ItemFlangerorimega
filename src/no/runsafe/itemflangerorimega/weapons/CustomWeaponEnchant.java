package no.runsafe.itemflangerorimega.weapons;

import no.runsafe.framework.api.entity.ILivingEntity;

public abstract class CustomWeaponEnchant implements ICustomWeaponEnchant
{
	@Override
	public boolean onDamageEntity(ILivingEntity entity)
	{
		return true;
	}
}
