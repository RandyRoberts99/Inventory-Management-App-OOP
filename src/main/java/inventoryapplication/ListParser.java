/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

import java.io.File;
import java.io.IOException;

// responsible for changing objects to the appropriate files, using helper classes
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
            jsonHandler.createJSONFile(invToParse, inventoryFile);
        }
    }
}