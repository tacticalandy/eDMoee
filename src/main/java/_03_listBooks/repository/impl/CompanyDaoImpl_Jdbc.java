package _03_listBooks.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _03_listBooks.model.CompanyBean;
import _03_listBooks.repository.CompanyDao;

// 本類別負責讀取資料庫內BookCompany表格內的紀錄
// 
public class CompanyDaoImpl_Jdbc implements Serializable, CompanyDao {
	private static final long serialVersionUID = 1L;
	private String tagName = "";
	private int selected = -1;
	private int id = 0;
	DataSource ds = null;

	public CompanyDaoImpl_Jdbc() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("CompanyDaoImpl_Jdbc()#建構子發生例外: " 
					  + ex.getMessage());
		}
	}

	@Override
	public CompanyBean getCompanyById() {
		CompanyBean cb = null;

		String sql = "SELECT * FROM BookCompany WHERE id= ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					cb = new CompanyBean(rs.getInt(1), rs.getString(2), 
							rs.getString(3), rs.getString(4));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("CompanyDaoImpl_Jdbc()#getCompanyById()發生例外: " 
					+ ex.getMessage());
		}
		return cb;
	}

	@Override
	public List<CompanyBean> getCompany() {
		List<CompanyBean> list = new ArrayList<>();
		String sql = "SELECT * FROM BookCompany";
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				CompanyBean cb = new CompanyBean(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4));
				list.add(cb);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("CompanyDaoImpl_Jdbc()#getCompany()發生例外: " 
					+ ex.getMessage());
		}

		return list;
	}

	@Override
	public int getSelected() {
		return selected;
	}

	@Override
	public void setSelected(int selected) {
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
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getSelectTag() {
		String ans = "";
		List<CompanyBean> cb = getCompany();
		ans += "<SELECT name='" + getTagName() + "'>";
		for (CompanyBean bean : cb) {
			int id = bean.getId();
			String name = bean.getName().substring(0, 4);
			if (id == selected) {
				ans += "<option value='" + id + "' selected>" + name + "</option>";
			} else {
				ans += "<option value='" + id + "'>" + name + "</option>";
			}
		}
		ans += "</SELECT>";
		return ans;
	}

}