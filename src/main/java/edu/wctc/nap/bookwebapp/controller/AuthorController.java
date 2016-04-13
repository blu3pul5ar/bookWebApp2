/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.nap.bookwebapp.controller;

import edu.wctc.nap.bookwebapp.model.Author;
import edu.wctc.nap.bookwebapp.service.AuthorService;
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.WebApplicationContext;

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
                    request.setAttribute("authors", as.findAll());
                    dest = AUTHORS;
                    break;
                case "deleteAuthor":
                    {
                        String authorId = (String)request.getParameter("id");
                        Author author = as.findById(authorId);
                        as.remove(author);
                        this.refreshList(request, as);
                        dest = AUTHORS;
                        break;
                    }
                case "edit":
                    {
                        String authorId = (String)request.getParameter("id");
                        Author author = as.findById(authorId);
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
                        Author author = as.findById(authorId);
                        author.setAuthorName(authorName);
                        author.setDateAdded(new Date(date));
                        author.setAuthorId(Integer.parseInt(authorId));
                        as.edit(author);
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
                            as.edit(author);
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
    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
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
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        as = (AuthorService) ctx.getBean("authorService");

    }
}