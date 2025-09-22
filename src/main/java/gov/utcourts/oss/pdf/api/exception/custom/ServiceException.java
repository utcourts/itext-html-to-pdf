package gov.utcourts.oss.pdf.api.exception.custom;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Custom Service Exception
 *
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
