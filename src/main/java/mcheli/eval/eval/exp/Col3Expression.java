package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.ShareExpValue;

public abstract class Col3Expression extends AbstractExpression {

   protected AbstractExpression exp1;
   protected AbstractExpression exp2;
   protected AbstractExpression exp3;


   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y, AbstractExpression z) {
      Col3Expression n = (Col3Expression)exp;
      n.setExpression(x, y, z);
      n.setPos(string, pos);
      return n;
   }

   protected Col3Expression() {}

   protected Col3Expression(Col3Expression from, ShareExpValue s) {
      super(from, s);
      if(from.exp1 != null) {
         this.exp1 = from.exp1.dup(s);
      }

      if(from.exp2 != null) {
         this.exp2 = from.exp2.dup(s);
      }

      if(from.exp3 != null) {
         this.exp3 = from.exp3.dup(s);
      }

   }

   public final void setExpression(AbstractExpression x, AbstractExpression y, AbstractExpression z) {
      this.exp1 = x;
      this.exp2 = y;
      this.exp3 = z;
   }

   protected final int getCols() {
      return 3;
   }

   protected int getFirstPos() {
      return this.exp1.getFirstPos();
   }

   protected void search() {
      super.share.srch.search(this);
      if(!super.share.srch.end()) {
         if(!super.share.srch.search3_begin(this)) {
            if(!super.share.srch.end()) {
               this.exp1.search();
               if(!super.share.srch.end()) {
                  if(!super.share.srch.search3_2(this)) {
                     if(!super.share.srch.end()) {
                        this.exp2.search();
                        if(!super.share.srch.end()) {
                           if(!super.share.srch.search3_3(this)) {
                              if(!super.share.srch.end()) {
                                 this.exp3.search();
                                 if(!super.share.srch.end()) {
                                    super.share.srch.search3_end(this);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected AbstractExpression replace() {
      this.exp1 = this.exp1.replace();
      this.exp2 = this.exp2.replace();
      this.exp3 = this.exp3.replace();
      return super.share.repl.replace3(this);
   }

   protected AbstractExpression replaceVar() {
      this.exp1 = this.exp1.replace();
      this.exp2 = this.exp2.replaceVar();
      this.exp3 = this.exp3.replaceVar();
      return super.share.repl.replaceVar3(this);
   }

   public boolean equals(Object obj) {
      if(obj instanceof Col3Expression) {
         Col3Expression e = (Col3Expression)obj;
         if(this.getClass() == e.getClass()) {
            return this.exp1.equals(e.exp1) && this.exp2.equals(e.exp2) && this.exp3.equals(e.exp3);
         }
      }

      return false;
   }

   public int hashCode() {
      return this.getClass().hashCode() ^ this.exp1.hashCode() ^ this.exp2.hashCode() * 2 ^ this.exp3.hashCode() * 3;
   }

   public void dump(int n) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < n; ++i) {
         sb.append(' ');
      }

      sb.append(this.getOperator());
      System.out.println(sb.toString());
      this.exp1.dump(n + 1);
      this.exp2.dump(n + 1);
      this.exp3.dump(n + 1);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if(this.exp1.getPriority() > super.prio && this.exp1.getCols() < 2) {
         sb.append(this.exp1.toString());
      } else {
         sb.append(super.share.paren.getOperator());
         sb.append(this.exp1.toString());
         sb.append(super.share.paren.getEndOperator());
      }

      sb.append(' ');
      sb.append(this.getOperator());
      sb.append(' ');
      if(this.exp2.getPriority() > super.prio && this.exp2.getCols() < 2) {
         sb.append(this.exp2.toString());
      } else {
         sb.append(super.share.paren.getOperator());
         sb.append(this.exp2.toString());
         sb.append(super.share.paren.getEndOperator());
      }

      sb.append(' ');
      sb.append(this.getEndOperator());
      sb.append(' ');
      if(this.exp3.getPriority() > super.prio && this.exp3.getCols() < 2) {
         sb.append(this.exp3.toString());
      } else {
         sb.append(super.share.paren.getOperator());
         sb.append(this.exp3.toString());
         sb.append(super.share.paren.getEndOperator());
      }

      return sb.toString();
   }
}
