package _03_listBooks.service;

import java.util.List;

import _03_listBooks.model.BookBean;

public interface BookService {
	int getTotalPages();

	List<BookBean> getPageBooks();

	int getPageNo();

	void setPageNo(int pageNo);

	int getRecordsPerPage();

	void setRecordsPerPage(int recordsPerPage);

	// 計算紀錄總筆數
	long getRecordCounts();

	BookBean getBook(int bookId);

	int updateBook(BookBean bean, long sizeInBytes) ;

	// 修改一筆記錄
	int updateBook(BookBean bean) ;

	// 依bookID來查詢單筆記錄
	BookBean queryBook(int bookID);

	// 依bookID來刪除單筆記錄
	int deleteBook(int no);

	// 新增一筆記錄
	int saveBook(BookBean bean);
	
	// 取出所有的類型
	List<String> getCategory();
}
