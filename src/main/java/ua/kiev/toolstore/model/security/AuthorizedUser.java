package ua.kiev.toolstore.model.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.kiev.toolstore.util.validator.UserUtil;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private UserWrapper userWrapper;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userWrapper = UserUtil.wrapUser(user);
    }


    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static Long id() {
        return get().userWrapper.getId();
    }


    public UserWrapper getUserWrapper(){
      return userWrapper;
    }

    @Override
    public String toString() {
        return userWrapper.toString();
    }
}
