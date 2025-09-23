package mcheli.eval.eval.exp;

import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.exp.FieldExpression;
import mcheli.eval.eval.exp.FunctionExpression;
import mcheli.eval.eval.exp.VariableExpression;
import mcheli.eval.eval.exp.WordExpression;
import mcheli.eval.eval.ref.Refactor;
import mcheli.eval.eval.repl.ReplaceAdapter;

public class Replace4RefactorName extends ReplaceAdapter {

   protected Refactor ref;


   Replace4RefactorName(Refactor ref) {
      this.ref = ref;
   }

   protected void var(VariableExpression exp) {
      String name = this.ref.getNewName((Object)null, exp.getWord());
      if(name != null) {
         exp.setWord(name);
      }

   }

   protected void field(FieldExpression exp) {
      AbstractExpression exp1 = exp.expl;
      Object obj = exp1.getVariable();
      if(obj == null) {
         throw new EvalException(2104, this.toString(), exp1.string, exp1.pos, (Throwable)null);
      } else {
         AbstractExpression exp2 = exp.expr;
         String name = this.ref.getNewName(obj, exp2.getWord());
         if(name != null) {
            exp2.setWord(name);
         }

      }
   }

   protected void func(FunctionExpression exp) {
      Object obj = null;
      if(exp.target != null) {
         obj = exp.target.getVariable();
      }

      String name = this.ref.getNewFuncName(obj, exp.name);
      if(name != null) {
         exp.name = name;
      }

   }

   public AbstractExpression replace0(WordExpression exp) {
      if(exp instanceof VariableExpression) {
         this.var((VariableExpression)exp);
      }

      return exp;
   }

   public AbstractExpression replace2(Col2Expression exp) {
      if(exp instanceof FieldExpression) {
         this.field((FieldExpression)exp);
      }

      return exp;
   }

   public AbstractExpression replaceFunc(FunctionExpression exp) {
      this.func(exp);
      return exp;
   }

   public AbstractExpression replaceVar(AbstractExpression exp) {
      if(exp instanceof VariableExpression) {
         this.var((VariableExpression)exp);
      } else if(exp instanceof FieldExpression) {
         this.field((FieldExpression)exp);
      } else if(exp instanceof FunctionExpression) {
         this.func((FunctionExpression)exp);
      }

      return exp;
   }
}
