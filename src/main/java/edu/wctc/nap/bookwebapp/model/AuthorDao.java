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
import javax.enterprise.context.Dependent;
import javax.inject.Inject;


   
/**
 *
 * @author Nicholas
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {
     private static final String TABLE_NAME = "author";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String DATE_ADDED = "date_added";
    @Inject
    private DBStrategy db;
    private String driver;
    private String url;
    private String user;
    private String pwd;
    @Override
    public void initDao(String driver, String url, String user, String pwd){
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(pwd);
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
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
        db.openConnection(driver, url, user, pwd);
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
         db.openConnection(driver, url, user, pwd);
         int result = db.deleteRecordbyPrimaryKey(TABLE_NAME, AUTHOR_ID, id);
         db.closeConnection();
         return result;
    }
     @Override
    public int updatebyID(Author author) throws SQLException {
        try {
            db.openConnection(driver, url, user, pwd);
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
        db.openConnection(driver, url, user, pwd);
        
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
            db.openConnection(driver, url, user, pwd);
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
