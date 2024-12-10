package pl.kosieradzki.lessons.block;

import lombok.Getter;

@Getter
public class BlockHours {
    private final String start;
    private final String end;

    public BlockHours(String start, String end) {
        this.start = start;
        this.end = end;
    }

}
