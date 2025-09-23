package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.ShareExpValue;

public abstract class Col2Expression extends AbstractExpression {

   public AbstractExpression expl;
   public AbstractExpression expr;


   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y) {
      Col2Expression n = (Col2Expression)exp;
      n.setExpression(x, y);
      n.setPos(string, pos);
      return n;
   }

   protected Col2Expression() {}

   protected Col2Expression(Col2Expression from, ShareExpValue s) {
      super(from, s);
      if(from.expl != null) {
         this.expl = from.expl.dup(s);
      }

      if(from.expr != null) {
         this.expr = from.expr.dup(s);
      }

   }

   public final void setExpression(AbstractExpression x, AbstractExpression y) {
      this.expl = x;
      this.expr = y;
   }

   protected final int getCols() {
      return 2;
   }

   protected final int getFirstPos() {
      return this.expl.getFirstPos();
   }

   public long evalLong() {
      return this.operateLong(this.expl.evalLong(), this.expr.evalLong());
   }

   public double evalDouble() {
      return this.operateDouble(this.expl.evalDouble(), this.expr.evalDouble());
   }

   public Object evalObject() {
      return this.operateObject(this.expl.evalObject(), this.expr.evalObject());
   }

   protected abstract long operateLong(long var1, long var3);

   protected abstract double operateDouble(double var1, double var3);

   protected abstract Object operateObject(Object var1, Object var2);

   protected void search() {
      super.share.srch.search(this);
      if(!super.share.srch.end()) {
         if(!super.share.srch.search2_begin(this)) {
            if(!super.share.srch.end()) {
               this.expl.search();
               if(!super.share.srch.end()) {
                  if(!super.share.srch.search2_2(this)) {
                     if(!super.share.srch.end()) {
                        this.expr.search();
                        if(!super.share.srch.end()) {
                           super.share.srch.search2_end(this);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected AbstractExpression replace() {
      this.expl = this.expl.replace();
      this.expr = this.expr.replace();
      return super.share.repl.replace2(this);
   }

   protected AbstractExpression replaceVar() {
      this.expl = this.expl.replaceVar();
      this.expr = this.expr.replaceVar();
      return super.share.repl.replaceVar2(this);
   }

   public boolean equals(Object obj) {
      if(obj instanceof Col2Expression) {
         Col2Expression e = (Col2Expression)obj;
         if(this.getClass() == e.getClass()) {
            return this.expl.equals(e.expl) && this.expr.equals(e.expr);
         }
      }

      return false;
   }

   public int hashCode() {
      return this.getClass().hashCode() ^ this.expl.hashCode() ^ this.expr.hashCode() * 2;
   }

   public void dump(int n) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < n; ++i) {
         sb.append(' ');
      }

      sb.append(this.getOperator());
      System.out.println(sb.toString());
      this.expl.dump(n + 1);
      this.expr.dump(n + 1);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if(this.expl.getPriority() < super.prio) {
         sb.append(super.share.paren.getOperator());
         sb.append(this.expl.toString());
         sb.append(super.share.paren.getEndOperator());
      } else {
         sb.append(this.expl.toString());
      }

      sb.append(this.toStringLeftSpace());
      sb.append(this.getOperator());
      sb.append(' ');
      if(this.expr.getPriority() < super.prio) {
         sb.append(super.share.paren.getOperator());
         sb.append(this.expr.toString());
         sb.append(super.share.paren.getEndOperator());
      } else {
         sb.append(this.expr.toString());
      }

      return sb.toString();
   }

   protected String toStringLeftSpace() {
      return " ";
   }
}
