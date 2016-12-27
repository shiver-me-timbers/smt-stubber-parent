package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class IterableFirstFinderTest {

    private Iterable<Object> iterable;
    private IterableFirstFinder<Object> firstFinder;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        iterable = mock(Iterable.class);
        firstFinder = new IterableFirstFinder<>(iterable);
    }

    @Test
    public void Can_get_the_first_element() {

        final Object expected = someThing();

        // Given
        given(iterable.iterator()).willReturn(asList(expected, someThing(), someThing()).iterator());

        // When
        final Object actual = firstFinder.getOrElse(someThing());

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Will_get_the_other_value_if_there_is_no_first_element() {

        final Object expected = someThing();

        // Given
        given(iterable.iterator()).willReturn(emptyList().iterator());

        // When
        final Object actual = firstFinder.getOrElse(expected);

        // Then
        assertThat(actual, is(expected));
    }
}