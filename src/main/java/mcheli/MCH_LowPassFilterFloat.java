package mcheli;

import mcheli.MCH_Queue;

public class MCH_LowPassFilterFloat {

   private MCH_Queue filter;


   public MCH_LowPassFilterFloat(int filterLength) {
      this.filter = new MCH_Queue(filterLength, 0.0F);
   }

   public void clear() {
      this.filter.clear(0.0F);
   }

   public void put(float t) {
      this.filter.put(t);
   }

   public float getAvg() {
      float f = 0.0F;

      for(int i = 0; i < this.filter.size(); ++i) {
         f += (Float) this.filter.get(i);
      }

      return f / (float)this.filter.size();
   }
}
