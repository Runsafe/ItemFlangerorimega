package no.runsafe.itemflangerorimega.weapons;

import no.runsafe.framework.api.entity.ILivingEntity;

public interface ICustomWeaponEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public boolean onDamageEntity(ILivingEntity entity);
}
