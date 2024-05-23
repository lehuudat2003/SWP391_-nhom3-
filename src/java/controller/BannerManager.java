/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Helper.UploadImage;
import dao.BannerDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Admin;
import model.Banner;
import model.Category;
import model.Product;

/**
 *
 * @author thach
 */

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)

public class BannerManager extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Banner</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Banner at " + request.getContextPath() + "</h1>");
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
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            Object object = session.getAttribute("account");
            Admin u = (Admin) object;
            if (u.getRoles().getId() == 2 || u.getRoles().getId() == 3) {
                BannerDAO badao = new BannerDAO();
                ArrayList<Banner> pl = badao.getAllBanner();

                request.setAttribute("pl", pl);

                request.getRequestDispatcher("managerbanner.jsp").forward(request, response);
            } else {
                response.sendRedirect("404.html");
            }
        } catch (Exception e) {
            response.sendRedirect("login");

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
        try {
            HttpSession session = request.getSession();
                        

            Object object = session.getAttribute("account");
            
            String tittle = request.getParameter("tittle");
            String content = request.getParameter("content");

            String id = request.getParameter("id");

            BannerDAO badao = new BannerDAO();
            response.getWriter().print(request.getParameter("photo"));
            UploadImage uploadImage = new UploadImage();
            String img = (String) uploadImage.uploadFile(request, "img");
            
            badao.insertBanner(img, tittle, content, id, id);
            
            response.sendRedirect("BannerManager");
        } catch (Exception e) {

            request.setAttribute("messregis", "Invalid input.Please Try again!");
            request.getRequestDispatcher("AddBanner.jsp").forward(request, response);

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
