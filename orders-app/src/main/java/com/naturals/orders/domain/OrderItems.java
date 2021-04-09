package com.naturals.orders.domain;

import com.naturals.orders.util.DefaultEntity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@NamedQueries({
		@NamedQuery(name = "OrderItems.findByOrder",
					query = "SELECT oi FROM OrderItems oi WHERE oi.order = ?1")})
public class OrderItems implements DefaultEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private Orders order;
	
	@Column(nullable = false, name = "product_id")
	private Long productId;
	
	@Column(nullable = false, name = "fruit_id")
	private Long fruitId;
	
	@Column(nullable = false)
	private double quantity = 0.0;

	@Column(nullable = false)
	private Double price = 0.0;

	public OrderItems() {
		
	}
	
	public OrderItems(Long id, Orders order, Long productId, Long fruitId, Double quantity, Double price) {
		this.id = id;
		this.order = order;
		this.productId = productId;
		this.fruitId = fruitId;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getFruitId() {
		return fruitId;
	}

	public void setFruitId(Long fruitId) {
		this.fruitId = fruitId;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Double getQuantity() { return quantity; }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fruitId == null) ? 0 : fruitId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItems other = (OrderItems) obj;
		if (fruitId == null) {
			if (other.getFruitId() != null)
				return false;
		} else if (!fruitId.equals(other.getFruitId()))
			return false;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		if (productId == null) {
			if (other.getProductId() != null)
				return false;
		} else if (!productId.equals(other.getProductId()))
			return false;
		return true;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new OrderItems(id, order, productId, fruitId, quantity, price);
	}
	
}
