package lk.pixcapsoft.deadpoint.block;

import lk.pixcapsoft.deadpoint.DeadPoint;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

import java.util.function.Function;

public class DeadPointGravestone{

    public static final Block DEADPOINT_GRAVESTONE = register(
            "deadpoint_gravestone",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(0.5f).noOcclusion().lightLevel(state -> 14),
            true
    );

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {

        ResourceKey<Block> blockKey = KeyOfBlock(name);
        Block block = blockFactory.apply(settings.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = KeyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

    }
    private static ResourceKey<Block> KeyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(DeadPoint.MOD_ID, name));
    }
    private static ResourceKey<Item> KeyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DeadPoint.MOD_ID, name));
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.accept(DEADPOINT_GRAVESTONE.asItem());
        });
    }
}
