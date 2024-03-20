/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package login;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.AccountInfo;

/**
 *
 * @author khiem
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
public class UploadServlet extends HttpServlet {

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
            out.println("<title>Servlet UploadServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadServlet at " + request.getContextPath() + "</h1>");
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
        String update = request.getParameter("update");
        out.println("doPost " + update + "  " + request.getParameter("user"));
        DAO d = new DAO();
        if (update.equalsIgnoreCase("UpdateProfile")) {

            String create = request.getParameter("create");
            String age_raw=request.getParameter("age");int age=0;
            try {
                age=Integer.parseInt(age_raw);
            } catch (Exception e) {
            }
            boolean gender=true;
            if(request.getParameter("gender").equals("0")){
                gender=false;
            }
            //tao profile moi
            if (create != null) {
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String fullname = request.getParameter("fullname");
                String address = request.getParameter("address");
                
                 HttpSession session = request.getSession(false);
                 Account a=(Account) session.getAttribute("account");
                 //get old ai
                //out.println(email+" "+phone+" "+fullname+" "+address);
                AccountInfo ai=d.createAccountInfo(fullname, email, phone, address, a,age,gender);
                session.setAttribute("accountinfo", ai);
                 //out.println(ai.getEmail()+" "+ai.getPhone()+" "+ai.getFullname()+" "+ai.getAddress());
            } //update profile cu len
            else {
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String fullname = request.getParameter("fullname");
                String address = request.getParameter("address");
                 HttpSession session = request.getSession(false);
                 //get old ai
                 AccountInfo ai=(AccountInfo) session.getAttribute("accountinfo");
                 Account a=(Account) session.getAttribute("account");
                 //update ai
                int test=d.updateAccountInfo(fullname, email, phone, address, ai,age,gender);
                //get new ai
                AccountInfo newai=d.getAccountInfo(a);
                //set session new ai
                session.setAttribute("accountinfo", newai);
                //out.println(newai.getFullname()+" "+newai.getEmail());
                //go back profile.jsp
                
            }

        } else {
            //get old account and new password
            String Password = request.getParameter("pass");
            String oldAccount = request.getParameter("olduser");
            String oldPassword = request.getParameter("oldpass");

            //test thoi
            out.println(" old: " + oldAccount);
            out.println(Password + " old: " + oldPassword);
            Account a = d.getAccount(oldAccount, oldPassword);
            out.println(a.getAid() + "  " + a.getUserName() + " " + a.getPassWord());
            out.println(d.updateAccount(Password, a));
            //tao session new account  (only password change)
            HttpSession session = request.getSession(false);
            Account newa = d.getAccount(a.getUserName(), Password);
            session.setAttribute("account", newa);
            //return profile.jsp
            
        }
        request.getRequestDispatcher("profile?cid=1").forward(request, response);

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
        //tu profile.jsp chuyen sang setting.jsp
        PrintWriter out = response.getWriter();
        String update = request.getParameter("update");
        //check either update profile or update password
        if (update.equalsIgnoreCase("UpdateProfile")) {
            request.getRequestDispatcher("setting.jsp?&update=1").forward(request, response);
        } else {
            request.getRequestDispatcher("setting.jsp?&update=2").forward(request, response);
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
