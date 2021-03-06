package seedu.inventory.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.csv.ImportCsvCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditItemCommand.
 */
//TODO
public class ImportCsvCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Helper method to provide temporary file paths
     */
    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + File.separator + fileName;
    }

    @Test
    public void execute_validFilePath_success() {
        Path filePath = Paths.get(getTempFilePath("validImport.csv"));
        ImportCsvCommand command = new ImportCsvCommand(filePath).setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS);
        String expectedMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_ITEMS, filePath);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);

        command = new ImportCsvCommand(filePath).setCommandWord(ImportCsvCommand.COMMAND_WORD_SALES);
        expectedMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_SALES, filePath);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);

        command = new ImportCsvCommand(filePath).setCommandWord(ImportCsvCommand.COMMAND_WORD_STAFFS);
        expectedMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_STAFFS, filePath);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);

        command = new ImportCsvCommand(filePath).setCommandWord(ImportCsvCommand.COMMAND_WORD_PURCHASE_ORDERS);
        expectedMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_PURCHASE_ORDERS, filePath);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void execute_invalidFileExtension_throwsCommandException() {
        Path filePath = Paths.get(getTempFilePath("invalidImport.notcsv"));
        ImportCsvCommand command = new ImportCsvCommand(filePath).setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS);
        String expectedMessage = String.format(ImportCsvCommand.MESSAGE_INVALID_CSV_FILEPATH, filePath);

        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidCommandWord_throwsCommandException() {
        Path filePath = Paths.get(getTempFilePath("validImport.csv"));
        ImportCsvCommand command = new ImportCsvCommand(filePath).setCommandWord("Dummy");
        String expectedMessage = ImportCsvCommand.MESSAGE_INVALID_COMMAND_WORD;

        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        final Path tempPath = Paths.get(getTempFilePath("validImport.csv"));
        final ImportCsvCommand standardCommand = new ImportCsvCommand(tempPath);

        // same values -> returns true
        ImportCsvCommand commandWithSameValues = new ImportCsvCommand(tempPath);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same commandWord -> returns true
        assertTrue(standardCommand.setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS)
                .equals(commandWithSameValues.setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS)));

        // null -> returns false
        assertFalse(standardCommand.equals((ImportCsvCommand) null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different filepath -> returns false
        Path differentPath = Paths.get(getTempFilePath("invalidImport.csv"));
        assertFalse(standardCommand.equals(new ImportCsvCommand(differentPath)));

        // different commandWord -> returns false
        assertFalse(standardCommand.setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS)
                .equals(commandWithSameValues.setCommandWord(ImportCsvCommand.COMMAND_WORD_PURCHASE_ORDERS)));

        // different commandWord -> returns false
        assertFalse(standardCommand.setCommandWord(ImportCsvCommand.COMMAND_WORD_ITEMS)
                .equals(commandWithSameValues.setCommandWord((String) null)));
    }


}
