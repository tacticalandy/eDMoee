package _03_listBooks.model;

import java.io.Serializable;
// 本類別封裝單筆出版社資料
public class CompanyBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id ;
	private String  name;
	private String  address;
	private String  url;
	
	public CompanyBean(Integer id, String name, String address, String url) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.url = url;
	}
	public CompanyBean() {
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	
	
}