package com.critical.example.sample_bitofcode.domain;

import com.critical.example.sample_bitofcode.util.DefaultEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status")
@NamedQueries({
		@NamedQuery(name = "OrderStatus.findByOrder",
				query = "SELECT os FROM OrderStatus os WHERE os.order = ?1")})
public class OrderStatus implements DefaultEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private Orders order;
	
	@Column(nullable = false, name = "date_created")
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	@Column(nullable = false, name = "status_id")
	private Long statusId;
	
	@Column(nullable = false, name = "status_description")
	private String statusDescription = "";

	public OrderStatus() {
	}
	
	public OrderStatus(Long id, Orders order, LocalDateTime dateCreated, Long statusId, String statusDescription) {
		super();
		this.id = id;
		this.order = order;
		this.dateCreated = dateCreated;
		this.statusId = statusId;
		this.statusDescription = statusDescription;
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

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((statusId == null) ? 0 : statusId.hashCode());
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
		OrderStatus other = (OrderStatus) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		if (statusId == null) {
			if (other.getStatusId() != null)
				return false;
		} else if (!statusId.equals(other.getStatusId()))
			return false;
		return true;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new OrderStatus(id, order, dateCreated, statusId, statusDescription);
	}
	
}