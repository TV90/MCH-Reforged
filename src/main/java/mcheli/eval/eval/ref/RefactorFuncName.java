package mcheli.eval.eval.ref;

import mcheli.eval.eval.ref.RefactorAdapter;

public class RefactorFuncName extends RefactorAdapter {

   protected Class targetClass;
   protected String oldName;
   protected String newName;


   public RefactorFuncName(Class targetClass, String oldName, String newName) {
      this.targetClass = targetClass;
      this.oldName = oldName;
      this.newName = newName;
      if(oldName == null || newName == null) {
         throw new NullPointerException();
      }
   }

   public String getNewFuncName(Object target, String name) {
      if(!name.equals(this.oldName)) {
         return null;
      } else {
         if(this.targetClass == null) {
            if(target == null) {
               return this.newName;
            }
         } else if(target != null && this.targetClass.isAssignableFrom(target.getClass())) {
            return this.newName;
         }

         return null;
      }
   }
}
