package seedu.inventory.model;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.staff.Staff;

/**
 * Represents the in-memory model of the inventory data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedInventory versionedInventory;
    private final FilteredList<Item> filteredItems;
    private final FilteredList<Staff> filteredStaffs;

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, UserPrefs userPrefs) {
        super();
        requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with inventory: " + inventory + " and user prefs " + userPrefs);

        versionedInventory = new VersionedInventory(inventory);
        filteredItems = new FilteredList<>(versionedInventory.getItemList());
        filteredStaffs = new FilteredList<>(versionedInventory.getStaffList());
    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyInventory newData) {
        versionedInventory.resetData(newData);
        indicateInventoryChanged();
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return versionedInventory;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateInventoryChanged() {
        raise(new InventoryChangedEvent(versionedInventory));
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedInventory.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        versionedInventory.removeItem(target);
        indicateInventoryChanged();
    }

    @Override
    public void addItem(Item item) {
        versionedInventory.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateInventoryChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedInventory.updateItem(target, editedItem);
        indicateInventoryChanged();
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //=========== User Management ===========================================
    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedInventory.hasStaff(staff);
    }

    @Override
    public void deleteStaff(Staff target) {
        requireNonNull(target);
        versionedInventory.removeStaff(target);
        indicateInventoryChanged();
    }

    @Override
    public void addStaff(Staff staff) {
        requireNonNull(staff);
        versionedInventory.addStaff(staff);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateInventoryChanged();
    }

    @Override
    public void updateStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);

        versionedInventory.updateStaff(target, editedStaff);
        indicateInventoryChanged();
    }

    // ================ Filtered Staff list accessors=============
    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return FXCollections.unmodifiableObservableList(filteredStaffs);
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    //================ Authentication ========================
    @Override
    public void authenticateUser(Staff toLogin) {
        requireNonNull(toLogin);

        versionedInventory.authenticateUser(toLogin);
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoInventory() {
        return versionedInventory.canUndo();
    }

    @Override
    public boolean canRedoInventory() {
        return versionedInventory.canRedo();
    }

    @Override
    public void undoInventory() {
        versionedInventory.undo();
        indicateInventoryChanged();
    }

    @Override
    public void redoInventory() {
        versionedInventory.redo();
        indicateInventoryChanged();
    }

    @Override
    public void commitInventory() {
        versionedInventory.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedInventory.equals(other.versionedInventory)
                && filteredItems.equals(other.filteredItems);
    }

}
