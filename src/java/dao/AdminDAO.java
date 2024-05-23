/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Roles;
import model.User;
import model.Admin;
import model.Banner;
import model.Category;
import model.OrderDetail;

/**
 *
 * @author thach
 */
public class AdminDAO extends DBContext {

    private MD5 md5 = new MD5();

    public static void main(String[] args) {
        AdminDAO udao = new AdminDAO();
        ArrayList<Admin> userList = udao.getAllAdmin();
        // ArrayList<Banner> userList = udao.getAllBanner();
        System.out.println(userList);
        // Print the current time
    }
    
     public ArrayList<User> getUsersByPage(int page, int itemsPerPage) {
    ArrayList<User> list = new ArrayList<>();
    int offset = (page - 1) * itemsPerPage;
    String sql = "SELECT * FROM Admin ORDER BY admin_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
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

     public int getNumberAdmin() {
        String sql = "  select count(*)from  Admin";
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
    
    public ArrayList<Admin> getAllUser() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "select * from [Admin]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                list.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<Admin> getAllAdmin() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "select * from [Admin]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                list.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r, rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Admin getAdminByEmail(String email, String pass) {
        String sql = "select * from [Admin] where [email]= ? and [password]=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, md5.getMd5(pass));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                Admin u = new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Admin> getAllUser1() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "select * from [Admin]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                list.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r, rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<Admin> Search(String uid, String search, String role) {
        ArrayList<Admin> ol = new ArrayList<>();
        String sql = "    select * from [Admin] where ([admin_name] like '%" + search + "%' or phone like '%" + search + "%' or address like '%" + search + "%') ";
        if (!uid.isEmpty()) {
            sql = sql + " and gender = ?";
        }
        if (!role.isEmpty()) {
            sql = sql + " and [role_id] = ?";
        }
        System.out.println(sql);
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (!uid.isEmpty() && role.isEmpty()) {
                ps.setString(1, uid);
            } else if (!role.isEmpty() && uid.isEmpty()) {
                ps.setString(1, role);
            } else if (!uid.isEmpty() && !role.isEmpty()) {
                ps.setString(1, uid);
                ps.setString(2, role);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                ol.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r, rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));

            }
        } catch (Exception e) {
        }
        return ol;
    }

    public void inserUser(String name, String email, String phone, String address, String pass, int gender, int role, String created_by, String modifile_by) {
        String sql = "  insert into [Admin] ([admin_name],[email],[password],[address],[gender] ,[phone],[role_id], [created_by], [created_on], [modifile_by], [modifile_on]) \n"
                + "  values (?,?,?,?,?,?,?,?,getdate(),?,getdate())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, md5.getMd5(pass));
            ps.setString(4, address);
            ps.setInt(5, gender);
            ps.setString(6, phone);
            ps.setInt(7, role);
            ps.setString(8, created_by);
            ps.setString(9, modifile_by);
            ps.executeUpdate();
        } catch (Exception e) {
        }

    }

    public Admin getAdminByEmail(String email) {
        String sql = "select * from [Admin] where [email]= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                Admin u = new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r);
                return u;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Admin> getAllAdminByEmail(String oid) {
        ArrayList<Admin> odl = new ArrayList<>();
        String sql = " select * from [Admin] where [email]= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, oid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles r = new Roles(rs.getInt(8));
                odl.add(new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7), r));
            }
        } catch (Exception e) {
        }
        return odl;
    }

    public void deleteAdmin(String id) {
        String sql = " delete [Admin] where [admin_id] =?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void UpdateAdmin(String name, String email, String phone, String address, int gender, int userid, String img) {
        String sql = " update [Admin] set [admin_name]=? ,[email] =? ,[address]=?, [gender]=? ,[phone]=?,[img]=? where [admin_id] =?";
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

    public void changePassword(String uid, String pass) {
        String sql = " update [Admin] set [password]=? where [admin_id] =?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, md5.getMd5(pass));
            stm.setString(2, uid);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

}
