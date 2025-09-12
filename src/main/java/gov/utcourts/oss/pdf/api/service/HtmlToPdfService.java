package gov.utcourts.oss.pdf.api.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Service class to handle HTML to PDF conversion logic.
 */
@Service
public class HtmlToPdfService {

    /**
     * Converts the given HTML file to a PDF and returns it as a {@link Resource}.
     *
     * @param file the HTML file to convert
     * @return PDF resource
     * @throws Exception if conversion fails
     */
    public Resource convert(MultipartFile file) throws Exception {
        try (InputStream htmlStream = new ByteArrayInputStream(file.getBytes());
             ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream()) {

            HtmlConverter.convertToPdf(htmlStream, pdfOutput);
            return new ByteArrayResource(pdfOutput.toByteArray());
        }
    }
}
