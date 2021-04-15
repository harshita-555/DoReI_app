package com.example.dorei;

public class Book  {

    private int isbn;
    private String author;
    private String subject;
    private String title;
    private int edition;
    private int grade;

    public Book(int isbn, String author, String subject, String title, int edition, int grade) {
        this.isbn = isbn;
        this.author = author;
        this.subject = subject;
        this.title = title;
        this.edition = edition;
        this.grade = grade;
    }

    public Book() {
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
