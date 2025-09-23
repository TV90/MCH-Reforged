package mcheli.hud;

import mcheli.hud.MCH_HudItem;

public class MCH_HudItemRect extends MCH_HudItem {

   private final String left;
   private final String top;
   private final String width;
   private final String height;


   public MCH_HudItemRect(int fileLine, String left, String top, String width, String height) {
      super(fileLine);
      this.left = toFormula(left);
      this.top = toFormula(top);
      this.width = toFormula(width);
      this.height = toFormula(height);
   }

   public void execute() {
      double x2 = MCH_HudItem.centerX + calc(this.left);
      double y2 = MCH_HudItem.centerY + calc(this.top);
      double x1 = x2 + (double)((int)calc(this.width));
      double y1 = y2 + (double)((int)calc(this.height));
      drawRect(x1, y1, x2, y2, MCH_HudItem.colorSetting);
   }
}
