package com.bridgelabzs.add_label_page.model;

public class Label {
    private int labelId;
    private int userId;
    private String labelName;

    public  Label(String labelName, int userId){
        this.labelName = labelName;
        this.userId = userId;
    }

    public Label(String labelName) {
        this.labelName =  labelName;
    }


    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Label Id: ").append(labelId).append("\n")
                .append(" Label Name: " ).append(labelName).append("\n")
                .append("UserModel Id: ").append(userId).append("\n");
        return stringBuilder.toString();
    }
}
