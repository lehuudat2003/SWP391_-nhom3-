/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import model.*;

/**
 *
 * @author Admin
 */
public class ProductDAO extends DBContext {

    public static void main(String[] args) {
        AdminDAO udao = new AdminDAO();
        ProductDAO pdao = new ProductDAO();
        ArrayList<Product_img> userList = pdao.getImgById("53");

//        ArrayList<Admin> userList = udao.getAllAdmin();
        // ArrayList<Banner> userList = udao.getAllBanner();
        System.out.println(userList);
        // Print the current time
    }
    public ArrayList<Product> getProductByPage(String cid, String search, int offset, int limit, String sort) {
    String sortby = "";
    switch (sort) {
        case "1":
            sortby = "order by p.create_date desc";
            break;
        case "2":
            sortby = "order by p.price asc";
            break;
        case "3":
            sortby = "order by p.price desc";
            break;
        default:
            sortby = "order by p.product_name desc";
            break;
    }
    ArrayList<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM Product p JOIN Category c ON p.category_id = c.category_id "
            + "WHERE p.category_id LIKE ? AND p.product_name LIKE ? "
            + sortby
            + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + cid + "%");
        ps.setString(2, "%" + search + "%");
        ps.setInt(3, offset);
        ps.setInt(4, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), 
                    new Category(rs.getInt("category_id"), rs.getString("category_name")), 
                    rs.getString(6), rs.getString(7), rs.getDate(8)));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    
   public ArrayList<Product> getProducts(String cid, String search, int index, String sort) {
        String sortby = "";
        switch (sort) {
            case "1":
                sortby = "order by p.create_date desc";
                break;
            case "2":
                sortby = "order by p.price asc";
                break;
            case "3":
                sortby = "order by p.price desc";
                break;
            default:
                sortby = "order by p.product_name desc";
                break;
        }
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product p JOIN Category c ON p.category_id = c.category_id "
                   + "WHERE p.category_id LIKE ? AND p.product_name LIKE ? "
                   + sortby + " OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");
            ps.setInt(3, (index - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),
                        new Category(rs.getInt("category_id"), rs.getString("category_name")),
                        rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNumberProduct(String cid, String search) {
        String sql = "SELECT count(*) FROM Product p JOIN Category c ON p.category_id = c.category_id "
                   + "WHERE p.category_id LIKE ? AND p.product_name LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
  public ArrayList<Product> getProduct(String cid, String search, int index, String sort) {
        String sortby = "";
        switch (sort) {
            case "1":
                sortby = "order by p.create_date desc";
                break;
            case "2":
                sortby = "order by p.price asc";
                break;
            case "3":
                sortby = "order by p.price desc";
                break;
            default:
                sortby = "order by p.product_name desc";
                break;

        }
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product p JOIN Category c ON p.category_id = c.category_id "
            + "WHERE p.category_id LIKE ? AND p.product_name LIKE ? "
            + sortby
            + " OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");
            ps.setInt(3, (index - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }


// news product
    public ArrayList<Product> getProductt(String cid, String search, int index, String sort) {

        String sortby = "order by p.create_date desc";
        ArrayList<Product> list = new ArrayList<>();
        String sql = "  select * from [Product] p, Category c where p.category_id = c.category_id \n"
                + "  and p.category_id like ?  and p.product_name like ? and stock like ? "
                + sortby
                + "  OFFSET ? ROWS FETCH NEXT 6  ROWS ONLY";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + sort + "%");
            ps.setInt(4, (index - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
// tim top
    public ArrayList<Product> getTopSelling() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "select top 6 p.*,c.* from Product p, (select sum(quantity) as numberSell, product_id from OrderDetail group by product_id) as b, Category c\n"
                + " where b.product_id = p.product_id and p.category_id = c.category_id order by b.numberSell desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
// cung tim theo cate va sort ben admin, seller
    public ArrayList<Product> getAllProductt(String cid, String search, String stock) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "  select * from Product p, Category c where p.category_id = c.category_id \n"
                + "  and p.category_id like ?  and p.product_name like ? and stock like ?"
                + " order by p.product_id asc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + stock + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
// cung tim theo cate and sort cua user
    public ArrayList<Product> getAllProduct(String cid, String search) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "  select * from Product p, Category c where p.category_id = c.category_id \n"
                + "  and p.category_id like ?  and p.product_name like ?"
                + " order by p.product_id asc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + cid + "%");
            ps.setString(2, "%" + search + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
//tim theo cate va search
//    public int getNumberProduct(String cid, String search) {
//        ArrayList<Product> list = new ArrayList<>();
//        String sql = "  select count(*) from Product p, Category c where p.category_id = c.category_id \n"
//                + "  and p.category_id like ?  and p.product_name like ?";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, "%" + cid + "%");
//            ps.setString(2, "%" + search + "%");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//        }
//        return 0;
//    }
// tim product 
    public Product getProductById(int pid) {
        String sql = " select * from Product p, Category c where p.category_id = c.category_id \n"
                + "  and p.product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8));
                return p;
            }
        } catch (Exception e) {
        }
        return null;
    }
//lay full category
    public ArrayList<Category> getCategory() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "  select* from Category";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void DeleteProduct(int pid) {
        String sql = "  DELETE FROM [Product] WHERE product_id =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void UpdateProduct(int id, String name, double price, int cateId, int stock, String createDate, String descri, String img) {
        String sql = "  UPDATE [Product]  set [product_name]= ?   ,[price] = ?  ,[stock]= ?,  \n"
                + "  [category_id] = ?   ,[img] = ? ,[description] = ?,[create_date]= ? \n"
                + "  where product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);
            ps.setInt(4, cateId);
            ps.setString(5, img);
            ps.setString(6, descri);
            ps.setString(7, createDate);
            ps.setInt(8, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void AddProduct(String name, double price, int cateId, int stock, String descri, String img) {
        String sql = "insert into [Product] ([product_name],[price],[stock],[category_id],[img],[description],[create_date]) values (?,?,?,?,?,?, getdate())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);
            ps.setInt(4, cateId);
            ps.setString(5, img);
            ps.setString(6, descri);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public ArrayList<Category> getAllCategorys() {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "select * from Category";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void AddCategory(String name) {
        String sql = "  insert into [Category] ([category_name])  values (?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void EditCategory(String name, String id) {
        String sql = " update [Category] set [category_name] = ? where [category_id] = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public ArrayList<Product> Search(String uid, String search, String role) {
        ArrayList<Product> ol = new ArrayList<>();
        String sql = "    select * from [Product] where ([product_name] like '%" + search + "%' or price like '%" + search + "%') ";
        if (!uid.isEmpty()) {
            sql = sql + " and category_id = ?";
        }
        if (!role.isEmpty()) {
            sql = sql + " and stock = ?";
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
                ol.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), new Category(rs.getInt("category_id"), rs.getString("category_name")), rs.getString(6), rs.getString(7), rs.getDate(8)));
            }
        } catch (Exception e) {
        }
        return ol;
    }

    public ArrayList<Feedback> getAllFeedbacks(String id) {
        ArrayList<Feedback> list = new ArrayList<>();
        //String sql = " select * from Feedback where product_id = ?";
        String sql = " select * from feedback a \n"
                + "join Users c on a.user_id = c.user_id where product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                //list.add(new Feedback(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4)));
                list.add(new Feedback(rs.getInt(1), rs.getString(2), rs.getInt(3), new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("img"))));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void AddFeedback(String comment, int user_id, int product_id) {
        String sql = "insert into [feedback] ([feedback_comment],[user_id],[product_id]) values (?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, comment);
            ps.setInt(2, user_id);
            ps.setInt(3, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void DeleteFeedback(int id) {
        String sql = " DELETE FROM [feedback] WHERE feedback_id =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void AddImage(String img, int product_id) {
        String sql = "insert into [Product_img] ([image_link],[product_id]) values (?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, img);

            ps.setInt(2, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public ArrayList<Product_img> getImgById(String id) {
        ArrayList<Product_img> list = new ArrayList<>();
        //String sql = " select * from Feedback where product_id = ?";
        String sql = " select * from Product_img \n"
                + "where product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                list.add(new Product_img(rs.getInt(1), rs.getString(2), rs.getInt(3)));

            }
        } catch (Exception e) {
        }
        return list;
    }

    public Product_img get1ImgById(int pid) {
        String sql = " select * from Product_img where image_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product_img p = new Product_img(rs.getInt(1), rs.getString(2), rs.getInt(3));
                return p;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
        public void DeleteImg(int pid) {
        String sql = "  DELETE FROM [Product_img] WHERE image_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
