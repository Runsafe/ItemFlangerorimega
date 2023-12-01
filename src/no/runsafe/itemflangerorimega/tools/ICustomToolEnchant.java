package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public interface ICustomToolEnchant
{
	String getEnchantText();
	String getSimpleName();
	boolean onBlockBreak(IPlayer player, IBlock block);
	boolean onUse(IPlayer player, RunsafeMeta item, IBlock rightClicked);
}
