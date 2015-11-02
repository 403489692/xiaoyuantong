package com.jlstudio.group.bean;

/**
 * Created by Administrator on 2015/10/24.
 */
public class ContactStudent {
    private String name;
    private ContactsGrade stu_grade;

    public ContactsGrade getStu_grade() {
        return stu_grade;
    }

    public void setStu_grade(ContactsGrade stu_grade) {
        this.stu_grade = stu_grade;
    }

    public ContactStudent() {
    }

    public ContactStudent(String name, ContactsGrade stu_grade) {
        this.name = name;
        this.stu_grade = stu_grade;
    }

    public ContactStudent(String name) {
        this.name = name;
    }

    public ContactStudent(ContactsGrade stu_grade) {
        this.stu_grade = stu_grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
