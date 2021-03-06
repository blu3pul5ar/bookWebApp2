/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.controller;

import edu.wctc.nap.bookwebapp.model.Author;
import edu.wctc.nap.bookwebapp.model.AuthorService;
import exceptions.DataAccessException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author npiette
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
    private String dbJndiName;
    private static final String AUTHORS = "authors.jsp";
    private static final String AUTHOR_EDIT_VIEW = "edit.jsp";
    private static final String AUTHOR_ADD_VIEW = "add.jsp";
    private static final String HOME = "index.jsp";
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    @Inject
    private AuthorService as;
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
      String dest = "";
            String taskType = request.getParameter("taskType");
        try{
            configDbConnection();
            switch (taskType) {
                case "viewAuthor":
                    request.setAttribute("authors", as.getAuthorList());
                    dest = AUTHORS;
                    break;
                case "deleteAuthor":
                    {
                        String authorId = (String)request.getParameter("id");
                        int i = as.deleteAuthorByID(authorId);
                        request.setAttribute("authorIdResp",i);
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "edit":
                    {
                        String authorId = (String)request.getParameter("id");
                        Author author = as.getAuthorById(authorId);
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
                        as.updateAuthorbyId(authorId, authorName);
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "new":
                    {
                        String authorName = request.getParameter("authorName");
                        if(authorName != null){
                            as.addAuthor(authorName);
                        }       this.refreshList(request, as);
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
    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = as.getAuthorList();
        request.setAttribute("authors", authors);
    }
    private void configDbConnection() throws NamingException, DataAccessException{
        if(dbJndiName == null){
        as.getDao().initDao(driverClass, url, userName, password);
        }
        else{
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            as.getDao().initDao(ds);
        }
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