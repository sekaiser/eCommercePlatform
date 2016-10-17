package ukma.eCommerce.core.userModule.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ukma.eCommerce.core.userModule.model.domain.bo.User;
import ukma.eCommerce.util.TextUtils;
import ukma.eCommerce.util.validation.ValidationUtil;

import java.util.regex.Pattern;

import static ukma.eCommerce.util.validation.ValidationUtil.EMAIL_PATTERN;

/**
 * @author Max Oliynick
 */
@Component
public final class UserValidator implements Validator {

    public static final int minIdLen = 4, maxIdLen = 128;
    public static final int minStrLen = 3, maxStrLen = 128;

    public static final Pattern PLAIN_STR_PATTERN = Pattern
            .compile(String.format(ValidationUtil.PLAIN_STR, minStrLen, maxStrLen));

    @Override
    public boolean supports(Class<?> cl) {
        return User.class.isAssignableFrom(cl);
    }

    @Override
    public void validate(Object o, Errors errors) {

        final User user = (User) o;

        final String id = user.getId();

        if (id == null || id.length() < minIdLen || id.length() > maxIdLen) {
            errors.rejectValue("id", "error.user.id", String.format("User id '%s' is invalid, it should contain %d-%d characters", user.getId(), minIdLen, maxIdLen));
        }

        if (TextUtils.nullOrEmpty(user.getEmail()) || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            errors.rejectValue("email", "error.user.email", String.format("Email '%s' is invalid", user.getEmail()));
        }

        if (!TextUtils.nullOrEmpty(user.getName()) && !PLAIN_STR_PATTERN.matcher(user.getName()).matches()) {
            errors.rejectValue("name", "error.user.name", String.format("Name '%s' is invalid", user.getName()));
        }

        if (!TextUtils.nullOrEmpty(user.getSurname()) && !PLAIN_STR_PATTERN.matcher(user.getSurname()).matches()) {
            errors.rejectValue("surname", "error.user.surname", String.format("Surname '%s' is invalid", user.getSurname()));
        }

        if (!TextUtils.nullOrEmpty(user.getCountry()) && !PLAIN_STR_PATTERN.matcher(user.getCountry()).matches()) {
            errors.rejectValue("country", "error.user.country", String.format("Country '%s' is invalid", user.getCountry()));
        }

        if (!TextUtils.nullOrEmpty(user.getAddress()) && !PLAIN_STR_PATTERN.matcher(user.getAddress()).matches()) {
            errors.rejectValue("address", "error.user.address", String.format("Address '%s' is invalid", user.getAddress()));
        }
    }
}