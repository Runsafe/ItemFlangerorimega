package no.runsafe.itemflangerorimega.bows;

import net.minecraft.server.v1_7_R2.*;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

import java.util.List;

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
		if (lastPitch == 0.0F && lastYaw == 0.0F) {
			float f = MathHelper.sqrt(motX * motX + motZ * motZ);

			lastYaw = yaw = (float) (Math.atan2(motX, motZ) * 180.0D / 3.1415927410125732D);
			lastPitch = pitch = (float) (Math.atan2(motY, (double) f) * 180.0D / 3.1415927410125732D);
		}

		Block block = world.getType(d, e, f);

		if (block.getMaterial() != Material.AIR) {
			block.updateShape(world, d, e, f);
			AxisAlignedBB axisalignedbb = block.a(world, d, e, f);

			if (axisalignedbb != null && axisalignedbb.a(Vec3D.a(locX, locY, locZ)))
				inGround = true;
		}

		if (shake > 0)
			--shake;

		if (isExplosive)
		{
			if (inGround)
			{
				die(); // Remove
				world.createExplosion(this, locX, locY, locZ, 2F, true, true);
			}
			else
			{
				for (j = 0; j < 4; ++j)
					this.world.addParticle("smoke", this.locX + this.motX * j / 4.0D, this.locY + this.motY * j / 4.0D, this.locZ + this.motZ * j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
			}
		}

		if (inGround)
		{
			int i = world.getData(d, e, f);

			if (block == g && i == h)
			{
				++at;
				if (at == 1200)
					this.die();
			}
			else
			{
				inGround = false;
				motX *= (double) (random.nextFloat() * 0.2F);
				motY *= (double) (random.nextFloat() * 0.2F);
				motZ *= (double) (random.nextFloat() * 0.2F);
				at = 0;
				au = 0;
			}
		}
		else
		{
			++au;
			Vec3D vec3d = Vec3D.a(locX, locY, locZ);
			Vec3D vec3d1 = Vec3D.a(locX + motX, locY + motY, locZ + motZ);
			MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, false, true, false);

			vec3d = Vec3D.a(locX, locY, locZ);
			vec3d1 = Vec3D.a(locX + motX, locY + motY, locZ + motZ);
			if (movingobjectposition != null)
				vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);

			Entity entity = null;
			List list = world.getEntities(this, boundingBox.a(motX, motY, motZ).grow(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			int j;
			float f1;

			for (j = 0; j < list.size(); ++j)
			{
				Entity entity1 = (Entity) list.get(j);

				if (entity1.Q() && (entity1 != this.shooter || this.au >= 5))
				{
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.grow((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);

					if (movingobjectposition1 != null)
					{
						double d1 = vec3d.distanceSquared(movingobjectposition1.pos); // CraftBukkit - distance efficiency

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
				movingobjectposition = new MovingObjectPosition(entity);

			if (movingobjectposition != null && movingobjectposition.entity != null && movingobjectposition.entity instanceof EntityHuman)
			{
				EntityHuman entityhuman = (EntityHuman) movingobjectposition.entity;

				if (entityhuman.abilities.isInvulnerable || this.shooter instanceof EntityHuman && !((EntityHuman) this.shooter).a(entityhuman))
					movingobjectposition = null;
			}

			float f2;
			float f3;

			if (movingobjectposition != null)
			{
				org.bukkit.craftbukkit.v1_7_R2.event.CraftEventFactory.callProjectileHitEvent(this); // CraftBukkit - Call event

				if (movingobjectposition.entity != null)
				{
					f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
					int k = MathHelper.f((double) f2 * this.damage);

					if (this.isCritical())
						k += this.random.nextInt(k / 2 + 2);

					DamageSource damagesource;

					if (this.shooter == null)
						damagesource = DamageSource.arrow(this, this);
					else
						damagesource = DamageSource.arrow(this, this.shooter);

					// CraftBukkit start - Moved damage call
					if (movingobjectposition.entity.damageEntity(damagesource, k))
					{
						if (this.isBurning() && !(movingobjectposition.entity instanceof EntityEnderman) && (!(movingobjectposition.entity instanceof EntityPlayer) || !(this.shooter instanceof EntityPlayer) || this.world.pvpMode)) { // CraftBukkit - abide by pvp setting if destination is a player
							EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 5);
							org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);

							if (!combustEvent.isCancelled())
								movingobjectposition.entity.setOnFire(combustEvent.getDuration());

							// CraftBukkit end
						}

						if (movingobjectposition.entity instanceof EntityLiving)
						{
							EntityLiving entityliving = (EntityLiving) movingobjectposition.entity;

							if (!this.world.isStatic)
								entityliving.p(entityliving.aY() + 1);

							if (this.knockbackStrength > 0)
							{
								f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
								if (f3 > 0.0F)
									movingobjectposition.entity.g(this.motX * (double) this.knockbackStrength * 0.6000000238418579D / (double) f3, 0.1D, this.motZ * (double) this.knockbackStrength * 0.6000000238418579D / (double) f3);
							}

							if (this.shooter != null && this.shooter instanceof EntityLiving) {
								EnchantmentManager.a(entityliving, this.shooter);
								EnchantmentManager.b((EntityLiving) this.shooter, entityliving);
							}

							if (this.shooter != null && movingobjectposition.entity != this.shooter && movingobjectposition.entity instanceof EntityHuman && this.shooter instanceof EntityPlayer) {
								((EntityPlayer) this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
							}
						}

						this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
						if (!(movingobjectposition.entity instanceof EntityEnderman)) {
							this.die();
						}
					} else {
						this.motX *= -0.10000000149011612D;
						this.motY *= -0.10000000149011612D;
						this.motZ *= -0.10000000149011612D;
						this.yaw += 180.0F;
						this.lastYaw += 180.0F;
						this.au = 0;
					}
				} else {
					this.d = movingobjectposition.b;
					this.e = movingobjectposition.c;
					this.f = movingobjectposition.d;
					this.g = this.world.getType(this.d, this.e, this.f);
					this.h = this.world.getData(this.d, this.e, this.f);
					this.motX = (double) ((float) (movingobjectposition.pos.a - this.locX));
					this.motY = (double) ((float) (movingobjectposition.pos.b - this.locY));
					this.motZ = (double) ((float) (movingobjectposition.pos.c - this.locZ));
					f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
					this.locX -= this.motX / (double) f2 * 0.05000000074505806D;
					this.locY -= this.motY / (double) f2 * 0.05000000074505806D;
					this.locZ -= this.motZ / (double) f2 * 0.05000000074505806D;
					this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.shake = 7;
					this.setCritical(false);
					if (this.g.getMaterial() != Material.AIR) {
						this.g.a(this.world, this.d, this.e, this.f, (Entity) this);
					}
				}
			}

			if (this.isCritical())
				for (j = 0; j < 4; ++j)
					this.world.addParticle("crit", this.locX + this.motX * (double) j / 4.0D, this.locY + this.motY * (double) j / 4.0D, this.locZ + this.motZ * (double) j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);

			locX += motX;
			locY += motY;
			locZ += motZ;

			//f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
			this.yaw = (float) (Math.atan2(motX, motZ) * 180.0D / 3.1415927410125732D);

			while (this.pitch - this.lastPitch >= 180.0F) {
				this.lastPitch += 360.0F;
			}

			while (this.yaw - this.lastYaw < -180.0F) {
				this.lastYaw -= 360.0F;
			}

			while (this.yaw - this.lastYaw >= 180.0F) {
				this.lastYaw += 360.0F;
			}

			this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
			this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
			float f4 = 0.99F;

			f1 = 0.05F;
			if (this.L()) {
				for (int l = 0; l < 4; ++l) {
					f3 = 0.25F;
					this.world.addParticle("bubble", this.locX - this.motX * (double) f3, this.locY - this.motY * (double) f3, this.locZ - this.motZ * (double) f3, this.motX, this.motY, this.motZ);
				}

				f4 = 0.8F;
			}

			if (this.K()) {
				this.extinguish();
			}

			this.motX *= (double) f4;
			this.motY *= (double) f4;
			this.motZ *= (double) f4;
			this.motY -= (double) f1;
			this.setPosition(this.locX, this.locY, this.locZ);
			this.H();
		}
	}

	private boolean isExplosive = false;
	private int d = -1;
	private int e = -1;
	private int f = -1;
	private Block g;
	private int h;
	private boolean inGround;
	private int at;
	private int au;
	private double damage = 2.0D;
}
