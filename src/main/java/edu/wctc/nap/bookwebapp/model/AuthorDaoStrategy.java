/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

import exceptions.DataAccessException;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Nicholas
 */
public interface AuthorDaoStrategy {

    public abstract List<Author> getAuthorList() throws ClassNotFoundException, SQLException,DataAccessException;
    public abstract int deleteAuthorByID(Object id) throws ClassNotFoundException, SQLException,DataAccessException;
    public int updatebyID(Integer authorId, String name) throws SQLException, ClassNotFoundException,DataAccessException;
    public abstract boolean addAuthor(Integer id,String name) throws SQLException,DataAccessException;
    public abstract Author getAuthorById(Integer authorId) throws DataAccessException,SQLException, ClassNotFoundException;
    public void initDao(String driver, String url, String user, String pwd);
    public void initDao(DataSource ds) throws DataAccessException;
}
