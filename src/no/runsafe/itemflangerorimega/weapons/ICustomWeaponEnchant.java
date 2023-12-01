package no.runsafe.itemflangerorimega.weapons;

import no.runsafe.framework.api.entity.ILivingEntity;

public interface ICustomWeaponEnchant
{
	String getEnchantText();
	String getSimpleName();
	boolean onDamageEntity(ILivingEntity entity);
}
