package problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVReaderTest {

  private ArrayList<Map> testCSVList = new ArrayList<>();;
  private CSVReader testReader;
  private CSVReader testReader2;
  private String path = "src/main/java/problem1/resources/insurance-company-members.csv";


  @BeforeEach
  void setUp() throws EmptyCSVException, IOException {
    assertThrows(FileNotFoundException.class, ()->{
      testReader = new CSVReader(" ");
    });
    assertThrows(EmptyCSVException.class, ()->{
      testReader = new CSVReader("src/main/java/problem1/resources/empty.csv");
    });

    testReader = new CSVReader(path);
  }

  @Test
  void getCsvList() throws IOException {

    Map<Integer, String> headers = new HashMap<>();
    BufferedReader inputFile = new BufferedReader(new FileReader(path));
    String line = "";
    line = inputFile.readLine();
    line = line.substring(1, line.length() - 1);
    String[] columns = line.split("\"*,*\"");
    for (int i = 0; i < columns.length; i++) {
      headers.put(i, columns[i]);
    }

    while ((line = inputFile.readLine()) != null) {
      Map<String, String> oneRow = new HashMap<>();
      line = line.substring(1, line.length() - 1);  //remove the extra space
      String[] s = line.split("\"*,*\"");
      for (int i = 0; i < columns.length; i++) {
        oneRow.put(headers.get(i), s[i]);   //<header, header_info>
      }
      testCSVList.add(oneRow);
    }
    assertEquals(testCSVList, testReader.getCsvList());
  }

  @Test
  void testEquals() throws EmptyCSVException, IOException {
    //compare with itself
    assertTrue(testReader.equals(testReader));
    //compare with null
    assertFalse(testReader.equals(null));
    //compare with other object
    assertFalse(testReader.equals("test"));
    //compare with another Reader same value
    CSVReader testReader2 = new CSVReader(path);
    assertTrue(testReader.equals(testReader2));
    //compare with another Reader different value, another.csv is only used for testing
    testReader2 = new CSVReader("src/main/java/problem1/resources/another.csv");
    assertFalse(testReader.equals(testReader2));
  }

  @Test
  void testHashCode() {
    Integer expectedHashCode = Objects.hash(testReader.getCsvList());
    assertEquals(expectedHashCode, testReader.hashCode());
  }

  @Test
  void testToString() {
    String expectedString = "CSVReader{" +
        "csvList=" + testReader.getCsvList() +
        '}';
    assertEquals(expectedString, testReader.toString());
  }

}