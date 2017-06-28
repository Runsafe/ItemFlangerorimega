package no.runsafe.itemflangerorimega.scaffolding;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorldEffect;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.IBlockBreak;
import no.runsafe.framework.api.event.block.IBlockPlace;
import no.runsafe.framework.api.event.block.IBlockRedstone;
import no.runsafe.framework.api.event.entity.IEntityExplode;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.*;
import no.runsafe.framework.minecraft.event.block.RunsafeBlockRedstoneEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityExplodeEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.worldguardbridge.IRegionControl;

public class ScaffoldingHandler implements IBlockPlace, IBlockBreak, IEntityExplode, IBlockRedstone
{
	public ScaffoldingHandler(IScheduler scheduler, IRegionControl control)
	{
		this.scheduler = scheduler;
		this.control = control;
		effect = new WorldBlockEffect(WorldBlockEffectType.BLOCK_DUST, Item.Decoration.Fence.Fence);
	}

	@Override
	public boolean OnBlockPlace(IPlayer player, final IBlock block)
	{
		RunsafeMeta item = player.getItemInHand();
		if (isItem(item) && control.playerCanBuildHere(player, block.getLocation()))
		{
			player.updateInventory();
			final ILocation blockLocation = block.getLocation();

			if (block.getRedstonePower() > 0)
			{
				blockLocation.playSound(Sound.Environment.Fizz, 1, 2);
				Effect.Extinguish.Play(blockLocation);
			}
			else
				scheduler.startSyncTask(() -> blockLocation.getBlock().set(Item.Unavailable.DoubleSlab.Plank), 1L);
		}
		return true;
	}

	@Override
	public boolean OnBlockBreak(IPlayer player, IBlock block)
	{
		if (block.is(Item.Unavailable.DoubleSlab.Plank))
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

		if (block.is(Item.Unavailable.DoubleSlab.Plank))
			handleScaffoldingBreak(block);
	}

	private boolean isItem(RunsafeMeta item)
	{
		if (item == null || !item.is(Item.Redstone.Device.NoteBlock))
			return false;

		String itemName = item.getDisplayName();
		return itemName != null && itemName.equals("§rCrate of Scaffolding");
	}

	public static RunsafeMeta getItem(int amount)
	{
		if (item == null)
		{
			item = Item.Redstone.Device.NoteBlock.getItem();
			item.setDisplayName("§rCrate of Scaffolding");
		}
		item.setAmount(amount);
		return item;
	}

	@Override
	public void OnEntityExplode(RunsafeEntityExplodeEvent event)
	{
		for (IBlock block : event.getBlockList())
		{
			if (block.is(Item.Unavailable.DoubleSlab.Plank))
			{
				event.setYield(0);
				return;
			}
		}
	}

	@Override
	public void OnBlockRedstoneEvent(RunsafeBlockRedstoneEvent event)
	{
		if (event.getBlock().is(Item.Unavailable.DoubleSlab.Plank))
			event.setNewCurrent(0);
	}

	private static RunsafeMeta item;
	private final IWorldEffect effect;
	private final IScheduler scheduler;
	private final IRegionControl control;
}
