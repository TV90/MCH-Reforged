package mcheli.aircraft;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Packet;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Network;

public class MCH_PacketIndReload extends MCH_Packet {

   public int entityID_Ac = -1;
   public int weaponID = -1;


   public int getMessageID() {
      return 536875059;
   }

   public void readData(ByteArrayDataInput data) {
      try {
         this.entityID_Ac = data.readInt();
         this.weaponID = data.readByte();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void writeData(DataOutputStream dos) {
      try {
         dos.writeInt(this.entityID_Ac);
         dos.writeByte(this.weaponID);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static void send(MCH_EntityAircraft ac, int weaponId) {
      if(ac != null) {
         MCH_PacketIndReload s = new MCH_PacketIndReload();
         s.entityID_Ac = W_Entity.getEntityId(ac);
         s.weaponID = weaponId;
         W_Network.sendToServer(s);
      }
   }
}
