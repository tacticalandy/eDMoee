package _03_listBooks.service.impl;

import java.util.List;

import _03_listBooks.model.BookBean;
import _03_listBooks.repository.BookDao;
import _03_listBooks.repository.impl.BookDaoImpl_Jdbc;
import _03_listBooks.service.BookService;

public class BookServiceImpl implements BookService {
	
    BookDao dao;
    
	public BookServiceImpl() {
		this.dao = new BookDaoImpl_Jdbc();
	}

	@Override
	public int getTotalPages() {
		return dao.getTotalPages();
	}

	@Override
	public List<BookBean> getPageBooks() {
		return dao.getPageBooks();
	}

	@Override
	public int getPageNo() {
		return dao.getPageNo();
	}

	@Override
	public void setPageNo(int pageNo) {
		dao.setPageNo(pageNo);
	}

	@Override
	public int getRecordsPerPage() {
		return dao.getRecordsPerPage();
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		dao.setRecordsPerPage(recordsPerPage);
	}

	@Override
	public long getRecordCounts() {
		return dao.getRecordCounts();
	}

	@Override
	public BookBean getBook(int bookId) {
		dao.setBookId(bookId);
		return dao.getBook();
	}

	@Override
	public int updateBook(BookBean bean, long sizeInBytes) {
		return dao.updateBook(bean, sizeInBytes);
	}

	@Override
	public int updateBook(BookBean bean) {
		return dao.updateBook(bean);
	}

	@Override
	public BookBean queryBook(int bookId) {
		return dao.queryBook(bookId);
	}

	@Override
	public int deleteBook(int no) {
		return dao.deleteBook(no);
	}

	@Override
	public int saveBook(BookBean bean) {
		return dao.saveBook(bean);
	}

	@Override
	public List<String> getCategory() {
		return dao.getCategory();
	}
}
