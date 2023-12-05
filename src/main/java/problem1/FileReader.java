package problem1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * class FileReader - save all file info into a String
 */
public class FileReader {

  //String stores all the data
  private String fileString = "";

  /**
   * Constructor - read file and save needed information
   * @param filePath - file path
   * @throws IOException if the input process has error
   */
  public FileReader(String filePath) throws IOException {
    this.readFile(filePath);
  }

  /**
   * Read the file and save the info into a String
   * @param filePath - file path
   */
  private void readFile(String filePath) throws IOException {
    BufferedReader inputFile = null;
    try{ inputFile = new BufferedReader(new java.io.FileReader(filePath));
      String line;
      while((line = inputFile.readLine()) != null){  //read line one by one and save into a String
        this.fileString = fileString + line + "\n";
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      throw new FileNotFoundException();
    } catch (IOException ioe) {
      System.out.println("Something went wrong! : " + ioe.getMessage());
      throw new IOException();
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
   * Getter for File String
   * @return fileString - String contains all info in the file
   */
  public String getFileString() {
    return fileString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileReader that = (FileReader) o;
    return Objects.equals(fileString, that.fileString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileString);
  }

  @Override
  public String toString() {
    return "FileReader{" +
        "fileString='" + fileString + '\'' +
        '}';
  }
}