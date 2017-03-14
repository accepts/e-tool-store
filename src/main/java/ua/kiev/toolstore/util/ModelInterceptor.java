package ua.kiev.toolstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.kiev.toolstore.model.security.AuthorizedUser;
import ua.kiev.toolstore.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor for every requests adds to the model the sum of LineItems amount of user's active order
 */
public class ModelInterceptor extends HandlerInterceptorAdapter {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(ModelInterceptor.class);

    @Autowired
    private OrderService orderService;


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
            if (authorizedUser != null) {
                modelAndView.getModelMap().addAttribute("itemsInOrder",
                        orderService.countItemsInActiveOrderOfUser(authorizedUser.getUserWrapper().getId()));
            }
        }
    }


}
