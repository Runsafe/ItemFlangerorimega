package no.runsafe.itemflangerorimega.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.weapons.CustomWeaponEnchantHandler;

public class EnchantWeapon extends PlayerCommand
{
	public EnchantWeapon(CustomWeaponEnchantHandler handler)
	{
		super(
			"weapon", "Enchants a weapon using a magical custom enchant.", "runsafe.flangerorimega.enchant.weapons",
			new EnchantWeaponArgument(handler)
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String enchantType = parameters.getValue("enchant");
		RunsafeMeta item = executor.getItemInMainHand();
		if (item != null)
		{
			this.handler.enchantWeapon(item, this.handler.getEnchant(enchantType));
			return "&2Your weapon has been enchanted with wizardry.";
		}
		return "&cYou are not holding something.";
	}

	private final CustomWeaponEnchantHandler handler;
}
