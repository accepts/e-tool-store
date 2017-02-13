package ua.kiev.toolstore.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.EnumSet;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final String EMAIL_PATTERN = "^[a-z]+[a-z0-9._]+@[a-z]+\\.[a-z.]{2,5}$";

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserValidator.class);

    @Autowired
    private UserService userService;

    // Validate USER fields (name + email)
    public boolean userFieldsValidator(User user){
        LOG.debug("<--- Start Validate USER: {}", user);
        //TODO move this option into Service layer
        if (user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(EnumSet.of(Role.ROLE_CUSTOMER));
        }

        if (user.getName().trim().isEmpty() || user.getName().trim().length() < 3) return false;
        return Pattern.compile(EMAIL_PATTERN).matcher(user.getEmail()).matches();
    }


    // Check USER for duplicating e-mail
    public boolean userDuplicateValidator(User user){
        LOG.debug("<---Validate USER check for duplicate---");
        return userService.countByEmail(user.getEmail()) <= 0;
    }

}
