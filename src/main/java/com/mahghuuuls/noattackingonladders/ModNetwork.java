package com.mahghuuuls.noattackingonladders;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class ModNetwork {

    private static final SimpleNetworkWrapper CHANNEL =
        NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);

    private ModNetwork() {}

    public static void initialize() {
        CHANNEL.registerMessage(ConfigSyncHandler.class, ConfigSyncMessage.class, 0, Side.CLIENT);
    }

    public static void syncConfig(EntityPlayerMP player) {
        CHANNEL.sendTo(
            new ConfigSyncMessage(ModConfig.blockMelee, ModConfig.blockBow, ModConfig.showMessages),
            player
        );
    }

    public static final class ConfigSyncMessage implements IMessage {

        private boolean blockMelee;
        private boolean blockBow;
        private boolean showMessages;

        public ConfigSyncMessage() {}

        private ConfigSyncMessage(boolean blockMelee, boolean blockBow, boolean showMessages) {
            this.blockMelee = blockMelee;
            this.blockBow = blockBow;
            this.showMessages = showMessages;
        }

        @Override
        public void fromBytes(ByteBuf buffer) {
            blockMelee = buffer.readBoolean();
            blockBow = buffer.readBoolean();
            showMessages = buffer.readBoolean();
        }

        @Override
        public void toBytes(ByteBuf buffer) {
            buffer.writeBoolean(blockMelee);
            buffer.writeBoolean(blockBow);
            buffer.writeBoolean(showMessages);
        }
    }

    public static final class ConfigSyncHandler
        implements IMessageHandler<ConfigSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(ConfigSyncMessage message, MessageContext context) {
            ClientAttackPolicy.useServerConfig(
                message.blockMelee,
                message.blockBow,
                message.showMessages
            );
            return null;
        }
    }
}
