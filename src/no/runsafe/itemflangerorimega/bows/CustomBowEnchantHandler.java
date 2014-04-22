package no.runsafe.itemflangerorimega.bows;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.event.entity.IEntityShootBowEvent;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityShootBowEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.nms.EntityRegister;

import java.util.*;

public class CustomBowEnchantHandler implements IEntityShootBowEvent, IServerReady
{
	public CustomBowEnchantHandler(ICustomBowEnchant[] enchants)
	{
		this.enchants = Arrays.asList(enchants);
		for (ICustomBowEnchant enchant : this.enchants)
			this.enchantMap.put(enchant.getSimpleName(), enchant);
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
		return this.enchantMap.containsKey(name) ? this.enchantMap.get(name) : null;
	}

	public Set<String> getAvailableEnchants()
	{
		return this.enchantMap.keySet();
	}

	@Override
	public void OnEntityShootBowEvent(RunsafeEntityShootBowEvent event)
	{
		RunsafeMeta item = null;
		RunsafeEntity shootingEntity = event.getEntity();

		if (shootingEntity instanceof ILivingEntity)
			item = ((ILivingEntity) shootingEntity).getEquipment().getItemInHand();

		if (item != null && item.is(Item.Combat.Bow))
		{
			List<ICustomBowEnchant> bowEnchants = new ArrayList<ICustomBowEnchant>();
			for (ICustomBowEnchant enchant : enchants)
			{
				if (hasEnchant(item, enchant))
				{
					CustomArrow arrow = (CustomArrow) ObjectUnwrapper.getMinecraft(event.getProjectile());
					if (arrow != null)
					{
						boolean allowShoot = enchant.onArrowShoot((ILivingEntity) shootingEntity, arrow);

						if (!allowShoot)
							arrow.dead = true;
					}
				}
			}
		}
	}

	@Override
	public void OnServerReady()
	{
		EntityRegister.registerOverrideEntity(CustomArrow.class, "Arrow", 10);
	}

	private HashMap<String, ICustomBowEnchant> enchantMap = new HashMap<String, ICustomBowEnchant>();
	private List<ICustomBowEnchant> enchants = new ArrayList<ICustomBowEnchant>();
}
