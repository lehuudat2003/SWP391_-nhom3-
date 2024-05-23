/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.OrderDetail;
import model.User;

/**
 *
 * @author Admin
 */
public class OrderDetails extends HttpServlet {

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
    // Thiết lập loại nội dung trả về là text/html với bảng mã UTF-8
    response.setContentType("text/html;charset=UTF-8");

    // Lấy giá trị của tham số 'oid' từ request và chuyển đổi thành kiểu integer
    int oid = Integer.valueOf(request.getParameter("oid"));

    // Tạo đối tượng OrderDAO để thao tác với cơ sở dữ liệu
    OrderDAO odao = new OrderDAO();
      UserDAO udao = new UserDAO();
      User user = udao.getUserByOrderId(oid);
    // Lấy danh sách chi tiết đơn hàng theo ID đơn hàng (oid)
    ArrayList<OrderDetail> odl = odao.getAllOrderDetailByoId(oid);

    // Đặt danh sách chi tiết đơn hàng vào request để truyền sang trang JSP
    request.setAttribute("odl", odl);
    request.setAttribute("user", user);
    // Chuyển hướng đến trang hiển thị chi tiết đơn hàng (orderdetails.jsp)
    request.getRequestDispatcher("orderdetails.jsp").forward(request, response);
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
