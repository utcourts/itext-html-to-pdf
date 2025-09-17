package gov.utcourts.oss.pdf.api.controller;


import gov.utcourts.oss.pdf.api.client.HtmlToPdfApi;
import gov.utcourts.oss.pdf.api.constant.AppConstant;
import gov.utcourts.oss.pdf.api.service.HtmlToPdfService;
import gov.utcourts.oss.pdf.api.utils.LoggerUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Controller class for Html to PDF api
 */
@RestController
@RequestMapping(AppConstant.API_BASE_URL)
public class HtmlToPdfController implements HtmlToPdfApi {

    private final HtmlToPdfService htmlToPdfService;

    public HtmlToPdfController(HtmlToPdfService htmlToPdfService) {
        this.htmlToPdfService = htmlToPdfService;
    }

    /**
     * Converts an uploaded HTML file to a PDF.
     *
     * <p>This endpoint accepts a single HTML file as input and returns a PDF file as output.
     * The request must be sent as {@code multipart/form-data} with the file in the "file" part.
     * The response content type is {@code application/pdf}.</p>
     *
     * <p>Example request using cURL:</p>
     * <pre>
     * curl -X POST "http://host-address/html-to-pdf/convert" \
     *      -H "Content-Type: multipart/form-data" \
     *      -F "file=@example.html" \
     *      --output output.pdf
     * </pre>
     *
     * @param file the HTML file to convert; must not be null
     * @return {@link ResponseEntity} containing the converted PDF as {@link org.springframework.core.io.Resource}
     *         with HTTP status {@code 200 OK} if successful
     */
    @PostMapping(
            value = AppConstant.PATH_CONVERT,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<Resource> convertHtmlToPdf(@RequestPart("file") MultipartFile file) {
        LoggerUtils.logEntry();

        String contentType = file.getContentType();
        LoggerUtils.info("Received Content Type: " +contentType);
        // Only allow HTML
        if (contentType == null ||
                !(contentType.equalsIgnoreCase(MediaType.TEXT_HTML_VALUE) || contentType.equalsIgnoreCase(MediaType.APPLICATION_XHTML_XML_VALUE))) {
            String errorCode = "415_UNSUPPORTED_TYPE";
            LoggerUtils.error(errorCode);
            throw new UnsupportedOperationException(errorCode);
        }

        Resource pdfResource = htmlToPdfService.convert(file);
        LoggerUtils.logExit();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfResource);
    }

}
