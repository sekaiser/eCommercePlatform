package ukma.eCommerce.core.paymentModule.repository.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ukma.eCommerce.core.paymentModule.model.domain.vo.types.OrderStatus;
import ukma.eCommerce.core.userModule.repository.po.CustomerPO;
import ukma.eCommerce.util.IBuilder;

@Entity
@Table(name = "orders")
public class OrderPO implements Serializable {

	private static final long serialVersionUID = -6339656414384828558L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type = "uuid-char")
	private UUID id;

	@Column(name = "creation_date", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationDate;

	@Column(name = "fulfilment_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fulfilmentDate;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_id", nullable = false/* , updatable = false */)
	private CustomerPO customer;

	// @OneToOne(optional = false)
	// @JoinColumn(name = "shipment_id", nullable = false/* , updatable = false
	// */)
	/*
	 * Here is the annotation to add in order to Hibernate to automatically
	 * insert and update ShipmentPO (if any)
	 */
	@OneToOne(optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "shipment_id", nullable = false/* , updatable = false */)
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private ShipmentPO shipment;

	// @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	/*
	 * Here is the annotation to add in order to Hibernate to automatically
	 * insert and update OrderItems (if any)
	 */
	// @SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemId.order", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE })
	private List<OrderItemPO> orderItems;

	@OneToMany(mappedBy = "order", cascade = CascadeType.REFRESH)
	private List<InvoicePO> invoices;

	public static class Builder implements IBuilder<OrderPO> {

		private UUID id;
		private CustomerPO customer;
		private ShipmentPO shipment;
		private List<OrderItemPO> orderItems;
		private OrderStatus status;
		private DateTime creationDate;
		private DateTime fulfilmentDate;

		public Builder() {

		}

		public Builder(OrderPO orderPO) {
			Objects.requireNonNull(orderPO, "orderPO can not be null");
			setId(orderPO.getId()).setCustomer(orderPO.getCustomer()).setShipment(orderPO.getShipment())
					.setOrderItems(orderPO.getOrderItems()).setStatus(orderPO.getStatus())
					.setCreationDate(orderPO.getCreationDate()).setFulfilmentDate(orderPO.getFulfilmentDate());
		}

		public UUID getId() {
			return id;
		}

		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}

		public CustomerPO getCustomer() {
			return customer;
		}

		public Builder setCustomer(CustomerPO customer) {
			this.customer = customer;
			return this;
		}

		public ShipmentPO getShipment() {
			return shipment;
		}

		public Builder setShipment(ShipmentPO shipment) {
			this.shipment = shipment;
			return this;
		}

		public List<OrderItemPO> getOrderItems() {
			return orderItems;
		}

		public Builder setOrderItems(List<OrderItemPO> orderItems) {
			this.orderItems = orderItems;
			return this;
		}

		public OrderStatus getStatus() {
			return status;
		}

		public Builder setStatus(OrderStatus status) {
			this.status = status;
			return this;
		}

		public DateTime getCreationDate() {
			return creationDate;
		}

		public Builder setCreationDate(DateTime creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public DateTime getFulfilmentDate() {
			return fulfilmentDate;
		}

		public Builder setFulfilmentDate(DateTime fulfilmentDate) {
			this.fulfilmentDate = fulfilmentDate;
			return this;
		}

		@Override
		public OrderPO build() {
			// TODO Auto-generated method stub
			return new OrderPO(this);
		}

	}

	public OrderPO() {

	}

	public OrderPO(Builder builder) {
		Objects.requireNonNull(builder, "builder can  not bu null");
		this.id = builder.getId();
		this.customer = Objects.requireNonNull(builder.getCustomer());
		this.shipment = Objects.requireNonNull(builder.getShipment());
		// this.orderItems = Objects.requireNonNull(builder.getOrderItems());
		// this.orderItems = builder.getOrderItems();
		this.orderItems = new ArrayList<OrderItemPO>();
		this.status = Objects.requireNonNull(builder.getStatus());
		this.creationDate = Objects.requireNonNull(builder.getCreationDate());
		this.fulfilmentDate = builder.getFulfilmentDate();
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public DateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getFulfilmentDate() {
		return this.fulfilmentDate;
	}

	public void setFulfilmentDate(DateTime fulfilmentDate) {
		this.fulfilmentDate = fulfilmentDate;
	}

	public OrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public CustomerPO getCustomer() {
		return this.customer;
	}

	public void setCustomer(CustomerPO customer) {
		this.customer = customer;
	}

	public ShipmentPO getShipment() {
		return this.shipment;
	}

	public void setShipment(ShipmentPO shipment) {
		this.shipment = shipment;
	}

	public List<OrderItemPO> getOrderItems() {
		return this.orderItems;
	}

	// protected is important
	protected void setOrderItems(List<OrderItemPO> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * add OrderPO to OrderItemPO and vice versa
	 * 
	 * @param orderItemPO
	 */
	public void addToOrderItemPO(OrderItemPO orderItemPO) {
		orderItemPO.setOrder(this);
		this.orderItems.add(orderItemPO);
	}

	public List<InvoicePO> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<InvoicePO> invoices) {
		this.invoices = invoices;
	}

	/**
	 * rewrite fields access to getters access for props because of Hibernate
	 * proxy
	 */
	

}
