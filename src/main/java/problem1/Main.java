package problem1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Main method encapsulating the main application logic with calls to helper functions to read,
 * validate, and write files through scripting
 */
public class Main {


  /**
   * @param args - input for Command Line arguments
   * @throws ValidationErrorException - catch validation exceptions for commands and general errors
   * @throws IOException - catch I/O stream exceptions for file reading and writing
   * @throws EmptyCSVException - catch CSV empty exceptions
   */
  public static void main(String[] args)
      throws ValidationErrorException, IOException, EmptyCSVException {
    try {
      CommandLineParser cmdParser = new CommandLineParser(args);  // CommandLineParser

      CSVReader csvReader = new CSVReader(cmdParser.getCsvFilePath());  // CSV reader
      List<Map> csvList= csvReader.getCsvList();  // get CSV data into a List

      TemplateFiller filler = new TemplateFiller(cmdParser.getOutputType(),
          cmdParser.getTypeWithTemplate(), csvList);  //template filler
      Map<String, String> drafts = filler.getDrafts();

      OutputWriter writer = new OutputWriter(cmdParser.getOutputDirectory(), drafts);

    } catch (ValidationErrorException e) {
      throw new ValidationErrorException("Error: " + e);
    }
  }
}
