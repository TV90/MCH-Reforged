package mcheli.weapon;

import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.tank.MCH_EntityTank;
import mcheli.weapon.MCH_EntityMarkerRocket;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponInfo;
import mcheli.weapon.MCH_WeaponParam;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MCH_WeaponMarkerRocket extends MCH_WeaponBase {

   public MCH_WeaponMarkerRocket(World w, Vec3 v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
      super(w, v, yaw, pitch, nm, wi);
      super.acceleration = 3.0F;
      super.explosionPower = 0;
      super.power = 0;
      super.interval = 60;
      if(w.isRemote) {
         super.interval += 10;
      }

   }

   public boolean shot(MCH_WeaponParam prm) {
      if(!super.worldObj.isRemote) {
         this.playSound(prm.entity);
         float yaw,pitch;
         if(prm.entity instanceof MCH_EntityTank) {
            MCH_EntityTank tank = (MCH_EntityTank) prm.entity;
            yaw = prm.user.rotationYaw ;
            pitch = prm.user.rotationPitch ;
            yaw += prm.randYaw;
            pitch += prm.randPitch;
            int wid = tank.getCurrentWeaponID(prm.user);
            MCH_AircraftInfo.Weapon w = tank.getAcInfo().getWeaponById(wid);
            float minPitch = w == null ? tank.getAcInfo().minRotationPitch : w.minPitch;
            float maxPitch = w == null ? tank.getAcInfo().maxRotationPitch : w.maxPitch;
            float playerYaw = MathHelper.wrapAngleTo180_float(tank.getRotYaw() - yaw);
            float playerPitch = tank.getRotPitch() * MathHelper.cos((float) (playerYaw * Math.PI / 180.0D))
                    + -tank.getRotRoll() * MathHelper.sin((float) (playerYaw * Math.PI / 180.0D));
            float playerYawRel = MathHelper.wrapAngleTo180_float(yaw - tank.getRotYaw());
            float yawLimit = (w == null ? 360F : w.maxYaw);
            float relativeYaw = MCH_Lib.RNG(playerYawRel, -yawLimit, yawLimit);
            yaw = MathHelper.wrapAngleTo180_float(tank.getRotYaw() + relativeYaw);
            pitch = MCH_Lib.RNG(pitch, playerPitch + minPitch, playerPitch + maxPitch);
            pitch = MCH_Lib.RNG(pitch, -90.0F, 90.0F);
         } else {
            yaw = prm.rotYaw;
            pitch = prm.rotPitch;
         }
         double tX = -MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F);
         double tZ = MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F);
         double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
         MCH_EntityMarkerRocket e = new MCH_EntityMarkerRocket(super.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, super.acceleration);
         e.setName(super.name);
         e.setParameterFromWeapon(this, prm.entity, prm.user);
         e.setMarkerStatus(1);
         super.worldObj.spawnEntityInWorld(e);
      } else {
         super.optionParameter1 = this.getCurrentMode();
      }

      return true;
   }
}