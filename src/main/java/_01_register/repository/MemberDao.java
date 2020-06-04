package _01_register.repository;

import java.sql.Connection;

import _01_register.model.MemberBean;
import _04_ShoppingCart.model.OrderBean;

public interface MemberDao {
	
	public boolean idExists(String id);

	public int saveMember(MemberBean mb) ;
	
	public MemberBean queryMember(String id);
	
	public MemberBean checkIDPassword(String userId, String password);	
	
	void updateUnpaidOrderAmount(OrderBean ob);

	public void setConnection(Connection con);
}