import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public abstract class ConfigurationReader {
  
  private File file;
  private Scanner fileScanner;

  ConfigurationReader(String fileName) {
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
    if(hasMoreLines()) {
      return fileScanner.nextLine();
    } 
      return null;
  }
 
 public abstract void load();
}
