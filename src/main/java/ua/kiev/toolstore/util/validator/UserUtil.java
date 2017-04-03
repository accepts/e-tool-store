package ua.kiev.toolstore.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.kiev.toolstore.model.enums.Role;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.model.security.User;
import ua.kiev.toolstore.model.security.UserWrapper;
import ua.kiev.toolstore.services.OrderService;
import ua.kiev.toolstore.services.UserService;
import ua.kiev.toolstore.util.LoggerWrapper;

import java.util.Collection;
import java.util.regex.Pattern;

@Component
public class UserUtil {

    private static final String EMAIL_PATTERN = "^[a-z]+[a-z0-9._]+@[a-z]+\\.[a-z.]{2,5}$";

    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserUtil.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    // Validate USER fields (name + email)
    public boolean userFieldsValidator(User user) {
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
    public boolean userDuplicateValidator(User user) {
        LOG.debug("<---Validate USER check for duplicate---");
        return userService.countByEmail(user.getEmail()) <= 0;
    }


    public static UserWrapper wrapUser(User user) {
        return new UserWrapper(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }


    public Long getUserId() {
        Long id = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            id = ((AuthorizedUser) principal).id();
            return id;
        } else {
            return null;
        }
    }


    //******************************* Addition Methods ********************************************

    public Collection<Role> getRoles() {
        Collection<Role> roles = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            roles = (Collection<Role>) ((UserDetails) principal).getAuthorities();
            return roles;
        } else {
            return null;
        }
    }

    public String getUserName() {
        String name = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            name = ((UserDetails) principal).getUsername();
            return name;
        } else {
            return null;
        }
    }

    public String getEmail() {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((AuthorizedUser) principal).getUserWrapper().getEmail();
            return email;
        } else {
            return null;
        }
    }

}
