package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;

public interface ICustomToolEnchant
{
	public String getEnchantText();
	public String getSimpleName();
	public void onBlockBreak(IBlock block);
}
