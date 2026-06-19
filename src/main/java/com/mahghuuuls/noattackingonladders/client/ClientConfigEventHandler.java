package com.mahghuuuls.noattackingonladders.client;

import com.mahghuuuls.noattackingonladders.ClientAttackPolicy;
import com.mahghuuuls.noattackingonladders.Tags;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ClientConfigEventHandler {

    private ClientConfigEventHandler() {}

    public static void register() {
        ClientConfigEventHandler handler = new ClientConfigEventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Tags.MOD_ID.equals(event.getModID())) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            ClientAttackPolicy.useLocalConfig();
        }
    }

    @SubscribeEvent
    public void onDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ClientAttackPolicy.useLocalConfig();
    }
}
