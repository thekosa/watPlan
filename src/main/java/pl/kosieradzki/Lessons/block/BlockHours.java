package pl.kosieradzki.Lessons.block;

public class BlockHours {
    private String start;
    private String end;

    public BlockHours(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart(){
        return start;
    }

    public String getEnd(){
        return end;
    }
}
