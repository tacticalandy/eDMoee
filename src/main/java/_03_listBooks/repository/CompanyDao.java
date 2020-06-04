package _03_listBooks.repository;

import java.util.List;

import _03_listBooks.model.CompanyBean;

public interface CompanyDao {

	List<CompanyBean> getCompany() ;

	CompanyBean getCompanyById() ;

	int getSelected();

	void setSelected(int selected);

	int getId();
	
	void setId(int id);

	String getTagName();

	void setTagName(String tagName);

	String getSelectTag();
	
}