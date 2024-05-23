/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.BlogCategory;

/**
 *
 * @author duypr
 */
@WebServlet("/BlogDetail")
public class BlogDetail extends HttpServlet {

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
            /* TODO output your page here. You may use the following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogDetail at " + request.getContextPath() + "</h1>");
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
        // Tạo một thể hiện của BlogDAO
        BlogDAO bdao = new BlogDAO();

        // Lấy tham số 'bid' từ yêu cầu hoặc chuỗi rỗng nếu không có
        String bid = request.getParameter("bid") == null ? "" : request.getParameter("bid");

        // Truy xuất đối tượng Blog bằng ID của nó sử dụng BlogDAO
        Blog b = bdao.getBlogById(bid);

        // Truy xuất đối tượng BlogCategory bằng ID của danh mục blog sử dụng BlogDAO
        BlogCategory bc = bdao.getBlogCategoryId(String.valueOf(b.getBlogCategoryObject()));

        // Cập nhật số lần xem cho bl
        bdao.updateViewCount(bid);

        // Đặt đối tượng Blog và BlogCategory như là các thuộc tính trong yêu cầu
        request.setAttribute("b", b);
        request.setAttribute("bc", bc);

        // Chuyển tiếp yêu cầu đến trang 'blogdetail.jsp'
        request.getRequestDispatcher("blogdetail.jsp").forward(request, response);
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
        // Gọi phương thức processRequest để xử lý các yêu cầu POST
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        // Trả về một mô tả ngắn về servlet
        return "Short description";
    }// </editor-fold>

}
