package controller;

import dao.AdminDAO;
import dao.BannerDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Banner;
import model.Category;
import model.Product;

public class HomePage extends HttpServlet {

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            String categoryId = request.getParameter("categoryId") == null ? "" : request.getParameter("categoryId");
            String search = request.getParameter("search") == null ? "" : request.getParameter("search");
            String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
            search = search.trim();
            
            ProductDAO pdao = new ProductDAO();
            AdminDAO adao = new AdminDAO();
            BannerDAO badao = new BannerDAO();
            
            ArrayList<Category> clist = pdao.getCategory();
            int totalProduct = pdao.getNumberProduct(categoryId, search);
            int productsPerPage = 9; // Number of products per page
            int numberOfPages = (int) Math.ceil((double) totalProduct / productsPerPage);
            int currentPage;
            String currentPageParam = request.getParameter("page");
            if (currentPageParam == null || currentPageParam.equals("")) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(currentPageParam);
            }
            int offset = (currentPage - 1) * productsPerPage;

            ArrayList<Product> plist = pdao.getProductByPage(categoryId, search, offset, productsPerPage, sort);
             ArrayList<Product> plist1 = pdao.getTopSelling();
            ArrayList<Banner> balist = badao.getAllBanner();
            request.setAttribute("plist", plist);
            request.setAttribute("plist1", plist1);
            request.setAttribute("balist", balist);
            request.setAttribute("clist", clist);
            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("currentPage", currentPage);
            
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
