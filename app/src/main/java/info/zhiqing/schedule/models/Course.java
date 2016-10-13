package info.zhiqing.schedule.models;

/**
 * Created by zhiqing on 16-10-12.
 */

public class Course {
    private String name;
    private String type;
    private String week_start;
    private String week_end;
    private String weekday;
    private String time_start;
    private String time_end;
    private String teacher;
    private String classroom;

    public Course() {
    }

    public Course(String name, String type, String week_start, String week_end, String weekday, String time_start, String time_end, String teacher, String classroom) {
        this.name = name;
        this.type = type;
        this.week_start = week_start;
        this.week_end = week_end;
        this.weekday = weekday;
        this.time_start = time_start;
        this.time_end = time_end;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeek_start() {
        return week_start;
    }

    public void setWeek_start(String week_start) {
        this.week_start = week_start;
    }

    public String getWeek_end() {
        return week_end;
    }

    public void setWeek_end(String week_end) {
        this.week_end = week_end;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
