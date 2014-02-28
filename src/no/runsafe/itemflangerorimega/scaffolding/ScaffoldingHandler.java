package no.runsafe.itemflangerorimega.scaffolding;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorldEffect;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.IBlockBreak;
import no.runsafe.framework.api.event.block.IBlockPlace;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.WorldBlockEffect;
import no.runsafe.framework.minecraft.WorldBlockEffectType;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.worldguardbridge.IRegionControl;

public class ScaffoldingHandler implements IBlockPlace, IBlockBreak
{
	public ScaffoldingHandler(IScheduler scheduler, IRegionControl control)
	{
		this.scheduler = scheduler;
		this.control = control;
		effect = new WorldBlockEffect(WorldBlockEffectType.BLOCK_DUST, Item.Decoration.Fence);
	}

	@Override
	public boolean OnBlockPlace(IPlayer player, final IBlock block)
	{
		RunsafeMeta item = player.getItemInHand();
		if (isItem(item) && control.playerCanBuildHere(player, block.getLocation()))
		{
			player.getInventory().removeExact(item, 1);
			player.updateInventory();
			scheduler.startSyncTask(new Runnable()
			{
				@Override
				public void run()
				{
					block.getLocation().getBlock().set(Item.Redstone.Piston.Box);
				}
			}, 1L);
		}
		return true;
	}

	@Override
	public boolean OnBlockBreak(IPlayer player, IBlock block)
	{
		if (block.is(Item.Redstone.Piston.Box))
		{
			handleScaffoldingBreak(block);
			return false;
		}
		return true;
	}

	private void handleScaffoldingBreak(IBlock block)
	{
		ILocation location = block.getLocation();
		block.set(Item.Unavailable.Air);
		location.getWorld().dropItem(location, getItem(1));
		location.playEffect(effect, 0.3F, 50, 50);

		check(location, 1, 0, 0);
		check(location, -1, 0, 0);
		check(location, 0, 0, 1);
		check(location, 0, 0, -1);
		check(location, 0, 1, 0);
		check(location, 0, -1, 0);
	}

	private void check(ILocation location, double x, double y, double z)
	{
		location = location.clone();
		location.offset(x, y, z);
		IBlock block = location.getBlock();

		if (block.is(Item.Redstone.Piston.Box))
			handleScaffoldingBreak(block);
	}

	private boolean isItem(RunsafeMeta item)
	{
		if (item == null || !item.is(Item.Decoration.Fence))
			return false;

		String itemName = item.getDisplayName();
		return itemName != null && itemName.equals("§rScaffolding");
	}

	public static RunsafeMeta getItem(int amount)
	{
		if (item == null)
		{
			item = Item.Decoration.Fence.getItem();
			item.setDisplayName("§rScaffolding");
		}
		item.setAmount(amount);
		return item;
	}

	private static RunsafeMeta item;
	private final IWorldEffect effect;
	private final IScheduler scheduler;
	private final IRegionControl control;
}
