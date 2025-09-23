package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.exp.ShareExpValue;

public class ShiftRightExpression extends Col2Expression {

   public ShiftRightExpression() {
      this.setOperator(">>");
   }

   protected ShiftRightExpression(ShiftRightExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new ShiftRightExpression(this, s);
   }

   protected long operateLong(long vl, long vr) {
      return vl >> (int)vr;
   }

   protected double operateDouble(double vl, double vr) {
      return vl / Math.pow(2.0D, vr);
   }

   protected Object operateObject(Object vl, Object vr) {
      return super.share.oper.shiftRight(vl, vr);
   }
}
