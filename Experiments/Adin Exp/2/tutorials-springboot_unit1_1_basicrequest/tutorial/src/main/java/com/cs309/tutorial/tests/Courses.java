package com.cs309.tutorial.tests;


public class Courses {

    private String course;
    private String courseNumber;
    public Courses(){}

    public Courses(String course, String courseNumber){
        this.course = course;
        this.courseNumber = courseNumber;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public String getCourse() {
        return this.course;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseNumber() {
        return this.courseNumber;
    }
    @Override
    public String toString() {
        return course + courseNumber;
    }
}
