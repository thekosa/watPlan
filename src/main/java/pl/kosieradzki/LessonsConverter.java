package pl.kosieradzki;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.kosieradzki.block.Block;
import pl.kosieradzki.block.BlockNumb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonsConverter {
    private final Elements lessons;
    private List<String> csvLessons;
    private final Block block = new Block();

    public LessonsConverter(Elements lessons) {
        this.lessons = lessons;
        csvLessons = new ArrayList<>();
        csvLessons.add("Temat,Lokalizacja,Data rozpoczęcia,Czas rozpoczęcia,Data zakończenia,Czas zakończenia,Przypomnienie wł./wył.,Data przypomnienia,Czas przypomnienia");
        elements2csv();
    }

    public List<String> elements2csv() {
        for (Element lesson : lessons) {
            csvLessons.add(constructName(lesson) + ","
                    + constructLocalization(lesson) + ","
                    + constructDate(lesson) + ","
                    + constructStartTime(lesson) + ","
                    + constructDate(lesson) + ","
                    + constructEndTime(lesson) + ","
                    + "Fałsz" + ","
                    + constructDate(lesson) + ","
                    + constructStartTime(lesson));
        }
        //System.out.println(csvLessons);
        return csvLessons;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(csvLessons.get(0));
        for (String s : csvLessons){
            stringBuilder.append("\n").append(s);
        }
        return stringBuilder.toString();
    }

    private String constructEndTime(Element lesson) {
        int id = getBlockId(lesson);
        return block.getBlocks().get(BlockNumb.values()[id]).getEnd();
    }

    private String constructStartTime(Element lesson) {
        int id = getBlockId(lesson);
        return block.getBlocks().get(BlockNumb.values()[id]).getStart();
    }

    private int getBlockId(Element lesson) {
        Element blockId = lesson.select(".block_id").first();
        assert blockId != null;
        return Integer.parseInt(blockId.text().substring(5));
    }

    private String constructDate(Element lesson) {
        Element date = lesson.select(".date").first();
        assert date != null;
        return date.text();
    }

    private String constructLocalization(Element lesson) {
        Element name = lesson.select(".name").first();
        assert name != null;
        return name.toString().split("<br>")[2].substring(1);
    }

    private String constructName(Element lesson) {
        Element name = lesson.select(".name").first();
        Element info = lesson.select(".info").first();

        assert info != null;
        String infoName = info.text().split("-")[0];

        assert name != null;
        String type = name.toString().split("<br>")[1].substring(1);
        String numb = "[" + name.toString().split("<br>")[3].split("\\[")[1].split("<")[0];

//        System.out.println(infoName + type + " " + numb);
        return infoName + type + " " + numb;
    }
}
