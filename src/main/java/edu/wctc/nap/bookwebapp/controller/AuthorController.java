/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.controller;

import edu.wctc.nap.bookwebapp.model.Author;
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
import wdu.wctc.nap.bookwebapp.ejb.AuthorFacade;

/**
 *
 * @author npiette
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
    private static final String AUTHORS = "authors.jsp";
    private static final String AUTHOR_EDIT_VIEW = "edit.jsp";
    private static final String AUTHOR_ADD_VIEW = "add.jsp";
    private static final String HOME = "index.jsp";
    private String dbJndiName;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    @Inject
    private AuthorFacade as;
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
                case "viewAuthor":
                    request.setAttribute("authors", as.findAll());
                    dest = AUTHORS;
                    break;
                case "deleteAuthor":
                    {
                        String authorId = (String)request.getParameter("id");
                        as.deleteAuthorById(authorId);
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "edit":
                    {
                        String authorId = (String)request.getParameter("id");
                        Author author = as.find(Integer.parseInt(authorId));
                        request.setAttribute("author", author);
                        dest= AUTHOR_EDIT_VIEW;
                        break;
                    }
                case "add":
                    dest = AUTHOR_ADD_VIEW;
                    break;
                case "save":
                    {
                        String authorName = request.getParameter("authorName");
                        String authorId = request.getParameter("authorId");
                        String date = request.getParameter("dateadded");
                        as.updateAuthor(authorId, authorName, date);
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "new":
                    {
                        String authorName = request.getParameter("authorName");
                        if(authorName != null){
                            Author author = new Author();
                            author.setAuthorName(authorName);
                            author.setDateAdded(new Date());
                            as.create(author);
                        }       
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "cancel":
                    this.refreshList(request, as);
                    dest = AUTHORS;
                    break;
                case "color":
                    String table = request.getParameter("showPaletteOnly");
                    String text = request.getParameter("showPaletteOnly1");
                    HttpSession session = request.getSession();
                    session.setAttribute("table",table);
                    session.setAttribute("text",text);
                    this.refreshList(request, as);
                    dest = AUTHORS;
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
    private void refreshList(HttpServletRequest request, AuthorFacade authService) throws Exception {
        List<Author> authors = as.findAll();
        request.setAttribute("authors", authors);
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