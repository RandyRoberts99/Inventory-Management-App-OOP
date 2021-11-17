package inventoryapplication;

public class InventoryItem
{
    private String itemName, serialNumber;
    private double itemPrice;

    public InventoryItem(String itemName, double itemPrice, String serialNumber)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.serialNumber = serialNumber;
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