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