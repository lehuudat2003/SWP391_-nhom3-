/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Cart {

    private int id;
    private int userId;
    private float totalPrice;
    private List<CartItem> items;
    private int quantity;

    public Cart() {
    }

    public Cart(int id, int userId, float totalPrice) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {

        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Phương thức này trả về một đối tượng CartItem dựa trên ID sản phẩm
    private CartItem getItemById(int id) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == id) {
                return item;
            }
        }
        // Trả về null nếu không tìm thấy mục với ID tương ứng
        return null;
    }

// Phương thức này kiểm tra xem một mục với ID sản phẩm đã tồn tại trong giỏ hàng chưa
    private boolean checkExist(int id) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == id) {
                // Trả về true nếu mục đã tồn tại
                return true;
            }
        }
        // Trả về false nếu mục chưa tồn tại
        return false;
    }

    public void addItem(CartItem newItem) {
        // Kiểm tra xem một mục với ID sản phẩm tương tự đã tồn tại trong giỏ hàng chưa
        if (checkExist(newItem.getProduct().getId())) {
            // Nếu tồn tại, cập nhật số lượng của mục đã tồn tại
            CartItem olditem = getItemById(newItem.getProduct().getId());
            olditem.setQuantity(olditem.getQuantity() + newItem.getQuantity());
        } else {
            // Nếu không tồn tại, thêm mục mới vào giỏ hàng
            items.add(newItem);
        }
    }

    public void removeItem(int id) {
        // Kiểm tra xem mục với ID sản phẩm tương ứng có tồn tại trong giỏ hàng không
        if (getItemById(id) != null) {
            // Nếu tồn tại, loại bỏ mục đó khỏi giỏ hàng
            items.remove(getItemById(id));
        }
    }

    public double getTotalMoney() {
        // Tính tổng số tiền của tất cả các mục trong giỏ hàng
        double total = 0;
        for (CartItem item : items) {
            total += (item.getQuantity() * item.getProduct().getPrice());
        }
        return total;
    }

// Phương thức này sẽ tính lại tổng giá trị của giỏ hàng
    private void recalculateTotalPrice() {
        float total = 0;
        for (CartItem item : items) {
            total += (item.getQuantity() * item.getProduct().getPrice());
        }
        setTotalPrice(total);
    }
    
    public void updateQuantity(int productId, int newQuantity) {
        CartItem itemToUpdate = getItemById(productId);
        if (itemToUpdate != null) {
            itemToUpdate.setQuantity(newQuantity);
            recalculateTotalPrice();
        } else {
            System.out.println("Product with ID " + productId + " not found in the cart.");
        }
    }

}
