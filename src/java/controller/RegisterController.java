/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AdminDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Admin;
import model.Category;
import model.Question1;
import model.Question2;
import model.Roles;
import model.User;

/**
 *
 * @author Admin
 */
public class RegisterController extends HttpServlet {

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
    UserDAO udao = new UserDAO();
    AdminDAO adao = new AdminDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");

            String q1 = request.getParameter("Q1Id");
            String q1_ans = request.getParameter("q1");

            String q2 = request.getParameter("Q2Id");
            String q2_ans = request.getParameter("q2");
            String pass = request.getParameter("pass");
 // Kiểm tra xem người dùng và quản trị viên với email đã cho có tồn tại không
            User checkExist = udao.getUserByEmail(email);
            Admin check = adao.getAdminByEmail(email);
//              if (pass.length() >= 6 && pass.matches(".*[A-Z]+.*") && pass.matches(".*[a-z]+.*") && pass.matches(".*\\d+.*")) {
            if (checkExist == null && check == null) {
//                udao.inserUser(name, email, phone, address, pass, Integer.valueOf(gender), q1, q1_ans, q2, q2_ans);
 // Tạo một đối tượng người dùng mới
                User u = new User(name, email, pass, address, phone, gender.equals("1") ? true : false, q1, q1_ans, q2, q2_ans, new Roles(1));
                session.setAttribute("ui", u);// Thiết lập đối tượng người dùng trong phiên
        
//                request.setAttribute("messregis", "Success");
//                response.sendRedirect("./HomePage");
 // Cài đặt thuộc tính email
      
                final String username = "thach3619@gmail.com";
                final String password = "gymy afkb saok kcrb";
                Properties prop = new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.starttls.enable", "true");
                Session sessions = Session.getInstance(prop, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
                  // Tạo một phiên email
                String code = UUID.randomUUID().toString();
                session.setAttribute("codecheck", code);
                                  // Chuẩn bị nội dung email
                String emailTo = email;

                String emailSubject = "Verify Account";
                String emailContent = "You need to verify your account by clicking the link:"
                        + "http://localhost:8080/OnlineShopPrj/checkEmailSignUp?code=" + code;
                try {
                    // Tạo và gửi thông điệp email
                    Message message = new MimeMessage(sessions);
                    message.setFrom(new InternetAddress(username));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
                    message.setSubject(emailSubject);
                    message.setText(emailContent);
                    Transport.send(message);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
//                ArrayList<Question1> q1list = udao.getQuestion1();
//                ArrayList<Question2> q2list = udao.getQuestion2();
//                request.setAttribute("q1list", q1list);
//                request.setAttribute("q2list", q2list);
 // Thiết lập thông báo cho việc đăng ký thành công
                request.setAttribute("messregis", "Pleases check your email!");
//                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("name", name);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.setAttribute("messregis", "Email exist");
            }
//            } else {
//                ArrayList<Question1> q1list = udao.getQuestion1();
//                ArrayList<Question2> q2list = udao.getQuestion2();
//                request.setAttribute("name", name);
//                request.setAttribute("email", email);
//                request.setAttribute("phone", phone);
//                request.setAttribute("address", address);
//                request.setAttribute("q1list", q1list);
//                request.setAttribute("q2list", q2list);
//
//                String passwordError = "Password must contain at least 6 characters, include upper-lower characters and digits.";
//                request.setAttribute("messregis", passwordError);
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }

        } catch (Exception e) {
// Nếu có ngoại lệ xảy ra trong quá trình đăng ký
            request.setAttribute("messregis", "Invalid input.Please Try again!");

        }
        //lay du lieu q1,q2
        ArrayList<Question1> q1list = udao.getQuestion1();
        ArrayList<Question2> q2list = udao.getQuestion2();
        request.setAttribute("q1list", q1list);
        request.setAttribute("q2list", q2list);

        request.getRequestDispatcher("login.jsp").forward(request, response);

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
