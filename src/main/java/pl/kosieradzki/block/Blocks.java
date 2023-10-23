package pl.kosieradzki.block;

import java.util.HashMap;
import java.util.Map;

public class Blocks {

    public Map<BlockNumb, BlockHours> blocks;

    public Blocks() {
        blocks = new HashMap<>();
        blocks.put(BlockNumb.block1, new BlockHours("08:00", "09:35"));
        blocks.put(BlockNumb.block2, new BlockHours("09:50", "11:25"));
        blocks.put(BlockNumb.block3, new BlockHours("11:40", "13:15"));
        blocks.put(BlockNumb.block4, new BlockHours("13:30", "15:05"));
        blocks.put(BlockNumb.block5, new BlockHours("15:45", "17:20"));
        blocks.put(BlockNumb.block6, new BlockHours("17:35", "19:10"));
        blocks.put(BlockNumb.block7, new BlockHours("19:25", "21:00"));
    }

    public Map<BlockNumb, BlockHours> getBlocks() {
        return blocks;
    }
}
