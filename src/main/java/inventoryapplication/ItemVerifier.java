/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

public class ItemVerifier
{
    public boolean validateName(String name)
    {
        return (name.length() > 1 && name.length() < 257);
    }
    public boolean validateDollarAmount(String userDollarAmount)
    {
        try
        {
            double dollarAmount = Double.parseDouble(userDollarAmount);
            return (dollarAmount > 0);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public boolean validateSerialNumber(String serialNumber)
    {
        String[] serialNumberSplit = serialNumber.split("-");

        boolean isValidFirstField = validateLetterField(serialNumberSplit[0]);
        boolean isValidNumberField = validateNumberField(serialNumberSplit[1], serialNumberSplit[2], serialNumberSplit[3]);

        return (isValidFirstField && isValidNumberField);
    }
    private boolean validateLetterField(String letterField)
    {
        if (letterField.length() == 1)
        {
            char letter = letterField.charAt(0);
            return Character.isLetter(letter);
        }
        else
        {
            return false;
        }
    }
    private boolean validateNumberField(String numField1, String numField2, String numField3)
    {
        if (numField1.length() == 3 && numField2.length() == 3 && numField3.length() == 3)
        {
            try
            {
                Integer.parseInt(numField1 + numField2 + numField3);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}