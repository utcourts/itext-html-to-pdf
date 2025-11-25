package gov.utcourts.oss.pdf.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlToPdfServiceTest {

    private final HtmlToPdfService service = new HtmlToPdfService();

    @Test
    void testConvert_ValidHtmlFile_ShouldGeneratePdf() throws Exception {
        // Prepare test data
        String htmlContent = "<html><body><h1>Hello PDF</h1><p>This is a test</p></body></html>";
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.html", "text/html", htmlContent.getBytes()
        );

        // Invoke method
        Resource pdfResource = service.convert(file);

        // Assert
        assertThat(pdfResource).isNotNull();
        assertThat(pdfResource.contentLength()).isGreaterThan(0);

        // Verify PDF header (first 4 bytes should be %PDF)
        try (InputStream in = pdfResource.getInputStream()) {
            byte[] header = new byte[4];
            int read = in.read(header);
            assertThat(read).isEqualTo(4);
            assertThat(new String(header)).isEqualTo("%PDF");
        }
    }
}
