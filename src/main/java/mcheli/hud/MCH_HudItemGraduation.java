package mcheli.hud;

import mcheli.MCH_Lib;
import mcheli.hud.MCH_HudItem;
import org.lwjgl.opengl.GL11;

public class MCH_HudItemGraduation extends MCH_HudItem {

   private final String drawRot;
   private final String drawRoll;
   private final String drawPosX;
   private final String drawPosY;
   private final int type;


   public MCH_HudItemGraduation(int fileLine, int type, String rot, String roll, String posx, String posy) {
      super(fileLine);
      this.drawRot = toFormula(rot);
      this.drawRoll = toFormula(roll);
      this.drawPosX = toFormula(posx);
      this.drawPosY = toFormula(posy);
      this.type = type;
   }

   public void execute() {
      GL11.glPushMatrix();
      int x = (int)(MCH_HudItem.centerX + calc(this.drawPosX));
      int y = (int)(MCH_HudItem.centerY + calc(this.drawPosY));
      GL11.glTranslated((double)x, (double)y, 0.0D);
      GL11.glRotatef((float)calc(this.drawRoll), 0.0F, 0.0F, 1.0F);
      GL11.glTranslated((double)(-x), (double)(-y), 0.0D);
      if(this.type == 0) {
         this.drawCommonGraduationYaw(calc(this.drawRot), MCH_HudItem.colorSetting, x, y);
      } else if(this.type == 1) {
         this.drawCommonGraduationPitch1(calc(this.drawRot), MCH_HudItem.colorSetting, x, y);
      } else if(this.type == 2 || this.type == 3) {
         this.drawCommonGraduationPitch2(calc(this.drawRot), MCH_HudItem.colorSetting, x, y);
      }

      GL11.glPopMatrix();
   }

   private void drawCommonGraduationPitch2(double playerPitch, int color, int posX, int posY) {
      playerPitch = -playerPitch;
      int pitch_n = (int)playerPitch / 5 * 5;
      double[] line = new double[8];
      int start = this.type == 2?0:1;
      int end = this.type == 2?5:4;
      int INT = this.type == 2?1:2;

      for(int i = start; i < end; ++i) {
         int pitch = -(-pitch_n - 10 + i * 5);
         double p_rest = playerPitch % 5.0D;
         boolean XO = true;
         boolean XI = true;
         int x = pitch != 0?50:100;
         int y = posY + (int)((double)(-60 * INT) + p_rest * 6.0D * (double)INT + (double)(i * 30 * INT));
         line[0] = (double)(posX - x);
         line[1] = (double)(y + (pitch == 0?0:(pitch > 0?2:-2)));
         line[2] = (double)(posX - 50);
         line[3] = (double)y;
         line[4] = (double)(posX + x);
         line[5] = line[1];
         line[6] = (double)(posX + 50);
         line[7] = (double)y;
         this.drawLine(line, color);
         line[0] = (double)(posX - 50);
         line[1] = (double)y;
         line[2] = (double)(posX - 30);
         line[3] = (double)y;
         line[4] = (double)(posX + 50);
         line[5] = (double)y;
         line[6] = (double)(posX + 30);
         line[7] = (double)y;
         if(pitch >= 0) {
            this.drawLine(line, color);
         } else {
            this.drawLineStipple(line, color, 1, '\ucccc');
         }

         if(pitch != 0) {
            this.drawCenteredString("" + pitch, posX - 50 - 10, y - 4, color);
            this.drawCenteredString("" + pitch, posX + 50 + 10, y - 4, color);
         }
      }

   }

   private void drawCommonGraduationPitch1(double playerPitch, int color, int posX, int posY) {
      int pitch = (int)playerPitch % 360;
      boolean INVY = true;
      int y = (int)(playerPitch * 10.0D % 10.0D);
      if(y < 0) {
         y += 10;
      }

      boolean GW = true;
      int posX_L = posX - 100;
      int posX_R = posX + 100;
      int linePosY = posY;
      posY -= 80;
      double[] line = new double[144];
      int p = playerPitch < 0.0D && y != 0?pitch - 9:pitch - 8;

      for(int verticalLine = 0; verticalLine < line.length / 8; ++p) {
         int olx = p % 3 == 0?15:5;
         byte ilx = 0;
         line[verticalLine * 8 + 0] = (double)(posX_L - olx);
         line[verticalLine * 8 + 1] = (double)(posY + verticalLine * 10 - y);
         line[verticalLine * 8 + 2] = (double)(posX_L + ilx);
         line[verticalLine * 8 + 3] = (double)(posY + verticalLine * 10 - y);
         line[verticalLine * 8 + 4] = (double)(posX_R + olx);
         line[verticalLine * 8 + 5] = (double)(posY + verticalLine * 10 - y);
         line[verticalLine * 8 + 6] = (double)(posX_R - ilx);
         line[verticalLine * 8 + 7] = (double)(posY + verticalLine * 10 - y);
         ++verticalLine;
      }

      this.drawLine(line, color);
      double[] var18 = new double[]{(double)(posX_L - 25), (double)(linePosY - 90), (double)posX_L, (double)(linePosY - 90), (double)posX_L, (double)(linePosY + 90), (double)(posX_L - 25), (double)(linePosY + 90)};
      this.drawLine(var18, color, 3);
      var18 = new double[]{(double)(posX_R + 25), (double)(linePosY - 90), (double)posX_R, (double)(linePosY - 90), (double)posX_R, (double)(linePosY + 90), (double)(posX_R + 25), (double)(linePosY + 90)};
      this.drawLine(var18, color, 3);
   }

   private void drawCommonGraduationYaw(double playerYaw, int color, int posX, int posY) {
      double yaw = MCH_Lib.getRotate360(playerYaw);
      boolean INVX = true;
      posX -= 90;
      double[] line = new double[76];
      int x = (int)(yaw * 10.0D) % 10;
      int y = (int)yaw - 9;

      for(int i = 0; i < line.length / 4; ++y) {
         int azPosX = posX + i * 10 - x;
         line[i * 4 + 0] = (double)azPosX;
         line[i * 4 + 1] = (double)posY;
         line[i * 4 + 2] = (double)azPosX;
         line[i * 4 + 3] = (double)(posY + (y % 45 == 0?15:(y % 3 == 0?10:5)));
         if(y % 45 == 0) {
            this.drawCenteredString(MCH_Lib.getAzimuthStr8(y), azPosX, posY - 10, -65536);
         } else if(y % 3 == 0) {
            int rot = y + 180;
            if(rot < 0) {
               rot += 360;
            }

            if(rot > 360) {
               rot -= 360;
            }

            this.drawCenteredString(String.format("%d", new Object[]{Integer.valueOf(rot)}), azPosX, posY - 10, color);
         }

         ++i;
      }

      this.drawLine(line, color);
   }
}
