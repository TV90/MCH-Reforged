package mcheli.eval.eval.var;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import mcheli.eval.eval.var.Variable;

public class MapVariable implements Variable {

   protected Map map;


   public MapVariable() {
      this(new HashMap());
   }

   public MapVariable(Map varMap) {
      this.map = varMap;
   }

   public void setMap(Map varMap) {
      this.map = varMap;
   }

   public Map getMap() {
      return this.map;
   }

   public void setValue(Object name, Object obj) {
      this.map.put(name, obj);
   }

   public Object getObject(Object name) {
      return this.map.get(name);
   }

   public long evalLong(Object val) {
      return ((Number)val).longValue();
   }

   public double evalDouble(Object val) {
      return ((Number)val).doubleValue();
   }

   public Object getObject(Object array, int index) {
      return Array.get(array, index);
   }

   public void setValue(Object array, int index, Object val) {
      Array.set(array, index, val);
   }

   public Object getObject(Object obj, String field) {
      try {
         Class e = obj.getClass();
         Field f = e.getField(field);
         return f.get(obj);
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void setValue(Object obj, String field, Object val) {
      try {
         Class e = obj.getClass();
         Field f = e.getField(field);
         f.set(obj, val);
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }
}
