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
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class ExportCsvItemsCommandTest {

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
        Path filePath = Paths.get(getTempFilePath("validExport.csv"));
        ExportCsvItemsCommand command = new ExportCsvItemsCommand(filePath);
        String expectedMessage = String.format(ExportCsvItemsCommand.MESSAGE_SUCCESS, filePath);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void execute_invalidFileExtension_throwsCommandException() {
        Path filePath = Paths.get(getTempFilePath("invalidExport.notcsv"));
        ExportCsvItemsCommand command = new ExportCsvItemsCommand(filePath);
        String expectedMessage = String.format(ExportCsvItemsCommand.MESSAGE_INVALID_CSV_FILEPATH, filePath);

        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }


    @Test
    public void equals() {
        final Path tempPath = Paths.get(getTempFilePath("validExport.csv"));
        final ExportCsvItemsCommand standardCommand = new ExportCsvItemsCommand(tempPath);

        // same values -> returns true
        ExportCsvItemsCommand commandWithSameValues = new ExportCsvItemsCommand(tempPath);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals((ExportCsvItemsCommand) null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different filepath -> returns false
        Path differentPath = Paths.get(getTempFilePath("invalidExport.csv"));
        assertFalse(standardCommand.equals(new ExportCsvItemsCommand(differentPath)));
    }

}
