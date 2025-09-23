package mcheli.eval.eval.exp;

import mcheli.eval.eval.Rule;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2OpeExpression;
import mcheli.eval.eval.exp.FieldExpression;
import mcheli.eval.eval.exp.VariableExpression;
import mcheli.eval.eval.exp.WordExpression;
import mcheli.eval.eval.ref.Refactor;
import mcheli.eval.eval.repl.ReplaceAdapter;
import mcheli.eval.eval.rule.ShareRuleValue;

public class Replace4RefactorGetter extends ReplaceAdapter {

   protected Refactor ref;
   protected ShareRuleValue rule;


   Replace4RefactorGetter(Refactor ref, Rule rule) {
      this.ref = ref;
      this.rule = (ShareRuleValue)rule;
   }

   protected AbstractExpression var(VariableExpression exp) {
      String name = this.ref.getNewName((Object)null, exp.getWord());
      return (AbstractExpression)(name == null?exp:this.rule.parse(name, exp.share));
   }

   protected AbstractExpression field(FieldExpression exp) {
      AbstractExpression exp1 = exp.expl;
      Object obj = exp1.getVariable();
      if(obj == null) {
         return exp;
      } else {
         AbstractExpression exp2 = exp.expr;
         String name = this.ref.getNewName(obj, exp2.getWord());
         if(name == null) {
            return exp;
         } else {
            exp.expr = this.rule.parse(name, exp2.share);
            return exp;
         }
      }
   }

   public AbstractExpression replace0(WordExpression exp) {
      return (AbstractExpression)(exp instanceof VariableExpression?this.var((VariableExpression)exp):exp);
   }

   public AbstractExpression replace2(Col2OpeExpression exp) {
      return (AbstractExpression)(exp instanceof FieldExpression?this.field((FieldExpression)exp):exp);
   }
}
