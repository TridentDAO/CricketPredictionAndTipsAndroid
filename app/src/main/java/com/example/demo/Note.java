package com.example.demo;


import com.google.firebase.firestore.Exclude;

public class Note {
    private String documentId;
    private String name;
    private String number;
    private String email;
    private String date;
    public Note() {
        //public no-arg constructor needed
    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public Note(String name, String number, String email, String Date) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.date = Date;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public String getEmail() {
        return email;
    }
    public String getDate() {
        return date;
    }
}
