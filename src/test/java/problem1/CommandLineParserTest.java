package problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {

  private String[] args;
  private CommandLineParser testCMD;
  private CommandLineParser testCMD2;
  private String emailTemplatePath;
  private String letterTemplatePath;
  private String csvPath;
  private String outputDirectory;


  @BeforeEach
  void setUp() throws ValidationErrorException {
    emailTemplatePath = "src/main/java/problem1/resources/email-template.txt";
    letterTemplatePath = "src/main/java/problem1/resources/letter-template.txt";
    csvPath = "src/main/java/problem1/resources/insurance-company-members.csv";
    outputDirectory = "src/main/java/problem1/resources/";
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", csvPath,
        "--output-dir", outputDirectory};
    testCMD = new CommandLineParser(args);
  }

  @Test
  void getCsvFilePath() {
    String expectedPath = "src/main/java/problem1/resources/insurance-company-members.csv";
    assertEquals(expectedPath, testCMD.getCsvFilePath());
  }

  @Test
  void getCsvFilePathWrongType() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", "", "--output-dir", outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getCsvFilePathNull() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", null, "--output-dir",
        outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getOutputDirectory() {
    String expectedPath = "src/main/java/problem1/resources/";
    assertEquals(expectedPath, testCMD.getOutputDirectory());
  }

  @Test
  void getOutputDirNull() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", "email-template.txt", "--letter", "--letter-template", "letter-template.txt", "--csv-file", "insurance-company-members.csv", "--output-dir", null};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getEmailNull() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", null, "--letter", "--letter-template",
        letterTemplatePath, "--csv-file", csvPath, "--output-dir", outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getEmailWrongFileType() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", "email-template.csv", "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", csvPath, "--output-dir",
        outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getLetterNull() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", null, "--csv-file", csvPath, "--output-dir",
        outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getLetterWrongType() throws ValidationErrorException {
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", "letter-template.csv", "--csv-file", csvPath, "--output-dir",
        outputDirectory};
    assertThrows(ValidationErrorException.class, () -> testCMD2 = new CommandLineParser(args));
  }

  @Test
  void getOutputType() {
    HashSet<String> testOutputType = new HashSet<>();
    testOutputType.add("--email");
    testOutputType.add("--letter");
    assertEquals(testOutputType,testCMD.getOutputType());
  }

  @Test
  void getTypeWithTemplate() {
    HashMap<String,String> testTypeWithTemplate = new HashMap<>();
    testTypeWithTemplate.put("--email",emailTemplatePath);
    testTypeWithTemplate.put("--letter",letterTemplatePath);
    assertEquals(testTypeWithTemplate, testCMD.getTypeWithTemplate());
  }

  @Test
  void testEquals() throws ValidationErrorException {
    //itself
    assertTrue(testCMD.equals(testCMD));

    //null
    assertFalse(testCMD.equals(null));

    //diff type
    String diff = "s";
    assertFalse(testCMD.equals(diff));

    //diff obj same fields
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", csvPath, "--output-dir", outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertTrue(testCMD.equals(testCMD2));
  }

  @Test
  void testEqualsDiffFields() throws ValidationErrorException {
    //diff obj same fields
    // different email path
    args = new String[]{"--email", "--email-template", "email-templates.txt", "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", csvPath, "--output-dir",
        outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));

    // different letter path
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", "letter-templates.txt", "--csv-file", csvPath, "--output-dir",
        outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));

    // different csv path
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", "insurance-company-memberss.csv",
        "--output-dir",
        outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));

    // different output path
    args = new String[]{"--email", "--email-template", emailTemplatePath, "--letter",
        "--letter-template", letterTemplatePath, "--csv-file", csvPath, "--output-dir",
        "/Users/jerry/Desktop/Repos"};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));

    args = new String[]{"--letter", "--letter-template", letterTemplatePath, "--csv-file",
        csvPath, "--output-dir", outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));

    args = new String[]{"--email", "--email-template", emailTemplatePath, "--csv-file",
        csvPath, "--output-dir", outputDirectory};
    testCMD2 = new CommandLineParser(args);
    assertFalse(testCMD.equals(testCMD2));
  }

  @Test
  void testHashCode() {
    int expectedHash = Objects.hash(testCMD.getCsvFilePath(),
        testCMD.getOutputDirectory(),
        testCMD.getOutputType(),
        testCMD.getTypeWithTemplate());
    assertEquals(expectedHash, testCMD.hashCode());
  }

  @Test
  void testToString() {
    String res = "CommandLineParser{" +
        "csvFilePath='" + testCMD.getCsvFilePath() + '\'' +
        ", outputDirectory='" + testCMD.getOutputDirectory() + '\'' +
        ", outputType=" + testCMD.getOutputType() +
        ", typeWithTemplate=" + testCMD.getTypeWithTemplate() +
        '}';
    assertEquals(res, testCMD.toString());
  }
}