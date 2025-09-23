package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col1Expression;
import mcheli.eval.eval.exp.ShareExpValue;

public class NotExpression extends Col1Expression {

   public NotExpression() {
      this.setOperator("!");
   }

   protected NotExpression(NotExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new NotExpression(this, s);
   }

   protected long operateLong(long val) {
      return val == 0L?1L:0L;
   }

   protected double operateDouble(double val) {
      return val == 0.0D?1.0D:0.0D;
   }

   public Object evalObject() {
      return super.share.oper.not(super.exp.evalObject());
   }
}
