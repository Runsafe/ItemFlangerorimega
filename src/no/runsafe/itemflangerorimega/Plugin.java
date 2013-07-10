package no.runsafe.itemflangerorimega;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchantHandler;
import no.runsafe.itemflangerorimega.bows.enchants.ExplosiveCharge;
import no.runsafe.itemflangerorimega.commands.EnchantBow;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void PluginSetup()
	{
		// Bow Enchants
		this.addComponent(ExplosiveCharge.class);

		// Handlers
		this.addComponent(CustomBowEnchantHandler.class);

		// Commands
		this.addComponent(EnchantBow.class);
	}
}
