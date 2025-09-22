package gov.utcourts.oss.pdf.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import static gov.utcourts.oss.pdf.api.constant.AppConstant.BASE_PACKAGE;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Html to Pdf Service Application class
 */
@SpringBootApplication(scanBasePackages = {BASE_PACKAGE})
public class HtmlToPdfServiceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HtmlToPdfServiceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(HtmlToPdfServiceApplication.class, args);
	}
}
