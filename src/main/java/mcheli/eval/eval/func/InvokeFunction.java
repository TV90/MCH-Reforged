package mcheli.eval.eval.func;

import java.lang.reflect.Method;
import mcheli.eval.eval.func.Function;

public class InvokeFunction implements Function {

   public long evalLong(Object object, String name, Long[] args) throws Throwable {
      if(object == null) {
         return 0L;
      } else {
         Object r = callMethod(object, name, args);
         return ((Number)r).longValue();
      }
   }

   public double evalDouble(Object object, String name, Double[] args) throws Throwable {
      if(object == null) {
         return 0.0D;
      } else {
         Object r = callMethod(object, name, args);
         return ((Number)r).doubleValue();
      }
   }

   public Object evalObject(Object object, String name, Object[] args) throws Throwable {
      return object == null?null:callMethod(object, name, args);
   }

   public static Object callMethod(Object obj, String name, Object[] args) throws Exception {
      Class c = obj.getClass();
      Class[] types = new Class[args.length];

      for(int m = 0; m < types.length; ++m) {
         types[m] = args[m].getClass();
      }

      Method var6 = c.getMethod(name, types);
      return var6.invoke(obj, args);
   }
}
