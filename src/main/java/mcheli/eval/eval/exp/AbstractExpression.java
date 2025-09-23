package mcheli.eval.eval.exp;

import java.util.List;
import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.exp.ShareExpValue;

public abstract class AbstractExpression {

   public static final int TRUE = 1;
   public static final int FALSE = 0;
   protected String string = null;
   protected int pos = -1;
   private String ope1;
   private String ope2;
   public ShareExpValue share;
   protected int prio;


   protected final boolean isTrue(boolean lng) {
      return lng?this.evalLong() != 0L:this.evalDouble() != 0.0D;
   }

   protected AbstractExpression() {}

   protected AbstractExpression(AbstractExpression from, ShareExpValue s) {
      this.string = from.string;
      this.pos = from.pos;
      this.prio = from.prio;
      if(s != null) {
         this.share = s;
      } else {
         this.share = from.share;
      }

      this.ope1 = from.ope1;
      this.ope2 = from.ope2;
   }

   public abstract AbstractExpression dup(ShareExpValue var1);

   public final String getOperator() {
      return this.ope1;
   }

   public final String getEndOperator() {
      return this.ope2;
   }

   public final void setOperator(String ope) {
      this.ope1 = ope;
   }

   public final void setEndOperator(String ope) {
      this.ope2 = ope;
   }

   protected String getWord() {
      return this.getOperator();
   }

   protected void setWord(String word) {
      throw new EvalException(2001, word, this.string, this.pos, (Throwable)null);
   }

   protected abstract int getCols();

   protected final void setPos(String string, int pos) {
      this.string = string;
      this.pos = pos;
   }

   protected abstract int getFirstPos();

   public final void setPriority(int prio) {
      this.prio = prio;
   }

   protected final int getPriority() {
      return this.prio;
   }

   protected void let(Object val, int pos) {
      throw new EvalException(2004, this.toString(), this.string, pos, (Throwable)null);
   }

   protected void let(long val, int pos) {
      this.let(new Long(val), pos);
   }

   protected void let(double val, int pos) {
      this.let(new Double(val), pos);
   }

   protected Object getVariable() {
      String word = this.toString();
      throw new EvalException(2002, word, this.string, this.pos, (Throwable)null);
   }

   protected void evalArgsLong(List args) {
      args.add(new Long(this.evalLong()));
   }

   protected void evalArgsDouble(List args) {
      args.add(new Double(this.evalDouble()));
   }

   protected void evalArgsObject(List args) {
      args.add(this.evalObject());
   }

   public abstract long evalLong();

   public abstract double evalDouble();

   public abstract Object evalObject();

   protected abstract void search();

   protected abstract AbstractExpression replace();

   protected abstract AbstractExpression replaceVar();

   public abstract boolean equals(Object var1);

   public abstract int hashCode();

   public boolean same(AbstractExpression exp) {
      return same(this.getOperator(), exp.getOperator()) && same(this.getEndOperator(), exp.getEndOperator()) && this.equals(exp);
   }

   private static boolean same(String str1, String str2) {
      return str1 == null?str2 == null:str1.equals(str2);
   }

   public abstract void dump(int var1);

   public abstract String toString();
}
