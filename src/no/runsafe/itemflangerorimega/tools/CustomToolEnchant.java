package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public abstract class CustomToolEnchant implements  ICustomToolEnchant
{
	@Override
	public void onBlockBreak(IBlock block)
	{
		// Replace me!
	}

	@Override
	public boolean onUse(IPlayer player, RunsafeMeta item, IBlock rightClicked)
	{
		// Replace me!
		return false;
	}
}
