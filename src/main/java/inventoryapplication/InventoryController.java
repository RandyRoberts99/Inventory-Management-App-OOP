package inventoryapplication;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class InventoryController {

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private MenuItem closeButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField firstNumberField;

    @FXML
    private MenuItem gitButton;

    @FXML
    private TextArea itemNameArea;

    @FXML
    private TableView<InventoryItem> inventoryViewer;

    @FXML
    private TextField letterField;

    @FXML
    private MenuItem loadButton;

    @FXML
    private TableColumn<InventoryItem, String> nameColumn;

    @FXML
    private MenuItem newButton;

    @FXML
    private TableColumn<InventoryItem, Double> priceColumn;

    @FXML
    private TextField priceField;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchKeyField;

    @FXML
    private TextField secondNumberField;

    @FXML
    private TableColumn<InventoryItem, String> serialNumberColumn;

    @FXML
    private TextField thirdNumberField;

    @FXML
    Alert fullAlert;

    @FXML
    Alert emptyAlert;

    @FXML
    Alert notFoundAlert;

    @FXML
    Alert selectionAlert;

    @FXML
    Alert confirmAlert;

    @FXML
    Alert filteredAlert;

    @FXML Alert saveAlert;

    private ItemVerifier itemVerifier;
    private ListParser listParser;

    private ObservableList<InventoryItem> inventoryList;
    private ObservableList<InventoryItem> filteredList;
    private InventoryItem emptyItem;

    private int editIndex;

    private int numItems;
    private static final int MAX_NUM_ITEMS = 1024;

    private boolean isFiltered = false;

    @FXML
    void initialize()
    {
        itemVerifier = new ItemVerifier();
        listParser = new ListParser();

        inventoryList = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();
        emptyItem = new InventoryItem("Empty", -1,"Empty");
        numItems = 1;

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        inventoryViewer.setItems(inventoryList);
        inventoryViewer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        inventoryList.add(emptyItem);

        fullAlert = new Alert(Alert.AlertType.ERROR);
        fullAlert.setTitle("Size Error");
        fullAlert.setContentText("Please remove an inventory item before adding a new one.");
        fullAlert.setResizable(false);

        emptyAlert = new Alert(Alert.AlertType.ERROR);
        emptyAlert.setTitle("Search Key Error");
        emptyAlert.setContentText("Please enter a search key.");
        emptyAlert.setResizable(false);

        notFoundAlert = new Alert(Alert.AlertType.ERROR);
        notFoundAlert.setTitle("No Items Error");
        notFoundAlert.setContentText("No items were found that match the search key.");
        notFoundAlert.setResizable(false);

        selectionAlert = new Alert(Alert.AlertType.ERROR);
        selectionAlert.setTitle("Selection Error");
        selectionAlert.setContentText("Please select an item to edit/delete.");
        selectionAlert.setResizable(false);

        confirmAlert = new Alert(Alert.AlertType.ERROR);
        confirmAlert.setTitle("Edit Field Error");
        confirmAlert.setContentText("Please ensure edited inputs are valid.");
        confirmAlert.setResizable(false);

        filteredAlert = new Alert(Alert.AlertType.ERROR);
        filteredAlert.setTitle("Filtered Error");
        filteredAlert.setContentText("Please de-select search mode to use other features.");
        filteredAlert.setResizable(false);

        saveAlert = new Alert(Alert.AlertType.ERROR);
        saveAlert.setTitle("Save Error");
        saveAlert.setContentText("Please select a valid directory and file name.");
        saveAlert.setResizable(false);
    }
    @FXML
    void addEmptyItem(ActionEvent event)
    {
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        inventoryViewer.requestFocus();
        if (numItems == MAX_NUM_ITEMS)
        {
            fullAlert.showAndWait();
            return;
        }
        inventoryList.add(emptyItem);
        numItems++;
    }
    @FXML
    void attemptCloseApp(ActionEvent event)
    {
        Platform.exit();
    }
    @FXML
    void clearItems(ActionEvent event)
    {
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        inventoryList.clear();
        editIndex = -1;
        numItems = 0;
    }
    @FXML
    void confirmItem(ActionEvent event)
    {
        if (editIndex == -1)
        {
            selectionAlert.showAndWait();
            return;
        }
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        String serialNumber = letterField.getText().toUpperCase() + "-" + firstNumberField.getText() + "-" + secondNumberField.getText() + "-" + thirdNumberField.getText();

        // check serial number, price, and name
        boolean isValidSerialNumber = itemVerifier.validateSerialNumber(serialNumber);
        boolean isUniqueSerialNumber = false;
        if (isValidSerialNumber)
        {
            isUniqueSerialNumber = checkDuplicateSerialNumber(serialNumber);
        }
        boolean isValidPrice = itemVerifier.validateDollarAmount(priceField.getText());
        boolean isValidName = itemVerifier.validateName(itemNameArea.getText());

        if (isValidSerialNumber && isUniqueSerialNumber && isValidPrice && isValidName)
        {
            InventoryItem newItem = new InventoryItem(itemNameArea.getText(), Double.parseDouble(priceField.getText()), serialNumber);
            inventoryList.set(editIndex, newItem);
        }
        else
        {
            confirmAlert.showAndWait();
        }
    }
    @FXML
    void createNewList(ActionEvent event)
    {
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        // clear the current list, add in a new empty item.
        inventoryList.clear();
        inventoryList.add(emptyItem);
        editIndex = -1;
        numItems = 1;
    }
    @FXML
    void deleteItems(ActionEvent event)
    {
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        int deleteIndex = inventoryViewer.getSelectionModel().getSelectedIndex();
        if (deleteIndex == -1)
        {
            selectionAlert.showAndWait();
            return;
        }
        inventoryList.remove(deleteIndex);
        numItems--;
    }
    @FXML
    void editItem(ActionEvent event)
    {
        if (isFiltered)
        {
            filteredAlert.showAndWait();
            return;
        }
        editIndex = inventoryViewer.getSelectionModel().getSelectedIndex();
        if (editIndex == -1)
        {
            selectionAlert.showAndWait();
        }
        else
        {
            InventoryItem editItem = inventoryList.get(editIndex);

            itemNameArea.setText(editItem.getItemName());
            priceField.setText(String.valueOf(editItem.getItemPrice()));
            String serialNumber = editItem.getSerialNumber();
            if (serialNumber.equalsIgnoreCase("Empty"))
            {
                // default value
                letterField.setText("A");
                firstNumberField.setText("000");
                secondNumberField.setText("000");
                thirdNumberField.setText("000");
            }
            else
            {
                String[] parsedSerialNumber = editItem.getSerialNumber().split("-");

                letterField.setText(parsedSerialNumber[0]);
                firstNumberField.setText(parsedSerialNumber[1]);
                secondNumberField.setText(parsedSerialNumber[2]);
                thirdNumberField.setText(parsedSerialNumber[3]);
            }
        }
    }
    @FXML
    void loadList(ActionEvent event)
    {

    }
    @FXML
    void openGithubLink() throws IOException
    {
        String websiteLink = "https://github.com/RandyRoberts99/roberts-app2";
        try
        {
            Desktop.getDesktop().browse(new URI(websiteLink));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void saveList(ActionEvent event) throws IOException {
        FileChooser listSaver = new FileChooser();
        listSaver.setInitialDirectory(new File(System.getProperty("user.dir")));

        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT File", "*.txt");
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("HTML File", "*.html");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON File", "*.json");

        listSaver.getExtensionFilters().add(txtFilter);
        listSaver.getExtensionFilters().add(htmlFilter);
        listSaver.getExtensionFilters().add(jsonFilter);

        Stage fileStage = new Stage();
        File listSaveFile = listSaver.showSaveDialog(fileStage);

        if (listSaveFile != null)
        {
            List<InventoryItem> invArrList = inventoryList.stream().toList();
            InventoryItem[] invToParse = invArrList.toArray(new InventoryItem[invArrList.size()]);

            listParser.parseAndSaveFile(invToParse, listSaveFile);
        }
        else
        {
            saveAlert.showAndWait();
        }
    }
    // search the current list of items for items that match a keyword
    @FXML
    void searchItems(ActionEvent event)
    {
        // if the list is already filtered, un-filter it and reset/return
        if (isFiltered)
        {
            inventoryViewer.setItems(inventoryList);
            filteredList.clear();
            isFiltered = false;
            searchButton.setText("Search");
        }
        // if the search key is empty, alert the user and return
        else if (searchKeyField.getText().equalsIgnoreCase("") || searchKeyField.getText().equalsIgnoreCase(null))
        {
            emptyAlert.showAndWait();
        }
        else
        {
            String searchKey = searchKeyField.getText();

            // check each item in the current list for matches, and add matches to the filtered list
            for (int i = 0; i < numItems; i++)
            {
                boolean hasKey = containsKey(inventoryList.get(i), searchKey);
                if (hasKey)
                {
                    filteredList.add(inventoryList.get(i));
                }
            }
            // if none of the items in the current list match, alert the user and return. otherwise,
            // display all items that match the search key
            if (filteredList.isEmpty())
            {
                notFoundAlert.showAndWait();
                filteredList.clear();
            }
            else
            {
                inventoryViewer.setItems(filteredList);
                isFiltered = true;
                searchButton.setText("Undo Search");
            }
        }
    }

    // check an item to see if it contains the search key
    private boolean containsKey(InventoryItem item, String searchKey)
    {
        String itemSerialNumber = item.getSerialNumber();
        String itemPriceStr = String.valueOf(item.getItemPrice());
        String itemName = item.getItemName();

        return (itemSerialNumber.toLowerCase().contains(searchKey.toLowerCase()) || itemPriceStr.toLowerCase().contains(searchKey.toLowerCase()) || itemName.toLowerCase().contains(searchKey.toLowerCase()));
    }
    private boolean checkDuplicateSerialNumber(String serialNumber)
    {
        for (int i = 0; i < numItems; i++)
        {
            if (inventoryList.get(i).getSerialNumber().equalsIgnoreCase(serialNumber) && i != editIndex)
            {
                return false;
            }
        }
        return true;
    }
}
