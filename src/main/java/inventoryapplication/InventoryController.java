package inventoryapplication;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        inventoryList = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();
        emptyItem = new InventoryItem("Empty", -1, "Empty");
        numItems = 1;

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        inventoryViewer.setItems(inventoryList);
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
    }
    @FXML
    void addEmptyItem(ActionEvent event)
    {
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
        inventoryList.clear();
        numItems = 0;
    }
    @FXML
    void confirmItem(ActionEvent event)
    {

    }
    @FXML
    void createNewList(ActionEvent event)
    {
        // clear the current list, add in a new empty item.
        inventoryList.clear();
        inventoryList.add(emptyItem);
    }
    @FXML
    void deleteItems(ActionEvent event)
    {
        int deleteIndex = inventoryViewer.getSelectionModel().getSelectedIndex();
        inventoryList.remove(deleteIndex);
    }
    @FXML
    void editItem(ActionEvent event)
    {
        editIndex = inventoryViewer.getSelectionModel().getSelectedIndex();
    }
    @FXML
    void loadList(ActionEvent event)
    {

    }
    @FXML
    void openGithubLink(ActionEvent event)
    {

    }
    @FXML
    void saveList(ActionEvent event)
    {

    }
    // search the current list of items for items that match a keyword
    @FXML
    void searchItems(ActionEvent event)
    {
        // if the list is already filtered, unfilter it and reset/return
        if (isFiltered)
        {
            inventoryViewer.setItems(inventoryList);
            filteredList.clear();
            isFiltered = false;
            searchButton.setText("Search");
        }
        else
        {
            String searchKey = searchKeyField.getText();

            // if the search key is empty, alert the user and return
            if (searchKey.equalsIgnoreCase("") || searchKey.equalsIgnoreCase(null))
            {
                emptyAlert.showAndWait();
                return;
            }
            else
            {
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
                    return;
                }
                else
                {
                    inventoryViewer.setItems(filteredList);
                    isFiltered = true;
                    searchButton.setText("Undo Search");
                }
            }
        }
    }

    private boolean containsKey(InventoryItem item, String searchKey)
    {
        String itemSerialNumber = item.getSerialNumber();
        String itemPriceStr = String.valueOf(item.getItemPrice());
        String itemName = item.getItemName();

        if (itemSerialNumber.toLowerCase().contains(searchKey.toLowerCase()) || itemPriceStr.toLowerCase().contains(searchKey.toLowerCase()) || itemName.toLowerCase().contains(searchKey.toLowerCase()))
        {
            return true;
        }
        return false;
    }
}
