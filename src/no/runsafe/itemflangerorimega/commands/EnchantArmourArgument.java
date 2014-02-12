package no.runsafe.itemflangerorimega.commands;

import com.google.common.collect.ImmutableList;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.ITabComplete;
import no.runsafe.framework.api.command.argument.IValueExpander;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchantHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EnchantArmourArgument extends RequiredArgument implements ITabComplete, IValueExpander
{
	public EnchantArmourArgument(CustomArmourEnchantHandler handler)
	{
		super("enchant");
		this.handler = handler;
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial)
	{
		Set<String> options = handler.getEnchants();
		if (partial == null || partial.isEmpty())
			return ImmutableList.copyOf(options);

		String match = partial.toLowerCase();
		List<String> alternatives = new ArrayList<String>(options.size());
		for (String option : options)
			if (option.toLowerCase().startsWith(match))
				alternatives.add(option);
		return alternatives;
	}

	@Nullable
	@Override
	public String expand(ICommandExecutor context, @Nullable String value)
	{
		List<String> options = getAlternatives((IPlayer) context, value);
		return options.isEmpty() || options.size() > 1 ? null : options.get(0);
	}

	private CustomArmourEnchantHandler handler;
}