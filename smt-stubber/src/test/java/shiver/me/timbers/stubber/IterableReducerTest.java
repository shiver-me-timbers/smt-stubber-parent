package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class IterableReducerTest {

    private Reducer<Integer, String> reducer;
    private Iterable<Integer> iterable;
    private IterableReducer<Integer, String> iterableReducer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        reducer = mock(Reducer.class);
        iterable = mock(Iterable.class);
        iterableReducer = new IterableReducer<>(iterable, reducer);
    }

    @Test
    public void Can_get_a_reduced_value() {

        final int input1 = someIntegerBetween(0, 100);
        final int input2 = someIntegerBetween(100, 200);
        final int input3 = someIntegerBetween(200, 300);

        final String result1 = someString();
        final String result2 = someString();
        final String expected = someString();

        // Given
        given(iterable.iterator()).willReturn(asList(input1, input2, input3).iterator());
        given(reducer.reduce(null, input1)).willReturn(result1);
        given(reducer.reduce(result1, input2)).willReturn(result2);
        given(reducer.reduce(result2, input3)).willReturn(expected);

        // When
        final String actual = iterableReducer.getElse(someString());

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_a_the_other_reduced_value_for_an_empy_iterable() {

        final String expected = someString();

        // Given
        given(iterable.iterator()).willReturn(Collections.<Integer>emptyList().iterator());

        // When
        final String actual = iterableReducer.getElse(expected);

        // Then
        verifyZeroInteractions(reducer);
        assertThat(actual, is(expected));
    }
}