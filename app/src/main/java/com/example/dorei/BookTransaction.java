package com.example.dorei;

public class BookTransaction {

    private String subject;
    private String title;
    private int user_id;
    private int isbn;
    private int completed;

    public BookTransaction(String subject, String title, int user_id, int isbn, int completed) {
        this.subject = subject;
        this.title = title;
        this.user_id = user_id;
        this.isbn = isbn;
        this.completed = completed;
    }

    public BookTransaction() {
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getCompleted() {
        return completed;
    }
}
