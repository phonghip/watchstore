/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package login;

import dal.DAO;
import dal.DAOCart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.AccountImage;
import model.AccountInfo;
import model.Cart;

/**
 *
 * @author khiem
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet  " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public static String getFullURL(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String URL = "";
        if (uri == "home" || uri == null) {
            //URL+="products?cid=0";
            URL += "home";
        } else if (uri != null && query == null) {

            URL += uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        } else {
            URL += uri.substring(uri.lastIndexOf("/") + 1, uri.length()) + "?" + query;
        }
        //URL.replaceAll("&", ";");
        return URL;
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

        DAO d = new DAO();
        ArrayList<Account> list = (ArrayList<Account>) d.getAllAccount();
        for (Account c : list) {
            out.println(c.getUserName());
        }
        
        String url=request.getParameter("url");
        request.setAttribute("list", list);
        if (request.getParameter("url") != null) {
            url = url.replaceAll(";", "&").replaceFirst("store.jsp", "products").replaceFirst("ProductDetails.jsp","products");
            if(request.getParameter("pid")!=null) url+="?id="+request.getParameter("pid")+"&page=1";
            
            request.setAttribute("urlRedirect", url);
        }
        out.println(url + "hehe");
        
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String u_raw = request.getParameter("email");
        String p_raw = request.getParameter("password");
        PrintWriter out = response.getWriter();

        DAO d = new DAO();

        Account a = d.checkLogin(u_raw, p_raw);

        if (a == null) {
            out.println(u_raw + p_raw);
            request.setAttribute("ms", "UserName or PassWord is wrong!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            if (a.isStatus()) {

                HttpSession session = request.getSession(false);
                request.setAttribute("ms", "Login Succesfull");
                AccountInfo ai = d.getAccountInfo(a);
                AccountImage am = d.getAccountImage(a);
                DAOCart dc=new DAOCart();
                List<Cart> clist=dc.getFromCart(a.getAid());
                if (clist != null) {
                    session.setAttribute("cartList", clist);
                }
                session.setAttribute("account", a);
                if (ai != null) {
                    session.setAttribute("accountinfo", ai);
                }
                if (am != null) {
                    session.setAttribute("accountimage", base64image(am.getImage()));
                }
                out.println(request.getParameter("URL"));
                request.getRequestDispatcher(request.getParameter("URL")).forward(request, response);
            }else{
                request.setAttribute("ms", "Account is Suspended!!!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
