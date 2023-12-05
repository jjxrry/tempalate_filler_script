package problem1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * class CSVReader, read in data from a csv file
 * and store the data into a List
 */
public class CSVReader {

  private List<Map> csvList = new ArrayList<>();  // info in each row

  //constants
  private static final String REGEX = "\"*,*\"";
  private static final int ONE = 1;

  /**
   * Constructor - read in a CSV file
   * @param csvPath - csv file path, as String
   * @throws EmptyCSVException if csv file is empty
   * @throws IOException if input processes have error
   */
  public CSVReader(String csvPath) throws EmptyCSVException, IOException {
    this.readCSV(csvPath);
  }


  /**
   * Read in CSV file
   * @param csvPath - csv file path
   */
  private void readCSV(String csvPath) throws IOException, EmptyCSVException {
    Map<Integer, String> headers = new HashMap<>();  //headers
    BufferedReader inputFile = null;
    try {inputFile = new BufferedReader(new FileReader(csvPath));
      //Get headers
      String line = "";
      line = inputFile.readLine();
      if(line == null){
        throw new EmptyCSVException("The CSV file is empty!");
      }
      line = line.substring(ONE, line.length() - ONE);  //remove the extra space
      String[] columns = this.lineSeparator(line);  //get headers in a String
      for(int i = 0; i < columns.length; i++){
        headers.put(i, columns[i]);
      }

      //read data from the second row
      while ((line = inputFile.readLine()) != null) {
        Map<String, String> oneRow = new HashMap<>();
        line = line.substring(ONE, line.length() - ONE);  //remove the extra space
        String[] s = this.lineSeparator(line);
        for (int i = 0; i < columns.length; i++) {
          oneRow.put(headers.get(i), s[i]);   //<header, header_info>
        }
        this.csvList.add(oneRow);
      }
    //catchers
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      throw new FileNotFoundException();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      throw new IOException();
    } catch (EmptyCSVException e) {
      throw new EmptyCSVException("The CSV file is empty!");
    } finally {
      if (inputFile != null) {
        try {
          inputFile.close();
        } catch (IOException e) {
          System.out.println("Failed to close input stream");
        }
      }
    }
  }

  /**
   * Split a String by regex
   * @param line - a String
   * @return an array of Strings
   */
  private String[] lineSeparator(String line){
    return line.split(REGEX);
  }

  /**
   * Getter for csvList
   * @return csvList contains all the csv info
   */
  public List<Map> getCsvList() {
    return csvList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CSVReader csvReader = (CSVReader) o;
    return Objects.equals(csvList, csvReader.csvList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csvList);
  }

  @Override
  public String toString() {
    return "CSVReader{" +
        "csvList=" + csvList +
        '}';
  }
}


