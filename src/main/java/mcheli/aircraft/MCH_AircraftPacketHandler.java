package mcheli.aircraft;

import com.google.common.io.ByteArrayDataInput;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.weapon.MCH_EntityTvMissile;
import mcheli.weapon.MCH_WeaponInfoManager;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.UUID;

public class MCH_AircraftPacketHandler {

    public static void onPacketIndRotation(EntityPlayer player, ByteArrayDataInput data) {
        if (player != null && !player.worldObj.isRemote) {
            MCH_PacketIndRotation req = new MCH_PacketIndRotation();
            req.readData(data);
            if (req.entityID_Ac > 0) {
                Entity e = player.worldObj.getEntityByID(req.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    ac.setRotRoll(req.roll);
                    if (req.rollRev) {
                        MCH_Lib.DbgLog(ac.worldObj, "onPacketIndRotation Error:req.rollRev y=%.2f, p=%.2f, r=%.2f", req.yaw, req.pitch, req.roll);
                        if (ac.getRiddenByEntity() != null) {
                            ac.getRiddenByEntity().rotationYaw = req.yaw;
                            ac.getRiddenByEntity().prevRotationYaw = req.yaw;
                        }

                        for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                            Entity entity = ac.getEntityBySeatId(1 + sid);
                            if (entity != null) {
                                entity.rotationYaw += entity.rotationYaw <= 0.0F ? 180.0F : -180.0F;
                            }
                        }
                    }

                    ac.setRotYaw(req.yaw);
                    ac.setRotPitch(req.pitch);
                }

            }
        }
    }

    public static void onPacketOnMountEntity(EntityPlayer player, ByteArrayDataInput data) {
        if (player != null && player.worldObj.isRemote) {
            MCH_PacketNotifyOnMountEntity req = new MCH_PacketNotifyOnMountEntity();
            req.readData(data);
            MCH_Lib.DbgLog(player.worldObj, "onPacketOnMountEntity.rcv:%d, %d, %d, %d", W_Entity.getEntityId(player), req.entityID_Ac, req.entityID_rider, req.seatID);
            if (req.entityID_Ac > 0) {
                if (req.entityID_rider > 0) {
                    if (req.seatID >= 0) {
                        Entity e = player.worldObj.getEntityByID(req.entityID_Ac);
                        if (e instanceof MCH_EntityAircraft) {
                            MCH_Lib.DbgLog(player.worldObj, "onPacketOnMountEntity:" + W_Entity.getEntityId(player));
                            player.worldObj.getEntityByID(req.entityID_rider);
                            MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                        }

                    }
                }
            }
        }
    }

    public static void onPacketNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data) {
        if (player != null && player.worldObj.isRemote) {
            MCH_PacketNotifyAmmoNum status = new MCH_PacketNotifyAmmoNum();
            status.readData(data);
            if (status.entityID_Ac > 0) {
                Entity e = player.worldObj.getEntityByID(status.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    StringBuilder msg = new StringBuilder("onPacketNotifyAmmoNum:");
                    msg.append(ac.getAcInfo() != null ? ac.getAcInfo().displayName : "null").append(":");
                    if (status.all) {
                        msg.append("All=true, Num=").append(status.num);

                        for (int i = 0; i < ac.getWeaponNum() && i < status.num; ++i) {
                            ac.getWeapon(i).setAmmoNum(status.ammo[i]);
                            ac.getWeapon(i).setRestAllAmmoNum(status.restAmmo[i]);
                            msg.append(", [").append(status.ammo[i]).append("/").append(status.restAmmo[i]).append("]");
                        }

                        MCH_Lib.DbgLog(e.worldObj, msg.toString());
                    } else if (status.weaponID < ac.getWeaponNum()) {
                        msg.append("All=false, WeaponID=").append(status.weaponID).append(", ").append(status.ammo[0]).append(", ").append(status.restAmmo[0]);
                        ac.getWeapon(status.weaponID).setAmmoNum(status.ammo[0]);
                        ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo[0]);
                        MCH_Lib.DbgLog(e.worldObj, msg.toString());
                    } else {
                        MCH_Lib.DbgLog(e.worldObj, "Error:" + status.weaponID);
                    }
                }

            }
        }
    }

    public static void onPacketStatusRequest(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketStatusRequest req = new MCH_PacketStatusRequest();
            req.readData(data);
            if (req.entityID_AC > 0) {
                Entity e = player.worldObj.getEntityByID(req.entityID_AC);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_PacketStatusResponse.sendStatus((MCH_EntityAircraft) e, player);
                }

            }
        }
    }

    public static void onPacketIndNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketIndNotifyAmmoNum req = new MCH_PacketIndNotifyAmmoNum();
            req.readData(data);
            if (req.entityID_Ac > 0) {
                Entity e = player.worldObj.getEntityByID(req.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    if (req.weaponID >= 0) {
                        MCH_PacketNotifyAmmoNum.sendAmmoNum((MCH_EntityAircraft) e, player, req.weaponID);
                    } else {
                        MCH_PacketNotifyAmmoNum.sendAllAmmoNum((MCH_EntityAircraft) e, player);
                    }
                }

            }
        }
    }

    public static void onPacketIndReload(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketIndReload ind = new MCH_PacketIndReload();
            ind.readData(data);
            if (ind.entityID_Ac > 0) {
                Entity e = player.worldObj.getEntityByID(ind.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    MCH_Lib.DbgLog(e.worldObj, "onPacketIndReload :%s", ac.getAcInfo().displayName);
                    ac.supplyAmmo(ind.weaponID);
                }

            }
        }
    }

    public static void onPacketStatusResponse(EntityPlayer player, ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            MCH_PacketStatusResponse status = new MCH_PacketStatusResponse();
            status.readData(data);
            StringBuilder msg = new StringBuilder("onPacketStatusResponse:");
            if (status.entityID_AC > 0) {
                msg.append("EID=").append(status.entityID_AC).append(":");
                Entity e = player.worldObj.getEntityByID(status.entityID_AC);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    if (status.seatNum > 0 && status.weaponIDs != null && status.weaponIDs.length == status.seatNum) {
                        msg.append("seatNum=").append(status.seatNum).append(":");

                        for (int i = 0; i < status.seatNum; ++i) {
                            ac.updateWeaponID(i, status.weaponIDs[i]);
                            msg.append("[").append(i).append(",").append(status.weaponIDs[i]).append("]");
                        }
                    } else {
                        msg.append("Error seatNum=").append(status.seatNum);
                    }
                }

                MCH_Lib.DbgLog(true, msg.toString());
            }
        }
    }

    public static void onPacketNotifyWeaponID(EntityPlayer player, ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            MCH_PacketNotifyWeaponID status = new MCH_PacketNotifyWeaponID();
            status.readData(data);
            if (status.entityID_Ac > 0) {
                Entity e = player.worldObj.getEntityByID(status.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    if (ac.isValidSeatID(status.seatID)) {
                        ac.getWeapon(status.weaponID).setAmmoNum(status.ammo);
                        ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo);
                        MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:WeaponID=%d (%d / %d)", status.weaponID, status.ammo, status.restAmmo);
                        if (W_Lib.isClientPlayer(ac.getEntityBySeatId(status.seatID))) {
                            MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:#discard:SeatID=%d, WeaponID=%d", status.seatID, status.weaponID);
                        } else {
                            MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:SeatID=%d, WeaponID=%d", status.seatID, status.weaponID);
                            ac.updateWeaponID(status.seatID, status.weaponID);
                        }
                    }
                }

            }
        }
    }

    public static void onPacketNotifyHitBullet(EntityPlayer player, ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            MCH_PacketNotifyHitBullet status = new MCH_PacketNotifyHitBullet();
            status.readData(data);
            if (status.entityID_Ac <= 0) {
                MCH_MOD.proxy.hitBullet();
            } else {
                Entity e = player.worldObj.getEntityByID(status.entityID_Ac);
                if (e instanceof MCH_EntityAircraft) {
                    ((MCH_EntityAircraft) e).hitBullet();
                }
            }

        }
    }

    public static void onPacketSeatListRequest(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketSeatListRequest req = new MCH_PacketSeatListRequest();
            req.readData(data);
            if (req.entityID_AC > 0) {
                Entity e = player.worldObj.getEntityByID(req.entityID_AC);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_PacketSeatListResponse.sendSeatList((MCH_EntityAircraft) e, player);
                }

            }
        }
    }

    public static void onPacketNotifyTVMissileEntity(EntityPlayer player, ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            MCH_PacketNotifyTVMissileEntity packet = new MCH_PacketNotifyTVMissileEntity();
            packet.readData(data);
            if (packet.entityID_Ac <= 0) {
                return;
            }

            if (packet.entityID_TVMissile <= 0) {
                return;
            }

            Entity e = player.worldObj.getEntityByID(packet.entityID_Ac);
            if (!(e instanceof MCH_EntityAircraft)) {
                return;
            }

            MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
            e = player.worldObj.getEntityByID(packet.entityID_TVMissile);
            if (!(e instanceof MCH_EntityTvMissile)) {
                return;
            }

            ((MCH_EntityTvMissile) e).shootingEntity = player;
            ac.setTVMissile((MCH_EntityTvMissile) e);
        }

    }

    public static void onPacketSeatListResponse(EntityPlayer player, ByteArrayDataInput data) {
        if (player.worldObj.isRemote) {
            MCH_PacketSeatListResponse seatList = new MCH_PacketSeatListResponse();
            seatList.readData(data);
            if (seatList.entityID_AC > 0) {
                Entity e = player.worldObj.getEntityByID(seatList.entityID_AC);
                if (e instanceof MCH_EntityAircraft) {
                    MCH_EntityAircraft ac = (MCH_EntityAircraft) e;
                    if (seatList.seatNum > 0 && seatList.seatNum == ac.getSeats().length && seatList.seatEntityID != null && seatList.seatEntityID.length == seatList.seatNum) {
                        for (int i = 0; i < seatList.seatNum; ++i) {
                            Entity entity = player.worldObj.getEntityByID(seatList.seatEntityID[i]);
                            if (entity instanceof MCH_EntitySeat) {
                                MCH_EntitySeat seat = (MCH_EntitySeat) entity;
                                seat.seatID = i;
                                seat.setParent(ac);
                                seat.parentUniqueID = ac.getCommonUniqueId();
                                ac.setSeat(i, seat);
                            }
                        }
                    }
                }

            }
        }
    }

    public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_EntityAircraft ac;
            if (player.ridingEntity instanceof MCH_EntitySeat) {
                MCH_EntitySeat pc = (MCH_EntitySeat) player.ridingEntity;
                ac = pc.getParent();
            } else {
                ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(player);
            }

            if (ac != null) {
                MCH_PacketSeatPlayerControl pc1 = new MCH_PacketSeatPlayerControl();
                pc1.readData(data);
                if (pc1.isUnmount) {
                    ac.unmountEntityFromSeat(player);
                } else if (pc1.switchSeat > 0) {
                    if (pc1.switchSeat == 3) {
                        player.mountEntity(null);
                        ac.keepOnRideRotation = true;
                        ac.interactFirst(player, true);
                    }

                    if (pc1.switchSeat == 1) {
                        ac.switchNextSeat(player);
                    }

                    if (pc1.switchSeat == 2) {
                        ac.switchPrevSeat(player);
                    }
                } else if (pc1.parachuting) {
                    ac.unmount(player);
                }

            }
        }
    }

    public static void onPacket_ClientSetting(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketNotifyClientSetting pc = new MCH_PacketNotifyClientSetting();
            pc.readData(data);
            MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(player);
            if (ac != null) {
                int sid = ac.getSeatIdByEntity(player);
                if (sid == 0) {
                    ac.cs_dismountAll = pc.dismountAll;
                    ac.cs_heliAutoThrottleDown = pc.heliAutoThrottleDown;
                    ac.cs_planeAutoThrottleDown = pc.planeAutoThrottleDown;
                    ac.cs_tankAutoThrottleDown = pc.tankAutoThrottleDown;
                }

                ac.camera.setShaderSupport(sid, pc.shaderSupport);
            }

        }
    }

    public static void onPacketNotifyInfoReloaded(EntityPlayer player, ByteArrayDataInput data) {
        if (!player.worldObj.isRemote) {
            MCH_PacketNotifyInfoReloaded pc = new MCH_PacketNotifyInfoReloaded();
            pc.readData(data);
            MCH_EntityAircraft ac;
            int i$;
            switch (pc.type) {
                case 0:
                    ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(player);
                    if (ac != null && ac.getAcInfo() != null) {
                        String var11 = ac.getAcInfo().name;
                        WorldServer[] var12 = MinecraftServer.getServer().worldServers;
                        i$ = var12.length;

                        for (int var13 = 0; var13 < i$; ++var13) {
                            WorldServer var14 = var12[var13];
                            List var15 = var14.loadedEntityList;

                            for (Object o : var15) {
                                if (o instanceof MCH_EntityAircraft) {
                                    ac = (MCH_EntityAircraft) o;
                                    if (ac.getAcInfo() != null && ac.getAcInfo().name.equals(var11)) {
                                        ac.changeType(var11);
                                        ac.createSeats(UUID.randomUUID().toString());
                                        ac.onAcInfoReloaded();
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 1:
                    MCH_WeaponInfoManager.reload();
                    WorldServer[] arr$ = MinecraftServer.getServer().worldServers;
                    int len$ = arr$.length;

                    for (i$ = 0; i$ < len$; ++i$) {
                        WorldServer world = arr$[i$];
                        List list = world.loadedEntityList;

                        for (Object o : list) {
                            if (o instanceof MCH_EntityAircraft) {
                                ac = (MCH_EntityAircraft) o;
                                if (ac.getAcInfo() != null) {
                                    ac.changeType(ac.getAcInfo().name);
                                    ac.createSeats(UUID.randomUUID().toString());
                                }
                            }
                        }
                    }
            }

        }
    }

}
