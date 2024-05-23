/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;

/**
 *
 * @author Admin
 */
public class OrderDAO extends DBContext {

    //Phuong thuc de them mot don hang moi vao co so du lieu
    public void insertOrder(User u, Cart cart, String notes) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //Theem 1 don hang moi vao co so du lieu
            String sql = "insert into [Order] ([user_id],[order_date],[total],[notes],[status]) values (?,GETDATE(), ?, ?,1)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, u.getId());
            ps.setDouble(2, cart.getTotalMoney());
            ps.setString(3, notes);
            ps.executeUpdate();
//Lay Id don hang vua them vao de su dung cho viec them thong tin don hang
            String sql1 = "select top 1 order_id from [Order] order by order_id desc";
            ps = connection.prepareStatement(sql1);
            rs = ps.executeQuery();

            if (rs.next()) {
                int oid = rs.getInt(1);
                //Them thong tin chi tiet don hang vao bang OrderDetail cho tung san pham trong gio hang
                for (CartItem item : cart.getItems()) {
                    String sql2 = "insert into [OrderDetail] ([order_id],[product_id]  ,[price],[quantity]) values (?,?, ?, ?)";
                    ps = connection.prepareStatement(sql2);
                    ps.setInt(1, oid);
                    ps.setInt(2, item.getProduct().getId());
                    ps.setDouble(3, item.getProduct().getPrice());
                    ps.setInt(4, item.getQuantity());
                    ps.executeUpdate();
                }
            }
            // Cap nhat so luong san pham trong bang product
            String sql3 = "update [Product] set [stock] = [stock] - ? "
                    + "where product_id = ?";
            ps = connection.prepareStatement(sql3);
            for (CartItem item : cart.getItems()) {
                ps.setInt(1, item.getQuantity());
                ps.setInt(2, item.getProduct().getId());
                ps.executeUpdate();
            }

        } catch (Exception e) {
        }
    }
    
    
    public boolean insertOrder1(User u, Cart cart, String notes) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        // Thêm 1 đơn hàng mới vào cơ sở dữ liệu
        String sql = "insert into [Order] ([user_id],[order_date],[total],[notes],[status]) values (?,GETDATE(), ?, ?,1)";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, u.getId());
        ps.setDouble(2, cart.getTotalMoney());
        ps.setString(3, notes);
        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            // Lấy Id đơn hàng vừa thêm vào để sử dụng cho việc thêm thông tin đơn hàng
            String sql1 = "select top 1 order_id from [Order] order by order_id desc";
            ps = connection.prepareStatement(sql1);
            rs = ps.executeQuery();

            if (rs.next()) {
                int oid = rs.getInt(1);
                // Thêm thông tin chi tiết đơn hàng vào bảng OrderDetail cho từng sản phẩm trong giỏ hàng
                for (CartItem item : cart.getItems()) {
                    String sql2 = "insert into [OrderDetail] ([order_id],[product_id]  ,[price],[quantity]) values (?,?, ?, ?)";
                    ps = connection.prepareStatement(sql2);
                    ps.setInt(1, oid);
                    ps.setInt(2, item.getProduct().getId());
                    ps.setDouble(3, item.getProduct().getPrice());
                    ps.setInt(4, item.getQuantity());
                    ps.executeUpdate();
                }
            }
            // Cập nhật số lượng sản phẩm trong bảng product
            String sql3 = "update [Product] set [stock] = [stock] - ? "
                    + "where product_id = ?";
            ps = connection.prepareStatement(sql3);
            for (CartItem item : cart.getItems()) {
                ps.setInt(1, item.getQuantity());
                ps.setInt(2, item.getProduct().getId());
                ps.executeUpdate();
            }
            return true; // Trả về true nếu thêm đơn hàng thành công
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra lỗi nếu có
    } finally {
        // Đóng các tài nguyên
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // In ra lỗi nếu có
        }
    }
    return false; // Trả về false nếu có lỗi xảy ra hoặc không thêm được đơn hàng
}

// phuong thuc lay danh sach don hang cho mot ngioi dung cu the trong khoang thoi gian nhat dinh

    public ArrayList<Order> getAllOrderByuId(int uid, String fdate, String tdate) {
        if (fdate.isEmpty()) {
            fdate = "1990-01-01";
        }
        if (tdate.isEmpty()) {
            tdate = "2990-01-01";
        }
        ArrayList<Order> ol = new ArrayList<>();
        //Lay tat ca thong tin don hang 
        String sql = "  select * from [Order] where user_id = ? and [order_date] between ? and ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setString(2, fdate);
            ps.setString(3, tdate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ol.add(new Order(rs.getInt(1), new User(rs.getInt(2)), rs.getDate(3), rs.getDouble(4), rs.getString(5), rs.getInt(6)));
            }
        } catch (Exception e) {
        }
        return ol;
    }
//phuong thuc lay danh sach don hang voi tuy chon loc theo trang thai,khoang thoi gian, va tu khoa tim kiem

    public ArrayList<Order> getAllOrder(String uid, String fdate, String tdate, String search) {
        if (fdate.isEmpty()) {
            fdate = "1990-01-01";
        }
        if (tdate.isEmpty()) {
            tdate = "2990-01-01";
        }
        ArrayList<Order> ol = new ArrayList<>();

        String sql = "    select * from [Order] o join Users u on o.user_id = u.user_id  where (u.user_name like '%" + search + "%' or u.phone like '%" + search + "%' or u.address like '%" + search + "%' or o.order_id like '%" + search + "%') and o.[order_date] between ? and ? ";
        if (!uid.isEmpty()) {
            sql = sql + " and o.status = ? ";
        }
        System.out.println(sql);
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fdate);
            ps.setString(2, tdate);
            if (!uid.isEmpty()) {
                ps.setString(3, uid);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ol.add(new Order(rs.getInt(1), new User(rs.getInt(2)), rs.getDate(3), rs.getDouble(4), rs.getString(5), rs.getInt(6), rs.getString("phone"), rs.getString("user_name"), rs.getString("address")));
            }
        } catch (Exception e) {
        }
        return ol;
    }

    public ArrayList<OrderDetail> getAllOrderDetailByoId(int oid) {
        ArrayList<OrderDetail> odl = new ArrayList<>();
        String sql = " SELECT  p.*, o.* FROM [OrderDetail] o, Product p where o.product_id = p.product_id and o.order_id =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id")), rs.getString(6), rs.getString(7), rs.getDate(8));
                odl.add(new OrderDetail(rs.getInt("detail_id"), rs.getInt("order_id"), p, rs.getDouble("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {
        }
        return odl;
    }

    public int getNumberOrder() {
        String sql = "  select count(*)from  OrderDetail";
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

    public double getTotalProfit() {
        String sql = "  select sum(price*quantity) from OrderDetail";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public void updateStatusOrder(int status, int id) {
        try {
            String sql = " update [Order] set status = " + status + " where [order_id] =" + id;
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void deleteOrder(String[] orderIds) {
        if (orderIds == null || orderIds.length == 0) {
            return;
        }

        PreparedStatement ps = null;

        try {
            String deleteOrderDetailSql = "DELETE FROM OrderDetail WHERE order_id IN (";
            StringBuilder placeholdersDetail = new StringBuilder();
            for (int i = 0; i < orderIds.length; i++) {
                placeholdersDetail.append("?");
                if (i < orderIds.length - 1) {
                    placeholdersDetail.append(", ");
                }
            }
            deleteOrderDetailSql += placeholdersDetail.toString() + ")";

            ps = connection.prepareStatement(deleteOrderDetailSql);
            for (int i = 0; i < orderIds.length; i++) {
                if (!orderIds[i].isEmpty()) {
                    ps.setInt(i + 1, Integer.parseInt(orderIds[i]));
                }
            }
            ps.executeUpdate();

            String deleteOrderSql = "DELETE FROM [Order] WHERE order_id IN (";
            StringBuilder placeholdersOrder = new StringBuilder();
            for (int i = 0; i < orderIds.length; i++) {
                placeholdersOrder.append("?");
                if (i < orderIds.length - 1) {
                    placeholdersOrder.append(", ");
                }
            }
            deleteOrderSql += placeholdersOrder.toString() + ")";

            ps = connection.prepareStatement(deleteOrderSql);
            for (int i = 0; i < orderIds.length; i++) {
                ps.setInt(i + 1, Integer.parseInt(orderIds[i]));
            }
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Replace "yourUserId" with an actual user ID for testing

            String[] orderIds = {"32", "33"};


        try {
            OrderDAO orderDAO   = new OrderDAO();
            // Call the method to delete orders with the specified order IDs
            orderDAO.deleteOrder(orderIds);

            System.out.println("Orders successfully deleted.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
