package no.runsafe.itemflangerorimega.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;

public class BoxItem extends PlayerCommand
{
	public BoxItem()
	{
		super("boxitem", "Spawn a derp box", "runsafe.items.box");
	}
	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		executor.getLocation().getBlock().set(Item.Redstone.Piston.Box);
		return null;
	}
}
