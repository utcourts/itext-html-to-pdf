package gov.utcourts.oss.pdf.api.controller;

import gov.utcourts.oss.pdf.api.constant.AppConstant;
import gov.utcourts.oss.pdf.api.exception.custom.ServiceException;
import gov.utcourts.oss.pdf.api.service.HtmlToPdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HtmlToPdfController.class)
@Import(HtmlToPdfControllerTest.MockConfig.class)
class HtmlToPdfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HtmlToPdfService htmlToPdfService;

    private MockMultipartFile mockFile;

    private static String CONVERT_ENDPOINT_URL = AppConstant.API_BASE_URL + AppConstant.PATH_CONVERT;
    @TestConfiguration
    static class MockConfig {
        @Bean
        HtmlToPdfService htmlToPdfService() {
            return Mockito.mock(HtmlToPdfService.class);
        }
    }

    @BeforeEach
    void setUp() {
        mockFile = new MockMultipartFile(
                "file",
                "test.html",
                "text/html",
                "<h1>Hello PDF</h1>".getBytes()
        );
    }

    @Test
    void testConvertHtmlToPdf_Success() throws Exception {
        ByteArrayResource pdfResource = new ByteArrayResource("PDF_CONTENT".getBytes());

        Mockito.when(htmlToPdfService.convert(any())).thenReturn(pdfResource);

        mockMvc.perform(multipart(CONVERT_ENDPOINT_URL)
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes("PDF_CONTENT".getBytes()));
    }

    @Test
    void testConvertHtmlToPdf_Failure() throws Exception {
        Mockito.when(htmlToPdfService.convert(any())).thenThrow(new ServiceException("HTML_CONVERSION_FAILED"));

        mockMvc.perform(multipart(CONVERT_ENDPOINT_URL)
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testConvert_InvalidFile_ShouldThrowException() throws Exception {
        MockMultipartFile mockUnsupportedFile = new MockMultipartFile(
                "file", "bad.txt", "text/plain", new byte[]{0, 1, 2, 3}
        );

        mockMvc.perform(multipart(CONVERT_ENDPOINT_URL)
                        .file(mockUnsupportedFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isUnsupportedMediaType());
    }
}
