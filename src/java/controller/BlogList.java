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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Blog;
import model.BlogCategory;

/**
 *
 * @author duypr
 */
public class BlogList extends HttpServlet {

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
        // Thiết lập kiểu nội dung của phản hồi
        response.setContentType("text/html;charset=UTF-8");
        
        // Tạo một đối tượng BlogDAO để thao tác với dữ liệu blog
        BlogDAO bdao = new BlogDAO();
        
        // Lấy các tham số từ yêu cầu hoặc sử dụng giá trị mặc định nếu không có
        String cateId = request.getParameter("cateId") == null ? "" : request.getParameter("cateId");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String index = request.getParameter("index") == null ? "1" : request.getParameter("index");
        String sort = request.getParameter("sort") == null ? "order by b.id asc" : request.getParameter("sort");
        
        // Lấy danh sách blog từ BlogDAO dựa trên các tham số truyền vào
        ArrayList<Blog> blist = bdao.getBlogs(cateId, name, Integer.valueOf(index), sort);
        
        // Lấy đối tượng BlogCategory dựa trên ID của danh mục blog
        BlogCategory bc = bdao.getBlogCategoryId(cateId);

        // Tính tổng số bài đăng để phục vụ việc phân trang
        int totalBlogs = bdao.countBlogs(cateId, name);
        int numberPage = (int) Math.ceil((double) totalBlogs / 9);
        
        //3.1 ->4 3.0 
        // Đặt các thuộc tính trong yêu cầu để chuyển đến trang 'blog.jsp'
        request.setAttribute("blist", blist);
        request.setAttribute("bc", bc);
        request.setAttribute("bcl", bdao.getBlogCategory());
        request.setAttribute("numberPage", numberPage);

        // Chuyển tiếp yêu cầu đến trang 'blog.jsp'
        request.getRequestDispatcher("blog.jsp").forward(request, response);
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
        // Gọi phương thức processRequest để xử lý yêu cầu GET
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
        // Gọi phương thức processRequest để xử lý yêu cầu POST
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
