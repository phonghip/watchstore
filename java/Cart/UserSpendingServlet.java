/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Cart;

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
import model.Account;
import model.Product;

/**
 *
 * @author khiem
 */
@WebServlet(name="UserSpendingServlet", urlPatterns={"/userspending"})
public class UserSpendingServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UserSpendingServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserSpendingServlet at " + request.getContextPath () + "</h1>");
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
        DAOCart dc=new DAOCart();
        DAO d=new DAO();
        String aid_raw=request.getParameter("aid");
        String money_raw=request.getParameter("money");
        PrintWriter out = response.getWriter();
        out.println(aid_raw);
        int aid=0,money=0;
        try {
            aid=Integer.parseInt(aid_raw);
            money=Integer.parseInt(money_raw);
        } catch (Exception e) {
        }
        List<Product> list=dc.getProductInAccount(aid);
        Account a=d.getAccountFromAid(aid);
        if(a.getMoney()>0){
            money=a.getMoney();
        }
        request.setAttribute("account", a);
        request.setAttribute("money", money);
        out.println(list.get(0).getDate());
        request.setAttribute("List", list);
        if (request.getParameter("page") == null) {
            request.getRequestDispatcher("UserSpendingInfo.jsp?page=1").forward(request, response);
        } else {
            request.getRequestDispatcher("UserSpendingInfo.jsp?page=" + request.getParameter("page")).forward(request, response);
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
