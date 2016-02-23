/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

import exceptions.DataAccessException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


   
/**
 *
 * @author Nicholas
 */
@SessionScoped
public class AuthorDao implements AuthorDaoStrategy, Serializable {
     private static final String TABLE_NAME = "author";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String DATE_ADDED = "date_added";
    @Inject
    private DBStrategy db;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PWD = "admin";

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }

    public AuthorDao() {
    }
    
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, URL, USER, PWD);
        List<Map<String, Object>> raw = db.findAllRecords(TABLE_NAME, 0);
        List<Author> authors = new ArrayList<>();
        for(Map rec : raw){
            Author author = new Author();
            Integer id = new Integer(rec.get(AUTHOR_ID).toString());
            author.setAuthorId(id);
            String name = rec.get(AUTHOR_NAME) == null ? "" : rec.get(AUTHOR_NAME).toString();
            author.setAuthorName(name);
            Date date = rec.get(DATE_ADDED) == null ? null : (Date)rec.get(DATE_ADDED);
            author.setDateAdded(date);
            authors.add(author);
        }
        db.closeConnection();
        return authors;
    }
    public int deleteAuthorByID(Object id) throws ClassNotFoundException, SQLException{
         db.openConnection(DRIVER, URL, USER, PWD);
         int result = db.deleteRecordbyPrimaryKey(TABLE_NAME, AUTHOR_ID, id);
         db.closeConnection();
         return result;
    }
     @Override
    public int updatebyID(Author author) throws SQLException {
        try {
            db.openConnection(DRIVER, URL, USER, PWD);
            List<String> authorColumns = Arrays.asList(AUTHOR_NAME, DATE_ADDED);;
            List<Object> authorValues = Arrays.asList(author.getAuthorName(), author.getDateAdded());
            int numAuthor = db.updateRecordByID(TABLE_NAME, authorColumns, authorValues, AUTHOR_ID, author.getAuthorId());
            return numAuthor;
        } catch (SQLException sqlE) {
            throw sqlE;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            db.closeConnection();
        }

    }
    
     @Override
     public Author getAuthorById(Integer authorId)throws DataAccessException, ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, URL, USER, PWD);
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorId((Integer)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        
        return author;
    }
    @Override
    public int addAuthor(Author author) throws SQLException{
        try {
            db.openConnection(DRIVER, URL, USER, PWD);
            List<String> authorColumns = Arrays.asList(AUTHOR_NAME, DATE_ADDED);;
            List<Object> authorValues = Arrays.asList(author.getAuthorName(), author.getDateAdded());
            int numAuthor = db.insertRecord(TABLE_NAME, authorColumns, authorValues);
            return numAuthor;
        } catch (SQLException sqlE) {
            throw sqlE;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            db.closeConnection();
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
    }
}
