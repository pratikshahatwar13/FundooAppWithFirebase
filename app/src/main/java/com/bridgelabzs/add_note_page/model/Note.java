package com.bridgelabzs.add_note_page.model;


public class Note {
    private String title;
    private String description;
    private String id;
    private String color;
    private String reminder;
    private String archive;

    public Note(){

    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Note(String title, Object description, Object color){

    }

    public Note(String id,String title, String description,String color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }


}
