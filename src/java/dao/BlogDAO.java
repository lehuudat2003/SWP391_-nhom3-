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
import java.util.List;
import model.Blog;
import model.*;

/**
 * Lớp này thực hiện các thao tác truy cập cơ sở dữ liệu liên quan đến blog.
 * Được thiết kế để tương tác với bảng 'Blog' và 'BlogCategory' trong cơ sở dữ
 * liệu. Extends từ lớp 'DBContext' để sử dụng kết nối đến cơ sở dữ liệu. Cung
 * cấp các phương thức để lấy và cập nhật thông tin về blog và danh mục blog.
 *
 * @author Admin
 */
public class BlogDAO extends DBContext {

    /**
     * Lấy danh sách 6 bài blog mới nhất.
     *
     * @return ArrayList chứa đối tượng Blog của 6 bài blog mới nhất.
     */
    public ArrayList<Blog> get6NewestBlog() {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy 6 bài blog mới nhất
            String sql = "   select top 6 * from [Blog] b , BlogCategory c\n"
                    + "  where b.blog_category = c.id and b.status =1 order by b.create_date desc , b.id desc ";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            // Lặp qua kết quả truy vấn và thêm các đối tượng Blog vào danh sách
            while (rs.next()) {
                Blog u = new Blog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getDate(9), rs.getInt(10), rs.getBoolean(11), rs.getString(12), new BlogCategory(rs.getInt(13), rs.getString(14)));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        BlogDAO dao = new BlogDAO();
        List<Blog> list = dao.get6NewestBlog();
        for (Blog blog : list) {
            System.out.println(blog);
        }
    }

    /**
     * Lấy danh sách các danh mục blog.
     *
     * @return ArrayList chứa đối tượng BlogCategory của các danh mục blog.
     */
    public ArrayList<BlogCategory> getBlogCategory() {
        ArrayList<BlogCategory> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy danh sách các danh mục blog
            String sql = "    select * from BlogCategory ";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            // Lặp qua kết quả truy vấn và thêm các đối tượng BlogCategory vào danh sách
            while (rs.next()) {
                BlogCategory u = new BlogCategory(rs.getInt(1), rs.getString(2));
                list.add(u);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * Lấy danh sách bài blog theo các tham số như danh mục, tên, trang và sắp
     * xếp.
     *
     * @param cateId ID của danh mục blog.
     * @param name Tên của bài blog hoặc một phần của tên.
     * @param index Trang hiện tại.
     * @param sort Chuỗi sắp xếp.
     * @return ArrayList chứa đối tượng Blog theo các điều kiện tìm kiếm.
     */
    public ArrayList<Blog> getBlogs(String cateId, String name, int index, String sort) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy danh sách bài blog theo các tham số
            String sql = "SELECT * FROM [Blog] b, BlogCategory c\n"
                    + "WHERE b.blog_category = c.id AND b.status = 1 "
                    + "AND b.blog_category LIKE ? AND b.blog_title LIKE ? " + sort
                    + " OFFSET ? ROWS FETCH NEXT 9 ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);

            // Setting parameters with placeholders
            stm.setString(1, "%" + cateId + "%");
            stm.setString(2, "%" + name + "%");
            stm.setInt(3, (index - 1) * 9);

            ResultSet rs = stm.executeQuery();

            // Lặp qua kết quả truy vấn và thêm các đối tượng Blog vào danh sách
            while (rs.next()) {
                Blog u = new Blog(
                        rs.getInt(1), // id
                        rs.getString(2), // banner
                        rs.getString(3), // blog_title
                        rs.getString(4), // blog_category
                        rs.getString(5), // introduction
                        rs.getString(6), // img1
                        rs.getString(7), // body
                        rs.getString(8), // conclusion
                        rs.getDate(9), // create_date
                        rs.getInt(10), // viewCount
                        rs.getBoolean(11), // status
                        rs.getString(12), // author
                        new BlogCategory(
                                rs.getInt(13), // BlogCategory id
                                rs.getString(14) // BlogCategory name
                        )
                );
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Đếm số lượng bài blog theo các tham số như danh mục và tên.
     *
     * @param cateId ID của danh mục blog.
     * @param name Tên của bài blog hoặc một phần của tên.
     * @return Số lượng bài blog thỏa mãn điều kiện tìm kiếm.
     */
    public int countBlogs(String cateId, String name) {
        try {
            // Truy vấn SQL để đếm số lượng bài blog theo các tham số
            String sql = "    select count(*) from [Blog] b , BlogCategory c\n"
                    + "  where b.blog_category = c.id and b.status =1  and b.blog_category like   '%" + cateId + "%'"
                    + " and b.blog_title like '%" + name + "%' ";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            // Trả về kết quả đếm
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    /**
     * Lấy thông tin về một danh mục blog dựa trên ID.
     *
     * @param cid ID của danh mục blog.
     * @return Đối tượng BlogCategory chứa thông tin của danh mục blog.
     */
    public BlogCategory getBlogCategoryId(String cid) {
        try {
            // Truy vấn SQL để lấy thông tin về một danh mục blog dựa trên ID
            String sql = "    select * from BlogCategory where id = " + cid;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            // Trả về đối tượng BlogCategory
            while (rs.next()) {
                BlogCategory u = new BlogCategory(rs.getInt(1), rs.getString(2));
                return u;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Lấy danh sách bài blog mới nhất theo danh mục, tên và trang.
     *
     * @param cateId ID của danh mục blog.
     * @param name Tên của bài blog hoặc một phần của tên.
     * @param index Trang hiện tại.
     * @return ArrayList chứa đối tượng Blog theo các điều kiện tìm kiếm.
     */
    public ArrayList<Blog> getNewestBlogByCategory(String cateId, String name, int index) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy danh sách bài blog mới nhất theo danh mục, tên và trang
            String sql = "SELECT * FROM [Blog] b, BlogCategory c\n"
                    + "WHERE b.blog_category = c.id AND b.status = 1 "
                    + "AND b.blog_category LIKE ? AND b.blog_title LIKE ? ORDER BY b.create_date DESC OFFSET ? ROWS FETCH NEXT 80 ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);

            // Setting parameters with placeholders
            stm.setString(1, "%" + cateId + "%");
            stm.setString(2, "%" + name + "%");
            stm.setInt(3, (index - 1) * 8);

            ResultSet rs = stm.executeQuery();

            // Lặp qua kết quả truy vấn và thêm các đối tượng Blog vào danh sách
            while (rs.next()) {
                Blog u = new Blog(
                        rs.getInt(1), // id
                        rs.getString(2), // banner
                        rs.getString(3), // blog_title
                        rs.getString(4), // blog_category
                        rs.getString(5), // introduction
                        rs.getString(6), // img1
                        rs.getString(7), // body
                        rs.getString(8), // conclusion
                        rs.getDate(9), // create_date
                        rs.getInt(10), // viewCount
                        rs.getBoolean(11), // status
                        rs.getString(12), // author
                        new BlogCategory(
                                rs.getInt(13), // BlogCategory id
                                rs.getString(14) // BlogCategory name
                        )
                );
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy danh sách bài blog mới nhất theo danh mục, tên và trang (dành cho
     * quản trị viên).
     *
     * @param cateId ID của danh mục blog.
     * @param name Tên của bài blog hoặc một phần của tên.
     * @param index Trang hiện tại.
     * @return ArrayList chứa đối tượng Blog theo các điều kiện tìm kiếm.
     */
    public ArrayList<Blog> getNewestBlogByCategoryManager(String cateId, String name, int index) {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy danh sách bài blog mới nhất theo danh mục, tên và trang (dành cho quản trị viên)
            String sql = "SELECT * FROM [Blog] b, BlogCategory c\n"
                    + "WHERE b.blog_category = c.id AND b.blog_category LIKE ? AND b.blog_title LIKE ? ORDER BY b.id ASC OFFSET ? ROWS FETCH NEXT 80 ROWS ONLY";
            PreparedStatement stm = connection.prepareStatement(sql);

            // Setting parameters with placeholders
            stm.setString(1, "%" + cateId + "%");
            stm.setString(2, "%" + name + "%");
            stm.setInt(3, (index - 1) * 8);

            ResultSet rs = stm.executeQuery();

            // Lặp qua kết quả truy vấn và thêm các đối tượng Blog vào danh sách
            while (rs.next()) {
                Blog u = new Blog(
                        rs.getInt(1), // id
                        rs.getString(2), // banner
                        rs.getString(3), // blog_title
                        rs.getString(4), // blog_category
                        rs.getString(5), // introduction
                        rs.getString(6), // img1
                        rs.getString(7), // body
                        rs.getString(8), // conclusion
                        rs.getDate(9), // create_date
                        rs.getInt(10), // viewCount
                        rs.getBoolean(11), // status
                        rs.getString(12), // author
                        new BlogCategory(
                                rs.getInt(13), // BlogCategory id
                                rs.getString(14) // BlogCategory name
                        )
                );
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy danh sách tất cả bài blog.
     *
     * @return ArrayList chứa đối tượng Blog của tất cả bài blog.
     */
    public ArrayList<Blog> getAllBlog() {
        ArrayList<Blog> list = new ArrayList<>();
        try {
            // Truy vấn SQL để lấy danh sách tất cả bài blog
            String sql = "SELECT * FROM [Blog] b, BlogCategory c\n"
                    + "WHERE b.blog_category = c.id";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            // Lặp qua kết quả truy vấn và thêm các đối tượng Blog vào danh sách
            while (rs.next()) {
                Blog u = new Blog(
                        rs.getInt(1), // id
                        rs.getString(2), // banner
                        rs.getString(3), // blog_title
                        rs.getString(4), // blog_category
                        rs.getString(5), // introduction
                        rs.getString(6), // img1
                        rs.getString(7), // body
                        rs.getString(8), // conclusion
                        rs.getDate(9), // create_date
                        rs.getInt(10), // viewCount
                        rs.getBoolean(11), // status
                        rs.getString(12), // author
                        new BlogCategory(
                                rs.getInt(13), // BlogCategory id
                                rs.getString(14) // BlogCategory name
                        )
                );
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy thông tin chi tiết về một bài blog dựa trên ID.
     *
     * @param id ID của bài blog.
     * @return Đối tượng Blog chứa thông tin chi tiết của bài blog.
     */
    public Blog getBlogById(String id) {
        try {
            // Truy vấn SQL để lấy thông tin chi tiết về một bài blog dựa trên ID
            String sql = "SELECT * FROM [Blog] b, BlogCategory c\n"
                    + "WHERE b.blog_category = c.id AND b.id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            // Trả về đối tượng Blog
            while (rs.next()) {
                Blog u = new Blog(
                        rs.getInt(1), // id
                        rs.getString(2), // banner
                        rs.getString(3), // blog_title
                        rs.getString(4), // blog_category
                        rs.getString(5), // introduction
                        rs.getString(6), // img1
                        rs.getString(7), // body
                        rs.getString(8), // conclusion
                        rs.getDate(9), // create_date
                        rs.getInt(10), // viewCount
                        rs.getBoolean(11), // status
                        rs.getString(12), // author
                        new BlogCategory(
                                rs.getInt(13), // BlogCategory id
                                rs.getString(14) // BlogCategory name
                        )
                );
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertNewBlog(String banner, String title, String cateId, String introduction, String body, String conclusion, String author, String img) {
        try {
            String sql = "INSERT INTO Blog ([banner], [blog_title], [blog_category], [introduction], [body], [conclusion], [author], [img1], [create_date], [viewCount], [status])\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), 0, 1)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, banner);
            stm.setString(2, title);
            stm.setString(3, cateId);
            stm.setString(4, introduction);
            stm.setString(5, body);
            stm.setString(6, conclusion);
            stm.setString(7, author);
            stm.setString(8, img);

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteById(String blogId) {
        try {
            String sql = "UPDATE Blog SET status = 0 WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blogId);

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Blog with ID " + blogId + " status updated to 0.");
            } else {
                System.out.println("No blog found with ID " + blogId + ". No status update performed.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateViewCount(String bid) {
        try {
            String sql = "   update Blog set  [viewCount] = viewCount + 1 where id = " + bid;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateBlog(String banner, String title, String cateId, String introduction,
            String body, String conclusion, String author, String img, String status, String bid) {
        try {
            String sql = "UPDATE Blog SET [banner] = ?, [blog_title] = ?, [blog_category] = ?, "
                    + "[introduction] = ?, [body] = ?, [conclusion] = ?, [author] = ?, [img1] = ?, [status] = ? WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, banner);
            stm.setString(2, title);
            stm.setString(3, cateId);
            stm.setString(4, introduction);
            stm.setString(5, body);
            stm.setString(6, conclusion);
            stm.setString(7, author);
            stm.setString(8, img);
            stm.setString(9, status);
            stm.setString(10, bid);

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCountViewBlog(String bid) {
        try {
            String sql = "select [viewCount] from Blog where id = " + bid;
            PreparedStatement stm = connection.prepareStatement(sql);
            int curView = 0;
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                curView = rs.getInt(1);
            }
            String sql2 = " update Blog set [viewCount] =? where id = " + bid;
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, curView + 1);
            stm2.executeUpdate();

        } catch (SQLException e) {
        }
    }
}
