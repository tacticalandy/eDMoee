package _04_ShoppingCart.model.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.dao.OrderItemDao;
import _04_ShoppingCart.model.ude.ProductStockException;
/*
 * 一張合格的訂單必須經過下列檢查
 * 	1.	檢查訂購之商品的數量是否足夠。
 *      此功能寫在本類別的updateProductStock()方法內，參考該方法的說明。
 */
public class OrderItemDaoImpl implements OrderItemDao {
	Connection conn;

	public OrderItemDaoImpl() {
	}
	/*
	 * 計算客戶欲購買之某項商品(以OrderItemBean物件oib來表示)的小計金額(subtotal)， 
	 * 計算公式為: 商品的數量 * 商品的單價  * 商品的折扣
	 */
	@Override
	public double findItemAmount(OrderItemBean oib) {
		double subtotal = oib.getQuantity() * oib.getUnitPrice() * oib.getDiscount();
		return subtotal;
	}
	

	@Override
	public int updateProductStock(OrderItemBean oib) {
		int n = 0;
		int stock = 0;
		String sql0 = "SELECT stock FROM Book WHERE bookId = ?";
		String sql1 = "UPDATE Book SET stock = stock - ? WHERE bookId = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql0);) {
			ps.setInt(1, oib.getBookId());
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					stock = rs.getInt(1);
					if (stock < oib.getQuantity()) {
						throw new ProductStockException("庫存數量不足: BookId: " 
								+ oib.getBookId() + ", 在庫量: " + stock+ ", 訂購量: " + oib.getQuantity());
					} else {
						;
					}
					try (PreparedStatement ps1 = conn.prepareStatement(sql1);) {
						ps1.setInt(1, oib.getQuantity());
						ps1.setInt(2, oib.getBookId());
						n = ps1.executeUpdate();
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("OrderItemDaoImpl類別#updateProductStock()發生SQL例外: " + ex.getMessage());
		}
		return n;
	}
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
		
	}
	
}
