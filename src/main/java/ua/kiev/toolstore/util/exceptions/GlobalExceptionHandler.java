package ua.kiev.toolstore.util.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.toolstore.model.security.AuthorizedUser;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView handleAllException(WebRequest req, Exception e) {
        LOG.error("<<-Exception at request " + req.getContextPath() + " \n " + e.getMessage());
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorMsg", e.getMessage());

        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            LOG.error("<<-Exception invoked by User with ID: (" + authorizedUser.getUserWrapper().getId()
                    + " ) and email: " + authorizedUser.getUserWrapper().getEmail() + " ) ");
        } else {
            LOG.error("<<-Exception invoked by Anonymous visitor");
        }

        return model;
    }


    @ExceptionHandler(CustomGenericException.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView handleCustomException(CustomGenericException e) {
        LOG.error("<<-CustomGenericException occur" + " \n " + e.getErrorMsg() + "\n" +
                e.getMessage() + " | Cause: " + e.getCause());
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorMsg", e.getErrorMsg());

        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            LOG.error("<<-Exception invoked by User with ID: (" + authorizedUser.getUserWrapper().getId()
                    + " ) and email: " + authorizedUser.getUserWrapper().getEmail() + " ) ");
        } else {
            LOG.error("<<-Exception invoked by Anonymous visitor");
        }

        return model;
    }

}

