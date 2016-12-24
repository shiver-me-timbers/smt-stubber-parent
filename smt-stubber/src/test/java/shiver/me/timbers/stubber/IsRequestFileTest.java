package shiver.me.timbers.stubber;

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class IsRequestFileTest {

    @Test
    public void Can_check_that_the_path_is_to_a_request_file() {

        final Paths paths = mock(Paths.class);

        final String path = someString();

        // Given
        given(paths.fileName(path)).willReturn(format("Request%s", someString()));

        // When
        final boolean actual = new IsRequestFile(paths).eval(path);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_the_path_is_not_to_a_request_file() {

        final Paths paths = mock(Paths.class);

        final String path = someString();

        // Given
        given(paths.fileName(path)).willReturn(someThing(
            someString(),
            format("%sRequest", someString(1, 8))
        ));

        // When
        final boolean actual = new IsRequestFile(paths).eval(path);

        // Then
        assertThat(actual, is(false));
    }
}