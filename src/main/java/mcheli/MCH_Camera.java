package mcheli;

import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityRenderer;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class MCH_Camera {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_NIGHTVISION = 1;
    public static final int MODE_THERMALVISION = 2;
    public static int currentCameraMode = 0;
    private final World worldObj;
    public double posX;
    public double posY;
    public double posZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public float partRotationYaw;
    public float partRotationPitch;
    public float prevPartRotationYaw;
    public float prevPartRotationPitch;
    private float zoom;
    private int[] mode;
    private boolean[] canUseShader;
    private int[] lastMode;
    private int lastZoomDir;


    public MCH_Camera(World w, Entity p) {
        this.worldObj = w;
        this.mode = new int[]{0, 0};
        this.zoom = 1.0F;
        this.lastMode = new int[this.getUserMax()];
        this.lastZoomDir = 0;
        this.canUseShader = new boolean[this.getUserMax()];
    }

    public MCH_Camera(World w, Entity p, double x, double y, double z) {
        this(w, p);
        this.setPosition(x, y, z);
        this.setCameraZoom(1.0F);
    }

    public int getUserMax() {
        return this.mode.length;
    }

    public void initCamera(int uid, Entity viewer) {
        this.setCameraZoom(1.0F);
        this.setMode(uid, 0);
        this.updateViewer(uid, viewer);
    }

    public void setMode(int uid, int m) {
        if (this.isValidUid(uid)) {
            this.mode[uid] = m < 0 ? 0 : m % this.getModeNum(uid);
            switch (this.mode[uid]) {
                case 0:
                    if (this.worldObj.isRemote) {
                        W_EntityRenderer.deactivateShader();
                    }
                    break;
                case 1:
                    if (this.worldObj.isRemote) {
                        W_EntityRenderer.activateShader("nightvision");
                    }
                    break;
                case 2:
                    if (this.worldObj.isRemote) {
                        W_EntityRenderer.activateShader("thermal");
                    }
                    break;
            }
            currentCameraMode = mode[uid];
        }
    }

    public void setShaderSupport(int uid, Boolean b) {
        if (this.isValidUid(uid)) {
            this.setMode(uid, 0);
            this.canUseShader[uid] = b;
        }

    }

    public boolean isValidUid(int uid) {
        return uid >= 0 && uid < this.getUserMax();
    }

    public int getModeNum(int uid) {
        return !this.isValidUid(uid) ? 2 : (this.canUseShader[uid] ? 3 : 2);
    }

    public int getMode(int uid) {
        return this.isValidUid(uid) ? this.mode[uid] : 0;
    }

    public String getModeName(int uid) {
        return this.getMode(uid) == 1 ? "NIGHT VISION" : (this.getMode(uid) == 2 ? "THERMAL VISION" : "");
    }

    public void updateViewer(int uid, Entity viewer) {
        if (this.isValidUid(uid) && viewer != null) {
            if (W_Lib.isEntityLivingBase(viewer) && !viewer.isDead) {
                PotionEffect pe;
                if ((this.getMode(uid) == 0 || this.getMode(uid) == 2) && this.lastMode[uid] != 0) {
                    pe = W_Entity.getActivePotionEffect(viewer, Potion.nightVision);
                    if (pe != null && pe.getDuration() > 0 && pe.getDuration() < 500) {
                        if (viewer.worldObj.isRemote) {
                            W_Entity.removePotionEffectClient(viewer, Potion.nightVision.id);
                        } else {
                            W_Entity.removePotionEffect(viewer, Potion.nightVision.id);
                        }
                    }
                }

                if (this.getMode(uid) == 1) {
                    pe = W_Entity.getActivePotionEffect(viewer, Potion.nightVision);
                    if ((pe == null || pe.getDuration() < 500) && !viewer.worldObj.isRemote) {
                        W_Entity.addPotionEffect(viewer, new PotionEffect(Potion.nightVision.id, 250, 0, true));
                    }
                }
            }

            this.lastMode[uid] = this.getMode(uid);
        }
    }

    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public float getCameraZoom() {
        return this.zoom;
    }

    public void setCameraZoom(float z) {
        float prevZoom = this.zoom;
        this.zoom = Math.max(z, 1.0F);
        if (this.zoom > prevZoom) {
            this.lastZoomDir = 1;
        } else if (this.zoom < prevZoom) {
            this.lastZoomDir = -1;
        } else {
            this.lastZoomDir = 0;
        }

    }
}
