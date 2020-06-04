package _01_register.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;

public class MemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer pKey;
	String memberId;
	String name;
	String password;
	String address;
	String email;
	String tel;
	String userType;
	Timestamp registerTime;
	Double totalAmt;
	Blob memberImage;
	Clob comment;
	String fileName;
	Double unpaid_amount;

	public MemberBean(Integer pKey, String memberId, String name, String password, String address, String email,
			String tel, String userType, Timestamp registerTime, Double totalAmt, Double unpaid_amount,
			Blob memberImage, String fileName) {
		this.pKey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.userType = userType;
		this.registerTime = registerTime;
		this.totalAmt = totalAmt;
		this.unpaid_amount = unpaid_amount;
		this.fileName = fileName;
		this.memberImage = memberImage;
	}


	public MemberBean() {
	}

	public Integer getPKey() {
		return pKey;
	}
	
	public void setPKey(int pKey) {
		this.pKey = pKey;
	}

	public Blob getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(Blob memberImage) {
		this.memberImage = memberImage;
	}

	public Clob getComment() {
		return comment;
	}

	public void setComment(Clob comment) {
		this.comment = comment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setTs(Timestamp ts) {
		this.registerTime = ts;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		email = mail;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String toString() {
		return "userid=" + memberId + "   password=" + password;
	}

	public String getUserType() {
		return userType;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public Double getUnpaid_amount() {
		return unpaid_amount;
	}

	public void setUnpaid_amount(Double unpaid_amount) {
		this.unpaid_amount = unpaid_amount;
	}
}
