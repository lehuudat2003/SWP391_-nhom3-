/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.User;
import model.Admin;

/**
 *
 * @author Admin
 */
public class Checkout extends HttpServlet {

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
        HttpSession session = request.getSession();

        // Lấy thông tin từ session
        Object object = session.getAttribute("account");
        Object object1 = session.getAttribute("cart");
        

        // Ép kiểu đối tượng "account" về đối tượng User
        User user = (User) object;
       

        // Ép kiểu đối tượng "cart" về đối tượng Cart
        Cart cart = (Cart) object1;

        // Lưu lại giỏ hàng đã được cập nhật vào session
        session.setAttribute("cart", cart);

        // Print information about the updated cart
        System.out.println("User: " + user);
       
        System.out.println("Updated Cart:");
        for (CartItem cartItem : cart.getItems()) {
            System.out.println("Product ID: " + cartItem.getProduct().getId() + ", Quantity: " + cartItem.getQuantity());
        }

        if (user == null) {
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
        // Lấy HttpSession từ request
        HttpSession session = request.getSession();

        // Lấy các đối tượng từ session
        Object object = session.getAttribute("account");
        Object object1 = session.getAttribute("cart");

        // Lấy ghi chú từ tham số của request
        String notes = request.getParameter("notes");

        // Ép kiểu đối tượng "account" về đối tượng User
        User u = (User) object;

        // Ép kiểu đối tượng "cart" về đối tượng Cart
        Cart cart = (Cart) object1;
        // Tạo một đối tượng OrderDAO
        OrderDAO odao = new OrderDAO();

        // Thực hiện thêm thông tin đơn hàng vào cơ sở dữ liệu
        boolean success = odao.insertOrder1(u, cart, notes);
        session.removeAttribute("cart");

        HttpSession resultSession = request.getSession();
        if (success) {
            // Đặt thông báo thành công vào session
            resultSession.setAttribute("orderResult", "success");
        } else {
            // Đặt thông báo thất bại vào session
            resultSession.setAttribute("orderResult", "error");
        }

        response.sendRedirect("result.jsp");

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
