package ua.kiev.toolstore.util;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class AuthenticationSuccessHandlerImpl implements ApplicationListener<AuthenticationSuccessEvent> {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(AuthenticationSuccessHandlerImpl.class);

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        LOG.debug("<---login success: "
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
    }




}
