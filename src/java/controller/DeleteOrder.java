/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import model.Admin;
import model.User;

/**
 *
 * @author ADMIN-PC
 */
public class DeleteOrder extends HttpServlet {

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
            out.println("<title>Servlet DeleteOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteOrder at " + request.getContextPath() + "</h1>");
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
        String selectedOrderIds = request.getParameter("selectedOrderIds");
        String selectedOrderId = request.getParameter("oid");
        HttpSession session = request.getSession();
        Object object = session.getAttribute("account");

        if (object != null) {
            if (object instanceof User) {
                User u = (User) object;
                // User-specific actions
                if (u.getRoles().getId() == 2) {
                    handleAdminActions(request, response, selectedOrderIds, selectedOrderId);
                } else {
                    handleUserActions(request, response, selectedOrderIds, selectedOrderId);
                }
            } else if (object instanceof Admin) {
                Admin admin = (Admin) object;
                // Admin-specific actions
                handleAdminActions(request, response, selectedOrderIds, selectedOrderId);
            } else {
                // Handle other types or log an error
            }
        } else {
            // Handle the case when the object is null
        }
    }

    private void handleAdminActions(HttpServletRequest request, HttpServletResponse response, String selectedOrderIds, String selectedOrderId) throws IOException {
        try {
            if (selectedOrderId == null || selectedOrderId.isEmpty()) {
                String[] orderIdsToDelete = selectedOrderIds.split(",");
                System.out.println(Arrays.toString(orderIdsToDelete));
                OrderDAO or = new OrderDAO();
                or.deleteOrder(orderIdsToDelete);
            } else {
                int sltOrderId = Integer.parseInt(selectedOrderId);
                OrderDAO or = new OrderDAO();
                or.updateStatusOrder(4, sltOrderId);
            }
            response.sendRedirect("ManagerOrder");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp"); // Handle the error by redirecting to an error page
        }
    }

    private void handleUserActions(HttpServletRequest request, HttpServletResponse response, String selectedOrderIds, String selectedOrderId) throws IOException {
        try {
            if (selectedOrderId == null || selectedOrderId.isEmpty()) {
                String[] orderIdsToDelete = selectedOrderIds.split(",");
                System.out.println(Arrays.toString(orderIdsToDelete));
                OrderDAO or = new OrderDAO();
                or.deleteOrder(orderIdsToDelete);
            } else {
                int sltOrderId = Integer.parseInt(selectedOrderId);
                OrderDAO or = new OrderDAO();
                or.updateStatusOrder(4, sltOrderId);
            }
            response.sendRedirect("MyOrder");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp"); // Handle the error by redirecting to an error page
        }
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
