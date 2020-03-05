
package appstore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class File {
    
    private String fileData;

    public File(String fileName) throws IOException {
      
        fileData = "";

        try {
           FileReader inStream = new FileReader(fileName);

            // Reading
            try (BufferedReader reader = new BufferedReader(inStream)) {
                
                String line = "";
                while (true) {
                    line = reader.readLine(); 
                    if (line == null) {
                        break;
                    }
                    fileData += line + "\n";
                }
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file " + fileName + ".");
            System.out.println(e);
        }
    }

    public String getData() {
        return fileData;
    }
}
