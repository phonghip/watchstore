/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package CRUD;

import dal.DAO;
import dal.DAOCart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author khiem
 */
@WebServlet(name="CrudServlet", urlPatterns={"/crud"})
public class CrudServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrudServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrudServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAO d=new DAO();
        DAOCart dc=new DAOCart();
        List<Category> homecat=d.getAll();
        request.setAttribute("category", homecat);
        List<Product> homepro=d.getAllProduct();
        
        
        
        PrintWriter out = response.getWriter();
        for(int i=0;i<5;i++){
        out.print(homepro.get(i).getDetails());}
        if(request.getParameter("search")!=null)
        {
            String search=request.getParameter("search");
            homepro=d.getProductsByName(search);
            request.setAttribute("product",homepro);
            
            if(request.getParameter("page")==null){
            request.getRequestDispatcher("crud.jsp?page=1").forward(request, response);
            }else{
             request.getRequestDispatcher("crud.jsp?page="+request.getParameter("page")).forward(request, response);
            }
        }else{
            if(request.getParameter("sort")!=null){int sort=0;
                try {
                    sort=Integer.parseInt(request.getParameter("sort"));
                } catch (Exception e) {
                }
            homepro=d.bubbleSort(homepro, homepro.size(), sort);}
            request.setAttribute("product",homepro );
            if(request.getParameter("page")==null){
            request.getRequestDispatcher("crud.jsp?page=1").forward(request, response);
            }else{
             request.getRequestDispatcher("crud.jsp?page="+request.getParameter("page")).forward(request, response);
            }
        }
        
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
