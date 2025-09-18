package gov.utcourts.oss.pdf.api.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import gov.utcourts.oss.pdf.api.exception.custom.ServiceException;
import gov.utcourts.oss.pdf.api.utils.LoggerUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static gov.utcourts.oss.pdf.api.constant.AppConstant.CHINESE_FONT_PATH;

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
     * @throws ServiceException if conversion fails
     */
    public Resource convert(MultipartFile file) throws ServiceException {
        LoggerUtils.logEntry();
        Resource resource = null;
        try (InputStream htmlStream = new ByteArrayInputStream(file.getBytes());
             ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream()) {

            HtmlConverter.convertToPdf(htmlStream, pdfOutput, buildConverterProperties());
            resource = new ByteArrayResource(pdfOutput.toByteArray());
            LoggerUtils.logExit();
            return resource;
        } catch(IOException e) {
            LoggerUtils.error(e);
            throw new ServiceException("HTML_CONVERSION_FAILED");
        }
    }

    /**
     * Build Converter Properties for handling chinese font
     *
     * @return ConverterProperties
     */
    private ConverterProperties buildConverterProperties() {
        ConverterProperties converterProperties = new ConverterProperties();
        try {
            FontProvider fontProvider = new DefaultFontProvider(false,false,true);
            FontProgram fontProgram = FontProgramFactory.createFont(CHINESE_FONT_PATH);
            fontProvider.addFont(fontProgram);
            converterProperties.setFontProvider(fontProvider);
        } catch (Exception exception) {
            LoggerUtils.error(exception);
        }
        return converterProperties;
    }
}
