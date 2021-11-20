package inventoryapplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HTMLHandler
{
    File listFile;
    FileWriter fileWriter;

    public void createHTMLFile(InventoryItem[] invToParse, File inventoryFile) throws IOException {
        String parsedData = "";
        parsedData += "<html> <!--Number of Items: " + invToParse.length + "-->\n<table>\n\t<tr>\n\t\t<td>Name</td>\n\t\t<td>Price</td>\n\t\t<td>SerialNumber</td>\n\t</tr>\n";

        for (int i = 0; i < invToParse.length; i++)
        {
            parsedData += "\t<tr>\n\t\t<td>" + invToParse[i].getItemName() + "</td>\n\t\t<td>" + invToParse[i].getItemPrice() + "</td>\n\t\t<td>" + invToParse[i].getSerialNumber() + "</td>\n\t</tr>\n";
        }
        parsedData += "</table>\n</html>";
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