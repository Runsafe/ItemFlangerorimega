package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.entity.IEntityShootBowEvent;
import no.runsafe.framework.api.event.entity.IProjectileHitEvent;
import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.entity.ProjectileEntity;
import no.runsafe.framework.minecraft.entity.RunsafeProjectile;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityShootBowEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeProjectileHitEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomBowEnchantHandler implements IEntityDamageByEntityEvent, IProjectileHitEvent, IEntityShootBowEvent
{
	public CustomBowEnchantHandler(ICustomBowEnchant[] enchants)
	{
		this.enchants = Arrays.asList(enchants);
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		if (event.getDamageActor() instanceof RunsafeProjectile)
		{
			RunsafeProjectile projectile = (RunsafeProjectile) event.getDamageActor();
			if (projectile.getEntityType() == ProjectileEntity.Arrow)
			{
				// We have an arrow.
			}
		}
	}

	@Override
	public void OnProjectileHit(RunsafeProjectileHitEvent event)
	{
		if (event.getProjectile().getNMS() instanceof MagicalArrow)
		{
			MagicalArrow arrow = (MagicalArrow) event.getProjectile().getNMS();
			RunsafeServer.Instance.getLogger().info("MAGICAL ARROW HIT THE FLOOR!");
		}
	}

	private boolean hasEnchant(RunsafeMeta item, ICustomBowEnchant enchant)
	{
		List<String> lore = item.getLore();
		return lore != null && lore.contains(enchant.getEnchantText());
	}

	@Override
	public void OnEntityShootBowEvent(RunsafeEntityShootBowEvent event)
	{
		if (event.getEntity() instanceof RunsafePlayer)
		{
			RunsafePlayer player = (RunsafePlayer) event.getEntity();
			event.setProjectile(ObjectWrapper.convert(new MagicalArrow(player).getBukkitEntity()));
		}
	}

	private List<ICustomBowEnchant> enchants = new ArrayList<ICustomBowEnchant>();
}
