package pl.kosieradzki.lessons.block;

import lombok.Getter;

/**
 * Represents a time block with specified start and end times.
 * <p>
 * This class is primarily used to define the time duration
 * for a specific block of a schedule. Instances of this class
 * are immutable, ensuring that the start and end times cannot
 * be modified after creation.
 * <p>
 * It is typically used in conjunction with other classes such as
 * {@code Blocks} and enumerations like {@code BlockNumb} to manage
 * scheduled blocks of time.
 */

@Getter
public class BlockHours {
    private final String start;
    private final String end;

    /**
     * Constructs a BlockHours instance with the specified start and end times.
     *
     * @param start the start time of the block, in the format "HH:mm"
     * @param end   the end time of the block, in the format "HH:mm"
     */
    public BlockHours(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
