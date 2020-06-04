package _03_listBooks.service.impl;

import java.io.Serializable;
import java.util.List;

import _03_listBooks.model.CompanyBean;
import _03_listBooks.repository.CompanyDao;
import _03_listBooks.repository.impl.CompanyDaoImpl_Jdbc;
import _03_listBooks.service.CompanyService;

// 本類別負責讀取資料庫內eBookCompany表格內的紀錄
// 
public class CompanyServiceImpl implements Serializable, CompanyService {
	
	private static final long serialVersionUID = 1L;
	CompanyDao dao;

	// Constructor
	public CompanyServiceImpl() {
		dao = new CompanyDaoImpl_Jdbc();
	}
	// --------------------------------------------
	public List<CompanyBean> getCompany() {
		return dao.getCompany();
	}

	public CompanyBean getCompanyById() {
		return dao.getCompanyById();
	}
	
	public String getSelectTag() {
		return dao.getSelectTag();
	}
	
	// getter, setter
	public int getSelected() {
		return dao.getSelected();
	}

	public void setSelected(int selected) {
		dao.setSelected(selected);
	}

	public String getTagName() {
		return dao.getTagName();
	}

	public void setTagName(String tagName) {
		dao.setTagName(tagName);
	}

	public int getId() {
		return dao.getId();
	}

	public void setId(int id) {
		dao.setId(id);
	}

}