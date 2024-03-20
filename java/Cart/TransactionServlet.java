/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Cart;

import dal.DAOCart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

/**
 *
 * @author khiem
 */
@WebServlet(name = "TransactionServlet", urlPatterns = {"/transaction"})
public class TransactionServlet extends HttpServlet {

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
            out.println("<title>Servlet TransactionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransactionServlet at " + request.getContextPath() + "</h1>");
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
        DAOCart dc = new DAOCart();
        PrintWriter out = response.getWriter();
        List<Transaction> aList = new ArrayList<>();
        if (request.getParameter("from") != null && request.getParameter("to") != null&& request.getParameter("to") != ""&& request.getParameter("to") != "") {
            String from_raw = request.getParameter("from");
            String to_raw = request.getParameter("to");
            Date to = Date.valueOf(to_raw);
            Date from = Date.valueOf(from_raw);
            //out.println("from "+from+" to"+to);
            List<Transaction> list=dc.getAllTransactionByDate(from, to);
            if(list.isEmpty()){
            out.println("null");    
            }else{
                aList=list;
            }
        }else{
            aList = dc.getAllTransaction();
        }
        if(aList.isEmpty()){
            out.println("null");       
        }else{
             request.setAttribute("transactList", aList);
             out.println(aList.get(0).getMoney());
        }

        
        
        if (request.getParameter("page") == null) {
            request.getRequestDispatcher("TransactionAll.jsp?page=1").forward(request, response);
        } else {
            request.getRequestDispatcher("TransactionAll.jsp?page=" + request.getParameter("page")).forward(request, response);
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
        doGet(request, response);
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
