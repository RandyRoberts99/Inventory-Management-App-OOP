/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class JSONHandler
{
    private ObjectMapper objectMapper = new ObjectMapper();

    public void createJSONFile(InventoryItem[] invToParse, File inventoryFile) throws IOException
    {
        String invParsed = objectMapper.writeValueAsString(invToParse);

        try (FileWriter fileWriter = new FileWriter(inventoryFile.getAbsolutePath()))
        {
            fileWriter.write(invParsed);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public List<InventoryItem> createListFromJSON(File inFile) throws IOException
    {
        return Arrays.asList(objectMapper.readValue(inFile, InventoryItem[].class));
    }
}