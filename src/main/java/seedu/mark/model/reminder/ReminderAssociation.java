package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.BookmarkContainNoReminderException;
import seedu.mark.model.bookmark.exceptions.ExistReminderException;
import seedu.mark.model.bookmark.exceptions.ReminderNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;


public class ReminderAssociation {
    private static final Comparator<Reminder> comparator = (Reminder a, Reminder b) ->
            (a.getRemindTime().isBefore(b.getRemindTime())) ? 1 : 0;
    //TODO: One bookmark may has multiple reminder in next version.
    private ObservableMap<Bookmark, Reminder> association = FXCollections.observableHashMap();

    public void addReminder(Bookmark bookmark, Reminder reminder) {
        requireAllNonNull(bookmark, reminder);

        if (association.containsKey(bookmark)) {
            throw new ExistReminderException();
        }

        association.put(bookmark, reminder);
    }

    public void deleteReminder(Reminder reminder) {
        requireAllNonNull(reminder);
        Bookmark bookmark = reminder.getBookmark();

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if(!association.remove(bookmark, reminder)) {
            throw new ReminderNotFoundException();
        }
    }

    public void setReminder(Reminder targetReminder, Reminder edittedReminder) {
        requireAllNonNull(targetReminder, edittedReminder);
        Bookmark bookmark = targetReminder.getBookmark();

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if (!association.replace(bookmark, targetReminder, edittedReminder)) {
            throw new ReminderNotFoundException();
        }
    }

    public ObservableList<Reminder> getReminderList() {
        ObservableList<Reminder> reminderList = FXCollections.observableArrayList();
        reminderList.addAll(association.values());
        return reminderList;
    }

    @Override
    public int hashCode() {
        return association.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderAssociation // instanceof handles nulls
                && association.equals(((ReminderAssociation) other).association));
    }
}
