/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;

/**
 *
 * @author Admin
 */
public class MyOrder extends HttpServlet {

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
            // Lấy giá trị tham số từ request hoặc sử dụng giá trị mặc định nếu tham số không tồn tại
            String fdate = request.getParameter("fdate") == null ? "1920-05-05" : request.getParameter("fdate");
            String tdate = request.getParameter("tdate") == null ? "3020-05-05" : request.getParameter("tdate");

            // Tạo đối tượng OrderDAO để tương tác với cơ sở dữ liệu
            OrderDAO odao = new OrderDAO();
            
            // Lấy HttpSession từ request
            HttpSession session = request.getSession();

            // Lấy đối tượng User từ session
            Object object = session.getAttribute("account");
            User u = (User) object;

            // Lấy danh sách đơn hàng của người dùng từ cơ sở dữ liệu
            ArrayList<Order> ol = odao.getAllOrderByuId(u.getId(), fdate, tdate);

            // Đặt danh sách đơn hàng vào thuộc tính của request để sử dụng trong JSP
            request.setAttribute("ol", ol);

            // Chuyển hướng người dùng đến trang myorder.jsp để hiển thị thông tin đơn hàng
            request.getRequestDispatcher("myorder.jsp").forward(request, response);
        } catch (Exception e) {
            // Xử lý ngoại lệ và chuyển hướng đến trang lỗi chung
            response.sendRedirect("myorder.jsp");
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
