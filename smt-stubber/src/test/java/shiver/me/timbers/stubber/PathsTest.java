package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class PathsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Paths paths;

    @Before
    public void setUp() {
        paths = new Paths();
    }

    @Test
    public void Can_check_if_a_path_exists() {

        // Given
        final String path = Thread.currentThread().getContextClassLoader().getResource("a/test/directory").getPath();

        // When
        final boolean actual = paths.exists(path);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_if_a_path_does_not_exist() {

        // Given
        final String path = format(
            "%s%s%s%s%s",
            someAlphanumericString(3),
            File.separator,
            someAlphanumericString(5),
            File.separator,
            someAlphanumericString(8)
        );

        // When
        final boolean actual = paths.exists(path);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_get_the_parent_path() {

        // Given
        final String expected = format(
            "%s%s%s%s%s%s%s%s",
            someThing("", "/"),
            someAlphanumericString(3),
            File.separator,
            someAlphanumericString(5),
            File.separator,
            someAlphanumericString(8),
            File.separator,
            someAlphanumericString(13)
        );

        // When
        final String actual = paths.parentPath(format(
            "%s%s%s%s",
            expected,
            File.separator,
            someAlphanumericString(13),
            File.separator
        ));

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void The_parent_path_for_a_root_directory_is_the_empty_string() {

        // When
        final String actual = paths.parentPath(someAlphanumericString(1, 13));

        // Then
        assertThat(actual, isEmptyString());
    }

    @Test
    public void Can_get_the_file_name_from_a_path() {

        // Given
        final String expected = someAlphanumericString(13);

        // When
        final String actual = paths.fileName(format(
            "%s%s%s%s%s%s%s%s",
            someThing("", "/"),
            someAlphanumericString(3),
            File.separator,
            someAlphanumericString(5),
            File.separator,
            someAlphanumericString(8),
            File.separator,
            expected
        ));

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Cannot_get_the_file_name_for_a_directory() {

        final String path = format(
            "%s%s%s%s%s%s%s%s%s",
            someThing("", "/"),
            someAlphanumericString(3),
            File.separator,
            someAlphanumericString(5),
            File.separator,
            someAlphanumericString(8),
            File.separator,
            someAlphanumericString(13),
            File.separator
        );

        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(format("The path (%s) is not for a file.", path));

        // When
        paths.fileName(path);
    }
}