package org.example;

import pl.kosieradzki.Lessons;
import pl.kosieradzki.LessonsConverter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new LessonsConverter(new Lessons("WCY23IY1S1").getLessons());
    }
}