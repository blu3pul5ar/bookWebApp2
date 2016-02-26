/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

import exceptions.DataAccessException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nicholas
 */
public interface AuthorDaoStrategy {

    public abstract List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    public abstract int deleteAuthorByID(Object id) throws ClassNotFoundException, SQLException;
    
    public abstract int updatebyID(Author author) throws SQLException;
    public abstract int addAuthor(String name) throws SQLException;
    public abstract Author getAuthorById(Integer authorId) throws DataAccessException,SQLException, ClassNotFoundException;
    public void initDao(String driver, String url, String user, String pwd);
}
