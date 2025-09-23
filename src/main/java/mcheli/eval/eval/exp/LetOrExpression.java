package mcheli.eval.eval.exp;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.BitOrExpression;
import mcheli.eval.eval.exp.ShareExpValue;

public class LetOrExpression extends BitOrExpression {

   public LetOrExpression() {
      this.setOperator("|=");
   }

   protected LetOrExpression(LetOrExpression from, ShareExpValue s) {
      super(from, s);
   }

   public AbstractExpression dup(ShareExpValue s) {
      return new LetOrExpression(this, s);
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
