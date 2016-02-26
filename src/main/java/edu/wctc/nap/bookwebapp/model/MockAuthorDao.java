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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author Nicholas
 */
@Alternative
@Dependent
public class MockAuthorDao implements AuthorDaoStrategy, Serializable{ 
       private Author one = new Author(001,"bob",new Date(95,5,2)); 
    private Author two = new Author(002,"chris",new Date(100,2,7)); 
    private Author three = new Author(003,"evan",new Date(112,10,27));
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
         List authors = new ArrayList();
    authors.add(one);
    authors.add(two);
    authors.add(three);
    return authors;
    }
public void initDao(String driver, String url, String user, String pwd){
        
    }
    @Override
    public int deleteAuthorByID(Object id) throws ClassNotFoundException, SQLException {
       return 1;
    }

    @Override
    public int updatebyID(Author author) throws SQLException {
        return 1;
    }

    @Override
    public int addAuthor(String name) throws SQLException {
        return 1;
    }

    @Override
    public Author getAuthorById(Integer authorId) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
