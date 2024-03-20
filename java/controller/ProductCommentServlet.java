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
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.ImgServlet;
import model.AccountImage;
import model.Product;
import model.Rating;

/**
 *
 * @author khiem
 */
@WebServlet(name = "ProductCommentServlet", urlPatterns = {"/productcomment"})
@MultipartConfig
public class ProductCommentServlet extends HttpServlet {

    String base64image(Blob blob) throws IOException {
        InputStream inputStream = null;
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
            out.println("<title>Servlet ProductCommentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductCommentServlet at " + request.getContextPath() + "</h1>");
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
        DAO d = new DAO();
        Product p = (Product) request.getAttribute("product");
        List<Rating> list = dc.getAllRating(p.getId());
        PrintWriter out = response.getWriter();
        int rate = 0;
        if (list != null && list.size() > 0) {
            int[] ratedetail = getRate(list);
            for (Rating r : list) {
                AccountImage ai = d.getAccountImage(r.getA());
                if (ai != null) {
                    r.setImage(base64image(ai.getImage()));
                }
                rate += r.getRating();
            }
            rate = rate / list.size();
            request.setAttribute("ratedetail", ratedetail);
            request.setAttribute("totalrate", rate);
            request.setAttribute("rating", list);
            out.println(list.get(0).getDate());

        }
        out.println(p.getName());
        if (request.getAttribute("page") != null) {
            int page=(int) request.getAttribute("page");
            out.println(request.getAttribute("page"));
            request.getRequestDispatcher("ProductDetails.jsp?page=" + page).forward(request, response);
        } else {
            out.println("hehe");
            request.getRequestDispatcher("ProductDetails.jsp?page=1" ).forward(request, response);
        }

    }

    public int[] getRate(List<Rating> list) {
        int[] ratedetail = new int[6];
        for (Rating r : list) {
            if (r.getRating() == 1) {
                ratedetail[1]++;
            }
            if (r.getRating() == 2) {
                ratedetail[2]++;
            }
            if (r.getRating() == 3) {
                ratedetail[3]++;
            }
            if (r.getRating() == 4) {
                ratedetail[4]++;
            }
            if (r.getRating() == 5) {
                ratedetail[5]++;
            }
        }
        return ratedetail;
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
