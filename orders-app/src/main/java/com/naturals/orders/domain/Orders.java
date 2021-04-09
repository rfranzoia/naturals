package com.naturals.orders.domain;

import com.naturals.orders.util.DefaultEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NamedQueries({
		@NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")})
public class Orders implements DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "date_created")
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	public Orders() {
	}

	public Orders(Long id, LocalDateTime dateCreated) {
		this.id = id;
		this.dateCreated = dateCreated;
	}

	public Orders(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Orders other = (Orders) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	@Override
	public Orders clone() {
		return new Orders(id, dateCreated);
	}

}
