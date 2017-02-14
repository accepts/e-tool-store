package ua.kiev.toolstore.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.model.security.UserWrapper;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.regex.Pattern;

@Component
public class UserUtil {

    private static final String EMAIL_PATTERN = "^[a-z]+[a-z0-9._]+@[a-z]+\\.[a-z.]{2,5}$";

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserUtil.class);

    @Autowired
    private UserService userService;

    // Validate USER fields (name + email)
    public boolean userFieldsValidator(User user){
        LOG.debug("<--- Start Validate USER: {}", user);
        if (user.getName().trim().isEmpty() || user.getName().trim().length() < 3) return false;

        if (user.getEmail().trim().isEmpty() || user.getEmail().trim().length() < 4) {
            return false;
        } else {
            user.setEmail(user.getEmail().toLowerCase());
            return Pattern.compile(EMAIL_PATTERN).matcher(user.getEmail()).matches();
        }
    }


    // Check USER for duplicating e-mail
    public boolean userDuplicateValidator(User user){
        LOG.debug("<---Validate USER check for duplicate---");
        return userService.countByEmail(user.getEmail()) <= 0;
    }


    public static UserWrapper wrapUser(User user) {
        return new UserWrapper(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

}
