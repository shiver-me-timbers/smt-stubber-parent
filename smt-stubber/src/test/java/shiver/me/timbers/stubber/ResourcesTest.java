package shiver.me.timbers.stubber;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

public class ResourcesTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_list_the_files_in_a_resource_directory() throws IOException {

        // When
        final List<String> actual = new Resources().listFiles("a/test/directory");

        // Then
        assertThat(actual, containsInAnyOrder("a/test/directory/one", "a/test/directory/two", "a/test/directory/three"));
    }

    @Test
    public void Will_get_an_IOException_if_the_path_is_invalid() throws IOException {

        final String path = someAlphanumericString();

        // Given
        expectedException.expect(IOException.class);
        expectedException.expectMessage(format("No directory in the classpath at path (%s).", path));

        // When
        new Resources().listFiles(path);
    }
}