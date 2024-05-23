/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.Product;

/**
 *
 * @author Admin
 */
public class AddToCart extends HttpServlet {

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
    // Thiết lập loại nội dung của phản hồi
    response.setContentType("text/html;charset=UTF-8");
    try {
        // Lấy HttpSession từ request
        HttpSession session = request.getSession();
        
        // Lấy thuộc tính "cart" từ session
        Object object = session.getAttribute("cart");

        // Khởi tạo các đối tượng cần thiết
        ProductDAO productDao = new ProductDAO();
        Cart cart = null;
        List<CartItem> items = new ArrayList<>();

        // Hiển thị một thông điệp trong phản hồi
        response.getWriter().println("||-|| \n");

        // Kiểm tra xem thuộc tính "cart" có khác null không
        if (object != null) {
            // Nếu không null, gán giá trị giỏ hàng hiện tại cho biến
            cart = (Cart) object;
        } else {
            // Nếu null, tạo một giỏ hàng mới
            cart = new Cart(items);
        }

        // Lấy các tham số từ request
        String url = request.getParameter("url");
        int productId = Integer.parseInt(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Lấy thông tin sản phẩm dựa trên ID sản phẩm
        Product product = productDao.getProductById(productId);

        // Tạo một đối tượng CartItem mới với sản phẩm và số lượng đã lấy
        CartItem item = new CartItem(product, quantity);

        // Thêm mục vào giỏ hàng
        cart.addItem(item);

        // Cập nhật thuộc tính "cart" trong session
        session.setAttribute("cart", cart);

        // Chuyển hướng người dùng dựa trên tham số "url" đã cung cấp
        switch (url) {
            case "HomePage":
                response.sendRedirect("HomePage");
                break;
            case "ProductDetails":
                response.sendRedirect("./ProductDetails?pid=" + productId);
                break;
            case "ProductList":
                response.sendRedirect("./ProductList?index=1");
                break;
            default:
                // Chuyển hướng đến trang mặc định nếu "url" không khớp với bất kỳ case nào
                response.sendRedirect("./ProductList?index=1");
                break;
        }

    } catch (Exception e) {
        // Xử lý các ngoại lệ
        e.printStackTrace();
        // Chuyển hướng đến trang lỗi chung
        response.sendRedirect("./404.html");
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
        processRequest(request, response);
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
