package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.PlusExpression;
import mcheli.eval.eval.exp.ShareExpValue;

public class LetPlusExpression extends PlusExpression {

   public LetPlusExpression() {
      this.setOperator("+=");
   }

   protected LetPlusExpression(LetPlusExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new LetPlusExpression(this, s);
   }

   public long evalLong() {
      long val = super.evalLong();
      super.expl.let(val, super.pos);
      return val;
   }

   public double evalDouble() {
      double val = super.evalDouble();
      super.expl.let(val, super.pos);
      return val;
   }

   public Object evalObject() {
      Object val = super.evalObject();
      super.expl.let(val, super.pos);
      return val;
   }

   protected AbstractExpression replace() {
      super.expl = super.expl.replaceVar();
      super.expr = super.expr.replace();
      return super.share.repl.replaceLet(this);
   }
}
