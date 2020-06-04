package _03_listBooks.repository.impl;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _03_listBooks.model.BookBean;
import _03_listBooks.repository.BookDao;

// 本類別使用純JDBC的技術來存取資料庫。
// 所有SQLException都以catch區塊捕捉，然後一律再次丟出RuntimeException。
// 對SQLException而言，即使catch下來，程式依然無法正常執行，所以捕捉SQLException，再次丟出
// RuntimeException。
public class BookDaoImpl_Jdbc implements Serializable, BookDao {

	private static final long serialVersionUID = 1L;
	private int bookId = 0; 	// 查詢單筆商品會用到此代號
	private int pageNo = 0;		// 存放目前顯示之頁面的編號
	private int recordsPerPage = GlobalService.RECORDS_PER_PAGE; // 預設值：每頁三筆
	private int totalPages = -1;
	DataSource ds = null;
	
	private String tagName = "";
	String selected = "";

	public BookDaoImpl_Jdbc() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("BookDaoImpl_Jdbc()#建構子發生例外: " 
										+ ex.getMessage());
		}
	}
	
	// 計算販售的商品總共有幾頁
	@Override
	public int getTotalPages() {
		// 注意下一列敘述的每一個型態轉換
		totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));

		return totalPages;
	}
	
	// 查詢某一頁的商品(書籍)資料，執行本方法前，一定要先設定實例變數pageNo的初值
	@Override
	public List<BookBean> getPageBooks() {
		List<BookBean> list = new ArrayList<BookBean>();
		
		String sql0 = "SELECT  * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY BOOKID)"
				+ " AS RowNum, b.BookId, b.author, b.bookNo, b.category, b.TITLE, b.PRICE, "
				+ " b.discount, b.companyID, b.fileName, b.coverImage, bc.name "
				+ " FROM Book b JOIN BookCompany bc ON  b.companyID = bc.id )"
				+ " AS NewTable WHERE RowNum >= ? AND RowNum <= ?";
		
//		String sql1 = "SELECT b.BookId, b.author, b.bookNo, b.category, b.TITLE, "
//				+ "b.PRICE, b.discount, b.companyID, b.fileName, b.coverImage, "
//				+ "bc.name FROM Book b JOIN BookCompany bc ON  b.companyID = bc.id "
//						+ " LIMIT ?, ?";
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
//		int startRecordNo = (pageNo - 1) * recordsPerPage;
//		int endRecordNo = recordsPerPage;
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					// 準備一個新的BookBean，將ResultSet內的一筆紀錄移植到BookBean內
					BookBean bean = new BookBean();    	
					bean.setBookId(rs.getInt("BookId"));		
					bean.setAuthor(rs.getString("author"));
					bean.setBookNo(rs.getString("bookNo"));
					bean.setCategory(rs.getString("category"));
					bean.setTitle(rs.getString("title"));
					bean.setPrice(rs.getDouble("price"));
					bean.setDiscount(rs.getDouble("discount"));
					bean.setCompanyId(rs.getInt("companyID"));
					bean.setFileName(rs.getString("Filename"));
					bean.setCoverImage(rs.getBlob("CoverImage"));
					bean.setCompanyName(rs.getString("name"));
					// 最後將BookBean物件放入大的容器內
					list.add(bean);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("BookDaoImpl_Jdbc()#getPageBooks()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}

	@Override
	public long getRecordCounts() {
		long count = 0; // 必須使用 long 型態
		String sql = "SELECT count(*) FROM Book";
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#getRecordCounts()發生例外: " 
										+ ex.getMessage());
		}
		return count;
	}
	
	@Override
	public List<String> getCategory() {
		String sql = "SELECT DISTINCT Category FROM Book";
		List<String> list = new ArrayList<>();
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				String cate = rs.getString(1);
				if (cate != null) {
					list.add(cate);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#getCategory()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}
	
	@Override
	public String getCategoryTag() {
		String ans = "";
		List<String> list = getCategory();
		ans += "<SELECT name='category'>";
		for (String cate : list) {
			if (cate.equals(selected)) {
				ans += "<option value='" + cate + "' selected>" + cate + "</option>";
			} else {
				ans += "<option value='" + cate + "'>" + cate + "</option>";
			}
		}
		ans += "</SELECT>";
		return ans;
	}
	
	// 修改一筆書籍資料
	@Override
	public int updateBook(BookBean bean, long sizeInBytes) {
		int n = 0;
		String sql = "UPDATE Book SET " 
				+ " title=?,  author=?,  price=?, discount = ?, coverImage = ?, "
				+ " fileName=?, bookNo=?, stock=?, companyID=? , category = ? WHERE bookID = ?";
		if (sizeInBytes == -1) { // 不修改圖片
			n = updateBook(bean);
			return n;
		}
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1, bean.getTitle());
			ps.setString(2, bean.getAuthor());
			ps.setDouble(3, bean.getPrice());
			ps.setDouble(4, bean.getDiscount());
			ps.setBlob(5, bean.getCoverImage());
			ps.setString(6, bean.getFileName());
			ps.setString(7, bean.getBookNo());
			ps.setInt(8, bean.getStock());
			ps.setInt(9, bean.getCompanyId());
			ps.setString(10, bean.getCategory());
			ps.setInt(11, bean.getBookId());
			n = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#updateBook(BookBean, long)發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	// 修改一筆書籍資料，不改圖片
	@Override
	public int updateBook(BookBean bean) {
		int n = 0;
		String sql = "UPDATE Book SET " 
				+ " title=?,  author=?,  price=?, discount=?,  bookNo=?, "
				+ " stock=?,  companyID=?,  category=?  WHERE bookId = ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.clearParameters();
			ps.setString(1, bean.getTitle());
			ps.setString(2, bean.getAuthor());
			ps.setDouble(3, bean.getPrice());
			ps.setDouble(4, bean.getDiscount());
			ps.setString(5, bean.getBookNo());
			ps.setInt(6, bean.getStock());
			ps.setInt(7, bean.getCompanyId());
			ps.setString(8, bean.getCategory());
			ps.setInt(9, bean.getBookId());

			n = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#updateBook(BookBean)發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	// 依bookID來查詢單筆記錄
	@Override
	public BookBean queryBook(int bookId)  {
		BookBean bean = null;
		String sql = "SELECT * FROM Book WHERE bookId = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, bookId);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					bean = new BookBean();
					bean.setBookId(rs.getInt("bookId"));
					bean.setTitle(rs.getString(2));
					bean.setAuthor(rs.getString(3));
					bean.setPrice(rs.getDouble(4));
					bean.setDiscount(rs.getDouble(5));
					bean.setCoverImage(rs.getBlob(6));
					bean.setFileName(rs.getString(7));
					bean.setBookNo(rs.getString(8));
					bean.setStock(rs.getInt(9));
					bean.setCategory(rs.getString("category"));
					bean.setCompanyId(rs.getInt(11));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#queryBook()發生例外: " 
										+ ex.getMessage());
		}
		return bean;
	}

	// 依bookID來刪除單筆記錄
	@Override
	public int deleteBook(int no) {
		int n = 0;
		String sql = "DELETE FROM Book WHERE bookId = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setInt(1, no);
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#deleteBook()發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	// 新增一筆記錄---
	@Override
	public int saveBook(BookBean bean) {
		int n = 0;

		String sql = "INSERT INTO Book " 
				+ " (title,  author,  price, discount, "
				+ " companyId, fileName, bookNo, coverImage, stock, category) " 
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (
			Connection connection = ds.getConnection();
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setString(1, bean.getTitle());
			pStmt.setString(2, bean.getAuthor());
			pStmt.setDouble(3, bean.getPrice());
			pStmt.setDouble(4, bean.getDiscount());
			pStmt.setInt(5, bean.getCompanyId());
			pStmt.setString(6, bean.getFileName());
			pStmt.setString(7, bean.getBookNo());
			pStmt.setBlob(8, bean.getCoverImage());
			pStmt.setInt(9, bean.getStock());
			pStmt.setString(10, bean.getCategory());
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#saveBook()發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	
	
	@Override
	public String getSelected() {
		return selected;
	}
	
	@Override
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
	@Override
	public String getTagName() {
		return tagName;
	}
	
	@Override
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Override
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public BookBean getBook() {
		BookBean bean = queryBook(this.bookId);
		return bean;
	}
	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
}