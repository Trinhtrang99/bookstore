package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.Book;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Model.BookAttribute.BOOK_AUTHOR;
import static com.example.myapplication.Model.BookAttribute.BOOK_CATEGORY;
import static com.example.myapplication.Model.BookAttribute.BOOK_CATEGOTY;
import static com.example.myapplication.Model.BookAttribute.BOOK_DATEBUY;
import static com.example.myapplication.Model.BookAttribute.BOOK_DESCRIPTION;
import static com.example.myapplication.Model.BookAttribute.BOOK_ID;
import static com.example.myapplication.Model.BookAttribute.BOOK_IMAGELINK;
import static com.example.myapplication.Model.BookAttribute.BOOK_MOUNT;
import static com.example.myapplication.Model.BookAttribute.BOOK_PAGE;
import static com.example.myapplication.Model.BookAttribute.BOOK_PRICE;
import static com.example.myapplication.Model.BookAttribute.BOOK_PUBLISHER;
import static com.example.myapplication.Model.BookAttribute.BOOK_RATESTAR;
import static com.example.myapplication.Model.BookAttribute.BOOK_RELEASEYEAR;
import static com.example.myapplication.Model.BookAttribute.BOOK_REVIEW;
import static com.example.myapplication.Model.BookAttribute.BOOK_TITLE;
import static com.example.myapplication.Model.BookAttribute.ID_ACC;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String TAG = "SqlHelper";
    static final String DB_NAME = "bookn.db";
    static final String DB_TABLE_ALL_BOOK = "AllBooks";
    static final String DB_TABLE_CART = "Cart";
    static final String DB_TABLE_CART_NEW = "CartNew";
    static final String DB_TABLE_HISTORY = "History";
    static final String DB_TABLE_ACCOUNT = "Account";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    public SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table acc
        String queryCreateTableAccount = "CREATE TABLE "+ DB_TABLE_ACCOUNT +"(" +
                "STT INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "phone Text," +
                "password Text," +
                "fullname Text,"+
                "address Text)";
        db.execSQL(queryCreateTableAccount);
        //Create table all books
        String queryCreateTableAllBook = "CREATE TABLE "+ DB_TABLE_ALL_BOOK +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "categoty Text)";
        db.execSQL(queryCreateTableAllBook);
        //create table Cart
        String queryCreateTableCartNew = "CREATE TABLE "+ DB_TABLE_CART_NEW +"(" +
                "STT INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "categoty Text," +
                "mount INTEGER,"+
                "idAcc INTEGER)";
        db.execSQL(queryCreateTableCartNew);


        //create table HISTORY


        String queryCreateTableHistory = "CREATE TABLE "+ DB_TABLE_HISTORY +"(" +
                "STT INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "categoty Text,"+
                "dateBuy Text)";
        db.execSQL(queryCreateTableHistory);
//        db.execSQL("DROP TABLE "+DB_TABLE_CART);
    }
    public int deleteAllBook()
    {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_ALL_BOOK, null,null);
    }
    public void deleteAllBookCart()
    {
        sqLiteDatabase = getWritableDatabase();
         sqLiteDatabase.delete(DB_TABLE_CART_NEW, null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }
    //Query Table AllBook
    public void InsertBookToAllBook(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGOTY, book.getCategory());
        sqLiteDatabase.insert(DB_TABLE_ALL_BOOK, null, contentValues);
    }


    public List<Book> getAllBook(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ALL_BOOK,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String categoty =cursor.getString(cursor.getColumnIndex(BOOK_CATEGOTY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,categoty));
        }
        return list;
    }
    //Query Table Cart
    public void InsertBookToCart(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGOTY, book.getCategory());
        sqLiteDatabase.insert(DB_TABLE_CART, null, contentValues);
    }

    public void InsertBookToCartNew(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGOTY, book.getCategory());
        contentValues.put(BOOK_MOUNT, book.getMount());
        contentValues.put(ID_ACC,book.getIdAcc());
        sqLiteDatabase.insert(DB_TABLE_CART_NEW, null, contentValues);
    }

    public List<Book> getAllBookInCartNew(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_CART_NEW,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGOTY));
            int mount = cursor.getInt(cursor.getColumnIndex(BOOK_MOUNT));
            int idAcc = cursor.getInt(cursor.getColumnIndex(ID_ACC));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category,mount,idAcc));
        }
        return list;
    }


    public List<Book> getAllBookInCart(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_CART,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGOTY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    public int deleteCart() {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_CART_NEW, null, null);
    }
    public int deleteItemInCart(String id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_CART, "id=?", new String[]{id});
    }
    public int deleteItemInCartNew(String id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_CART_NEW, "id=?", new String[]{id});
    }
    //Query table History
    public void InsertBookToHistory(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGOTY, book.getCategory());
        contentValues.put(BOOK_DATEBUY, book.getDateBuy());
        sqLiteDatabase.insert(DB_TABLE_HISTORY, null, contentValues);
    }
    public List<Book> getAllBookInHistory(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGOTY));
            String dateBuy = cursor.getString(cursor.getColumnIndex(BOOK_DATEBUY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category,dateBuy));
        }
        return list;
    }
    public int deleteHistory() {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_HISTORY, null, null);
    }
    //query table Account


    public void InsertAccount(Account account) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("phone", account.getPhone());
        contentValues.put("password", account.getPassword());
        contentValues.put("fullname", account.getFullName());
        contentValues.put("address", account.getAddress());

        sqLiteDatabase.insert(DB_TABLE_ACCOUNT, null, contentValues);
    }
    public List<Account> getAllAccount(){
        List<Account> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        //cursor con trỏ dữ liệu
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ACCOUNT,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("STT"));
            String phone =cursor.getString(cursor.getColumnIndex("phone"));
            String password =cursor.getString(cursor.getColumnIndex("password"));
            String fullname =cursor.getString(cursor.getColumnIndex("fullname"));
            String address =cursor.getString(cursor.getColumnIndex("address"));
            list.add(new Account(id,phone,password,fullname,address));
        }
        return list;
    }


    public List<Book>  getListBookName(String name){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ALL_BOOK,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String categoty =cursor.getString(cursor.getColumnIndex(BOOK_CATEGOTY));
            if(categoty.equals(name)==true){
                list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,categoty));
            }

        }
        return list;
    }
    public boolean UpDateDataBook(String tt , double rateStar,
            int numOfReview){
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_TITLE, tt);
        contentValues.put(BOOK_RATESTAR, rateStar);
        contentValues.put(BOOK_REVIEW, numOfReview);
        sqLiteDatabase.update(DB_TABLE_ALL_BOOK, contentValues,BOOK_TITLE+"=?",new String[]{tt});
        return true;
    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    public boolean updateData(String namebook,int mount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_MOUNT,mount);
        db.update(DB_TABLE_CART_NEW, contentValues, "title = ?",new String[] {namebook});
        return true;
    }

    public void upDatePass(String sdt, String pass){
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("password",pass);
        sqLiteDatabase.update(DB_TABLE_ACCOUNT,contentValues,"phone" + " = ?",new String[] {sdt});
        sqLiteDatabase.close();
    }

}
