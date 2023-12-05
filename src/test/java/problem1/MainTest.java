package problem1;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {

  private static final String EMAIL = "--email";
  private static final String EMAIL_TEMPLATE_COMMAND = "--email-template";
  private static final String EMAIL_TEMPLATE = "src/main/java/problem1/resources/email-template.txt";
  private static final String LETTER = "--letter";
  private static final String LETTER_TEMPLATE_COMMAND = "--letter-template";
  private static final String LETTER_TEMPLATE = "src/main/java/problem1/resources/letter-template.txt";
  private static final String OUTPUT_DIR = "--output-dir";
  private static final String CSV_FILE = "--csv-file";
  private Main main;
  private String[] args;

  @BeforeEach
  void setUp() throws ValidationErrorException, IOException {
    args = new String[]{
        EMAIL, EMAIL_TEMPLATE_COMMAND, EMAIL_TEMPLATE, LETTER,
        LETTER_TEMPLATE_COMMAND, LETTER_TEMPLATE,
        OUTPUT_DIR, "src/TestPackage/",
        CSV_FILE, "src/main/java/problem1/resources/insurance-company-members.csv"

    };
    main = new Main();
  }

  @Test
  void testMain() throws ValidationErrorException, IOException, EmptyCSVException {
    main.main(args);
  }
}