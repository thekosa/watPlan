package pl.kosieradzki.Lessons;

import com.google.api.client.util.DateTime;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.kosieradzki.Calendar.CalendarEventEntity;
import pl.kosieradzki.Lessons.Block.Blocks;
import pl.kosieradzki.Lessons.Block.BlockNumb;

import java.util.ArrayList;
import java.util.List;

public class LessonsConverter {
    private final Blocks blocks = new Blocks();

    public List<CalendarEventEntity> elements2CEE(Elements lessons) {
        List<CalendarEventEntity> cee = new ArrayList<>();
        for (Element lesson : lessons) {
            cee.add(new CalendarEventEntity(constructName(lesson),
                    constructLocalization(lesson),
                    constructStartDateTime(lesson),
                    constructEndDateTime(lesson)));
        }
        return cee;
    }

    public List<String> elements2csv(Elements lessons) {
        List<String> csvLessons = new ArrayList<>();
        csvLessons.add("Temat,Lokalizacja,Data rozpoczęcia,Czas rozpoczęcia,Data zakończenia,Czas zakończenia,Przypomnienie wł./wył.,Data przypomnienia,Czas przypomnienia");
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

    /*
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(csvLessons.get(0));
            for (String s : csvLessons) {
                stringBuilder.append("\n").append(s);
            }
            return stringBuilder.toString();
        }
    */
    private DateTime constructStartDateTime(Element lesson) {
        return new DateTime(constructDate(lesson) + "T" + constructStartTime(lesson) + ":00Z");
    }

    private DateTime constructEndDateTime(Element lesson) {
        return new DateTime(constructDate(lesson) + "T" + constructEndTime(lesson) + ":00Z");
    }

    private String constructEndTime(Element lesson) {
        int id = getBlockId(lesson);
        return blocks.getBlocks().get(BlockNumb.values()[id]).getEnd();
    }

    private String constructStartTime(Element lesson) {
        int id = getBlockId(lesson);
        return blocks.getBlocks().get(BlockNumb.values()[id]).getStart();
    }


    private String constructDate(Element lesson) {
        Element date = lesson.select(".date").first();
        assert date != null;
        // System.out.println("to tu podkreślniki? "+date);
        return date.text().replaceAll("_", "-");
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

    private int getBlockId(Element lesson) {
        Element blockId = lesson.select(".block_id").first();
        assert blockId != null;
        return Integer.parseInt(blockId.text().substring(5)) - 1;
    }

    public String getLessonName(Element lesson) {
        Element info = lesson.select(".info").first();

        assert info != null;
        return info.text().split("-")[0].trim().toLowerCase();
    }
}
