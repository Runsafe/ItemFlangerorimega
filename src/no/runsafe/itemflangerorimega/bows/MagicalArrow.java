package no.runsafe.itemflangerorimega.bows;

import net.minecraft.server.v1_6_R1.EntityArrow;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class MagicalArrow extends EntityArrow
{
	public MagicalArrow(RunsafePlayer player)
	{
		super(player.getWorld().getNMS());
		this.shooter = player.getNMS();
		//this.enchant = enchant;
	}

	private ICustomBowEnchant enchant;
}
