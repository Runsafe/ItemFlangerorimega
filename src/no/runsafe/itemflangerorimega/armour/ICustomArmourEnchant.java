package no.runsafe.itemflangerorimega.armour;

import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;

public interface ICustomArmourEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public void entityDamageEvent(RunsafeEntityDamageEvent event);
	public void entityDamageByEntityEvent(RunsafeEntityDamageByEntityEvent event);
}
