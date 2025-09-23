package mcheli.throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mcheli.MCH_BaseInfo;
import mcheli.MCH_Color;
import mcheli.wrapper.W_Item;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.IModelCustom;

public class MCH_ThrowableInfo extends MCH_BaseInfo {

   public final String name;
   public String displayName;
   public HashMap displayNameLang;
   public int itemID;
   public W_Item item;
   public List recipeString;
   public List recipe;
   public boolean isShapedRecipe;
   public int power;
   public float acceleration;
   public float accelerationInWater;
   public float dispenseAcceleration;
   public int explosion;
   public int explosionBlock;
   public int delayFuse;
   public float bound;
   public int timeFuse;
   public boolean flaming;
   public int stackSize;
   public float soundVolume;
   public float soundPitch;
   public float proximityFuseDist;
   public float accuracy;
   public int aliveTime;
   public int bomblet;
   public float bombletDiff;
   public IModelCustom model;
   public float smokeSize;
   public int smokeNum;
   public float smokeVelocityVertical;
   public float smokeVelocityHorizontal;
   public float gravity;
   public float gravityInWater;
   public String particleName;
   public boolean disableSmoke;
   public MCH_Color smokeColor;
   public boolean handFlare = false;


   public MCH_ThrowableInfo(String name) {
      this.name = name;
      this.displayName = name;
      this.displayNameLang = new HashMap();
      this.itemID = 0;
      this.item = null;
      this.recipeString = new ArrayList();
      this.recipe = new ArrayList();
      this.isShapedRecipe = true;
      this.power = 0;
      this.acceleration = 1.0F;
      this.accelerationInWater = 1.0F;
      this.dispenseAcceleration = 1.0F;
      this.explosion = 0;
      this.delayFuse = 0;
      this.bound = 0.2F;
      this.timeFuse = 0;
      this.flaming = false;
      this.stackSize = 1;
      this.soundVolume = 1.0F;
      this.soundPitch = 1.0F;
      this.proximityFuseDist = 0.0F;
      this.accuracy = 0.0F;
      this.aliveTime = 10;
      this.bomblet = 0;
      this.bombletDiff = 0.3F;
      this.model = null;
      this.smokeSize = 10.0F;
      this.smokeNum = 0;
      this.smokeVelocityVertical = 1.0F;
      this.smokeVelocityHorizontal = 1.0F;
      this.gravity = 0.0F;
      this.gravityInWater = -0.04F;
      this.particleName = "explode";
      this.disableSmoke = true;
      this.smokeColor = new MCH_Color();
   }

   public void checkData() {
      this.timeFuse *= 20;
      this.aliveTime *= 20;
      this.delayFuse *= 20;
   }

   public void loadItemData(String item, String data) {
      if(item.compareTo("displayname") == 0) {
         this.displayName = data;
      } else {
         String[] s;
         if(item.compareTo("adddisplayname") == 0) {
            s = data.split("\\s*,\\s*");
            if(s != null && s.length == 2) {
               this.displayNameLang.put(s[0].trim(), s[1].trim());
            }
         } else if(item.equalsIgnoreCase("itemid")) {
            this.itemID = this.toInt(data, 0, '\uffff');
         } else if(item.compareTo("addrecipe") != 0 && item.compareTo("addshapelessrecipe") != 0) {
            if(item.equalsIgnoreCase("power")) {
               this.power = this.toInt(data);
            } else if(item.equalsIgnoreCase("acceleration")) {
               this.acceleration = this.toFloat(data, 0.0F, 100.0F);
            } else if(item.equalsIgnoreCase("accelerationinwater")) {
               this.accelerationInWater = this.toFloat(data, 0.0F, 100.0F);
            } else if(item.equalsIgnoreCase("DispenseAcceleration")) {
               this.dispenseAcceleration = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("explosion")) {
               this.explosion = this.toInt(data, 0, 50);
            } else if(item.equalsIgnoreCase("DelayFuse")) {
               this.delayFuse = this.toInt(data, 0, 100000);
            } else if(item.equalsIgnoreCase("Bound")) {
               this.bound = this.toFloat(data, 0.0F, 100000.0F);
            } else if(item.equalsIgnoreCase("TimeFuse")) {
               this.timeFuse = this.toInt(data, 0, 100000);
            } else if(item.equalsIgnoreCase("flaming")) {
               this.flaming = this.toBool(data);
            } else if(item.equalsIgnoreCase("StackSize")) {
               this.stackSize = this.toInt(data, 1, 64);
            } else if(item.equalsIgnoreCase("soundvolume")) {
               this.soundVolume = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("soundpitch")) {
               this.soundPitch = this.toFloat(data, 0.0F, 1.0F);
            } else if(item.equalsIgnoreCase("proximityfusedist")) {
               this.proximityFuseDist = this.toFloat(data, 0.0F, 20.0F);
            } else if(item.equalsIgnoreCase("accuracy")) {
               this.accuracy = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("aliveTime")) {
               this.aliveTime = this.toInt(data, 0, 1000000);
            } else if(item.equalsIgnoreCase("bomblet")) {
               this.bomblet = this.toInt(data, 0, 1000);
            } else if(item.equalsIgnoreCase("BombletDiff")) {
               this.bombletDiff = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("SmokeSize")) {
               this.smokeSize = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("SmokeNum")) {
               this.smokeNum = this.toInt(data, 0, 1000);
            } else if(item.equalsIgnoreCase("SmokeVelocityVertical")) {
               this.smokeVelocityVertical = this.toFloat(data, -100.0F, 100.0F);
            } else if(item.equalsIgnoreCase("SmokeVelocityHorizontal")) {
               this.smokeVelocityHorizontal = this.toFloat(data, 0.0F, 1000.0F);
            } else if(item.equalsIgnoreCase("gravity")) {
               this.gravity = this.toFloat(data, -50.0F, 50.0F);
            } else if(item.equalsIgnoreCase("gravityInWater")) {
               this.gravityInWater = this.toFloat(data, -50.0F, 50.0F);
            } else if(item.equalsIgnoreCase("particle")) {
               this.particleName = data.toLowerCase().trim();
               if(this.particleName.equalsIgnoreCase("none")) {
                  this.particleName = "";
               }
            } else if(item.equalsIgnoreCase("DisableSmoke")) {
               this.disableSmoke = this.toBool(data);
            } else if(item.equalsIgnoreCase("SmokeColor")) {
               s = data.split("\\s*,\\s*");
               if(s.length >= 3) {
                  float f = 0.003921569F;
                  this.smokeColor = new MCH_Color(1.0F, 0.003921569F * (float)this.toInt(s[0], 0, 255), 0.003921569F * (float)this.toInt(s[1], 0, 255), 0.003921569F * (float)this.toInt(s[2], 0, 255));
               }
            } else if(item.equalsIgnoreCase("HandFlare")) {
               this.handFlare = this.toBool(data);
            }
         } else {
            this.isShapedRecipe = item.equalsIgnoreCase("addrecipe");
            this.recipeString.add(data.toUpperCase());
         }
      }

   }

   public class RoundItem {

      public final int num;
      public final Item item;


      public RoundItem(int n, Item i) {
         this.num = n;
         this.item = i;
      }
   }
}
