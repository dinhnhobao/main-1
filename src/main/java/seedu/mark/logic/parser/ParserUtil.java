package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.util.StringUtil;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String folder} into a {@code Folder}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code folder} is invalid.
     */
    public static Folder parseFolder(String folder) throws ParseException {
        requireNonNull(folder);
        String trimmedFolder = folder.trim();
        if (!Folder.isValidFolder(trimmedFolder)) {
            throw new ParseException(Folder.MESSAGE_CONSTRAINTS);
        }
        return new Folder(trimmedFolder);
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     * Empty remarks will be replaced by the default {@code Remark}.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();

        if (Remark.isEmptyRemark(trimmedRemark)) {
            return Remark.getDefaultRemark();
        } else if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }

        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String url} into a {@code Url}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code url} is invalid.
     */
    public static Url parseUrl(String url) throws ParseException {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        if (!Url.isValidUrl(trimmedUrl)) {
            throw new ParseException(Url.MESSAGE_CONSTRAINTS);
        }
        return new Url(trimmedUrl);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}