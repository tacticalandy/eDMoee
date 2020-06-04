package _02_login.service.impl;
import _01_register.model.MemberBean;
import _01_register.repository.MemberDao;
import _01_register.repository.impl.MemberDaoImpl_Jdbc;
import _02_login.service.LoginService;

// 登入時系統必須執行的checkIDPassword()功能交由 MemberDao來完成 
public class LoginServiceImpl implements LoginService {
	MemberDao  dao ;
	public LoginServiceImpl() {
		this.dao = new MemberDaoImpl_Jdbc();
	}
	// 檢查帳號與密碼是否與某位已登入會員的帳號密碼完全相同。
//	public MemberBean processLogin(String userId, String password) {
//		MemberBean mb = checkIDPassword(userId, password);
//		return mb;
//	}
	
	public MemberBean checkIDPassword(String userId, String password) {
		MemberBean mb = dao.checkIDPassword(userId, password);
		return mb;
	}
}
