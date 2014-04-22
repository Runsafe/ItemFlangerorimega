package no.runsafe.itemflangerorimega.bows;

import net.minecraft.server.v1_7_R2.EntityArrow;
import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.World;

public class CustomArrow extends EntityArrow
{
	public CustomArrow(World world)
	{
		super(world);
	}

	public CustomArrow(World world, double d0, double d1, double d2)
	{
		super(world, d0, d1, d2);
	}

	public CustomArrow(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1)
	{
		super(world, entityliving, entityliving1, f, f1);
	}

	public CustomArrow(World world, EntityLiving entityliving, float f)
	{
		super(world, entityliving, f);
	}

	public void setExplosive(boolean flag)
	{
		isExplosive = flag;
	}

	@Override
	public void h()
	{
		super.h();

		if (inGround)
		{
			die(); // Remove
			world.createExplosion(this, locX, locY, locZ, 2F, true, true);
		}
	}

	private boolean isExplosive = false;
}
