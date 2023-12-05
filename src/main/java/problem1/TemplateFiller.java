package problem1;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class TemplateFiller - fill out template using given List<Map> data from the CSV file
 * and save all the filename grouped with its filled template into a Map
 */
public class TemplateFiller {

  private HashSet<String> types;  //{"--email", --"letter"}
  private HashMap<String,String> typeWithTemplate = new HashMap<>();
  private Map<String, String> drafts = new TreeMap<>();
  private static final String SPACE = "--"; // represent --
  private static final String FIRST_NAME = "first_name";
  private static final String LAST_NAME = "last_name";
  private static final String PATTERN = "\\[\\[(.+?)]]";
  private static final Integer ONE = 1;

  /**
   * Constructor
   * @param outputType - A set contains all the output types
   * @param typeWithTemplate - a map contains types and their associated template
   * @param csvList - input information as List<Map>
   * @throws IOException -catch input/output exceptions
   */
  public TemplateFiller(HashSet<String> outputType, HashMap<String, String> typeWithTemplate, List<Map> csvList) throws IOException {
    this.types = outputType;
    this.typeWithTemplate = typeWithTemplate;
    this.fillTemplate(csvList);
  }

  /**
   * Fill the template using given data
   * @param csvList - given data as List<Map>
   * @throws IOException -catch input/output exceptions
   */
  private void fillTemplate(List<Map> csvList) throws IOException {
    //String templatePath = "";
    for(String type: this.types){
      String templatePath = typeWithTemplate.get(type);
      type = type.replace(SPACE, "");

      FileReader fileReader = new FileReader(templatePath);
      String template = fileReader.getFileString();

      for(int i = 0; i < csvList.size(); i++){
        this.fillInfo(type, template, csvList.get(i));
      }
    }
  }

  /**
   * Helper method for fillTemplate - find [[]] and replaced the content with given info
   * @param type - output type, as String
   * @param template - template by type, as String
   * @param oneRow - one row of the information, as Map
   */
  private void fillInfo(String type, String template, Map<String, String> oneRow) {
    Pattern p = Pattern.compile(PATTERN);
    Matcher m = p.matcher(template);

    StringBuilder draft = new StringBuilder();

    while(m.find()){
      String replaceString = oneRow.get(m.group(ONE));
      if(replaceString != null){
        m.appendReplacement(draft, replaceString);
      }
    }

    m.appendTail(draft);
    String key = oneRow.get(FIRST_NAME)+ "_" + oneRow.get(LAST_NAME) + "_" + type;

    //Save filename and its contents
    this.drafts.put(key,draft.toString());
  }

  /**
   * Return types of it.
   * @return types of it, as HashSet.
   */
  public HashSet<String> getTypes() {
    return types;
  }

  /**
   * Return type and template path
   * @return type and template path, as HashMap
   */
  public HashMap<String, String> getTypeWithTemplate() {
    return typeWithTemplate;
  }

  /**
   * Return drafts.
   * @return drafts, as Map
   */
  public Map<String, String> getDrafts() {
    return drafts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateFiller that = (TemplateFiller) o;
    return Objects.equals(types, that.types) && Objects.equals(typeWithTemplate,
        that.typeWithTemplate) && Objects.equals(drafts, that.drafts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(types, typeWithTemplate, drafts);
  }

  @Override
  public String toString() {
    return "TemplateFiller{" +
        "types=" + types +
        ", typeWithTemplate=" + typeWithTemplate +
        ", drafts=" + drafts +
        '}';
  }
}
