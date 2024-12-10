package pl.kosieradzki.lessons.block;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a collection of block schedules with predefined time intervals.
 * <p>
 * The Blocks class maintains a mapping between block numbers (represented by the {@code BlockNumb} enum)
 * and their respective time intervals (represented by {@code BlockHours}). Each block is associated with
 * a specific start and end time, which are defined upon instantiation of the class. The mapping is stored
 * in an internal {@code Map<BlockNumb, BlockHours>}.
 * <p>
 * Key functionality:
 * - Provides a mapping of block numbers to their time intervals.
 * - Ensures consistent and predefined duration for each block.
 */
@Getter
public class Blocks {
    public Map<BlockNumb, BlockHours> blocks;

    /**
     * Initializes the Blocks object with a predefined schedule of time intervals.
     * <p>
     * This constructor populates the `blocks` map with seven predefined time blocks,
     * associating each block number from the `BlockNumb` enumeration with a specific
     * time interval defined using the `BlockHours` class. The block-to-time mapping
     * ensures a consistent and fixed schedule for all blocks.
     * <p>
     * Each block is assigned:
     * - block1: 08:00 - 09:35
     * - block2: 09:50 - 11:25
     * - block3: 11:40 - 13:15
     * - block4: 13:30 - 15:05
     * - block5: 16:00 - 17:35
     * - block6: 17:50 - 19:25
     * - block7: 19:40 - 21:15
     */
    public Blocks() {
        blocks = new HashMap<>();
        blocks.put(BlockNumb.block1, new BlockHours("08:00", "09:35"));
        blocks.put(BlockNumb.block2, new BlockHours("09:50", "11:25"));
        blocks.put(BlockNumb.block3, new BlockHours("11:40", "13:15"));
        blocks.put(BlockNumb.block4, new BlockHours("13:30", "15:05"));
        blocks.put(BlockNumb.block5, new BlockHours("16:00", "17:35"));
        blocks.put(BlockNumb.block6, new BlockHours("17:50", "19:25"));
        blocks.put(BlockNumb.block7, new BlockHours("19:40", "21:15"));
    }
}
