package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class IterableFilterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Iterable iterable;
    private Condition<Integer> condition;
    private IterableFilter<Integer> filter;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        iterable = mock(Iterable.class);
        condition = mock(Condition.class);
        filter = new IterableFilter<>(iterable, condition);
    }

    @Test
    public void Can_create_a_mapper() {

        // Given
        final Mapper mapper = mock(Mapper.class);

        // When
        final IterableMapper actual = filter.map(mapper);

        // Then
        assertThat(actual, allOf(hasField("iterable", filter), hasField("mapper", mapper)));
    }

    @Test
    public void Can_iterate_over_a_filter() {

        final List<Integer> actual = new ArrayList<>();

        final int input1 = someIntegerBetween(0, 100);
        final int input2 = someIntegerBetween(100, 200);
        final int input3 = someIntegerBetween(200, 300);

        // Given
        given(iterable.iterator()).willReturn(asList(input1, input2, input3).iterator());
        given(condition.eval(input1)).willReturn(true);
        given(condition.eval(input2)).willReturn(false);
        given(condition.eval(input3)).willReturn(true);

        // When
        for (Integer item : filter) {
            actual.add(item);
        }

        // Then
        assertThat(actual, contains(input1, input3));
    }

    @Test
    public void Can_just_call_next_on_a_filter() {

        final int input1 = someIntegerBetween(0, 100);
        final int input2 = someIntegerBetween(100, 200);

        // Given
        given(iterable.iterator()).willReturn(asList(input1, input2).iterator());
        given(condition.eval(input1)).willReturn(false);
        given(condition.eval(input2)).willReturn(true);
        final Iterator<Integer> iterator = filter.iterator();

        // When
        final int actual = iterator.next();

        // Then
        assertThat(actual, is(input2));
    }

    @Test
    public void Can_call_next_too_many_times() {

        final int input1 = someIntegerBetween(0, 100);

        // Given
        given(iterable.iterator()).willReturn(singleton(input1).iterator());
        given(condition.eval(input1)).willReturn(false);
        final Iterator<Integer> iterator = filter.iterator();
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage(
            format("There are no more elements in the %s iterator.", filter.getClass().getSimpleName())
        );

        // When
        iterator.next();
    }

    @Test
    public void Cannot_remove_from_a_filter_iterator() {

        // Given
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage(
            format("Cannot remove from an %s iterator.", filter.getClass().getSimpleName())
        );

        // When
        filter.iterator().remove();
    }
}