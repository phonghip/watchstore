/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package login;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.AccountImage;

/**
 *
 * @author khiem
 */
@WebServlet(name="ImgServlet", urlPatterns={"/img"})
@MultipartConfig
public class ImgServlet extends HttpServlet {
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
            out.println("<title>Servlet ImgServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImgServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
        
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
        Part part=request.getPart("imageProfile");
        InputStream s=part.getInputStream();
        DAO d=new DAO();
        PrintWriter out = response.getWriter();
        if(request.getParameter("add")!=null){
            //add image cho account
            HttpSession session = request.getSession(false);
            Account a=(Account) session.getAttribute("account");
            AccountImage am=d.createAccountImage(a, s);
            session.setAttribute("accountimage", base64image(am.getImage()));
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }else if(request.getParameter("update")!=null){
            //update image
            HttpSession session = request.getSession(false);
            Account a=(Account) session.getAttribute("account");
            out.println(d.updateAccountImage(a, s));
            AccountImage am=d.getAccountImage(a);
            session.setAttribute("accountimage", base64image(am.getImage()));
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
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
