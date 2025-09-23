package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.exp.ShareExpValue;

public class GreaterEqualExpression extends Col2Expression {

   public GreaterEqualExpression() {
      this.setOperator(">=");
   }

   protected GreaterEqualExpression(GreaterEqualExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new GreaterEqualExpression(this, s);
   }

   protected long operateLong(long vl, long vr) {
      return vl >= vr?1L:0L;
   }

   protected double operateDouble(double vl, double vr) {
      return vl >= vr?1.0D:0.0D;
   }

   protected Object operateObject(Object vl, Object vr) {
      return super.share.oper.greaterEqual(vl, vr);
   }
}
