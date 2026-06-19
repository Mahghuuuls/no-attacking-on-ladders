package com.mahghuuuls.noattackingonladders;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ClimbingAttackHandler {

    private static final String MELEE_BLOCKED_MESSAGE = "You can't attack while climbing!";
    private static final String BOW_BLOCKED_MESSAGE = "You can't shoot while climbing!";

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!shouldBlockMelee(player)) {
            return;
        }

        event.setCanceled(true);
        if (!player.world.isRemote) {
            showConfiguredMessage(player, MELEE_BLOCKED_MESSAGE);
        }
    }

    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        boolean canDrawBow = event.hasAmmo() || player.capabilities.isCreativeMode;
        boolean shouldBlock = player.world.isRemote
            ? ClientAttackPolicy.shouldBlockBow(player)
            : ModConfig.blockBow && isActivelyClimbing(player);

        if (!canDrawBow || !shouldBlock) {
            return;
        }

        event.setAction(new ActionResult<>(EnumActionResult.FAIL, event.getBow()));
        if (!player.world.isRemote) {
            showConfiguredMessage(player, BOW_BLOCKED_MESSAGE);
        } else {
            ClientAttackPolicy.showBowBlockedMessage(player);
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!ModConfig.blockBow || !isActivelyClimbing(player)) {
            return;
        }

        event.setCanceled(true);
        if (!player.world.isRemote) {
            showConfiguredMessage(player, BOW_BLOCKED_MESSAGE);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            ModNetwork.syncConfig((EntityPlayerMP) event.player);
        }
    }

    public static boolean shouldBlockMelee(EntityPlayer player) {
        return ModConfig.blockMelee && isActivelyClimbing(player);
    }

    public static void showMeleeBlockedMessage(EntityPlayer player) {
        player.sendStatusMessage(new TextComponentString(MELEE_BLOCKED_MESSAGE), true);
    }

    public static void showBowBlockedMessage(EntityPlayer player) {
        player.sendStatusMessage(new TextComponentString(BOW_BLOCKED_MESSAGE), true);
    }

    public static boolean isActivelyClimbing(EntityPlayer player) {
        return player != null && player.isOnLadder() && !player.onGround;
    }

    private static void showConfiguredMessage(EntityPlayer player, String message) {
        if (ModConfig.showMessages) {
            player.sendStatusMessage(new TextComponentString(message), true);
        }
    }
}
