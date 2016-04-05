/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.controller;

import edu.wctc.nap.bookwebapp.model.Author;
import edu.wctc.nap.bookwebapp.model.Book;
import exceptions.DataAccessException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import wdu.wctc.nap.bookwebapp.ejb.AbstractFacade;
import wdu.wctc.nap.bookwebapp.ejb.AuthorFacade;
import wdu.wctc.nap.bookwebapp.ejb.BookFacade;

/**
 *
 * @author npiette
 */
@WebServlet(name = "bookController", urlPatterns = {"/bookController"})
public class bookController extends HttpServlet {
    private static final String BOOKS = "listBooks.jsp";
    private static final String BOOK_EDIT_VIEW = "edit_book.jsp";
    private static final String BOOK_ADD_VIEW = "add_book.jsp";
    private static final String HOME = "index.jsp";
    private String dbJndiName;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    @Inject
    private AbstractFacade<Book> bf;
    @Inject
    private AbstractFacade<Author> as;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
      String dest = "";
            String taskType = request.getParameter("taskType");
        try{
            switch (taskType) {
                case "view":
                    request.setAttribute("books", bf.findAll());
                    dest = BOOKS;
                    break;
                case "deleteBook":
                    {
                        String authorId = (String)request.getParameter("id");
                        Book book = bf.find(new Integer(authorId));
                        bf.remove(book);
                        this.refreshBookList(request, bf);
                        dest = BOOKS;
                        break;
                    }
                case "edit":
                    {
                        request.setAttribute("dropDownAuthors", as.findAll());
                        String bookId = (String)request.getParameter("id");
                        Book book = bf.find(Integer.parseInt(bookId));
                        request.setAttribute("book", book);
                        this.refreshAuthporList(request, as);
                        this.refreshBookList(request, bf);
                        dest=BOOK_EDIT_VIEW;
                        break;
                    }
                case "add":
                    request.setAttribute("dropDownAuthors", as.findAll());
                    this.refreshAuthporList(request, as);
                    this.refreshBookList(request, bf);
                    dest = BOOK_ADD_VIEW;
                    break;
                case "save":
                    {
                        String title = request.getParameter("title");
                        String Id = request.getParameter("Id");
                        String isbn = request.getParameter("isbn");
                        String authorId = request.getParameter("authorId");
                        Author author = as.find(new Integer(authorId));
                        Book book = new Book();
                        book.setAuthorId(author);
                        book.setBookId(Integer.parseInt(Id));
                        book.setIsbn(isbn);
                        book.setTitle(title);
                        bf.edit(book);
                        this.refreshBookList(request, bf);
                        dest = BOOKS;
                        break;
                    }
                case "new":
                    {
                        String title = request.getParameter("title");
                        String isbn = request.getParameter("isbn");
                        String authorId = request.getParameter("authorId");
                        Book book = new Book();
                        Author author = as.find(new Integer(authorId));
                        book.setAuthorId(author);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        bf.create(book);
                        this.refreshBookList(request, bf);
                        dest = BOOKS;
                        break;
                    }
                case "cancel":
                    this.refreshBookList(request, bf);
                    dest = BOOKS;
                    break;
                case "color":
                    String table = request.getParameter("showPaletteOnly");
                    String text = request.getParameter("showPaletteOnly1");
                    HttpSession session = request.getSession();
                    session.setAttribute("table",table);
                    session.setAttribute("text",text);
                    this.refreshBookList(request, bf);
                    dest = HOME;
                    break;
                default:
                    dest = HOME;
                    break;
            }
           }catch(Exception e){
                request.setAttribute(HOME, e);
           }
                RequestDispatcher view = request.getRequestDispatcher(response.encodeURL(dest));
                view.forward(request, response);
    }
    private void refreshBookList(HttpServletRequest request, AbstractFacade<Book> bf) throws Exception {
        List<Book> book = bf.findAll();
        request.setAttribute("books", book);
    }  
    private void refreshAuthporList(HttpServletRequest request, AbstractFacade<Author> as) throws Exception {
        List<Author> author = as.findAll();
        request.setAttribute("authors", author);
    }    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**S
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    @Override
    public void init(){
       // driverClass = getServletContext().getInitParameter("db.driver.class");
        //url = getServletContext().getInitParameter("db.url");
        //userName = getServletContext().getInitParameter("db.username");
        //password = getServletContext().getInitParameter("db.password");
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
    }
}