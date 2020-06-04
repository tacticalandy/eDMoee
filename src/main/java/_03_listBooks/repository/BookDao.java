package _03_listBooks.repository;

import java.util.List;

import _03_listBooks.model.BookBean;

public interface BookDao {

	int getTotalPages();

	List<BookBean> getPageBooks();

	int getPageNo();

	void setPageNo(int pageNo);

	int getRecordsPerPage();

	void setRecordsPerPage(int recordsPerPage);

	// 計算紀錄總筆數
	long getRecordCounts();

	void setBookId(int bookId);

	BookBean getBook();

	int updateBook(BookBean bean, long sizeInBytes) ;

	// 修改一筆記錄
	int updateBook(BookBean bean) ;

	// 依bookID來查詢單筆記錄
	BookBean queryBook(int bookID);

	// 依bookID來刪除單筆記錄
	int deleteBook(int no);

	// 新增一筆記錄
	int saveBook(BookBean bean);

	List<String> getCategory();

	void setSelected(String selected);

	String getSelected();

	String getCategoryTag();

	String getTagName();

	void setTagName(String tagName);

}