/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSVHandler
{
    File listFile;
    FileWriter fileWriter;

    public void createTSVFile(InventoryItem[] invToParse, File inventoryFile) throws IOException
    {
        String parsedData = "";
        parsedData += "Name\tPrice (In $USD)\tSerialNumber\tNumber of Items: " + invToParse.length + "\n";

        for (int i = 0; i < invToParse.length; i++)
        {
            parsedData += invToParse[i].getItemName() + "\t" + invToParse[i].getItemPrice() + "\t" + invToParse[i].getSerialNumber() + "\n";
        }
        listFile = new File(inventoryFile.getAbsolutePath());
        try (FileWriter tsvWriter = new FileWriter(listFile, false))
        {
            tsvWriter.write(parsedData);
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
    public List<InventoryItem> createListFromTSV(File inFile) throws FileNotFoundException
    {
        List<InventoryItem> newInventoryList = new ArrayList<>();
        try (Scanner reader = new Scanner(inFile))
        {
            String[] intSubStr = reader.nextLine().split(" ");

            int numItems = Integer.parseInt(intSubStr[intSubStr.length - 1]);

            for (int i = 0; i < numItems; i++)
            {
                InventoryItem newItem = new InventoryItem("didntwork", -2.01, "didntwork");
                String[] items = reader.nextLine().split("\t");

                newItem.setItemName(items[0]);
                newItem.setItemPrice(Double.parseDouble(items[1]));
                newItem.setSerialNumber(items[2]);

                newInventoryList.add(newItem);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return newInventoryList;
    }
}