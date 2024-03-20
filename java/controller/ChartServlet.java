/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import model.Chart;
import model.Product;

/**
 *
 * @author khiem
 */
@WebServlet(name = "ListServlet", urlPatterns = {"/chart"})
public class ChartServlet extends HttpServlet {

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
            out.println("<title>Servlet ListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        DAOCart dc = new DAOCart();
        DAO d = new DAO();
        String year_raw = request.getParameter("year");
        int year = 0,month=0;
        

        //year
        if (request.getParameter("year") != null) {
            try {
                year = Integer.parseInt(year_raw);
            } catch (Exception e) {
            }
        }       
        //month
        if (request.getParameter("month") != null) {
            try {
                month = Integer.parseInt(request.getParameter("month"));
            } catch (Exception e) {
            }
        }
        
        
        //test
        out.println(year + " " + month);
        List<Chart> listChart = dc.getSaleChart(year, month);
        List<Chart> listChartEachBrand=dc.getSaleChartEachBrand(year, month);
        if(year==0){
        List<Chart> list = dc.getTopChartProduct();
        request.setAttribute("products", list);
        }
        else{
            List<Chart> list = dc.getTopChartProduct(year,month);request.setAttribute("products", list);
        }
        if (month == 0) {
//            for (Chart c : listChartEachBrand) {
//                out.println(c.getBrand().getName()+ " " + c.getY());
//            }
//            for (Chart c : listChart) {
//                out.println(c.getMonth()+ " " + c.getY());
//            }
            
        } else {
//            for (Chart c : listChartEachBrand) {
//                out.println(c.getBrand().getName()+ " " + c.getY());
//            }
//            for (Chart c : listChart) {
//                out.println(c.getMonth()+ " " + c.getY());
//            }
        }
        int totalSale=0;
        for (Chart c : listChart) {
                totalSale+=c.getY();
            }
        int totalmoney=0;
        if(year==0){
             totalmoney=dc.getTotalSaleChart();
        }else{
        totalmoney=dc.getTotalSaleChart(year, month);
        }
        out.println(totalmoney);
        //requestScope Attribute
        request.setAttribute("totalMoney", totalmoney);
        request.setAttribute("totalSale", totalSale);
        request.setAttribute("chart", listChart);
        request.setAttribute("chartbrand", listChartEachBrand);
        request.getRequestDispatcher("SaleChart.jsp").forward(request, response);
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
