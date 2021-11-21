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

public class HTMLHandler
{
    File listFile;
    FileWriter fileWriter;

    public void createHTMLFile(InventoryItem[] invToParse, File inventoryFile) throws IOException
    {
        String parsedData = "";
        parsedData += "<html><!--Number of Items: " + invToParse.length + " -->\n<table>\n\t<tr>\n\t\t<td>Name</td>\n\t\t<td>Price</td>\n\t\t<td>SerialNumber</td>\n\t</tr>\n";

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
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public List<InventoryItem> createListFromHTML(File inFile) throws FileNotFoundException
    {
        Scanner reader = new Scanner(inFile);
        List<InventoryItem> newInventoryList = new ArrayList<>();

        String[] itemNumStr = reader.nextLine().split(" ");
        String numItemStr = itemNumStr[3];
        int numItems = Integer.parseInt(numItemStr);

        for (int i = 0; i < 6; i++)
        {
            reader.nextLine();
        }
        for (int i = 0; i < numItems; i++)
        {
            InventoryItem newItem = new InventoryItem("", -2.0, "");
            reader.nextLine();

            String[] nameDataArr = reader.nextLine().split("<td>");
            String nameData = nameDataArr[1].substring(0, nameDataArr[1].length() - 5);
            newItem.setItemName(nameData);

            String[] priceDataArr = reader.nextLine().split("<td>");
            double priceData = Double.parseDouble(priceDataArr[1].substring(0, priceDataArr[1].length() - 5));
            newItem.setItemPrice(priceData);

            String[] serialNumberArr = reader.nextLine().split("<td>");
            String serialNumberData = serialNumberArr[1].substring(0, serialNumberArr[1].length() - 5);
            newItem.setSerialNumber(serialNumberData);

            newInventoryList.add(newItem);
            reader.nextLine();
        }
        reader.close();
        return newInventoryList;
    }
}