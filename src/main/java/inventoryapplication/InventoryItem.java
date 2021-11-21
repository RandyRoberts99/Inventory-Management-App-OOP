package inventoryapplication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;

public class InventoryItem
{
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private String itemName;
    private String serialNumber;
    private double itemPrice;

    @JsonCreator
    public InventoryItem(@JsonProperty("itemName") String itemName, @JsonProperty("itemPrice") double itemPrice, @JsonProperty("serialNumber") String serialNumber)
    {
        this.itemName = itemName;
        this.itemPrice = Double.parseDouble(decimalFormat.format(itemPrice));
        this.serialNumber = serialNumber;
    }
    public String getItemName()
    {
        return itemName;
    }
    public String getSerialNumber()
    {
        return serialNumber;
    }
    public double getItemPrice()
    {
        return itemPrice;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    public void setItemPrice(double itemPrice)
    {
        this.itemPrice = itemPrice;
    }
}