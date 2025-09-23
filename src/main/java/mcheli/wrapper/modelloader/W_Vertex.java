package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class W_Vertex {

   public float x;
   public float y;
   public float z;


   public W_Vertex(float x, float y) {
      this(x, y, 0.0F);
   }

   public W_Vertex(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void normalize() {
      double d = Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
      this.x = (float)((double)this.x / d);
      this.y = (float)((double)this.y / d);
      this.z = (float)((double)this.z / d);
   }

   public void add(W_Vertex v) {
      this.x += v.x;
      this.y += v.y;
      this.z += v.z;
   }

   public boolean equal(W_Vertex v) {
      return this.x == v.x && this.y == v.y && this.z == v.z;
   }
}
