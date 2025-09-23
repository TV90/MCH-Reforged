package mcheli.eval.eval.rule;

import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col1Expression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.exp.FunctionExpression;
import mcheli.eval.eval.lex.Lex;
import mcheli.eval.eval.rule.AbstractRule;
import mcheli.eval.eval.rule.ShareRuleValue;

public class Col1AfterRule extends AbstractRule {

   public AbstractExpression func;
   public AbstractExpression array;
   public AbstractExpression field;


   public Col1AfterRule(ShareRuleValue share) {
      super(share);
   }

   public AbstractExpression parse(Lex lex) {
      AbstractExpression x = super.nextRule.parse(lex);

      while(true) {
         switch(lex.getType()) {
         case 2147483634:
            String ope = lex.getOperator();
            int pos = lex.getPos();
            if(!this.isMyOperator(ope)) {
               return x;
            }

            if(lex.isOperator(this.func.getOperator())) {
               x = this.parseFunc(lex, x);
            } else if(lex.isOperator(this.array.getOperator())) {
               x = this.parseArray(lex, x, ope, pos);
            } else if(lex.isOperator(this.field.getOperator())) {
               x = this.parseField(lex, x, ope, pos);
            } else {
               x = Col1Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x);
               lex.next();
            }
            break;
         default:
            return x;
         }
      }
   }

   protected AbstractExpression parseFunc(Lex lex, AbstractExpression x) {
      AbstractExpression a = null;
      lex.next();
      if(!lex.isOperator(this.func.getEndOperator())) {
         a = super.share.funcArgRule.parse(lex);
         if(!lex.isOperator(this.func.getEndOperator())) {
            throw new EvalException(1001, new String[]{this.func.getEndOperator()}, lex);
         }
      }

      lex.next();
      x = FunctionExpression.create(x, a, super.prio, lex.getShare());
      return x;
   }

   protected AbstractExpression parseArray(Lex lex, AbstractExpression x, String ope, int pos) {
      AbstractExpression y = super.share.topRule.parse(lex.next());
      if(!lex.isOperator(this.array.getEndOperator())) {
         throw new EvalException(1001, new String[]{this.array.getEndOperator()}, lex);
      } else {
         lex.next();
         x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
         return x;
      }
   }

   protected AbstractExpression parseField(Lex lex, AbstractExpression x, String ope, int pos) {
      AbstractExpression y = super.nextRule.parse(lex.next());
      x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
      return x;
   }
}
