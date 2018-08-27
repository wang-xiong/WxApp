package com.example.rxjava_library.movie;

import java.util.List;

/**
 * Created by wangxiong on 2018/8/10.
 */

public class MovieResult {
    private String title;
    private List<Subjects> subjects;

    public String getTitle() {
        return title;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public static class Subjects {
        String id;
        String title;
        String year;

        public Subjects(String id, String title, String year) {
            this.id = id;
            this.title = title;
            this.year = year;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }
    }
}
