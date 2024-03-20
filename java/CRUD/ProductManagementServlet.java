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
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author khiem
 */
@WebServlet(name = "ProductManagementServlet", urlPatterns = {"/management"})
public class ProductManagementServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagementServlet at " + request.getContextPath() + "</h1>");
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
        List<Category> homecat = d.getAll();
        request.setAttribute("category", homecat);
        List<Product> homepro = d.getAllProduct();
        request.setAttribute("product", homepro);
        String manage = request.getParameter("manage");
        request.setAttribute("manage", manage);
        PrintWriter out = response.getWriter();
        out.println(manage);
        if (manage.equalsIgnoreCase("add") || manage.equalsIgnoreCase("update")) {
            if (request.getParameter("pid") != null) {
                int pid = 0;
                String pid_raw = request.getParameter("pid");
                try {
                    pid = Integer.parseInt(pid_raw);
                } catch (Exception e) {
                }
                Product p = d.getProductsBYid(pid);
                String[] image = p.getImage().split(",");
                String img1 = image[0], img2 = image[1], img3 = image[2], img4 = image[3], name = p.getName(), id = p.getId(), detail = p.getDetails(), describe = p.getDescribe();
                int cid = p.getCategory().getId();
                double price = p.getPrice();
                request.getRequestDispatcher("update.jsp?name=" + name + "&price=" + price + "&describe=" + describe + "&image1=" + img1 + "&image2=" + img2 + "&image3=" + img3 + "&image4=" + img4 + "&detail=" + detail + "&brand=" + cid + "&pid=" + pid).forward(request, response);
            } else {
                request.getRequestDispatcher("update.jsp").forward(request, response);
            }

        } else {
            out.println(d.deleteProduct(request.getParameter("id")));
            request.getRequestDispatcher("crud").forward(request, response);
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
        String manage = request.getParameter("manage");
        request.setAttribute("manage", manage);
       
        List<Product> homepro = d.getAllProduct();

        String name = request.getParameter("name");
        String price_raw = request.getParameter("price");
        String describe = request.getParameter("describe");
        String img1 = request.getParameter("image1");
        String img2 = request.getParameter("image2");
        String img3 = request.getParameter("image3");
        String img4 = request.getParameter("image4");
        String img = img1 + "," + img2 + "," + img3 + "," + img4;
        String brand = request.getParameter("brand");
        String size = request.getParameter("size");
        String type = request.getParameter("type");
        String color = request.getParameter("color");
        String material = request.getParameter("material");
        String detail= size+","+material+","+type+","+color;
        int price = 10;
        int cid = 0;
        try {
            cid = Integer.parseInt(brand);
            price = Integer.parseInt(price_raw);
        } catch (Exception e) {
        }
        PrintWriter out = response.getWriter();
            if (manage.equalsIgnoreCase("add")) {
                Product c = d.addProduct(d.getMaxId()+1, name, price, describe, img, cid, detail);
                out.println(d.getMaxId());
                request.getRequestDispatcher("crud").forward(request, response);
            }
        else{
            String pid=request.getParameter("pid");
            Product c = d.updateProduct(pid, name, price, describe, img, cid, detail);
            out.println(c.getName()+pid);
            request.getRequestDispatcher("crud").forward(request, response);
                    
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
