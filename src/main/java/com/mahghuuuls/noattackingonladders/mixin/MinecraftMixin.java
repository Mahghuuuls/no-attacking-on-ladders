package com.mahghuuuls.noattackingonladders.mixin;

import com.mahghuuuls.noattackingonladders.ClientAttackPolicy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow public EntityPlayerSP player;
    @Shadow public RayTraceResult objectMouseOver;

    @Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
    private void noAttackingOnLadders$blockClimbingAttack(CallbackInfo callback) {
        if (objectMouseOver == null || objectMouseOver.typeOfHit != RayTraceResult.Type.ENTITY) {
            return;
        }

        if (ClientAttackPolicy.shouldBlockMelee(player)) {
            ClientAttackPolicy.showMeleeBlockedMessage(player);
            callback.cancel();
        }
    }
}
