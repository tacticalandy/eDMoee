package _04_ShoppingCart.model;

public class OrderItemBean {
	Integer seqno;
	Integer orderNo;
	Integer bookId;
	String description;
	Integer quantity;
	Double unitPrice;
	Double discount;

	public OrderItemBean(Integer seqno, Integer orderNo, Integer bookID,
			String description, Integer quantity, Double unitPrice, Double discount) {
		super();
		this.seqno = seqno;
		this.orderNo = orderNo;
		this.bookId = bookID;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}
	public OrderItemBean(Integer orderNo, Integer bookID,
			String description, Integer quantity, Double unitPrice, Double discount) {
		super();
		
		this.orderNo = orderNo;
		this.bookId = bookID;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}
	public OrderItemBean() {
		
	}
	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookID) {
		this.bookId = bookID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}