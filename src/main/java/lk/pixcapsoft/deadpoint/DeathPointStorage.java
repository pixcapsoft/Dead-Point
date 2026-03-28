package lk.pixcapsoft.deadpoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.UUID;

public class DeathPointStorage {

    private static HashMap<UUID, BlockPos> data = new HashMap<>();
    private static HashMap<UUID, ResourceKey<Level>> dimensionData = new HashMap<>();

    public static void store(UUID id, BlockPos pos, ResourceKey<Level> dimension) {
        data.put(id, pos);
        dimensionData.put(id, dimension);
    }

    public static BlockPos get(UUID id) {
        return data.get(id);
    }

    public static ResourceKey<Level> getDimension(UUID id) {return dimensionData.get(id);}
}
