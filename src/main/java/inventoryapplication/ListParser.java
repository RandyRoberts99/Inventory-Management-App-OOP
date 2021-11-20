package inventoryapplication;

import java.io.File;
import java.io.IOException;

public class ListParser
{
    TSVHandler tsvHandler = new TSVHandler();
    HTMLHandler htmlHandler = new HTMLHandler();
    JSONHandler jsonHandler = new JSONHandler();

    public void parseAndSaveFile(InventoryItem[] invToParse, File inventoryFile) throws IOException {
        String fileExtension = inventoryFile.getAbsolutePath().substring(inventoryFile.getAbsolutePath().length() - 5);

        if (fileExtension.toLowerCase().contains("txt"))
        {
            tsvHandler.createTSVFile(invToParse, inventoryFile);
        }
        else if (fileExtension.toLowerCase().contains("html"))
        {
            htmlHandler.createHTMLFile(invToParse, inventoryFile);
        }
        else
        {

        }
    }
}