package mcheli.multiplay;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import mcheli.MCH_Packet;
import mcheli.multiplay.MCH_MultiplayClient;
import mcheli.wrapper.W_Network;

public class MCH_PacketLargeData extends MCH_Packet {

   public int imageDataIndex = -1;
   public int imageDataSize = 0;
   public int imageDataTotalSize = 0;
   public byte[] buf;


   public int getMessageID() {
      return 536873472;
   }

   public void readData(ByteArrayDataInput data) {
      try {
         this.imageDataIndex = data.readInt();
         this.imageDataSize = data.readInt();
         this.imageDataTotalSize = data.readInt();
         this.buf = new byte[this.imageDataSize];
         data.readFully(this.buf);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void writeData(DataOutputStream dos) {
      try {
         MCH_MultiplayClient.readImageData(dos);
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public static void send() {
      MCH_PacketLargeData p = new MCH_PacketLargeData();
      W_Network.sendToServer(p);
   }
}
