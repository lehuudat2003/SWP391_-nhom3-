/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author thach
 */
public class checkEmailSignUp extends HttpServlet {

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
                HttpSession ss = request.getSession();
        User u = (User) ss.getAttribute("ui");
        String message = "";
        UserDAO udao = new UserDAO();
        
        if (u != null && ss.getAttribute("codecheck").toString().equals(request.getParameter("code"))) {
                udao.inserUser(u.getName(), u.getEmail(), u.getPhone(), u.getAddress(), u.getPassword(), Integer.parseInt(u.getGender()?"1":"0"), u.getQ1_id(), u.getQ1_ans(), u.getQ2_id(), u.getQ2_ans());
//                User u = new User(name, email, pass, address, phone, q1_ans, q1_ans, q2_ans, q2_ans, new Roles(1));
//                request.setAttribute("messregis", "Success");
//                response.sendRedirect("./HomePage");
            message = "Sign Up Successfully!";
        } else {
            message = "Invalid access!";
        }
        ss.setAttribute("message", message);
        request.getRequestDispatcher("checkEmailSignUp.jsp").forward(request, response);
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
