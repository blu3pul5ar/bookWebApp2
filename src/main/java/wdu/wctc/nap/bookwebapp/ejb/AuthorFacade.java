/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdu.wctc.nap.bookwebapp.ejb;

import edu.wctc.nap.bookwebapp.model.Author;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicholas
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.nap_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    public void delteAuthorById(String id){
        Author author = this.find(new Integer(id));
        this.remove(author);
    }
    public void updateAuthor(String id, String name,String date){
        Author author = new Author();
        if(id== null){
            author.setAuthorName(name);
            author.setDateAdded(new Date(date));
        }
        else{
            author.setAuthorId(new Integer(id));
            author.setAuthorName(name);           
            author.setDateAdded(new Date(date));
        }
        this.getEntityManager().merge(author);
    }
}
