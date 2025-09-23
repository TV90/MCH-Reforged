package mcheli.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class MCH_Gui extends GuiScreen {

   protected int centerX = 0;
   protected int centerY = 0;
   protected Random rand = new Random();
   protected float smoothCamPartialTicks;
   public static int scaleFactor;


   public MCH_Gui(Minecraft minecraft) {
      super.mc = minecraft;
      this.smoothCamPartialTicks = 0.0F;
      super.zLevel = -110.0F;
   }

   public void initGui() {
      super.initGui();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onTick() {}

   public abstract boolean isDrawGui(EntityPlayer var1);

   public abstract void drawGui(EntityPlayer var1, boolean var2);

   public void drawScreen(int par1, int par2, float partialTicks) {
      this.smoothCamPartialTicks = partialTicks;
      W_ScaledResolution scaledresolution = new W_ScaledResolution(super.mc, super.mc.displayWidth, super.mc.displayHeight);
      scaleFactor = scaledresolution.getScaleFactor();
      if(!super.mc.gameSettings.hideGUI) {
         super.width = super.mc.displayWidth / scaleFactor;
         super.height = super.mc.displayHeight / scaleFactor;
         this.centerX = super.width / 2;
         this.centerY = super.height / 2;
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if(super.mc.thePlayer != null) {
            this.drawGui(super.mc.thePlayer, super.mc.gameSettings.thirdPersonView != 0);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

   }

   public void drawTexturedModalRectRotate(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot) {
      GL11.glPushMatrix();
      GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
      GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
      float f = 0.00390625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(-width / 2.0D, height / 2.0D, (double)super.zLevel, uLeft * 0.00390625D, (vTop + vHeight) * 0.00390625D);
      tessellator.addVertexWithUV(width / 2.0D, height / 2.0D, (double)super.zLevel, (uLeft + uWidth) * 0.00390625D, (vTop + vHeight) * 0.00390625D);
      tessellator.addVertexWithUV(width / 2.0D, -height / 2.0D, (double)super.zLevel, (uLeft + uWidth) * 0.00390625D, vTop * 0.00390625D);
      tessellator.addVertexWithUV(-width / 2.0D, -height / 2.0D, (double)super.zLevel, uLeft * 0.00390625D, vTop * 0.00390625D);
      tessellator.draw();
      GL11.glPopMatrix();
   }

   public void drawTexturedRect(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, double textureWidth, double textureHeight) {
      float fx = (float)(1.0D / textureWidth);
      float fy = (float)(1.0D / textureHeight);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(left, top + height, (double)super.zLevel, uLeft * (double)fx, (vTop + vHeight) * (double)fy);
      tessellator.addVertexWithUV(left + width, top + height, (double)super.zLevel, (uLeft + uWidth) * (double)fx, (vTop + vHeight) * (double)fy);
      tessellator.addVertexWithUV(left + width, top, (double)super.zLevel, (uLeft + uWidth) * (double)fx, vTop * (double)fy);
      tessellator.addVertexWithUV(left, top, (double)super.zLevel, uLeft * (double)fx, vTop * (double)fy);
      tessellator.draw();
   }

   public void drawLineStipple(double[] line, int color, int factor, int pattern) {
      GL11.glEnable(2852);
      GL11.glLineStipple(factor, (short)pattern);
      this.drawLine(line, color);
      GL11.glDisable(2852);
   }

   public void drawLine(double[] line, int color) {
      this.drawLine(line, color, 1);
   }

   public void drawString(String s, int x, int y, int color) {
      this.drawString(super.mc.fontRenderer, s, x, y, color);
   }

   public void drawDigit(String s, int x, int y, int interval, int color) {
      GL11.glEnable(3042);
      GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color & 255), (byte)(color >> 24 & 255));
      int srcBlend = GL11.glGetInteger(3041);
      int dstBlend = GL11.glGetInteger(3040);
      GL11.glBlendFunc(770, 771);
      W_McClient.MOD_bindTexture("textures/gui/digit.png");

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if(c >= 48 && c <= 57) {
            this.drawTexturedModalRect(x + interval * i, y, 16 * (c - 48), 0, 16, 16);
         }

         if(c == 45) {
            this.drawTexturedModalRect(x + interval * i, y, 160, 0, 16, 16);
         }
      }

      GL11.glBlendFunc(srcBlend, dstBlend);
      GL11.glDisable(3042);
   }

   public void drawCenteredString(String s, int x, int y, int color) {
      this.drawCenteredString(super.mc.fontRenderer, s, x, y, color);
   }

   public void drawLine(double[] line, int color, int mode) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color >> 0 & 255), (byte)(color >> 24 & 255));
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(mode);

      for(int i = 0; i < line.length; i += 2) {
         tessellator.addVertex(line[i + 0], line[i + 1], (double)super.zLevel);
      }

      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
      GL11.glPopMatrix();
   }

   public void drawPoints(double[] points, int color, int pointWidth) {
      int prevWidth = GL11.glGetInteger(2833);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color >> 0 & 255), (byte)(color >> 24 & 255));
      GL11.glPointSize((float)pointWidth);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(0);

      for(int i = 0; i < points.length; i += 2) {
         tessellator.addVertex(points[i], points[i + 1], 0.0D);
      }

      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
      GL11.glPointSize((float)prevWidth);
   }

   public void drawPoints(ArrayList points, int color, int pointWidth) {
      int prevWidth = GL11.glGetInteger(2833);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color >> 0 & 255), (byte)(color >> 24 & 255));
      GL11.glPointSize((float)pointWidth);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(0);

      for(int i = 0; i < points.size(); i += 2) {
         tessellator.addVertex(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D);
      }

      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
      GL11.glPointSize((float)prevWidth);
   }
}
