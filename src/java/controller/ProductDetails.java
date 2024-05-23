/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Category;
import model.Feedback;
import model.Product;
import model.Product_img;

/**
 *
 * @author Admin
 */
public class ProductDetails extends HttpServlet {

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
        try {
            ProductDAO pdao = new ProductDAO();
            String pid = request.getParameter("pid") == null ? "" : request.getParameter("pid");
            ArrayList<Product> plist = pdao.getProduct("", "", 1, "1");
            ArrayList<Product> plist2 = pdao.getProduct("2", "", 1, "1");
            ArrayList<Product> plist1 = pdao.getTopSelling();
            Product p = pdao.getProductById(Integer.valueOf(pid));
//            ArrayList<Feedback> f = pdao.getAllFeedbacks(pid);
//            ArrayList<Product_img> o = pdao.getImgById(pid);

//            request.setAttribute("f", f);
            request.setAttribute("p", p);
            request.setAttribute("plist", plist);
            request.setAttribute("plist1", plist1);
            request.setAttribute("plist2", plist2);
//            request.setAttribute("o", o);
            request.getRequestDispatcher("productdetails.jsp").forward(request, response);
        } catch (Exception e) {
            // commit 
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
        processRequest(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            ProductDAO pdao = new ProductDAO();
            String pid = request.getParameter("pid") == null ? "" : request.getParameter("pid");
            String comment = request.getParameter("comment");
            String id = request.getParameter("id");
            Product p = pdao.getProductById(Integer.valueOf(pid));

            ArrayList<Product_img> o = pdao.getImgById(pid);

            pdao.AddFeedback(comment, Integer.parseInt(id), Integer.parseInt(pid));

            ArrayList<Feedback> f = pdao.getAllFeedbacks(pid);

            request.setAttribute("f", f);

            request.setAttribute("p", p);
            request.setAttribute("o", o);
            request.getRequestDispatcher("productdetails.jsp").forward(request, response);
        } catch (Exception e) {

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
