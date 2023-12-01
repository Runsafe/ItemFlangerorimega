package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.projectiles.IProjectile;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.entity.IEntityShootBowEvent;
import no.runsafe.framework.api.event.entity.IProjectileHitEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.ProjectileEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityShootBowEvent;
import no.runsafe.framework.minecraft.event.entity.RunsafeProjectileHitEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.itemflangerorimega.Plugin;
import no.runsafe.worldguardbridge.IRegionControl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomBowEnchantHandler implements IProjectileHitEvent, IEntityShootBowEvent, IEntityDamageByEntityEvent
{
	public CustomBowEnchantHandler(ICustomBowEnchant[] enchants, IRegionControl regionControl)
	{
		this.regionControl = regionControl;
		this.enchants = Arrays.asList(enchants);
		for (ICustomBowEnchant enchant : this.enchants)
			this.enchantMap.put(enchant.getSimpleName(), enchant);
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		if (!(event.getDamageActor() instanceof IProjectile))
			return;

		IProjectile projectile = (IProjectile) event.getDamageActor();
		if (projectile.getEntityType() != ProjectileEntity.Arrow || !this.isTrackedArrow(projectile))
			return;

		List<ICustomBowEnchant> arrowEnchants = this.trackedArrows.get(projectile.getEntityId());
		for (ICustomBowEnchant enchant : arrowEnchants)
			enchant.onArrowCollideEntity(projectile, event.getEntity());
	}

	@Override
	public void OnProjectileHit(RunsafeProjectileHitEvent event)
	{
		IProjectile projectile = event.getProjectile();
		if (!this.isTrackedArrow(projectile))
			return;

		IEntity shooter = projectile.getShootingEntity();
		if (shooter != null)
		{
			Plugin.debugger.debugFine("Arrow collision. shooter entity type: " + shooter.getEntityType());
			Plugin.debugger.debugFine("Arrow collision. shooter entity UUID: " + shooter.getUniqueId());

			if (shooter instanceof IPlayer && !regionControl.playerCanBuildHere((IPlayer) shooter, projectile.getLocation()))
			{
				this.unTrackProjectile(projectile);
				return;
			}
		}

		List<ICustomBowEnchant> arrowEnchants = this.trackedArrows.get(projectile.getEntityId());
		for (ICustomBowEnchant enchant : arrowEnchants)
		{
			enchant.onArrowCollide(projectile);
			if (projectile.isOnGround())
				enchant.onArrowCollideBlock(projectile, projectile.getImpaledBlock());
		}
		this.unTrackProjectile(projectile);
	}

	private void unTrackProjectile(IProjectile projectile)
	{
		this.trackedArrows.remove(projectile.getEntityId());
	}

	private boolean isTrackedArrow(IProjectile projectile)
	{
		return this.trackedArrows.containsKey(projectile.getEntityId());
	}

	private boolean hasEnchant(RunsafeMeta item, ICustomBowEnchant enchant)
	{
		List<String> lore = item.getLore();
		return lore != null && lore.contains("§r§7" + enchant.getEnchantText());
	}

	public void enchantBow(RunsafeMeta item, ICustomBowEnchant enchant)
	{
		if (!this.hasEnchant(item, enchant))
			item.addLore("§r§7" + enchant.getEnchantText());
	}

	public ICustomBowEnchant getEnchant(String name)
	{
		return this.enchantMap.getOrDefault(name, null);
	}

	public Set<String> getAvailableEnchants()
	{
		return this.enchantMap.keySet();
	}

	@Override
	public void OnEntityShootBowEvent(RunsafeEntityShootBowEvent event)
	{
		int entityID = event.getProjectile().getEntityId();

		if (trackedArrows.containsKey(entityID))
			return;

		IEntity shootingEntity = event.getEntity();

		RunsafeMeta item = null;
		if (shootingEntity instanceof IPlayer)
		{
			if (!regionControl.playerCanBuildHere((IPlayer) shootingEntity, shootingEntity.getLocation()))
				return;

			item = ((IPlayer) shootingEntity).getItemInMainHand();
		}
		else if (shootingEntity instanceof ILivingEntity)
			item = ((ILivingEntity) shootingEntity).getEquipment().getItemInHand();

		if (item == null || !item.is(Item.Combat.Bow))
			return;

		List<ICustomBowEnchant> bowEnchants = new ArrayList<>();
		for (ICustomBowEnchant enchant : enchants)
		{
			if (!hasEnchant(item, enchant))
				continue;

			boolean allowShoot = enchant.onArrowShoot((ILivingEntity) shootingEntity, event.getProjectile());

			if (allowShoot)
				bowEnchants.add(enchant);
			else
				event.getProjectile().remove();
		}

		if (!bowEnchants.isEmpty())
			trackedArrows.put(entityID, bowEnchants);
	}

	private final ConcurrentHashMap<Integer, List<ICustomBowEnchant>> trackedArrows = new ConcurrentHashMap<>();
	private final HashMap<String, ICustomBowEnchant> enchantMap = new HashMap<>();
	private final List<ICustomBowEnchant> enchants;
	private final IRegionControl regionControl;
}
