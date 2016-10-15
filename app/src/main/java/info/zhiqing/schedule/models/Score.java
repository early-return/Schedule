package info.zhiqing.schedule.models;

import java.io.Serializable;

/**
 * Created by zhiqing on 16-10-13.
 */

public class Score implements Serializable{
    private String year;
    private String semester;
    private String code;
    private String name;
    private String type;
    private float credit;
    private float usual;
    private float exam;
    private float score;
    private float resit;

    public Score() {
    }

    public Score(String year, String semester, String code, String name, String type, float credit, float usual, float exam, float score, float resit) {
        this.year = year;
        this.semester = semester;
        this.code = code;
        this.name = name;
        this.type = type;
        this.credit = credit;
        this.usual = usual;
        this.exam = exam;
        this.score = score;
        this.resit = resit;
    }

    @Override
    public String toString() {
        return "Score{" +
                "year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", credit=" + credit +
                ", usual=" + usual +
                ", exam=" + exam +
                ", score=" + score +
                ", resit=" + resit +
                '}';
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getUsual() {
        return usual;
    }

    public void setUsual(float usual) {
        this.usual = usual;
    }

    public float getExam() {
        return exam;
    }

    public void setExam(float exam) {
        this.exam = exam;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getResit() {
        return resit;
    }

    public void setResit(float resit) {
        this.resit = resit;
    }
}
