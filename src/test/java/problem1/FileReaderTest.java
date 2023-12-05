package problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {
  private String testFileString;
  private FileReader fileReader;
  @BeforeEach
  void setUp() throws IOException {
    assertThrows(FileNotFoundException.class, ()->{
      fileReader = new FileReader(" ");
    });
    testFileString = "src/main/java/problem1/resources/email-template.txt";
    fileReader = new FileReader(testFileString);
  }

  @Test
  void testReadFile() throws IOException {
    // Ensure that the getFileString method returns the expected content
    String expectedContent = "From: insuranceCompany@ic.com\n"
        + "To: [[email]]\n"
        + "Subject: Insurance company – information about recent data breach\n"
        + "Dear [[first_name]] [[last_name]],\n"
        + "As you may have heard or read, last month we learned that criminals forced their way into our systems, \n"
        + "and stole information about our customers. Late last week, as part of our ongoing investigation, \n"
        + "we learned that the taken information includes names, mailing addresses, phone numbers or email addresses.\n"
        + " \n"
        + "I am writing to make you aware that your name, mailing address, phone number or email address may have been \n"
        + "taken during the intrusion. \n"
        + "\n"
        + "I am truly sorry this incident occurred, and I sincerely regret any inconvenience it may cause you. \n"
        + "\n"
        + "Because we value you as a customer, and because your trust is important to us, our company is offering you one \n"
        + "year of free credit monitoring through Experian’s ProtectMyID product, which includes identity theft insurance \n"
        + "where available. You will receive more information about this offer via regular mail.\n"
        + "\n"
        + "You can find additional information and FAQs about this incident at our website. If you have further questions, \n"
        + "you may call us at 866-852-8680. \n"
        + "\n"
        + "Thank you for your patience and your loyalty. \n"
        + "Sincerely,\n"
        + "Insurance Company CEO"
        + "\n"
        + "\n"
        ;
    assertEquals(expectedContent, fileReader.getFileString());
  }

  @Test
  void testEquals() throws IOException {
    // test with itself
    assertTrue(fileReader.equals(fileReader));

    // test with null
    assertFalse(fileReader.equals(null));

    // test with another object
    assertFalse(fileReader.equals(1));

    // test with same object
    FileReader anotherReader = new FileReader(testFileString);
    assertTrue(fileReader.equals(anotherReader));
  }

  @Test
  void testHashCode(){
    int hc = Objects.hash(fileReader.getFileString());
    assertEquals(hc, fileReader.hashCode());
  }

  @Test
  void testToString() {
    String s = "FileReader{" +
        "fileString='" + fileReader.getFileString() + '\'' +
        '}';
    assertEquals(s, fileReader.toString());
  }
}