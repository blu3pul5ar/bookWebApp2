/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.model;

import exceptions.DataAccessException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Nicholas
 */
@SessionScoped
public class AuthorService implements Serializable{
    @Inject
    private AuthorDaoStrategy dao;

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public AuthorService() {
    }
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        return dao.getAuthorList();
    }
    public int deleteAuthorByID(Object id) throws ClassNotFoundException, SQLException{
        return dao.deleteAuthorByID(id);
    }

    public boolean addAuthor(String name) throws SQLException {
        Integer id = null;
        return dao.addAuthor(id,name);
    }
    public Author getAuthorById(String authorId) throws DataAccessException, SQLException, ClassNotFoundException {
        return dao.getAuthorById(Integer.parseInt(authorId));
    }
     public void updateAuthorbyId(String authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException {
         Integer id = Integer.parseInt(authorId);
         dao.updatebyID(id,authorName);
    }
      
        public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }

     
}
