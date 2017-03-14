package ua.kiev.toolstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
import ua.kiev.toolstore.util.validator.UserUtil;

@Service
public class AuthenticationSuccessHandlerImpl implements ApplicationListener<AuthenticationSuccessEvent> {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(AuthenticationSuccessHandlerImpl.class);

    @Autowired
    private UserUtil userUtil;


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        LOG.debug("<---login success-");
//        userUtil.setItemsInOrder(6);
//        LOG.debug("<----LOGIN Success for userID: ( " + userUtil.getUserId()
//                + " ), userEmail: (" + userUtil.getEmail() + " ) \n items in Order: ( " + userUtil.getItemsInOrder()
//                + " )"  );
    }




}
