package filereaders;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public abstract class ConfigurationReader {

  private File file;
  private Scanner fileScanner;

  public ConfigurationReader(String fileName) {
    file = new File(fileName);
    try {
      fileScanner = new Scanner(file);
    } catch(FileNotFoundException error) {
      error.printStackTrace();
    }
  }

  boolean hasMoreLines(){
    return fileScanner.hasNextLine();
  }

  String nextLine() {

    if(hasMoreLines()){
      String currentLine = fileScanner.nextLine();
      if(currentLine.contains("#") == false) {
        return currentLine;
      }
    }
    return "";
  }

 public abstract void load();
}
