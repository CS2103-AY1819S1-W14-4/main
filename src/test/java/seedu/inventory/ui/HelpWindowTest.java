package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static seedu.inventory.ui.HelpWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import javafx.stage.Stage;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        FxToolkit.registerStage(helpWindow::getRoot);
        helpWindowHandle = new HelpWindowHandle(helpWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, helpWindowHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        guiRobot.interact(helpWindow::show);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
        assumeFalse("Test skipped in headless mode: Window focus behavior is buggy.", guiRobot.isHeadlessMode());
        guiRobot.interact(helpWindow::show);

        // Focus on another stage to remove focus from the helpWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(helpWindow.getRoot().isFocused());

        guiRobot.interact(helpWindow::focus);
        assertTrue(helpWindow.getRoot().isFocused());
    }
}
