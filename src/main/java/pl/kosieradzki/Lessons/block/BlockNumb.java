package pl.kosieradzki.lessons.block;

/**
 * Represents the available enumerations for block numbers.
 * <p>
 * This enum defines a sequence of ordered blocks commonly used to represent
 * specific time blocks in a schedule. Each block corresponds to a time interval
 * defined elsewhere, for instance, in the {@code Blocks} class through the
 * {@code BlockHours} association.
 * <p>
 * Usage of this enum includes:
 * - Mapping blocks to time intervals.
 * - Determining the order of blocks.
 * - Identifying specific blocks in relation to entities like lessons or events.
 * <p>
 * Blocks included:
 * - block1: The first block.
 * - block2: The second block.
 * - block3: The third block.
 * - block4: The fourth block.
 * - block5: The fifth block.
 * - block6: The sixth block.
 * - block7: The seventh block.
 */
public enum BlockNumb {
    block1, block2, block3, block4, block5, block6, block7
}
