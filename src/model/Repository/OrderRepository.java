package model.Repository;

import model.Order;
import model.Product;
import model.User;
import util.singleton.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO Pattern implementation
public class OrderRepository {
    private Connection connection;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    
    public OrderRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
    }
    
    public Order findById(int id) {
        try {
            String query = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                User user = userRepository.findById(userId);
                List<Product> products = getProductsForOrder(id);
                
                return new Order(
                    rs.getInt("id"),
                    user,
                    products,
                    rs.getString("payment_method")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private List<Product> getProductsForOrder(int orderId) {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT product_id FROM order_items WHERE order_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                Product product = productRepository.findById(productId);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Order> findByUser(User user) {
        List<Order> orders = new ArrayList<>();
        try {
            String query = "SELECT * FROM orders WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                List<Product> products = getProductsForOrder(rs.getInt("id"));
                
                orders.add(new Order(
                    rs.getInt("id"),
                    user,
                    products,
                    rs.getString("payment_method")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public boolean save(Order order) {
        try {
            connection.setAutoCommit(false);
            
            // Insert order
            String query = "INSERT INTO orders (user_id, total_amount, order_date, status, payment_method) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, order.getUser().getId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            stmt.setString(4, order.getStatus());
            stmt.setString(5, order.getPaymentMethod());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return false;
            }
            
            // Insert order items
            String itemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement itemStmt = connection.prepareStatement(itemQuery);
            
            for (Product product : order.getProducts()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, product.getId());
                itemStmt.setInt(3, 1); // Assuming quantity 1 for simplicity
                itemStmt.addBatch();
            }
            
            itemStmt.executeBatch();
            connection.commit();
            
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateStatus(int orderId, String status) {
        try {
            String query = "UPDATE orders SET status = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
