package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.exp.ShareExpValue;

public class PlusExpression extends Col2Expression {

   public PlusExpression() {
      this.setOperator("+");
   }

   protected PlusExpression(PlusExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new PlusExpression(this, s);
   }

   protected long operateLong(long vl, long vr) {
      return vl + vr;
   }

   protected double operateDouble(double vl, double vr) {
      return vl + vr;
   }

   protected Object operateObject(Object vl, Object vr) {
      return super.share.oper.plus(vl, vr);
   }
}
