package ukma.eCommerce.core.paymentModule.model.dwo;

import org.joda.time.DateTime;
import ukma.eCommerce.util.validation.IValidateable;

import java.util.Objects;

/**
 * <p>
 * Value object which describes credit card form
 * </p>
 *
 * @author ������
 */
public final class CreditCardDTO implements IValidateable {

    private final String number;
    private final String cvc;
    private final DateTime expirationDate;

    public CreditCardDTO(String number, String cvv, DateTime expirationDate) {
        this.number = Objects.requireNonNull(number, "card number == null");
        this.cvc = Objects.requireNonNull(cvv, "cvv == null");
        this.expirationDate = Objects.requireNonNull(expirationDate, "exp date == null");
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvc;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cvc == null) ? 0 : cvc.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }
}

