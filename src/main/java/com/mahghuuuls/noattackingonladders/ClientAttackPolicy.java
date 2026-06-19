package com.mahghuuuls.noattackingonladders;

import net.minecraft.entity.player.EntityPlayer;

public final class ClientAttackPolicy {

    private static volatile boolean blockMelee = ModConfig.blockMelee;
    private static volatile boolean blockBow = ModConfig.blockBow;
    private static volatile boolean showMessages = ModConfig.showMessages;

    private ClientAttackPolicy() {}

    public static boolean shouldBlockMelee(EntityPlayer player) {
        return blockMelee && ClimbingAttackHandler.isActivelyClimbing(player);
    }

    public static boolean shouldBlockBow(EntityPlayer player) {
        return blockBow && ClimbingAttackHandler.isActivelyClimbing(player);
    }

    public static void showMeleeBlockedMessage(EntityPlayer player) {
        if (showMessages) {
            ClimbingAttackHandler.showMeleeBlockedMessage(player);
        }
    }

    public static void showBowBlockedMessage(EntityPlayer player) {
        if (showMessages) {
            ClimbingAttackHandler.showBowBlockedMessage(player);
        }
    }

    public static void useServerConfig(
        boolean serverBlocksMelee,
        boolean serverBlocksBow,
        boolean serverShowsMessages
    ) {
        blockMelee = serverBlocksMelee;
        blockBow = serverBlocksBow;
        showMessages = serverShowsMessages;
    }

    public static void useLocalConfig() {
        useServerConfig(ModConfig.blockMelee, ModConfig.blockBow, ModConfig.showMessages);
    }
}
