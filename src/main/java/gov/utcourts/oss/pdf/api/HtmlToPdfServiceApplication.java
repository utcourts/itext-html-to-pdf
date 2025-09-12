package gov.utcourts.oss.pdf.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Html to Pdf Service Application class
 */
@SpringBootApplication
public class HtmlToPdfServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HtmlToPdfServiceApplication.class, args);
	}
}
