/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.User;

/**
 *
 * @author ADMIN-PC
 */
public class UpdateQuantityCart extends HttpServlet {

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
            out.println("<title>Servlet UpdateQuantityCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateQuantityCart at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");
        Object object1 = session.getAttribute("cart");

        // Ép kiểu đối tượng "cart" về đối tượng Cart
        Cart cart = (Cart) object1;

        if (quantityParam != null && !quantityParam.isEmpty()) {
            // Chuyển đổi quantityParam thành số nguyên
            int newQuantity = Integer.parseInt(quantityParam);

            // Thực hiện cập nhật số lượng trong giỏ hàng (giả sử bạn có một đối tượng giỏ hàng là 'cart')
            // Chú ý: Đây chỉ là một giả định, bạn cần điều chỉnh mã này phù hợp với cấu trúc và logic của ứng dụng bạn
            for (CartItem cartItem : cart.getItems()) {
                int existingProductId = cartItem.getProduct().getId();
                if (existingProductId == Integer.parseInt(productIdParam)) {
                    cart.updateQuantity(existingProductId, newQuantity);
                    break; // Nếu bạn đã tìm thấy sản phẩm, bạn có thể thoát vòng lặp
                }
            }
        }

        // Lưu lại giỏ hàng đã được cập nhật vào session
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("ViewCart").forward(request, response);
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
