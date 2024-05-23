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
import model.Feedback;
import model.Product;
import model.Product_img;

public class DeleteProduct2 extends HttpServlet {

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
        String pid = request.getParameter("pid") == null ? "" : request.getParameter("pid");
        int id = Integer.valueOf(request.getParameter("mod"));
        ProductDAO pdao = new ProductDAO();
        pdao.DeleteImg(id);

        Product p = pdao.getProductById(Integer.valueOf(pid));
        ArrayList<Feedback> f = pdao.getAllFeedbacks(pid);
        ArrayList<Product_img> o = pdao.getImgById(pid);

        request.setAttribute("f", f);
        request.setAttribute("p", p);

        request.setAttribute("o", o);
        request.getRequestDispatcher("productdetails.jsp").forward(request, response);

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
