package problem1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * This program can accept the following command line arguments.
 */
public class CommandLineParser {

  // Paths and flags for command line arguments
  private String csvFilePath;
  private String outputDirectory;
  private HashSet<String> outputType = new HashSet<>();  //{"--email"}
  private HashMap<String,String> typeWithTemplate = new HashMap<>();  //{"--email"="emailTemplatePath"}

  //Constants
  private static final String EMAIL = "--email";
  private static final String EMAIL_TEMPLATE = "--email-template";
  private static final String LETTER = "--letter";
  private static final String LETTER_TEMPLATE = "--letter-template";
  private static final String CSV_FILE = "--csv-file";
  private static final String OUTPUT_DIR = "--output-dir";
  private static final String TXT = ".txt";
  private static final String CSV = ".csv";
  private static final int SPACE_TWO = 2;

  /**
   * Constructs a CommandLineParser object and initiates argument parsing and validation.
   * @param args The command line arguments to be parsed.
   * @throws ValidationErrorException If there are validation errors in the command line arguments.
   */
  public CommandLineParser(String[] args) throws ValidationErrorException {
    this.runParser(args);
  }

  /**
   * Initiates the argument parsing and validation process.
   * @param args The command line arguments to be parsed.
   * @throws ValidationErrorException If there are validation errors in the command line arguments.
   */
  private void runParser(String[] args) throws ValidationErrorException {
    this.parseArgs(args);
    this.validateArgs();
  }

  /**
   * Parses the command line arguments and sets corresponding variables.
   * @param args The command line arguments to be parsed.
   * @throws ValidationErrorException If there are validation errors in the command line arguments.
   */
  private void parseArgs(String[] args) throws ValidationErrorException {
    //still 0
    for(int i = 0; i < args.length; ++i){
      switch (args[i]) {
        // Generate email/letter messages. If this option is provided,
        // then --email/letter template must also be provided.
        case EMAIL, LETTER:
          this.outputType.add(args[i]);
          break;

        // A filename for the email/letter template.
        case EMAIL_TEMPLATE, LETTER_TEMPLATE:
          if((i+1)<args.length){
            String templatePath = args[++i];

            if(templatePath == null){
              throw new ValidationErrorException("Error: no template was given.");
            }

            if(!templatePath.endsWith(TXT)){
              throw new ValidationErrorException("Error: provided template is not a .txt file.");
            }

            this.typeWithTemplate.put(args[i - SPACE_TWO], templatePath);
          }
          break;

        // The CSV file to process. This option is required.
        case CSV_FILE:
          if((i+1)<args.length) {
            this.csvFilePath = args[++i];
          }
          break;

        // The folder to store all generated files. This option is required.
        case OUTPUT_DIR:
          if((i+1)<args.length) {
            this.outputDirectory = args[++i];
          }
          break;
      }
    }
  }

  /**
   * Validates the parsed command line arguments.
   * @throws ValidationErrorException If there are validation errors in the command line arguments.
   */
  private void validateArgs() throws ValidationErrorException {
    //check if CSV path exists
    if(csvFilePath == null){
      throw new ValidationErrorException("Error: no --csv-file was given.");
    }

    //check if CSV is a CSV file
    if(!csvFilePath.endsWith(CSV)){
      throw new ValidationErrorException("Error: provided --csv-file is not a .csv file.");
    }

    //check if output dir path exists
    if(outputDirectory == null){
      throw new ValidationErrorException("Error: no --output-dir was given.");
    }
  }

  /**
   * Gets the CSV file path from the command line arguments.
   * @return The CSV file path, as a String.
   */
  public String getCsvFilePath() {
    return csvFilePath;
  }

  /**
   * Gets the output directory path from the command line arguments.
   * @return The output directory path, as a String.
   */
  public String getOutputDirectory() {
    return outputDirectory;
  }

  /**
   * @return all types of the outputs
   */
  public HashSet<String> getOutputType() {
    return outputType;
  }

  /**
   * @return a Map with type and its template
   */
  public HashMap<String, String> getTypeWithTemplate() {
    return typeWithTemplate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandLineParser that = (CommandLineParser) o;
    return Objects.equals(csvFilePath, that.csvFilePath) && Objects.equals(
        outputDirectory, that.outputDirectory) && Objects.equals(outputType,
        that.outputType) && Objects.equals(typeWithTemplate, that.typeWithTemplate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csvFilePath, outputDirectory, outputType, typeWithTemplate);
  }

  @Override
  public String toString() {
    return "CommandLineParser{" +
        "csvFilePath='" + csvFilePath + '\'' +
        ", outputDirectory='" + outputDirectory + '\'' +
        ", outputType=" + outputType +
        ", typeWithTemplate=" + typeWithTemplate +
        '}';
  }
}
