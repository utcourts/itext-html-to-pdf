package gov.utcourts.oss.pdf.api.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerUtils {

    static ResourceBundle errorBundle;
    static{
        errorBundle = ResourceBundle.getBundle("error");
    }

    // Trace log without params
    public static void trace(String message) {
        String methodName = getCallingMethod();
        log.trace("{} : {}", methodName, message);
    }

    // Trace log without params
    public static void debug(String message) {
        String methodName = getCallingMethod();
        log.debug("{} : {}", methodName, message);
    }

    // Info log without params
    public static void info(String message) {
        String methodName = getCallingMethod();
        log.info("{} : {}", methodName, message);
    }

    // Info log with params
    public static void info(String message, Object[] param) {
        String methodName = getCallingMethod();
        log.info("{} : {} with params {}", methodName, message, Arrays.toString(param));
    }

    // Warn log with params
    public static void warn(String message, Object[] param) {
        String methodName = getCallingMethod();
        log.warn("{} : {} with params {}", methodName, message, Arrays.toString(param));
    }

    // Warn Log with Custom Error Code and Error Message
    public static void warn(String errorCode) {
        String methodName = getCallingMethod();
        String errorMessage = errorBundle.getString(errorCode);
        log.warn("WARNING: {} - {}, occurred in {}", errorCode, errorMessage, methodName);
    }

    // Warn Log with Custom Error Code and Error Message along with Exception Message
    public static void warn(String errorCode, Exception e) {
        String methodName = getCallingMethod();
        String errorMessage = errorBundle.getString(errorCode);
        log.warn("WARNING : {} - {} occurred in {} due to {}",
                errorCode, errorMessage, methodName, e.getMessage());
    }

    public static void warnWithMessage(String message, Exception e) {
        String methodName = getCallingMethod();
        log.warn("WARNING : {} occurred in {} due to {}", message, methodName, e.getMessage());
    }

    // Warn Log with Exception Message
    public static void warn(Exception e) {
        String methodName = getCallingMethod();
        log.warn("WARNING: {} occurred in {}", e.getMessage(), methodName);;
    }

    // Error Log with Exception Message
    public static void error(Exception e) {
        String methodName = getCallingMethod();
        log.error("Error occurred in {} - {}", methodName, e.getMessage());;
    }

    // Error Log with Custom Error Code and Error Message
    public static void error(String errorCode) {
        String methodName = getCallingMethod();
        String errorMessage = errorBundle.getString(errorCode);
        log.error("Error occurred in {} : {} - {}", methodName, errorCode, errorMessage);
    }

    // Error Log with Custom Error Code and Error Message along its params
    public static void error(String errorCode, Object params) {
        String methodName = getCallingMethod();
        String errorMessage = getLoggerString(errorCode, params);
        log.error("Error occurred in {} : {} - {}", methodName, errorCode, errorMessage);
    }

    // Error Log with Custom Error Code and Error Message along with Exception Message
    public static void error(String errorCode, Exception e) {
        String methodName = getCallingMethod();
        String errorMessage = errorBundle.getString(errorCode);
        log.error("Error occurred in {} : {} - {} due to {}", methodName, errorCode, errorMessage, e.getMessage());
    }

    // Error Log with Custom Message and Exception
    public static void errorWithMessage(String message, Exception e) {
        String methodName = getCallingMethod();
        log.error("Error occurred in {} : {} - {}", methodName, message, e.getMessage());;
    }

    // Entry log with params for methods ( Custom Logging )
    public static void logEntry(Object... params) {
        String methodName = getCallingMethod();
        if(!ObjectUtils.isEmpty(params)) {
            log.trace("Entering method {} with params {}", methodName, Arrays.deepToString(params));
        } else {
            log.trace("Entering method {}", methodName);
        }
    }

    // Entry log with params for methods ( AOP Logging )
    public static void logEntryAOP(String methodName, Object... params) {
        if(!ObjectUtils.isEmpty(params)) {
            log.trace("Entering method {} with params {}", methodName, Arrays.deepToString(params));
        } else {
            log.trace("Entering method {}", methodName);
        }
    }

    // Exit log for methods ( Custom Logging )
    public static void logExit() {
        String methodName = getCallingMethod();
        log.trace("Exiting method {}", methodName);
    }

    // Exit log with return values for methods ( Custom Logging )
    public static void logExit(Object result) {
        String methodName = getCallingMethod();
        log.trace("Exiting method {} with return value {}", methodName, result.toString());
    }

    // Exit log for methods ( AOP Logging )
    public static void logExitAOP(String methodName) {
        log.trace("Exiting method {}", methodName);
    }

    // Exit log with return values for methods ( AOP Logging )
    public static void logExitAOP(String methodName, Object result) {
        log.trace("Exiting method {} with return value {}", methodName, result.toString());
    }

    // Retrieves the calling class and method names
    private static String getCallingMethod() {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        return ste[2].getClassName() + "." + ste[2].getMethodName();
    }

    // Constructs the error message along with its params
    public static String getLoggerString(String loggerCode, Object param) {
        String loggerString = null;
        if (errorBundle != null) {
            loggerString = MessageFormat.format(errorBundle.getString(loggerCode), param);
        }
        return loggerString;
    }
}
