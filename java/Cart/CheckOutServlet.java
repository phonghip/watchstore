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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Cart;

/**
 *
 * @author khiem
 */
@WebServlet(name="CheckOutServlet", urlPatterns={"/checkout"})
public class CheckOutServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CheckOutServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOutServlet at " + request.getContextPath () + "</h1>");
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
        String total_raw=request.getParameter("totalMoney");double total_a=0;
        out.println(total_raw);
        try {
            total_a=Double.parseDouble(total_raw);
        } catch (Exception e) {
        }
        //out.println(total_a);
        int total=(int)total_a;
         out.println(total);
        HttpSession session = request.getSession(false);
        Account a=(Account) session.getAttribute("account");
        DAOCart dc=new DAOCart();
                List<Cart> clist=dc.getFromCart(a.getAid());
            out.println(total);  
                //insert transaction product
            out.println("insert into pt"+dc.addTransaction(a.getAid(), total));
//            out.println("delete from pt"+dc.deleteFromProduct());
            for(Cart c:clist){
                out.print(c.getName()+c.getNumber());
                out.println(" tran detail: "+dc.addTransactionDetail(c.getPid(),c.getNumber()));
            }
                for(Cart c:clist){
                    if(dc.getProductSale(c.getPid())==0) {out.println("product sale: "+dc.addProductSale(c.getNumber(), c.getPid()));}
                    else out.println("product sale "+dc.updateProductSale(c.getNumber()+dc.getProductSale(c.getPid()), c.getPid()));
                }
                dc.deleteAfterPayment(a.getAid());
                session.removeAttribute("cartList");               
//                out.println(total);
       request.getRequestDispatcher("cart.jsp?ms=thank").forward(request, response);

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

}
