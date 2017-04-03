package ua.kiev.toolstore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* Example of usage:
* protected static final LoggerWrapper LOG = LoggerWrapper.get(XXXXX.class);
* LOG.info("Some info: {} \n", objectForPrint);
*/
public class LoggerWrapper {

    private Logger logger;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    //==================Constructors===============================

    public static LoggerWrapper get(Class aClass) {
        return new LoggerWrapper(LoggerFactory.getLogger(aClass));
    }

    public LoggerWrapper(Logger logger) {
        this.logger = logger;
    }

    //==================Logging methods===============================

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(String msg, Object... arguments) {
        logger.debug(msg, gson.toJson(arguments));
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(String msg, Object... arguments) {
        logger.info(msg, gson.toJson(arguments));
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }


 /*
   public boolean isDebug() {
        return logger.isDebugEnabled();
    }

    public IllegalStateException getIllegalStateException(String msg) {
        return getIllegalStateException(msg, null);
    }

    public IllegalStateException getIllegalStateException(String msg, Throwable e) {
        logger.error(msg, e);
        return new IllegalStateException(msg, e);
    }

    public IllegalArgumentException getIllegalArgumentException(String msg) {
        return getIllegalArgumentException(msg, null);
    }

    public IllegalArgumentException getIllegalArgumentException(String msg, Throwable e) {
        logger.error(msg, e);
        return new IllegalArgumentException(msg, e);
    }

    public UnsupportedOperationException getUnsupportedOperationException(String msg) {
        logger.error(msg);
        return new UnsupportedOperationException(msg);
    }

    public NotFoundException getNotFoundException(String reason) {
        logger.error("No data found");
        return new NotFoundException(reason);
    }

    public ErrorInfo getErrorInfo(CharSequence requestUrl, Exception e) {
        logger.error("Exception at request " + requestUrl, e);
        return new ErrorInfo(requestUrl, e);
    }*/
}

