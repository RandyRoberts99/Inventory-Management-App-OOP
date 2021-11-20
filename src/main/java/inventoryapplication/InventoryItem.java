package inventoryapplication;

import java.text.DecimalFormat;

public class InventoryItem
{
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private String itemName, serialNumber;
    private double itemPrice;

    public InventoryItem(String itemName, double itemPrice, String serialNumber)
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
}