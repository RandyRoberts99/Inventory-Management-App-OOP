package inventoryapplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TSVHandler
{
    File listFile;
    FileWriter fileWriter;

    public void createTSVFile(InventoryItem[] invToParse, File inventoryFile) throws IOException {
        String parsedData = "";
        parsedData += "Name\tPrice (In $USD)\tSerialNumber\tNumber of Items: " + invToParse.length + "\n";
        for (int i = 0; i < invToParse.length; i++)
        {
            parsedData += invToParse[i].getItemName() + "\t" + invToParse[i].getItemPrice() + "\t" + invToParse[i].getSerialNumber() + "\n";
        }
        try
        {
            listFile = new File(inventoryFile.getAbsolutePath());
            fileWriter = new FileWriter(listFile, false);
            fileWriter.write(parsedData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            fileWriter.close();
        }
    }
}