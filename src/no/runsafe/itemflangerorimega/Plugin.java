package no.runsafe.itemflangerorimega;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Events;
import no.runsafe.itemflangerorimega.armour.CustomArmourEnchantHandler;
import no.runsafe.itemflangerorimega.armour.enchants.DergonThunder;
import no.runsafe.itemflangerorimega.armour.enchants.PathOfFrost;
import no.runsafe.itemflangerorimega.armour.enchants.Resilience;
import no.runsafe.itemflangerorimega.bows.CustomBowEnchantHandler;
import no.runsafe.itemflangerorimega.bows.enchants.DispenseItem;
import no.runsafe.itemflangerorimega.bows.enchants.ExplosiveCharge;
import no.runsafe.itemflangerorimega.bows.enchants.FireworkTest;
import no.runsafe.itemflangerorimega.bows.enchants.KaosFire;
import no.runsafe.itemflangerorimega.commands.EnchantArmour;
import no.runsafe.itemflangerorimega.commands.EnchantBow;
import no.runsafe.itemflangerorimega.commands.EnchantTool;
import no.runsafe.itemflangerorimega.commands.EnchantWeapon;
import no.runsafe.itemflangerorimega.scaffolding.ScaffoldingCommand;
import no.runsafe.itemflangerorimega.scaffolding.ScaffoldingHandler;
import no.runsafe.itemflangerorimega.tools.CustomToolEnchantHandler;
import no.runsafe.itemflangerorimega.tools.enchants.Lumberjacking;
import no.runsafe.itemflangerorimega.tools.enchants.MoltenSoaking;
import no.runsafe.itemflangerorimega.tools.enchants.Superheating;
import no.runsafe.itemflangerorimega.weapons.CustomWeaponEnchantHandler;
import no.runsafe.itemflangerorimega.weapons.enchants.SlappingRank1;
import no.runsafe.itemflangerorimega.weapons.enchants.SlappingRank2;
import no.runsafe.itemflangerorimega.weapons.enchants.SlappingRank3;
import no.runsafe.itemflangerorimega.weapons.enchants.SlappingRank4;
import no.runsafe.itemflangerorimega.weapons.enchants.SlappingRank5;

public class Plugin extends RunsafeConfigurablePlugin
{
	public static IDebug debugger;

	@Override
	protected void pluginSetup()
	{
		debugger = getComponent(IDebug.class);

		// Framework features
		addComponent(Events.class);
		addComponent(Commands.class);

		// Bow Enchants
		addComponent(ExplosiveCharge.class);
		addComponent(FireworkTest.class);
		addComponent(DispenseItem.class);
		addComponent(KaosFire.class);

		// Armour enchants
		addComponent(Resilience.class);
		addComponent(DergonThunder.class);
		addComponent(PathOfFrost.class);

		// Tool Enchants
		//addComponent(SaplingGarner.class);
		addComponent(MoltenSoaking.class);
		addComponent(Superheating.class);
		addComponent(Lumberjacking.class);

		addComponent(SlappingRank1.class);
		addComponent(SlappingRank2.class);
		addComponent(SlappingRank3.class);
		addComponent(SlappingRank4.class);
		addComponent(SlappingRank5.class);

		// Handlers
		addComponent(CustomBowEnchantHandler.class);
		addComponent(CustomArmourEnchantHandler.class);
		addComponent(CustomToolEnchantHandler.class);
		addComponent(CustomWeaponEnchantHandler.class);

		Command customEnchantCommand = new Command("customenchant", "Enchant an item with a custom enchant", null);
		addComponent(customEnchantCommand);

		customEnchantCommand.addSubCommand(getInstance(EnchantBow.class));
		customEnchantCommand.addSubCommand(getInstance(EnchantArmour.class));
		customEnchantCommand.addSubCommand(getInstance(EnchantTool.class));
		customEnchantCommand.addSubCommand(getInstance(EnchantWeapon.class));

		addComponent(ScaffoldingHandler.class);
		addComponent(ScaffoldingCommand.class);

		// Config
		addComponent(Config.class);
	}
}
