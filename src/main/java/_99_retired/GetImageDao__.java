package _99_retired;

import _01_register.model.MemberBean;
import _03_listBooks.model.BookBean;

public interface GetImageDao__ {
	public BookBean getBookImage(int bookId);

	public MemberBean getMemberImage(String memberId);
}
