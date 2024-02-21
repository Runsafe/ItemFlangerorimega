package no.runsafe.itemflangerorimega.armour.enchants;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchant;

public class PathOfFrost extends CustomArmourEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Path of Frost I";
	}

	@Override
	public String getSimpleName()
	{
		return "path_of_frost";
	}

	@Override
	public void playerMove(IPlayer player, ILocation from, ILocation to)
	{
		if(player.cannotBuild()) // Don't let players freeze water where they don't have permission to build.
			return;

		checkBlock(to, 0, 0);
		checkBlock(to, 1, 0);
		checkBlock(to, -1, 0);
		checkBlock(to, 0, 1);
		checkBlock(to, 0, -1);
		checkBlock(to, -1, -1);
		checkBlock(to, 1, 1);
		checkBlock(to, 1, -1);
		checkBlock(to, -1, 1);
	}

	private void checkBlock(ILocation location, int offsetX, int offsetZ)
	{
		ILocation newLoc = location.clone();
		newLoc.offset(offsetX, 0, offsetZ);

		IBlock block = newLoc.getBlock();
		if (block.is(Item.Unavailable.StationaryWater))
			block.set(Item.BuildingBlock.Ice);
	}
}
