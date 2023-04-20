package sample03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtils;

public class ProductDao {

	public void insertProduct(Product product) throws SQLException {
		String sql = "insert into sample_products "
				+ "(product_no, product_name, product_maker, product_price, product_discount_rate, product_stock) "
				+ "values "
				+ "(sample_product_seq.nextval, ?, ?, ?, ?, ?)";
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, product.getName());
		pstmt.setString(2, product.getMaker());
		pstmt.setInt(3, product.getPrice());
		pstmt.setDouble(4, product.getDiscountRate());
		pstmt.setInt(5, product.getStock());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
	}
	
	public List<Product> getAllProducts() throws SQLException {
		String sql = "select product_no,"
				+ " product_name,"
				+ " product_maker,"
				+ " product_price,"
				+ " product_discount_rate,"
				+ " product_stock,"
				+ " product_create_date "
				+ "from sample_products "
				+ "order by product_no desc ";
		
		List<Product> products = new ArrayList<>();
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Product product = new Product(rs.getInt("product_no"),
					rs.getString("product_name"),
					rs.getString("product_maker"),
					rs.getInt("product_price"),
					rs.getDouble("product_discount_rate"),
					rs.getInt("product_stock"),
					rs.getDate("product_create_date"));
			products.add(product);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return products;
	}
	
	public List<Object[]> getProductsByPrice(int minPrice, int maxPrice) throws SQLException {
		String sql = "select product_no, product_name, product_maker, product_price, product_discount_rate, product_stock, product_create_date, product_price - (product_price * product_discount_rate) AS discounted_price "
				+ "from sample_products "
				+ "where product_price between ? and ? "
				+ "order by discounted_price, product_discount_rate desc, product_no desc";
		
		List<Object[]> products = new ArrayList<>();
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, minPrice);
		pstmt.setInt(2, maxPrice);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Product product = new Product(rs.getInt("product_no"),
					rs.getString("product_name"),
					rs.getString("product_maker"),
					rs.getInt("product_price"),
					rs.getDouble("product_discount_rate"),
					rs.getInt("product_stock"),
					rs.getDate("product_create_date"));
			
			products.add(new Object[] {product, rs.getInt("discounted_price")});
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return products;
	}
	
	public Product getProductByNo(int no) throws SQLException {
		String sql = "select product_no,"
				+ " product_name,"
				+ " product_maker,"
				+ " product_price,"
				+ " product_discount_rate,"
				+ " product_stock,"
				+ " product_create_date "
				+ "from sample_products "
				+ "where product_no = ?";
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, no);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return new Product(rs.getInt("product_no"),
					rs.getString("product_name"),
					rs.getString("product_maker"),
					rs.getInt("product_price"),
					rs.getDouble("product_discount_rate"),
					rs.getInt("product_stock"),
					rs.getDate("product_create_date"));
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return null;
	}
	
//	public void updateProduct(Product product) {
//		
//	}
	
	public boolean deleteProduct(int no) throws SQLException {
		String sql = "delete from sample_products "
				+ "where product_no = ?";
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, no);
		
		boolean isDeleted = pstmt.executeUpdate() > 0 ? true : false;
		
		pstmt.close();
		con.close();
		
		return isDeleted;
	}
}
