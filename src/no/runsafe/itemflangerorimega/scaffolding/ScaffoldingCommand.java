package no.runsafe.itemflangerorimega.scaffolding;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class ScaffoldingCommand extends PlayerCommand
{
	public ScaffoldingCommand()
	{
		super("scaffolding", "Spawn a stack of scaffolding", "runsafe.items.scaffolding");
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		executor.give(ScaffoldingHandler.getItem(64));
		return null;
	}
}
