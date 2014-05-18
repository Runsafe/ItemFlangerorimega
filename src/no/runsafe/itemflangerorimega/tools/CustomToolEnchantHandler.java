package no.runsafe.itemflangerorimega.tools;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.IBlockBreak;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.*;

public class CustomToolEnchantHandler implements IBlockBreak, IPlayerRightClick
{
	public CustomToolEnchantHandler(ICustomToolEnchant[] enchants)
	{
		this.enchants = Arrays.asList(enchants);
		for (ICustomToolEnchant enchant : this.enchants)
			this.enchantMap.put(enchant.getSimpleName(), enchant);
	}

	@Override
	public boolean OnBlockBreak(IPlayer player, IBlock block)
	{
		RunsafeMeta item = player.getItemInHand();

		boolean shouldCancel = false;
		if (item != null)
		{
			for (ICustomToolEnchant enchant : enchants)
			{
				if (hasEnchant(item, enchant))
				{
					if (enchant.onBlockBreak(block))
						shouldCancel = true;

				}
			}
		}
		return !shouldCancel;
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		boolean shouldCancel = false;
		if (usingItem != null)
		{
			for (ICustomToolEnchant enchant : enchants)
			{
				if (hasEnchant(usingItem, enchant))
				{
					if (enchant.onUse(player, usingItem, targetBlock))
						shouldCancel = true;
				}
			}
		}
		return !shouldCancel;
	}

	private boolean hasEnchant(RunsafeMeta item, ICustomToolEnchant enchant)
	{
		List<String> lore = item.getLore();
		return lore != null && lore.contains("§r§7" + enchant.getEnchantText());
	}

	public void enchantTool(RunsafeMeta item, ICustomToolEnchant enchant)
	{
		if (!this.hasEnchant(item, enchant))
			item.addLore("§r§7" + enchant.getEnchantText());
	}

	public ICustomToolEnchant getEnchant(String name)
	{
		return this.enchantMap.containsKey(name) ? this.enchantMap.get(name) : null;
	}

	public Set<String> getAvailableEnchants()
	{
		return this.enchantMap.keySet();
	}

	private HashMap<String, ICustomToolEnchant> enchantMap = new HashMap<String, ICustomToolEnchant>();
	private List<ICustomToolEnchant> enchants = new ArrayList<ICustomToolEnchant>();
}
