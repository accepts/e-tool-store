package ua.kiev.toolstore.util.exceptions;

public class CustomGenericException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;
    private String errorMsg;


//    ------------- Getter-Setter-Constructors

    public CustomGenericException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errorMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



}
