package no.runsafe.itemflangerorimega;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchantHandler;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void PluginSetup()
	{
		this.addComponent(CustomBowEnchantHandler.class);
	}
}
