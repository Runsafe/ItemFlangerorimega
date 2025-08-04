package no.runsafe.itemflangerorimega;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

import java.util.ArrayList;
import java.util.List;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		worlds.clear();
		worlds.addAll(config.getConfigValueAsList("blacklistedWorlds"));
		kaosFireDurabilityLoss = config.getConfigValueAsInt("kaosFireDurabilityLoss");
		kaosFireRadius = config.getConfigValueAsInt("kaosFireRadius");
	}

	public static boolean isBlacklistedWorld(IWorld world)
	{
		if (world == null)
			return false;

		return worlds.contains(world.getName());
	}

	private static final List<String> worlds = new ArrayList<>(0);
	public static int kaosFireDurabilityLoss;
	public static int kaosFireRadius;
}
