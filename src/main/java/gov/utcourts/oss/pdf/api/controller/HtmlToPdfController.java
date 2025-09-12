package gov.utcourts.oss.pdf.api.controller;


import com.itextpdf.html2pdf.HtmlConverter;
import gov.utcourts.oss.pdf.api.client.rest.api.HtmlToPdfApi;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Controller class for Html to PDF api
 */
@RestController
@RequestMapping("/html-to-pdf")
public class HtmlToPdfController implements HtmlToPdfApi {

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
           value = "/convert",
           consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
           produces = MediaType.APPLICATION_PDF_VALUE
   )
    public ResponseEntity<Resource> convertHtmlToPdf(
            @RequestPart(value = "file") MultipartFile file) {
       // Output stream of converted pdf content
       ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();

       // Read html file content as bytes
       try (InputStream htmlStream = new ByteArrayInputStream(file.getBytes())) {
           HtmlConverter.convertToPdf(htmlStream, pdfOutput);

           ByteArrayResource resource = new ByteArrayResource(pdfOutput.toByteArray());
           return ResponseEntity.ok()
                   .contentType(MediaType.APPLICATION_PDF)
                   .body(resource);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
}
