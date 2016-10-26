package ukma.eCommerce.core.paymentModule.model.domain.bo;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import ukma.eCommerce.core.paymentModule.model.domain.vo.CreditCard;
import ukma.eCommerce.core.paymentModule.model.domain.vo.InvoiceID;
import ukma.eCommerce.core.paymentModule.model.domain.vo.types.ChargeStatus;
import ukma.eCommerce.util.IBuilder;
import ukma.eCommerce.util.validation.ValidationUtil;

/**
 * Aggregate root object that represents charge
 *
 * @author Solomka
 */
public final class Charge {

	private final UUID id;
	private final InvoiceID invoice;
	private final CreditCard creditCard;
	private DateTime paymentDate;
	private ChargeStatus status;

	/**
	 * builder that creates immutable instance of {@linkplain Charge}
	 *
	 * @author Solomka
	 */

	public static class Builder implements IBuilder<Charge> {

		private UUID id;
		private InvoiceID invoice;
		private CreditCard creditCard;
		private DateTime paymentDate;
		private ChargeStatus status;

		public Builder() {
		}

		public Builder(Charge charge) {

			Objects.requireNonNull(charge, "charge must not be null");

			setId(charge.getId()).setInvoice(charge.getInvoice()).setCreditCard(charge.getCreditCard())
					.setStatus(charge.getStatus());
		}

		public UUID getId() {
			return id;
		}

		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}

		/*
		 * public Build setID(){ this.id = UUID.randomUUID() return this; }
		 */

		public InvoiceID getInvoice() {
			return invoice;
		}

		public Builder setInvoice(InvoiceID invoice) {
			this.invoice = invoice;
			return this;
		}

		public CreditCard getCreditCard() {
			return creditCard;
		}

		public Builder setCreditCard(CreditCard creditCard) {
			this.creditCard = creditCard;
			return this;
		}

		public DateTime getPaymentDate() {
			return paymentDate;
		}

		public Builder setPaymentDate(DateTime paymentDate) {
			this.paymentDate = paymentDate;
			return this;
		}

		public ChargeStatus getStatus() {
			return status;
		}

		public Builder setStatus(ChargeStatus status) {
			this.status = status;
			return this;
		}

		@Override
		public Charge build() {
			return new Charge(this);
		}

	}

	private Charge(@NotNull Builder builder) {

		Objects.requireNonNull(builder, "builder must no be null");

		this.id = ValidationUtil.validate(builder.getId());
		this.invoice = ValidationUtil.validate(builder.getInvoice());
		this.creditCard = ValidationUtil.validate(builder.getCreditCard());
		this.paymentDate = builder.getPaymentDate();
		this.status = Objects.requireNonNull(builder.getStatus(), "status == null");
	}

	public UUID getId() {
		return id;
	}

	public InvoiceID getInvoice() {
		return invoice;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public ChargeStatus getStatus() {
		return status;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(DateTime paymentDate) {

		Objects.requireNonNull(paymentDate, "paymentDate == null");
		this.paymentDate = paymentDate;
	}

	public void changeStatus(ChargeStatus status) {
		this.status = Charge.checkStatus(this.paymentDate, status);
	}

	private static ChargeStatus checkStatus(DateTime paymentDate, ChargeStatus status) {

		Objects.requireNonNull(status);

		if (paymentDate != null && (status == ChargeStatus.PENDING))
			throw new IllegalArgumentException(
					String.format("Charge with paymentDate can't have ChargeStatus.PENDING ", status));

		if (paymentDate == null && (status == ChargeStatus.SUCCEEDED || status == ChargeStatus.RETURNED
				|| status == ChargeStatus.FAILED))
			throw new IllegalArgumentException(
					String.format("Charge without paymentDate can't have ChargeStatus.SUCCEEDED" + " || "
							+ "status == ChargeStatus.RETURNED || status == ChargeStatus.FAILED ", status));

		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Charge other = (Charge) obj;
		if (creditCard == null) {
			if (other.creditCard != null)
				return false;
		} else if (!creditCard.equals(other.creditCard))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Charge [id=" + id + ", invoice=" + invoice + ", creditCard=" + creditCard + ", paymentDate="
				+ paymentDate + ", status=" + status + "]";
	}

}
