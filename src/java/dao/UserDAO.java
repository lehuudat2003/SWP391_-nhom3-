/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Order;
import model.Product;
import model.Question1;
import model.Question2;
import model.Roles;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    private MD5 md5 = new MD5();

    public static void main(String[] args) {
        String pass = "123456";
        int count = 1;

        if (pass.length() >= 6 && pass.matches(".*[A-Z]+.*") && pass.matches(".*[a-z]+.*") && pass.matches(".*\\d+.*")) {
            count = 0;
            System.out.println("đúng");
        }

        System.out.println(count);
        // Print the current time
    }
    public ArrayList<User> getUsersByPage(int page, int itemsPerPage) {
    ArrayList<User> list = new ArrayList<>();
    int offset = (page - 1) * itemsPerPage;
    String sql = "SELECT * FROM Users ORDER BY user_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, offset);
        ps.setInt(2, itemsPerPage);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Roles r = new Roles(rs.getInt(8));
            User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
            list.add(u);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    return list;
}

    public ArrayList<User> getAllUser() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "select * from [Users]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                list.add(u);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;

    }

    public User getUserByEmail(String email, String pass) {
        String sql = "select * from [Users] where [email]=? and [password]=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, md5.getMd5(pass));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<User> getAllUser1() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "select * from [Users]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r, rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = "select * from [Users] where [user_id]=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "select * from [Users] where [email]= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
   

    public void inserUser(String name, String email, String phone, String address, String pass, int gender, String q1_id, String q1_ans, String q2_id, String q2_ans) {
        String sql = "  insert into [Users] ([user_name],[email],[password],[address],[gender] ,[phone],[role_id],[q1_id],[q1_answer],[q2_id],[q2_answer]) \n"
                + "  values (?,?,?,?,?,?,1,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, md5.getMd5(pass));
            ps.setString(4, address);
            ps.setInt(5, gender);
            ps.setString(6, phone);
            ps.setString(7, q1_id);
            ps.setString(8, q1_ans);
            ps.setString(9, q2_id);
            ps.setString(10, q2_ans);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void UpdateUser(String name, String email, String phone, String address, int gender, int userid, String img) {
        String sql = " update [Users] set [user_name]=? ,[email] =? ,[address]=?, [gender]=? ,[phone]=?,[img]=? where [user_id] =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setInt(4, gender);
            ps.setString(5, phone);
            ps.setInt(7, userid);
            ps.setString(6, img);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int getNumberUser() {
        String sql = "  select count(*)from  Users";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public void changePassword(String uid, String pass) {
        String sql = " update [Users] set [password]=? where [user_id] =?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, md5.getMd5(pass));
            stm.setString(2, uid);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void deleteuser(String uid) {
        String sql = " delete [Users] where [user_id] =?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, uid);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public ArrayList<Question1> getQuestion1() {
        ArrayList<Question1> list = new ArrayList<>();
        String sql = "  select* from question1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Question1(rs.getInt("q1_id"), rs.getString("q1_name")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Question2> getQuestion2() {
        ArrayList<Question2> list = new ArrayList<>();
        String sql = "  select* from question2";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Question2(rs.getInt("q2_id"), rs.getString("q2_name")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public User FogotPass(String email, String phone, String address, String q1, String q1_ans, String q2, String q2_ans) {
        String sql = "select * from [Users] where [email]= ? and [phone]=? and [address]=? and [q1_id]=? and [q1_answer]=? and [q2_id]=? and [q2_answer]=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, q1);
            ps.setString(5, q1_ans);
            ps.setString(6, q2);
            ps.setString(7, q2_ans);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void changePasswordByEmail(String email, String pass) {
        String sql = " update [Users] set [password]=? where [email] =?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, md5.getMd5(pass));
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public ArrayList<User> Search(String uid, String search) {
        ArrayList<User> ol = new ArrayList<>();
        String sql = "    select * from [Users] where ([user_name] like '%" + search + "%' or phone like '%" + search + "%' or address like '%" + search + "%') ";
        if (!uid.isEmpty()) {
            sql = sql + " and gender = ?";
        }
        System.out.println(sql);
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (!uid.isEmpty()) {
                ps.setString(1, uid);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                ol.add(u);
            }
        } catch (Exception e) {
        }
        return ol;
    }
    public ArrayList<User> searchAndPaginate(String uid, String search, int index) {
        ArrayList<User> list = new ArrayList<>();
        String baseSql = "SELECT * FROM Users WHERE (user_name LIKE ? OR phone LIKE ? OR address LIKE ?)";
        String paginationSql = " ORDER BY user_id ASC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
        String finalSql = uid.isEmpty() ? baseSql : baseSql + " AND gender = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(finalSql + paginationSql);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");

            if (!uid.isEmpty()) {
                ps.setString(4, uid);
                ps.setInt(5, (index - 1) * 5);
            } else {
                ps.setInt(4, (index - 1) * 5);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    


    public User getUserByOrderId(int orderId) {
        String sql = "SELECT u.* FROM Users u "
                + "JOIN [Order] o ON u.user_id = o.user_id "
                + "WHERE o.order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Roles r = new Roles(rs.getInt(8));
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
