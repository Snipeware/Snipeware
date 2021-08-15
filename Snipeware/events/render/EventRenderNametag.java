package Snipeware.events.render;

import Snipeware.events.Cancellable;
import net.minecraft.entity.EntityLivingBase;

public final class EventRenderNametag extends Cancellable {
   private final EntityLivingBase entity;
   private String renderedName;

   public EventRenderNametag(EntityLivingBase entity, String renderedName) {
      this.entity = entity;
      this.renderedName = renderedName;
   }

   public EntityLivingBase getEntity() {
      return this.entity;
   }

   public String getRenderedName() {
      return this.renderedName;
   }

   public void setRenderedName(String renderedName) {
      this.renderedName = renderedName;
   }
}
