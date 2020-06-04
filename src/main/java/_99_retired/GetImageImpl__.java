package _99_retired;

import _01_register.model.MemberBean;
import _01_register.repository.MemberDao;
import _01_register.repository.impl.MemberDaoImpl_Jdbc;
import _03_listBooks.model.BookBean;
import _03_listBooks.repository.BookDao;
import _03_listBooks.repository.impl.BookDaoImpl_Jdbc;
public class GetImageImpl__ implements GetImageDao__ {
	
	public GetImageImpl__() {
	}	
	
	@Override
	public BookBean getBookImage(int bookId) {
		BookBean bb = null;
		try {
			BookDao dao = new BookDaoImpl_Jdbc();
			bb = dao.queryBook(bookId);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bb;
	}
	@Override
	public MemberBean getMemberImage(String memberId) {
		MemberBean mb = null;
		try {
			MemberDao dao = new MemberDaoImpl_Jdbc();
			mb = dao.queryMember(memberId);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mb;
	}

}
