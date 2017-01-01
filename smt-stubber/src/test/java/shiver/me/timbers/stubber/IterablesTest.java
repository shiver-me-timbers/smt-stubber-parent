package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class IterablesTest {

    private Iterables iterables;

    @Before
    public void setUp() {
        iterables = new Iterables();
    }

    @Test
    public void Can_create_a_list_filter() {

        // Given
        final Iterable iterable = mock(Iterable.class);
        final Condition condition = mock(Condition.class);

        // When
        final IterableFilter actual = iterables.filter(iterable, condition);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterable), hasField("condition", condition)));
    }

    @Test
    public void Can_create_a_list_mapper() {

        // Given
        final Iterable iterable = mock(Iterable.class);
        final Mapper mapper = mock(Mapper.class);

        // When
        final IterableMapper actual = iterables.map(iterable, mapper);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterable), hasField("mapper", mapper)));
    }

    @Test
    public void Can_create_a_for_each() {

        // Given
        final Iterable iterable = mock(Iterable.class);
        final Apply apply = mock(Apply.class);

        // When
        final IterableForEach actual = iterables.forEach(iterable, apply);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterable), hasField("apply", apply)));
    }
}