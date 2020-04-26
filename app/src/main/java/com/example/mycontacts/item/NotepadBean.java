package com.example.mycontacts.item;

public class NotepadBean {
    private  String id;
    private  String  notepadContent; //记录的姓名
    private  String  mobileContent; //记录的号码
    private  String notepadTime;    //保存记录的时间

    public String getId() {
        return id;
    }

    public String getMoblieContent() {
        return mobileContent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoblieContent(String moblieContent) {
        this.mobileContent = moblieContent;
    }

    public String getNotepadContent() {
        return notepadContent;
    }

    public void setNotepadContent(String notepadContent) {
        this.notepadContent = notepadContent;
    }

    public String getNotepadTime() {
        return notepadTime;
    }

    public void setNotepadTime(String notepadTime) {
        this.notepadTime = notepadTime;
    }
}
