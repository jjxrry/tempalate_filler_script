package problem1;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Class OutputWriter - generate email or letter using filled template
 * and save them into directory
 */
public class OutputWriter {
  private static final String TXT = ".txt";

  /**
   * Constructor - generate email or letter using filled template and save
   * @param dirPath - directory path
   * @param file - a map contains name and drafted template needs to be converted
   * @throws IOException if the input process has error
   */

  public OutputWriter(String dirPath, Map<String, String> file) throws IOException {
    this.writer(dirPath, file);
  }

  /**
   * Generate files one by one and save in the directory
   * @param dirPath - directory path
   * @param file - file stores all the filled templates and keys
   * @throws IOException - Input Output Exception
   */
  private void writer(String dirPath, Map<String, String> file) throws IOException {
    createFileDir(dirPath);
    BufferedWriter output = null;
    //generate file for each person one by one
    for (String key : file.keySet()) {
      try {
        output = new BufferedWriter(new FileWriter(dirPath + key + TXT));
        output.write(file.get(key));
      } catch (IOException e) {
        throw new IOException(e);
      } finally {
        if (output != null) {
          try {
            output.close();
          } catch (IOException e) {
            System.out.println("Something went wrong! : " + e.getMessage());
          }
        }
      }
    }
  }

  /**
   * Use the Java File class to create directory if they don't already exist.
   * @param dirPath - directory path
   */
  private void createFileDir(String dirPath) {
    File dir = new File(dirPath);
    if(!dir.exists()){
      dir.mkdir();
    }
  }
}
