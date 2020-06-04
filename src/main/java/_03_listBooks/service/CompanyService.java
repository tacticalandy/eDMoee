package _03_listBooks.service;

import java.util.List;

import _03_listBooks.model.CompanyBean;

public interface CompanyService {

	List<CompanyBean> getCompany() ;

	CompanyBean getCompanyById() ;

	String getSelectTag();

	//-----------------------------------------
	int getSelected();

	void setSelected(int selected);

	int getId();
	
	void setId(int id);

	String getTagName();

	void setTagName(String tagName);
//	

	
}