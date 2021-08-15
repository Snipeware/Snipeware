package Snipeware.module.impl.visuals;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import Snipeware.api.annotations.Handler;
import Snipeware.events.render.EventModelUpdate;
import Snipeware.events.render.EventRender3D;
import Snipeware.module.Module;
import Snipeware.util.visual.RenderUtil;
import Snipeware.value.impl.BooleanValue;

public final class SkeletonESP extends Module {
	
   private final Map playerRotationMap = new WeakHashMap();

   public SkeletonESP() {
	  super("SkeletonESP", 0, ModuleCategory.VISUALS);
   }

   @Handler
   public final void onModelUpdate(final EventModelUpdate event) {
      ModelPlayer model = event.getModel();
      this.playerRotationMap.put(event.getPlayer(), new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
   }

   @Handler
   public final void onRender3D(final EventRender3D event) {
      this.setupRender(true);
      GL11.glEnable(2903);
      GL11.glDisable(2848);
      this.playerRotationMap.keySet().removeIf(this::contain);
      Map playerRotationMap = this.playerRotationMap;
      List worldPlayers = mc.theWorld.playerEntities;
      Object[] players = playerRotationMap.keySet().toArray();
      int i = 0;

      for(int playersLength = players.length; i < playersLength; ++i) {
         EntityPlayer player = (EntityPlayer)players[i];
         float[][] entPos = (float[][])playerRotationMap.get(player);
         if (entPos != null && player.getEntityId() != -1488 && player.isEntityAlive() && RenderUtil.isInViewFrustrum((Entity)player) && !player.isDead && player != mc.thePlayer && !player.isPlayerSleeping() && !player.isInvisible()) {
            GL11.glPushMatrix();
            float[][] modelRotations = (float[][])playerRotationMap.get(player);
            GL11.glLineWidth(1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            double x = RenderUtil.interpolateScale(player.posX, player.lastTickPosX, (float)event.getPartialTicks()) - mc.getRenderManager().getRenderPosX();
            double y = RenderUtil.interpolateScale(player.posY, player.lastTickPosY, (float)event.getPartialTicks()) - mc.getRenderManager().getRenderPosY();
            double z = RenderUtil.interpolateScale(player.posZ, player.lastTickPosZ, (float)event.getPartialTicks()) - mc.getRenderManager().getRenderPosZ();
            GL11.glTranslated(x, y, z);
            float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
            GL11.glRotatef(-bodyYawOffset, 0.0F, 1.0F, 0.0F);
            GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? -0.235D : 0.0D);
            float legHeight = player.isSneaking() ? 0.6F : 0.75F;
            float rad = 57.29578F;
            GL11.glPushMatrix();
            GL11.glTranslated(-0.125D, (double)legHeight, 0.0D);
            if (modelRotations[3][0] != 0.0F) {
               GL11.glRotatef(modelRotations[3][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[3][1] != 0.0F) {
               GL11.glRotatef(modelRotations[3][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[3][2] != 0.0F) {
               GL11.glRotatef(modelRotations[3][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.125D, (double)legHeight, 0.0D);
            if (modelRotations[4][0] != 0.0F) {
               GL11.glRotatef(modelRotations[4][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[4][1] != 0.0F) {
               GL11.glRotatef(modelRotations[4][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[4][2] != 0.0F) {
               GL11.glRotatef(modelRotations[4][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? 0.25D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, player.isSneaking() ? -0.05D : 0.0D, player.isSneaking() ? -0.01725D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(-0.375D, (double)legHeight + 0.55D, 0.0D);
            if (modelRotations[1][0] != 0.0F) {
               GL11.glRotatef(modelRotations[1][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[1][1] != 0.0F) {
               GL11.glRotatef(modelRotations[1][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[1][2] != 0.0F) {
               GL11.glRotatef(-modelRotations[1][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, -0.5D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375D, (double)legHeight + 0.55D, 0.0D);
            if (modelRotations[2][0] != 0.0F) {
               GL11.glRotatef(modelRotations[2][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            if (modelRotations[2][1] != 0.0F) {
               GL11.glRotatef(modelRotations[2][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
            }

            if (modelRotations[2][2] != 0.0F) {
               GL11.glRotatef(-modelRotations[2][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, -0.5D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0F, 1.0F, 0.0F);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
            if (modelRotations[0][0] != 0.0F) {
               GL11.glRotatef(modelRotations[0][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, 0.3D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0.0D, player.isSneaking() ? -0.16175D : 0.0D, player.isSneaking() ? -0.48025D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
            GL11.glVertex3d(0.125D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, 0.55D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
            GL11.glVertex3d(0.375D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
         }
      }

      this.setupRender(false);
   }

   private void setupRender(boolean start) {
      if (start) {
    	  RenderUtil.startSmooth();

         GL11.glDisable(2929);
         GL11.glDisable(3553);
      } else {
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         RenderUtil.endSmooth();
      }

      GL11.glDepthMask(!start);
   }

   private boolean contain(Object o) {
      return !mc.theWorld.playerEntities.contains((EntityPlayer) o);
   }
}
