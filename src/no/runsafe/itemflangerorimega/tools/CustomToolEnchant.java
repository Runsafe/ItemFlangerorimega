package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public abstract class CustomToolEnchant implements  ICustomToolEnchant
{
	@Override
	public boolean onBlockBreak(IPlayer player, IBlock block)
	{
		// Replace me!
		return false;
	}

	@Override
	public boolean onUse(IPlayer player, RunsafeMeta item, IBlock rightClicked)
	{
		// Replace me!
		return false;
	}
}
