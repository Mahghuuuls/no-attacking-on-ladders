package com.mahghuuuls.noattackingonladders;

import com.mahghuuuls.noattackingonladders.client.ClientConfigEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = Tags.MOD_ID,
    name = Tags.MOD_NAME,
    version = Tags.VERSION,
    dependencies = "required-after:mixinbooter"
)
public final class NoAttackingOnLaddersMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModNetwork.initialize();
        ClimbingAttackHandler attackHandler = new ClimbingAttackHandler();
        MinecraftForge.EVENT_BUS.register(attackHandler);
        FMLCommonHandler.instance().bus().register(attackHandler);

        if (event.getSide().isClient()) {
            ClientConfigEventHandler.register();
        }
    }
}
