package lk.pixcapsoft.deadpoint;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;

import java.util.List;

public class DeathBookHelper {
    public static ItemStack createDeathBook (BlockPos deathpos, Player player, String deathDimName) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        MutableComponent page = Component.literal("Death Location\n\n")
                .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD)
                .append(Component.literal("X: ").withStyle(ChatFormatting.DARK_PURPLE))
                .append(Component.literal(String.valueOf(deathpos.getX())).withStyle(ChatFormatting.GRAY))
                .append(Component.literal("\nY: ").withStyle(ChatFormatting.DARK_PURPLE))
                .append(Component.literal(String.valueOf(deathpos.getY())).withStyle(ChatFormatting.GRAY))
                .append(Component.literal("\nZ: ").withStyle(ChatFormatting.DARK_PURPLE))
                .append(Component.literal(String.valueOf(deathpos.getZ())).withStyle(ChatFormatting.GRAY))
                .append(Component.literal("\nAt: ").withStyle(ChatFormatting.DARK_PURPLE))
                .append(Component.literal(deathDimName).withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal("\n\nRespawn Book Mod").withStyle(ChatFormatting.DARK_GREEN))
                .append(Component.literal("\nA mod by PixCap Soft.").withStyle(ChatFormatting.DARK_AQUA));


        WrittenBookContent content = new WrittenBookContent(
                Filterable.passThrough("Death Location"),
                String.valueOf("Respawn Book mod by PixCapSoft"),
                0,
                List.of(Filterable.passThrough(page)),
                true
        );

        book.set(DataComponents.WRITTEN_BOOK_CONTENT, content);
        return book;

    }
}
