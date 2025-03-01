package io.robgui09.rhider.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.util.FormattedCharSequence;
import io.robgui09.rhider.Config;
import javax.annotation.Nullable;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.util.StringDecomposer;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.world.scores.PlayerTeam;
import java.util.HashMap;
import net.minecraft.client.gui.Gui;
import java.util.List;
import org.joml.Matrix4f;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.network.chat.FormattedText;
import org.objectweb.asm.Opcodes;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.renderer.entity.EntityRenderer;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

/*
   @Shadow
   private final PoseStack pose = new PoseStack();

   @Shadow
   private final MultiBufferSource.BufferSource bufferSource = new RenderBuffers().bufferSource();

   

   @Overwrite
   public int drawString(Font p_283343_, @Nullable String p_281896_, int p_283569_, int p_283418_, int p_281560_, boolean p_282130_) {
      if (p_281896_ == null) {
         return 0;
      } else {
         int i = p_283343_.drawInBatch(p_281896_.replaceAll(rhider_magic,"<скрыто>"), (float)p_283569_, (float)p_283418_, p_281560_, p_282130_, this.pose.last().pose(), this.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880, p_283343_.isBidirectional());
         this.flushIfUnmanaged();
         return i;
      }
   }
*/

   private final String rhider_magic = Config.MAGIC_NUMBER_INTRODUCTION.get();
   private final String rhider_target = Config.MAGIC_TARGET_INTRODUCTION.get();
   
   @ModifyVariable(method = "renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), index = 2)
   private Component wrapComponentsInj(Component r) {
        if (Config.isMatch(rhider_magic,r.getString())) {
			return Component.literal(r.getString().replaceAll(rhider_magic,rhider_target));
		} else {
			return r;
		}
   }
   
   /*
   @Overwrite
   private float renderText(String p_273765_, float p_273532_, float p_272783_, int p_273217_, boolean p_273583_, Matrix4f p_272734_, MultiBufferSource p_272595_, Font.DisplayMode p_273610_, int p_273727_, int p_273199_) {
      Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(p_272595_, p_273532_, p_272783_, p_273217_, p_273583_, p_272734_, p_273610_, p_273199_);
      StringDecomposer.iterateFormatted(p_273765_.replaceAll(rhider_magic,"<скрыто>"), Style.EMPTY, font$stringrenderoutput);
      return font$stringrenderoutput.finish(p_273727_, p_273532_);
   }
   */
}
