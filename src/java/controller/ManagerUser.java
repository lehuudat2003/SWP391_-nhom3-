package controller;

import dao.UserDAO;
import model.User;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ManagerUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDAO udao = new UserDAO();

        // Pagination logic
        int numberOfUsers = udao.getNumberUser();
        int itemsPerPage = 2;
        int numberPage = (int) Math.ceil((double) numberOfUsers / itemsPerPage);
        int currentPage;
        String currentPageParam = request.getParameter("index");
        if (currentPageParam == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPageParam);
        }

        // Retrieve the paginated list of users
        ArrayList<User> userList = udao.getUsersByPage(currentPage, itemsPerPage);
        
        request.setAttribute("numberOfUsers", numberOfUsers);
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("userList", userList);
        
        request.getRequestDispatcher("manageruser.jsp").forward(request, response);
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
