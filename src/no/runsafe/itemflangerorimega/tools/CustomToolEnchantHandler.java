package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.IBlockBreak;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.Config;
import no.runsafe.worldguardbridge.IRegionControl;

import java.util.*;

public class CustomToolEnchantHandler implements IBlockBreak, IPlayerRightClick
{
	public CustomToolEnchantHandler(ICustomToolEnchant[] enchants, IRegionControl regionControl)
	{
		this.enchants = Arrays.asList(enchants);
		this.regionControl = regionControl;
		for (ICustomToolEnchant enchant : this.enchants)
			this.enchantMap.put(enchant.getSimpleName(), enchant);
	}

	@Override
	public boolean OnBlockBreak(IPlayer player, IBlock block)
	{
		if (Config.isBlacklistedWorld(player.getWorld()))
			return true;

		if (regionControl.playerCannotBuildHere(player, block.getLocation()))
			return true;

		RunsafeMeta item = player.getItemInMainHand();

		if (item == null)
			return true;

		boolean shouldCancel = false;
		for (ICustomToolEnchant enchant : enchants)
		{
			if (notAlreadyEnchanted(item, enchant))
				continue;

			if (enchant.onBlockBreak(player, block))
				shouldCancel = true;
		}

		return !shouldCancel;
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		if (usingItem == null)
			return true;

		if (Config.isBlacklistedWorld(player.getWorld()))
			return true;

		if (targetBlock != null && regionControl.playerCannotBuildHere(player, targetBlock.getLocation()))
			return true;

		if (regionControl.playerCannotBuildHere(player, player.getLocation()))
			return true;

		boolean shouldCancel = false;
		for (ICustomToolEnchant enchant : enchants)
		{
			if (notAlreadyEnchanted(usingItem, enchant))
				continue;

			if (enchant.onUse(player, usingItem, targetBlock))
				shouldCancel = true;
		}
		return !shouldCancel;
	}

	private boolean notAlreadyEnchanted(RunsafeMeta item, ICustomToolEnchant enchant)
	{
		List<String> lore = item.getLore();
		return lore == null || !lore.contains("§r§7" + enchant.getEnchantText());
	}

	public void enchantTool(RunsafeMeta item, ICustomToolEnchant enchant)
	{
		if (this.notAlreadyEnchanted(item, enchant))
			item.addLore("§r§7" + enchant.getEnchantText());
	}

	public ICustomToolEnchant getEnchant(String name)
	{
		return this.enchantMap.getOrDefault(name, null);
	}

	public Set<String> getAvailableEnchants()
	{
		return this.enchantMap.keySet();
	}

	private final HashMap<String, ICustomToolEnchant> enchantMap = new HashMap<>();
	private final List<ICustomToolEnchant> enchants;
	private final IRegionControl regionControl;
}
