package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalPersons.ALICE;
import static seedu.mark.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.PersonBuilder;

public class BookmarkTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Bookmark bookmark = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> bookmark.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameBookmark(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBookmark(null));

        // different phone and url -> returns false
        Bookmark editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameBookmark(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameBookmark(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));

        // same name, same url, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));

        // same name, same phone, same url, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bookmark aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different bookmark -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Bookmark editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different url -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
