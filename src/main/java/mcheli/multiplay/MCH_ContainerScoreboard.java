package mcheli.multiplay;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class MCH_ContainerScoreboard extends Container {

   public final EntityPlayer thePlayer;


   public MCH_ContainerScoreboard(EntityPlayer player) {
      this.thePlayer = player;
   }

   public boolean canInteractWith(EntityPlayer player) {
      return true;
   }
}
