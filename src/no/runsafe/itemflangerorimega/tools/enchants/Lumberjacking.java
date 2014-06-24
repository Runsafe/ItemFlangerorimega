package no.runsafe.itemflangerorimega.tools.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.itemflangerorimega.tools.CustomToolEnchant;

public class Lumberjacking extends CustomToolEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Lumberjacking I";
	}

	@Override
	public String getSimpleName()
	{
		return "lumberjack";
	}

	@Override
	public boolean onBlockBreak(IBlock block)
	{
		breakBlock(block);
		return true;
	}

	private void breakBlock(IBlock block)
	{
		block.breakNaturally();
		ILocation location = block.getLocation();

		check(location, 1, 0, 0);
		check(location, -1, 0, 0);
		check(location, 0, 0, 1);
		check(location, 0, 0, -1);
		check(location, 0, 1, 0);
		check(location, 0, -1, 0);
	}

	private void check(ILocation location, int offsetX, int offsetY, int offsetZ)
	{
		location = location.clone();
		location.offset(offsetX, offsetY, offsetZ);
		IBlock block = location.getBlock();

		if (block.is(Item.Decoration.Leaves.Any) || block.is(Item.BuildingBlock.Wood.Any))
			breakBlock(block);
	}
}
