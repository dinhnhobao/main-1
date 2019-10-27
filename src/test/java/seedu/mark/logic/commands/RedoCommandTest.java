package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.deleteFirstBookmark;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new StorageStub();

    @Test
    public void execute() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(model);
        model.undoMark();
        model.undoMark();

        deleteFirstBookmark(expectedModel);
        deleteFirstBookmark(expectedModel);
        expectedModel.undoMark();
        expectedModel.undoMark();

        // Two redoable Mark states
        String expectedRecord1 = expectedModel.redoMark();
        String expectedMessage1 = String.format(RedoCommand.MESSAGE_SUCCESS, expectedRecord1);
        assertCommandSuccess(new RedoCommand(), model, storage, expectedMessage1, expectedModel);

        // Single redoable Mark state
        String expectedRecord2 = expectedModel.redoMark();
        String expectedMessage2 = String.format(RedoCommand.MESSAGE_SUCCESS, expectedRecord2);
        assertCommandSuccess(new RedoCommand(), model, storage, expectedMessage2, expectedModel);

        // No redoable Mark state
        assertCommandFailure(new RedoCommand(), model, storage, RedoCommand.MESSAGE_FAILURE);
    }
}