package com.example.dorei;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String USER_ID = "ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_TABLE = "USER_TABLE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "myDatabase.db", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " ( " + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT NOT NULL, " + USER_PASSWORD + " TEXT NOT NULL," + USER_EMAIL + " NOT NULL," +
                " HOUSENO NOT NULL, STREETNO NOT NULL,STREETNAME NOT NULL,CITY NOT NULL,STATE NOT NULL,PIN_CODE NOT NULL)";
        String createBookTableStatement = "CREATE TABLE BOOK_TABLE ( ISBN INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT NOT NULL, AUTHOR TEXT NOT NULL, SUBJECT TEXT NOT NULL,EDITION INTEGER NOT NULL, GRADE INTEGER NOT NULL)";
        String createBookDonateTableStatement = "CREATE TABLE BOOK_DONATE_TABLE ( ISBN INTEGER NOT NULL , ID INTEGER NOT NULL , TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP , COLLECTED INTEGER DEFAULT 0 , "+
                "CONSTRAINT DONATE_PK PRIMARY KEY (ISBN, ID), FOREIGN KEY (ISBN) REFERENCES BOOK_TABLE (ISBN) , FOREIGN KEY (ID) REFERENCES USER_TABLE (ID) )";
        String createBookReceiveTableStatement = "CREATE TABLE BOOK_REQUEST_TABLE ( ISBN INTEGER NOT NULL , ID INTEGER NOT NULL , TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP , DELIVERED INTEGER DEFAULT 0 , "+
                "CONSTRAINT DONATE_PK PRIMARY KEY (ISBN, ID), FOREIGN KEY (ISBN) REFERENCES BOOK_TABLE (ISBN) , FOREIGN KEY (ID) REFERENCES USER_TABLE (ID) )";
        String createContactsTableStatement = "CREATE TABLE CONTACT_TABLE ( ID INTEGER NOT NULL, PHONE TEXT NOT NULL, CONSTRAINT CONTACT_PK PRIMARY KEY (ID,PHONE) ,"+
                "FOREIGN KEY (ID) REFERENCES USER_TABLE (ID) )";
        String createManagerTableStatement = "CREATE TABLE MANAGER_TABLE (MANAGER_ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT NOT NULL, PASSWORD TEXT NOT NULL)";

        db.execSQL(createUserTableStatement);
        db.execSQL(createBookTableStatement);
        db.execSQL(createBookDonateTableStatement);
        db.execSQL(createBookReceiveTableStatement);
        db.execSQL(createContactsTableStatement);
        db.execSQL(createManagerTableStatement);
        ContentValues cv = new ContentValues();
        cv.put("EMAIL", "manager@gmail.com");
        cv.put("PASSWORD", "manager");
        db.insert("MANAGER_TABLE",null,cv);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS BOOK_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS BOOK_DONATE_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS BOOK_REQUEST_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS CONTACT_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS MANAGER_TABLE");
        onCreate(db);

    }

    public boolean addUser(UserModel userModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME,userModel.getName());
        cv.put(USER_PASSWORD, userModel.getPassword());
        cv.put(USER_EMAIL, userModel.getEmail());
        cv.put("HOUSENO",userModel.getHouse_number());
        cv.put("STREETNO", userModel.getStreet_number());
        cv.put("STREETNAME",userModel.getStreet_name());
        cv.put("CITY", userModel.getCity());
        cv.put("STATE",userModel.getState());
        cv.put("PIN_CODE",userModel.getPostal_code());

        long insert = db.insert(USER_TABLE, null, cv);

        if(insert == -1) return false;
        else return true;
    }

    public boolean addBookDonation(Book book, int userId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("AUTHOR",book.getAuthor());
        cv.put("TITLE",book.getTitle());
        cv.put("SUBJECT", book.getSubject());
        cv.put("EDITION",book.getEdition());
        cv.put("GRADE",book.getGrade());

        Log.e("addBookDonation", String.valueOf(userId));
        if(userId<0) return false;

        long insert = db.insert("BOOK_TABLE",null,cv);

        String sql1 = "SELECT last_insert_rowid()";
        Cursor cursor = db.rawQuery(sql1, null);
        int isbn;
        if(cursor.moveToFirst())
        {
            isbn = cursor.getInt(0);
            Log.e("getUser", String.valueOf(isbn));
            ContentValues cv2 = new ContentValues();
            cv2.put("ID", userId);
            cv2.put("ISBN", isbn);
            long insert2 = db.insert("BOOK_DONATE_TABLE",null,cv2);
            if(insert2!=-1) return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean addContact (int userId, String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ID",userId);
        cv.put("PHONE",phone);

        Log.e("addContact", String.valueOf(userId));
        if(userId<0) return false;

        long insert = db.insert("CONTACT_TABLE",null,cv);
        db.close();

        return (insert!=-1);
    }

    public boolean makeRequest(int userId, int isbn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", userId);
        cv.put("ISBN", isbn);

        long insert = db.insert("BOOK_REQUEST_TABLE",null,cv);
        db.close();

        return (insert!=-1);
    }


    public void clearDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS BOOK_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS BOOK_DONATE_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS BOOK_REQUEST_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS CONTACT_TABLE");
        db.execSQL(" DROP TABLE IF EXISTS MANAGER_TABLE");
        onCreate(db);

        /*String createManagerTableStatement = "CREATE TABLE MANAGER_TABLE (MANAGER_ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT NOT NULL, PASSWORD TEXT NOT NULL)";
        db.execSQL(createManagerTableStatement);
        ContentValues cv = new ContentValues();
        cv.put("EMAIL", "manager@gmail.com");
        cv.put("PASSWORD", "manager");
        db.insert("MANAGER_TABLE",null,cv);
        db.close();*/


        //onCreate(db);
    }

    public UserModel getUser(int userId){

        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM "+ USER_TABLE +" WHERE " +USER_ID+" = " +userId;

        UserModel user = new UserModel();

        Cursor cursor = db.rawQuery(selectStatement,null);
        if(cursor.moveToFirst())
        {

            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String house_no  = cursor.getString(4);
            String street_no = cursor.getString(5);
            String street_name = cursor.getString(6);
            String city = cursor.getString(7);
            String state = cursor.getString(8);
            String  pin_code  = cursor.getString(9);

            user = new UserModel(userId,username,password,email,house_no,street_no,street_name,city,state,pin_code);


        }
        cursor.close();
        db.close();

        return user;
    }

    public int getUser(String username, String password){

        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT " + USER_ID +" FROM "+ USER_TABLE +" WHERE " +USER_EMAIL+" ='" +username+ "' AND "+USER_PASSWORD+" ='" + password +"'";

        Cursor cursor = db.rawQuery(selectStatement,null);
        int  id = -1;
        if(cursor.moveToFirst())
        {
            id = cursor.getInt(0);
            //Log.e("getUser", String.valueOf(id));

        }
        cursor.close();
        db.close();

        return id;
    }

    public ArrayList<Integer> getSummary()
    {
        ArrayList<Integer> myList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query1 = "SELECT COUNT(*) FROM USER_TABLE";
        String query2 = "SELECT COUNT(*) FROM BOOK_DONATE_TABLE";
        String query3 = "SELECT COUNT(*) FROM BOOK_REQUEST_TABLE";
        String query4 = "SELECT COUNT(*) FROM BOOK_DONATE_TABLE AS BD WHERE BD.COLLECTED = 0";
        String query5 = "SELECT COUNT(*) FROM BOOK_REQUEST_TABLE AS BD WHERE BD.DELIVERED = 0";


        Cursor cursor = db.rawQuery(query1,null);
        if(cursor.moveToFirst()) myList.add(cursor.getInt(0));
        else myList.add(-1);

        cursor = db.rawQuery(query2,null);
        if(cursor.moveToFirst()) myList.add(cursor.getInt(0));
        else myList.add(-1);

        cursor = db.rawQuery(query3,null);
        if(cursor.moveToFirst()) myList.add(cursor.getInt(0));
        else myList.add(-1);

        cursor = db.rawQuery(query4,null);
        if(cursor.moveToFirst()) myList.add(cursor.getInt(0));
        else myList.add(-1);

        cursor = db.rawQuery(query5,null);
        if(cursor.moveToFirst()) myList.add(cursor.getInt(0));
        else myList.add(-1);

        cursor.close();
        db.close();

        return myList;
    }

    public boolean completeTransaction(int isbn, int userId, int flag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", userId);
        cv.put("ISBN", isbn);

        if(flag == 0)
        {
            cv.put("COLLECTED", 1);
            db.update("BOOK_DONATE_TABLE",cv,"ISBN = ?", new String[]{String.valueOf(isbn)});

        }
        else
        {
            cv.put("DELIVERED",1);
            db.update("BOOK_REQUEST_TABLE",cv,"ISBN = ?", new String[]{String.valueOf(isbn)});
        }


        db.close();
        return true;
    }

    public boolean getManager(String username, String password){

        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT MANAGER_ID FROM MANAGER_TABLE WHERE EMAIL ='" +username+ "' AND PASSWORD  ='" + password +"'";

        Cursor cursor = db.rawQuery(selectStatement,null);
        boolean isManager = false;
        if(cursor.moveToFirst())
        {
            isManager = true;
        }
        cursor.close();
        db.close();

        return isManager;
    }

    public ArrayList<Book> getRequestableBooks(String subject1)
    {
        ArrayList<Book> myBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = " SELECT B.ISBN, B.TITLE, B.AUTHOR, B.SUBJECT, B.EDITION, B.GRADE "+
                " FROM BOOK_TABLE AS B , BOOK_DONATE_TABLE AS BD "+
                " WHERE BD.ISBN = B.ISBN AND BD.COLLECTED = 1 AND B.SUBJECT = '"+subject1+"' AND "+
                "BD.ISBN NOT IN (SELECT BR.ISBN FROM BOOK_REQUEST_TABLE AS BR)";

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int isbn = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String subject = cursor.getString(3);
                int edition  = cursor.getInt(4);
                int grade = cursor.getInt(5);

                Book book = new Book(isbn,author,subject,title,edition,grade);
                myBooks.add(book);

            }while (cursor.moveToNext());

        }
        else
        {

        }
        cursor.close();
        db.close();

        return myBooks;
    }

    public ArrayList<BookTransaction> getManagerTransaction(int flag)
    {
        ArrayList<BookTransaction> myList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString;
        if(flag == 0)
        {
            queryString = " SELECT B.ISBN, B.TITLE, B.SUBJECT, BD.ID, BD.COLLECTED "+
                    " FROM BOOK_TABLE AS B , BOOK_DONATE_TABLE AS BD "+
                    " WHERE BD.ISBN = B.ISBN ";
        }
        else
        {
            queryString = " SELECT B.ISBN, B.TITLE, B.SUBJECT, BD.ID, BD.DELIVERED "+
                    " FROM BOOK_TABLE AS B , BOOK_REQUEST_TABLE AS BD "+
                    " WHERE BD.ISBN = B.ISBN ";
        }

        Log.e("getUser", queryString);

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int isbn = cursor.getInt(0);
                String title = cursor.getString(1);
                String subject = cursor.getString(2);
                int userId  = cursor.getInt(3);
                int success = cursor.getInt(4);

                BookTransaction transaction = new BookTransaction(subject,title,userId,isbn,success);
                myList.add(transaction);

            }while (cursor.moveToNext());

        }
        else
        {

        }
        cursor.close();
        db.close();
        return myList;

    }

    public ArrayList<Pair<Book,Integer>> getUserTransaction(int userId, int flag)
    {
        ArrayList<Pair<Book,Integer>> myList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString;
        if(flag == 0)
        {
            queryString = " SELECT B.ISBN, B.TITLE, B.AUTHOR, B.SUBJECT, B.EDITION, B.GRADE, BD.COLLECTED "+
                    " FROM BOOK_TABLE AS B , BOOK_DONATE_TABLE AS BD "+
                    " WHERE BD.ISBN = B.ISBN AND BD.ID = " + userId;
        }
        else
        {
            queryString = " SELECT B.ISBN, B.TITLE, B.AUTHOR, B.SUBJECT, B.EDITION, B.GRADE, BD.DELIVERED "+
                    " FROM BOOK_TABLE AS B , BOOK_REQUEST_TABLE AS BD "+
                    " WHERE BD.ISBN = B.ISBN AND BD.ID = " + userId;
        }

        Log.e("getUser", queryString);

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int isbn = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String subject = cursor.getString(3);
                int edition  = cursor.getInt(4);
                int grade = cursor.getInt(5);
                int success = cursor.getInt(6);

                Book book = new Book(isbn,author,subject,title,edition,grade);
                Pair<Book,Integer> p = new Pair<>(book, success);
                myList.add(p);

            }while (cursor.moveToNext());

        }
        else
        {

        }
        cursor.close();
        db.close();
        return myList;

    }

    public ArrayList<String> getContactNumbers(int userId){
        ArrayList<String> myNum = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = " SELECT PHONE FROM CONTACT_TABLE WHERE ID = " + userId;

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                String contact = cursor.getString(0);
                myNum.add(contact);

            }while (cursor.moveToNext());

        }
        else
        {

        }
        cursor.close();
        db.close();
        return myNum;
    }

    public List<UserModel> getUsers()
    {
        List<UserModel> myUsers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + USER_TABLE;

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int userId = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String email = cursor.getString(3);
                String house_no  = cursor.getString(4);
                String street_no = cursor.getString(5);
                String street_name = cursor.getString(6);
                String city = cursor.getString(7);
                String state = cursor.getString(8);
                String  pin_code  = cursor.getString(9);

                UserModel user = new UserModel(userId,username,password,email,house_no,street_no,street_name,city,state,pin_code);
                myUsers.add(user);
            }while (cursor.moveToNext());

        }
        else
        {

        }
        cursor.close();
        db.close();

        return myUsers;
    }
}
