/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CRUD;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

/**
 *
 * @author khiem
 */
@WebServlet(name = "CategoryManagementServlet", urlPatterns = {"/categorymanagement"})
public class CategoryManagementServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryManagementServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
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
        DAO d = new DAO();
        String manage = request.getParameter("manage");
        request.setAttribute("manage", manage);
        PrintWriter out = response.getWriter();
        out.println(manage);
        if (manage.equalsIgnoreCase("delete")) {
            int cid = 0;
                String cid_raw = request.getParameter("cid");
                try {
                    cid = Integer.parseInt(cid_raw);
                } catch (Exception e) {
                }
            
            if(!d.checkCategoryToDelete(cid))
            {d.deleteCategory(cid);}
            else{
                request.setAttribute("error", "cant delete a brand with products");
            }
            request.getRequestDispatcher("crudcategory").forward(request, response);
        } else {
            if (request.getParameter("cid") != null) {
                int cid = 0;
                String cid_raw = request.getParameter("cid");
                try {
                    cid = Integer.parseInt(cid_raw);
                } catch (Exception e) {
                }
                Category c = d.getCategoryById(cid);
                request.setAttribute("category", c);
                request.getRequestDispatcher("updatecategory.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("updatecategory.jsp").forward(request, response);

            }

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
        DAO d = new DAO();
        PrintWriter out = response.getWriter();
        String manage = request.getParameter("manage");
        request.setAttribute("manage", manage);
        String name = request.getParameter("name");
        String describe = request.getParameter("describe");
        String brand = request.getParameter("cid");
        out.println(name+describe+"  "+brand+" "+manage);
        if (manage.equalsIgnoreCase("add")) {
            out.println(d.addCategory(d.getMaxIdCategory()+1, name, describe));
            request.getRequestDispatcher("crudcategory").forward(request, response);
        } else {
            int cid = 0;
            try {
                cid = Integer.parseInt(brand);
            } catch (Exception e) {
            }
            out.println(d.updateCategory(cid, name, describe).getName());
            request.getRequestDispatcher("crudcategory").forward(request, response);
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

}
