/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BlogDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Blog;
import model.BlogCategory;

/**
 *
 * @author DLCT
 */
public class AddNewBlog extends HttpServlet {

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
            String ActionName = request.getParameter("Blog");
            BlogDAO bdao = new BlogDAO();
            System.out.println(ActionName);
            //create
            if (ActionName != null) {
                if (ActionName.equalsIgnoreCase("Create")) {
                    String banner = request.getParameter("banner");
                    String title = request.getParameter("title");
                    String cateId = request.getParameter("cateId");
                    String introduction = request.getParameter("introduction");
                    String body = request.getParameter("body");
                    String conclusion = request.getParameter("conclusion");
                    String author = request.getParameter("author");
                    String img = request.getParameter("image");

                    System.out.println(banner + title + img + cateId);
                    bdao.insertNewBlog(banner, title, cateId, introduction, body, conclusion, author, img);
                    response.sendRedirect("BlogList");

                }
                //update
                if (ActionName.equalsIgnoreCase("Update")) {
                    System.out.println("toi la update");
                    String bid = request.getParameter("bid");
                    String banner = request.getParameter("banner");
                    String title = request.getParameter("title");
                    String cateId = request.getParameter("cateId");
                    String introduction = request.getParameter("introduction");
                    String body = request.getParameter("body");
                    String conclusion = request.getParameter("conclusion");
                    String author = request.getParameter("author");
                    String img = request.getParameter("image");
                    String status = "1";
                    System.out.println(banner);
                    System.out.println(title);
                    System.out.println(cateId);
                    System.out.println(introduction);
                    System.out.println(body);
                    System.out.println(conclusion);
                    System.out.println(author);
                    System.out.println(img);
                    System.out.println(bid);
//                    bdao.updateBlog("cc", "cc", "2", "aa", "cc", "aaa", "aa", "aaaaa", "1", "10");
                    bdao.updateBlog(banner, title, cateId, introduction, body, conclusion, author, img, status, bid);
                    response.sendRedirect("BlogList");
                }

                //delete
                if (ActionName.equalsIgnoreCase("Delete")) {
                    String cateId = request.getParameter("cateId") == null ? "" : request.getParameter("cateId");
                    String name = request.getParameter("name") == null ? "" : request.getParameter("name");
                    String index = "1";
                    String sort = request.getParameter("sort") == null ? "order by b.id asc" : request.getParameter("sort");
                    String deleteId = request.getParameter("deleteId");
                    bdao.deleteById(deleteId);
                    ArrayList<Blog> blist = bdao.getBlogs(cateId, name, Integer.valueOf(index), sort);

                    // Lấy đối tượng BlogCategory dựa trên ID của danh mục blog
                    BlogCategory bc = bdao.getBlogCategoryId(cateId);

                    // Tính tổng số bài đăng để phục vụ việc phân trang
                    int totalBlogs = bdao.countBlogs(cateId, name);
                    int numberPage = (int) Math.ceil((double) totalBlogs / 12);

                    request.setAttribute("blist", blist);
                    request.setAttribute("bc", bc);
                    request.setAttribute("bcl", bdao.getBlogCategory());
                    request.setAttribute("numberPage", numberPage);
                    request.getRequestDispatcher("manageBlog.jsp").forward(request, response);
                }

                //send edit
                if (ActionName.equalsIgnoreCase("Edit")) {
                    String editId = request.getParameter("editId");
                    System.out.println(editId);
                    Blog blog = bdao.getBlogById(editId);
                    ArrayList bcl = bdao.getBlogCategory();
                    request.setAttribute("bcl", bcl);
                    request.setAttribute("blist", blog);
                    request.getRequestDispatcher("editBlog.jsp").forward(request, response);
                }
                if (ActionName.equalsIgnoreCase("Manage Blog")) {
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

                    request.getRequestDispatcher("manageBlog.jsp").forward(request, response);
                }
                if (ActionName.equalsIgnoreCase("Create New Blog")) {
                    request.setAttribute("bcl", bdao.getBlogCategory());

                    request.getRequestDispatcher("addBlog.jsp").forward(request, response);
                }
            }
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
