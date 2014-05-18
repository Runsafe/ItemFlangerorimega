package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public interface ICustomToolEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public boolean onBlockBreak(IBlock block);
	public boolean onUse(IPlayer player, RunsafeMeta item, IBlock rightClicked);
}
