package no.runsafe.itemflangerorimega.tools.enchants;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.tools.CustomToolEnchant;

public class Superheating extends CustomToolEnchant
{
	@Override
	public String getEnchantText()
	{
		return "Superheating I";
	}

	@Override
	public String getSimpleName()
	{
		return "superheating";
	}

	@Override
	public boolean onBlockBreak(IPlayer player, IBlock block)
	{
		if (!block.is(Item.Ore.Iron) && !block.is(Item.Ore.Gold))
			return false;

		block.set(Item.Unavailable.Air);
		RunsafeMeta item = block.is(Item.Ore.Iron) ? Item.Materials.IronIngot.getItem() : Item.Materials.GoldIngot.getItem();
		item.setAmount(1);

		block.getWorld().dropItem(block.getLocation(), item);
		return true;
	}
}
