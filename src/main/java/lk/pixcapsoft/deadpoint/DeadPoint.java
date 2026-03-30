package lk.pixcapsoft.deadpoint;

import lk.pixcapsoft.deadpoint.block.DeadPointGravestone;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class DeadPoint implements ModInitializer {
	public static final String MOD_ID = "dead-point";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        registerDeath();
        registerRespawn();
        DeadPointGravestone.initialize();

	}

    public void registerDeath() {
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if (livingEntity instanceof Player player) {
                BlockPos deathpos = player.blockPosition();
                double x = deathpos.getX();
                double y = deathpos.getY();
                double z = deathpos.getZ();

                ServerLevel level = (ServerLevel) player.level();

                DeathPointStorage.store(player.getUUID(), deathpos, player.level().dimension());
                if (!level.getServer().isDedicatedServer()) {
                    level.setBlockAndUpdate(deathpos, DeadPointGravestone.DEADPOINT_GRAVESTONE.defaultBlockState());
                }

            }
        } );
    }

    private String formatDimension(ResourceKey<Level> dimension) {
        if (dimension.equals(Level.OVERWORLD)) return "Overworld";
        if (dimension.equals(Level.NETHER)) return "Nether";
        if (dimension.equals(Level.END)) return "End";
        return "Custom Dimension";
    }

    public void registerRespawn() {
        ServerPlayerEvents.AFTER_RESPAWN.register(((serverPlayer, serverPlayer1, b) -> {
            var coords = DeathPointStorage.get(serverPlayer.getUUID());

            if (coords != null) {

                //sendMessage(serverPlayer);
                boolean keepInventory = serverPlayer.level()
                        .getGameRules()
                        .get(GameRules.KEEP_INVENTORY);

                ServerLevel level = (ServerLevel) serverPlayer1.level();

                if (level.getServer().isDedicatedServer()) {
                    serverPlayer1.sendSystemMessage(Component.literal("We can only spawn gravestone in singleplayer world. Currently you are on multiplayer world. - Dead Point Mod").withStyle(ChatFormatting.RED));
                }

                if (keepInventory) {
                    serverPlayer1.sendSystemMessage(
                            Component.literal("You died at ")
                                    .append(Component.literal(
                                            "X: " + coords.getX() + " Y: " + coords.getY() + " Z: " + coords.getZ()
                                    ).withStyle(ChatFormatting.YELLOW))
                    );
                } else {
                    BlockPos deathpos = DeathPointStorage.get(serverPlayer.getUUID());
                    ResourceKey<Level> deathDimension = DeathPointStorage.getDimension(serverPlayer.getUUID());
                    String deathDimName = formatDimension(deathDimension);
                    ItemStack deathbook = DeathBookHelper.createDeathBook(deathpos, serverPlayer, deathDimName);
                    serverPlayer1.getInventory().setItem(0, deathbook);
                }
            }
        }));
    }

}