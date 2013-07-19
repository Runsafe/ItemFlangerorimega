package no.runsafe.itemflangerorimega.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchantHandler;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Set;

public class EnchantBow extends PlayerCommand
{
	public EnchantBow(CustomBowEnchantHandler handler)
	{
		super("enchantbow", "Enchants a bow using a magical custom enchant.", "runsafe.flangerorimega.enchant.bows", "enchant");
		this.handler = handler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		String enchantType = parameters.get("enchant").toLowerCase();
		Set<String> enchantTypes = this.handler.getAvailableEnchants();
		if (enchantTypes.contains(enchantType))
		{
			RunsafeMeta item = executor.getItemInHand();
			if (item != null && item.is(Item.Combat.Bow))
			{
				this.handler.enchantBow(item, this.handler.getEnchant(enchantType));
				return "&2Your bow has been enchanted with wizardry.";
			}
			return "&cYou are not holding a bow.";
		}
		return "Valid enchant types: " + StringUtils.join(enchantTypes, ", ");
	}

	private CustomBowEnchantHandler handler;
}
