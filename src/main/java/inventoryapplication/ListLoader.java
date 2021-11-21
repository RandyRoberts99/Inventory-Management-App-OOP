/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListLoader
{
    TSVHandler tsvHandler = new TSVHandler();
    HTMLHandler htmlHandler = new HTMLHandler();
    JSONHandler jsonHandler = new JSONHandler();

    // checks to see which format to work with, and uses the helper classes to load a new object
    public List<InventoryItem> loadListFromFile(File inventoryFile) throws IOException
    {
        String fileExtension = inventoryFile.getAbsolutePath().substring(inventoryFile.getAbsolutePath().length() - 5);

        if (fileExtension.toLowerCase().contains("txt"))
        {
            return tsvHandler.createListFromTSV(inventoryFile);
        }
        else if (fileExtension.toLowerCase().contains("html"))
        {
            return htmlHandler.createListFromHTML(inventoryFile);
        }
        else
        {
            return jsonHandler.createListFromJSON(inventoryFile);
        }
    }
}