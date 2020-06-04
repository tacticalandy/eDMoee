package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _03_listBooks.model.BookBean;
import _03_listBooks.repository.impl.BookDaoImpl_Jdbc;
import _03_listBooks.service.CompanyService;
import _03_listBooks.service.impl.CompanyServiceImpl;


@WebServlet("/_20_productMaintain/BookPreUpdate.do")
public class BookPreUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
		     response.sendRedirect(request.getContextPath() + "/index.jsp");
		     return;
		}
		int bookId = 0;
		String strBookId = request.getParameter("BOOKID");
		
		if (strBookId != null) {
			bookId = Integer.parseInt(strBookId);
		}
		
		BookDaoImpl_Jdbc bookDao = new BookDaoImpl_Jdbc();
		BookBean bean = bookDao.queryBook(bookId);
		
		session.setAttribute("bean", bean);

		bookDao.setSelected(bean.getCategory());
		String categoryTag = bookDao.getCategoryTag();
		request.setAttribute("SelectCategoryTag", categoryTag);
		//System.out.println("categoryTag=" + categoryTag);
		
		CompanyService cs = new CompanyServiceImpl();
		cs.setSelected(bean.getCompanyId());
		cs.setTagName("companyID");
		String companyTag = cs.getSelectTag();
		request.setAttribute("SelectCompanyTag", companyTag);
		//System.out.println("companyTag=" + companyTag);

		RequestDispatcher rd = request.getRequestDispatcher("/_20_productMaintain/BookUpdate.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
