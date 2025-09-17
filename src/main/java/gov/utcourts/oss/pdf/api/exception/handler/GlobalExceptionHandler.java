package gov.utcourts.oss.pdf.api.exception.handler;

import gov.utcourts.oss.pdf.api.exception.custom.ServiceException;
import gov.utcourts.oss.pdf.api.model.BadRequestError;
import gov.utcourts.oss.pdf.api.model.Error;
import gov.utcourts.oss.pdf.api.model.InternalServerError;
import gov.utcourts.oss.pdf.api.model.UnsupportedRequestError;
import gov.utcourts.oss.pdf.api.model.MaxUploadSizeExceededError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote  Custom Global Exception Handler
 *
 */
@RestControllerAdvice
@PropertySource("classpath:error.properties")
public class GlobalExceptionHandler {

    private final Environment env;
    private final Error error;

    @Autowired
    public GlobalExceptionHandler(Environment env) {
        this.env = env;
        this.error = new Error();
    }

    /**
     * @author Ramakrishnan Kuppusami
     *
     * Handles all the Internal exceptions
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected InternalServerError handleExceptionInternal(Exception e) {

        error.setCode("500_INTERNAL_SERVER_ERROR");
        error.setMessage(env.getProperty(error.getCode()));

        InternalServerError internalServerError = new InternalServerError();
        internalServerError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        internalServerError.setError(error);
        return internalServerError;
    }

    /**
     * @author Ramakrishnan Kuppusami
     *
     * Handles all the Custom thrown exceptions
     *
     * @param serviceException
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected InternalServerError handleCustomException(ServiceException serviceException) {

        error.setCode(serviceException.getMessage());
        error.setMessage(env.getProperty(error.getCode()));

        InternalServerError internalServerError = new InternalServerError();
        internalServerError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        internalServerError.setError(error);
        return internalServerError;
    }

    /**
     * @author Ramakrishnan Kuppusami
     *
     * Handles all the BadRequest exceptions
     *
     * @param e
     * @return
     */
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentConversionNotSupportedException.class,
            MissingServletRequestPartException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected BadRequestError handleBadRequest(Exception e) {

        error.setCode("400_BAD_REQUEST");
        error.setMessage(env.getProperty(error.getCode()));

        BadRequestError badRequestError = new BadRequestError();
        badRequestError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        badRequestError.setError(error);

        return badRequestError;
    }

    /**
     * @author Ramakrishnan Kuppusami
     *
     * Handles UnsupportedOperationException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected UnsupportedRequestError handleUnsupportedMediaType(Exception e) {

        error.setCode(e.getMessage());
        error.setMessage(env.getProperty(error.getCode()));

        UnsupportedRequestError unsupportedRequestError = new UnsupportedRequestError();
        unsupportedRequestError.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        unsupportedRequestError.setError(error);

        return unsupportedRequestError;
    }

    /**
     * @author Ramakrishnan Kuppusami
     *
     * Handles payload size exceeded exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler({
            MaxUploadSizeExceededException.class,
    })
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ResponseBody
    protected MaxUploadSizeExceededError handleMaxUploadSizeError(Exception e) {

        error.setCode("413_MAX_UPLOAD_SIZE_EXCEEDED");
        error.setMessage(env.getProperty(error.getCode()));

        MaxUploadSizeExceededError maxUploadSizeExceededError = new MaxUploadSizeExceededError();
        maxUploadSizeExceededError.setStatusCode(HttpStatus.PAYLOAD_TOO_LARGE.value());
        maxUploadSizeExceededError.setError(error);

        return maxUploadSizeExceededError;
    }
}

