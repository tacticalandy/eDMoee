package _01_register.service;

import _01_register.model.MemberBean;
import _04_ShoppingCart.model.OrderBean;

public interface MemberService {
	
	boolean idExists(String id);
	int saveMember(MemberBean mb);
	void updateUnpaidOrderAmount(OrderBean ob);
	MemberBean queryMember(String id);
}
