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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.ImgServlet;
import model.Account;
import model.AccountImage;
import model.Chart;

/**
 *
 * @author khiem
 */
@WebServlet(name="UserChartServlet", urlPatterns={"/userchart"})
@MultipartConfig
public class UserChartServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UserChartServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserChartServlet at " + request.getContextPath () + "</h1>");
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
        PrintWriter out = response.getWriter();
        DAOCart dc=new DAOCart();
        DAO d=new DAO();
        int year=0,month=0;
        
        //year
        if (request.getParameter("year") != null) {
            try {
                year = Integer.parseInt(request.getParameter("year"));
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
        List<Account> list=new ArrayList<>();
        if(year==0){
            list=dc.getAccountUserChart();}
        else{
            list=dc.getAccountUserChart(year, month);
        }
        
        List<Chart> rating=new ArrayList<>();List<Chart> gender=new ArrayList<>();List<Chart> age=new ArrayList<>();
        if(year==0){
            rating=dc.getRatingChart();gender=dc.getGenderChart();age=dc.getAgeChart();
        }
        else{
            rating=dc.getRatingChart(year, month);gender=dc.getGenderChart(year, month);age=dc.getAgeChart(year, month);
        }
        
        for(Account a:list){
            
            AccountImage ai=d.getAccountImage(a);
            if(ai !=null){
            a.setImage(base64image(ai.getImage()));}
        }
        list=d.bubbleSortAccount(list, list.size(), 1);
        
        
        //set RequestScope Attribute
        request.setAttribute("age", age);
        request.setAttribute("gender", gender);
        request.setAttribute("rating", rating);
        request.setAttribute("account", list);
        request.getRequestDispatcher("UserChart.jsp").forward(request, response);
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    String base64image(Blob blob) throws IOException{
        InputStream inputStream =null;
        try {
            inputStream = blob.getBinaryStream();
        } catch (SQLException ex) {
            Logger.getLogger(ImgServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);                  
                }
                 
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                return base64Image;
    }
}
