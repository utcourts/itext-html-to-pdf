package gov.utcourts.oss.pdf.api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Ramakrishnan Kuppusami
 *
 * @implNote Application Constant
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {

    // PACKAGE
    public static final String BASE_PACKAGE = "gov.utcourts.oss.pdf.api";
    public static final String API_BASE_URL = "/html-to-pdf";
    public static final String PATH_CONVERT = "/convert";
    public static final String CHINESE_FONT_PATH = "fonts/Microsoft Yahei.ttf";
}
