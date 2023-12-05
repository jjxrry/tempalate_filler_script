package problem1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemplateFillerTest {

  private Map<String, String> testDrafts;
  private List<Map> testCsvList; // store container
  private HashSet<String> testTypes = new HashSet<>(); // types {'--email'}
  private HashSet<String> anotherTestTypes = new HashSet<>();
  private HashMap<String, String> testTypeWithTemplate = new HashMap<>();
  private HashMap<String, String> anotherTypeWithTemplate = new HashMap<>();
  private TemplateFiller testTemplateFiller; // filler
  private String filePath;
  private List<Map> anotherCSVList = new ArrayList<>();
  @BeforeEach
  void setUp() throws IOException {
    testDrafts = new TreeMap<>();
    testCsvList = new ArrayList<>();
    Map<String, String> map1 = new HashMap<>(); // information of user
    map1.put("first_name", "J");
    map1.put("last_name", "D");
    map1.put("company_name", "google");
    map1.put("address", "11th Ave");
    map1.put("city", "Seattle");
    map1.put("county", "K");
    map1.put("state", "Washington");
    map1.put("zip", "98109");
    map1.put("phone1", "1111");
    map1.put("phone2", "2222");
    map1.put("email", "j.d@123.com");
    map1.put("web", "www.jd.com");
    testCsvList.add(map1); // CSVList added user1's information

    Map<String, String> map2 = new HashMap<>();
    map2.put("first_name", "K");
    map2.put("last_name", "D");
    map2.put("company_name", "google");
    map2.put("address", "11th Ave");
    map2.put("city", "Seattle");
    map2.put("county", "K");
    map2.put("state", "Washington");
    map2.put("zip", "98109");
    map2.put("phone1", "1111");
    map2.put("phone2", "2222");
    map2.put("email", "k.d@123.com");
    map2.put("web", "www.kd.com");
    testCsvList.add(map2); // CSVList added user2's information

    anotherCSVList.add(map1);

    // template's model
    filePath = "src/main/java/problem1/resources/testForTemplateFiller.txt";

    testTypes.add("--email");
    testTypeWithTemplate.put("--email", filePath);
    testTemplateFiller = new TemplateFiller(testTypes, testTypeWithTemplate, testCsvList);

    anotherTestTypes.add("--letter");
    anotherTestTypes.add("--email");
    anotherTypeWithTemplate.put("--letter", filePath);
    anotherTypeWithTemplate.put("--email", filePath);
  }


  @Test
  void getTypes() {
    HashSet<String> expectedTypes = new HashSet<>();
    expectedTypes.add("--email");
    assertEquals(testTypes, testTemplateFiller.getTypes());
  }


  @Test
  void getTypeWithTemplate() {
    assertEquals(testTypeWithTemplate, testTemplateFiller.getTypeWithTemplate());
  }

  @Test
  void getDrafts() {
    Map<String, String> expectedDrafts = new TreeMap<>();
    expectedDrafts.put("J_D_email", "J D 11th Ave, Seattle\nK, Washington, 98109\n(j.d@123.com)\nDear J D\n");
    expectedDrafts.put("K_D_email", "K D 11th Ave, Seattle\nK, Washington, 98109\n(k.d@123.com)\nDear K D\n");

    assertEquals(expectedDrafts, testTemplateFiller.getDrafts());
  }

  @Test
  void testEquals() throws IOException {
    // test with itself
    assertTrue(testTemplateFiller.equals(testTemplateFiller));

    // test with null
    assertFalse(testTemplateFiller.equals(null));

    // test with another object
    assertFalse(testTemplateFiller.equals('a'));

    // same values
    TemplateFiller anotherFiller = new TemplateFiller(testTypes, testTypeWithTemplate, testCsvList);
    assertTrue(testTemplateFiller.equals(anotherFiller));

    // different template
    anotherFiller = new TemplateFiller(testTypes, anotherTypeWithTemplate, testCsvList);
    assertFalse(testTemplateFiller.equals(anotherFiller));

    // different csv list
    anotherFiller = new TemplateFiller(testTypes, testTypeWithTemplate, anotherCSVList);
    assertFalse(testTemplateFiller.equals(anotherFiller));

//    // different types
//    anotherFiller = new TemplateFiller(anotherTestTypes, testTypeWithTemplate, testCsvList);
//    assertFalse(testTemplateFiller.equals(anotherFiller));
//
//
  }

  @Test
  void testHashCode() {
    int hc = Objects.hash(testTemplateFiller.getTypes(), testTemplateFiller.getTypeWithTemplate(),
        testTemplateFiller.getDrafts());
    assertEquals(hc, testTemplateFiller.hashCode());
  }

  @Test
  void testToString() {
    String s = "TemplateFiller{" +
        "types=" + testTemplateFiller.getTypes() +
        ", typeWithTemplate=" + testTemplateFiller.getTypeWithTemplate() +
        ", drafts=" + testTemplateFiller.getDrafts() +
        '}';
    assertEquals(s, testTemplateFiller.toString());
  }


}