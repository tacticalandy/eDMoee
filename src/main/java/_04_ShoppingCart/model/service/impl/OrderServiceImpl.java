package _04_ShoppingCart.model.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _01_register.repository.MemberDao;
import _01_register.repository.impl.MemberDaoImpl_Jdbc;
import _04_ShoppingCart.model.OrderBean;
import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.dao.OrderDao;
import _04_ShoppingCart.model.dao.OrderItemDao;
import _04_ShoppingCart.model.dao.impl.OrderDaoImpl;
import _04_ShoppingCart.model.dao.impl.OrderItemDaoImpl;
import _04_ShoppingCart.model.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private DataSource ds;
	private OrderItemDao oidao;
	private OrderDao odao;
	private MemberDao mdao;

	public OrderServiceImpl() {
		try {
			Context ctx = new InitialContext();
			ds 	  = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
			oidao = new OrderItemDaoImpl();
			odao  = new OrderDaoImpl();
			mdao  = new MemberDaoImpl_Jdbc();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	// 這是一個交易
	public void persistOrder(OrderBean ob) {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		try {
			// 交易開始
			con.setAutoCommit(false);
			// 檢查未付款餘額是否超過限額，並更新未付款餘額
			mdao.setConnection(con);
			mdao.updateUnpaidOrderAmount(ob);
			
			// 檢查所有訂單明細所訂購之商品的庫存數量是否足夠
			checkStock(ob, con);
			
			// 儲存訂單
			odao.setConnection(con);
			odao.insertOrder(ob);
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
				System.out.println("發生異常，交易回滾.....,原因: " + e.getMessage());
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

	}

	public void checkStock(OrderBean ob, Connection con) {
		Set<OrderItemBean> items = ob.getItems();
		oidao.setConnection(con);
		for (OrderItemBean oib : items) {
			oidao.updateProductStock(oib);
		}
	}



	public OrderDao getOdao() {
		return odao;
	}

	public void setOdao(OrderDao odao) {
		this.odao = odao;
	}

	@Override
	public OrderBean getOrder(int orderNo) {
		return odao.getOrder(orderNo);
	}

	@Override
	public List<OrderBean> getAllOrders() {
		return odao.getAllOrders();
	}

	@Override
	public List<OrderBean> getMemberOrders(String memberId) {
		return odao.getMemberOrders(memberId);
	}

}
