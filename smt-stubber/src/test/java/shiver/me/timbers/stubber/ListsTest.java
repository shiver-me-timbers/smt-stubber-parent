package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class ListsTest {

    private Lists lists;

    @Before
    public void setUp() {
        lists = new Lists();
    }

    @Test
    public void Can_create_a_list_filter() {

        // Given
        final Iterable iterable = mock(Iterable.class);
        final Condition condition = mock(Condition.class);

        // When
        final IterableFilter actual = lists.filter(iterable, condition);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterable), hasField("condition", condition)));
    }

    @Test
    public void Can_create_a_list_mapper() {

        // Given
        final Iterable iterable = mock(Iterable.class);
        final Mapper mapper = mock(Mapper.class);

        // When
        final IterableMapper actual = lists.map(iterable, mapper);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterable), hasField("mapper", mapper)));
    }
}