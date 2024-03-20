/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
public class ProductsServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductsServlet at " + request.getContextPath() + "</h1>");
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
    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            if (queryString.contains("&cid=")) {
                int s = queryString.indexOf("&cid=");
                queryString = queryString.substring(0, s) + queryString.substring(s + 6, queryString.length());

            }
            if (queryString.contains("&AllMaterial=on")) {
                int s = queryString.indexOf("&AllMaterial=on");
                queryString = queryString.substring(0, s) + queryString.substring(s + 15, queryString.length());
            }
            if (queryString.contains("&AllSize=on")) {
                int s = queryString.indexOf("&AllSize=on");
                queryString = queryString.substring(0, s) + queryString.substring(s + 11, queryString.length());
            }
            if (queryString.contains("&AllStyle=on")) {
                int s = queryString.indexOf("&AllStyle=on");
                queryString = queryString.substring(0, s) + queryString.substring(s + 12, queryString.length());
            }

            if (queryString.contains("&page=") && queryString.contains("&sort=")) {
                int s = queryString.indexOf("&page");
                queryString = queryString.substring(0, s) + queryString.substring(s + 7, queryString.length());

            } else if (queryString.contains("&page=")) {
                int s = queryString.indexOf("&page");
                queryString = queryString.substring(0, s) + queryString.substring(s + 7, queryString.length());
            } else if (queryString.contains("&sort=")) {
                int x = queryString.indexOf("&sort");
                queryString = queryString.substring(0, x) + queryString.substring(x + 7, queryString.length());
            }
            return requestURL.append('?').append(queryString).toString();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String gold = request.getParameter("gold");
        String platinum = request.getParameter("platinum");
        String leather = request.getParameter("leather");
        String silicone = request.getParameter("silicone");
        String small = request.getParameter("small");
        String big = request.getParameter("big");
        String medium = request.getParameter("medium");
        String classic = request.getParameter("classic");
        String electronics = request.getParameter("electronics");
        String color = request.getParameter("color");

        String price_min = request.getParameter("price-min");
        String price_max = request.getParameter("price-max");
        int min, max;
        String URL = getFullURL(request);
        
        request.setAttribute("URLpath", URL);
        out.print(URL);

        if (gold == null && platinum == null && leather == null && silicone == null && small == null && big == null && medium == null && classic == null && electronics == null && price_min == null && price_max == null) {

            if (request.getParameter("page") != null && request.getParameter("id") == null) {
                String cid_raw = request.getParameter("cid");
                String page_raw = request.getParameter("page");

                int cid, page;
                try {
                    cid = Integer.parseInt(cid_raw);
                    page = Integer.parseInt(page_raw);
                    DAO d = new DAO();
                    List<Product> list = d.getProductsBYCid(cid);
                    Category category = d.getCategoryById(cid);
                    request.setAttribute("products", list);
                    request.setAttribute("page", page);
                    request.setAttribute("cid", cid);
                    request.setAttribute("cat", category);

                } catch (NumberFormatException e) {
                }
                request.getRequestDispatcher("sort").forward(request, response);
            }
            //store category=?
            if (request.getParameter("cid") != null) {
                String cid_raw = request.getParameter("cid");
                int cid;
                try {
                    cid = Integer.parseInt(cid_raw);
                    DAO d = new DAO();
                    List<Product> list = d.getProductsBYCid(cid);
                    request.setAttribute("products", list);
                    request.setAttribute("page", 1);
                    request.setAttribute("cid", cid);
                    Category category = d.getCategoryById(cid);
                    request.setAttribute("cat", category);

                } catch (NumberFormatException e) {

                }
                request.getRequestDispatcher("sort").forward(request, response);
            }
            //product Details =?
            if (request.getParameter("id") != null) {
                out.println("\n" + request.getParameter("id"));
                String id_raw = request.getParameter("id");
                if (request.getParameter("page") != null) {
                    String page_raw = request.getParameter("page");
                    int page = 0;
                    try {
                        page = Integer.parseInt(page_raw);
                    } catch (Exception e) {
                    }
                    request.setAttribute("page", page);
                }
                int id;
                try {
                    id = Integer.parseInt(id_raw);
                    DAO d = new DAO();
                    List<Product> list = d.getTheSameProduct(id);
                    Product p = d.getProductsBYid(id);
                    request.setAttribute("product", p);
                    request.setAttribute("products", list);

                } catch (NumberFormatException e) {

                }
                request.getRequestDispatcher("productcomment").forward(request, response);
            }
        } //filter=?
        else {

            String cid_raw = request.getParameter("cid");
            String page_raw = request.getParameter("page");
            if (color.contains("Allcolor")) {
                color = "";
            }
            String details = gold + "," + platinum + "," + leather + "," + silicone
                    + ":" + small + "," + medium + "," + big + ":" + classic + "," + electronics + ":" + color;

            //----------------price filter
            if (price_min != null && price_max != null) {
                //-price
                price_min = price_min.replace(".00", "");
                price_max = price_max.replace(".00", "");
                try {
                    min = Integer.parseInt(price_min);
                    max = Integer.parseInt(price_max);
                    out.println("min:  " + min + "max: " + max);
                    details += ":" + min + "," + max;
                    request.setAttribute("min", min);
                    request.setAttribute("max", max);

                } catch (Exception e) {
                }
                //-price

            }
            int cid, page;
            //-----------page filter
            try {
                if (page_raw != null) {
                    page = Integer.parseInt(page_raw);
                    request.setAttribute("page", page);
                    out.println("page: " + page);
                } else {
                    request.setAttribute("page", 1);
                }
                cid = Integer.parseInt(cid_raw);
                DAO d = new DAO();
                //-----------------get product after filter
                List<Product> list = d.getProductsAfterFilter(cid, details);
                //set attribute cid,products,cat
                request.setAttribute("products", list);
                request.setAttribute("cid", cid);
                Category category = d.getCategoryById(cid);
                request.setAttribute("cat", category);
                

                //------------------test value before redirect
                out.println(details);
                String[] detailz = details.split(":");
                out.println("detail size:" + detailz.length);
                String[] detail1 = detailz[0].split(",");
                String[] detail2 = detailz[1].split(",");
                String[] detail3 = detailz[2].split(",");
                String[] detail4 = detailz[4].split(",");

                out.println(detailz[0] + "  " + detailz[1] + " " + detailz[2] + " " + detailz[3]);
                String sql = "";
                for (String i : detail1) {
                    if (i.equalsIgnoreCase("null")) {
                        continue;
                    } else {
                        sql += " or p.details like '%" + i + "%'";
                    }

                }
                out.println(sql);

                for (String i : detail2) {
                    if (i.equalsIgnoreCase("null")) {
                        continue;
                    } else {
                        sql += " or p.details like '%" + i + "%'";
                    }

                }
                out.println(sql);

                for (String i : detail3) {
                    if (i.equalsIgnoreCase("null")) {
                        continue;
                    } else {
                        sql += " or p.details like '%" + i + "%'";
                    }

                }
                int dem = 0;
                for (String i : detail4) {
                    if (i.equalsIgnoreCase("null")) {
                        continue;
                    } else if (dem > 0) {
                        sql += " and p.price< " + i;
                        dem++;
                    } else if (dem == 0) {
                        sql += "p.price>" + i;
                        dem++;
                    }
                }
                out.println(sql + " and p.color=" + detailz[3]);

            } catch (Exception e) {
            }
            request.getRequestDispatcher("sort").forward(request, response);
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
