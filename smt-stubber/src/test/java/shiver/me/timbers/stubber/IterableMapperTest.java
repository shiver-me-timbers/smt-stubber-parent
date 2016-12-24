package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class IterableMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Iterable<Integer> iterable;
    private Mapper<Integer, String> mapper;
    private IterableMapper<Integer, String> iterableMapper;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        iterable = mock(Iterable.class);
        mapper = mock(Mapper.class);
        iterableMapper = new IterableMapper<>(iterable, mapper);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_create_a_reducer() {

        // Given
        final Reducer reducer = mock(Reducer.class);

        // When
        final IterableReducer actual = iterableMapper.reduce(reducer);

        // Then
        assertThat(actual, allOf(hasField("iterable", iterableMapper), hasField("reducer", reducer)));
    }

    @Test
    public void Can_iterate_over_a_mapper() {

        final List<String> actual = new ArrayList<>();

        final int input1 = someIntegerBetween(0, 100);
        final int input2 = someIntegerBetween(100, 200);
        final int input3 = someIntegerBetween(200, 300);
        final String output1 = someString();
        final String output2 = someString();
        final String output3 = someString();

        // Given
        given(iterable.iterator()).willReturn(asList(input1, input2, input3).iterator());
        given(mapper.map(input1)).willReturn(output1);
        given(mapper.map(input2)).willReturn(output2);
        given(mapper.map(input3)).willReturn(output3);

        // When
        for (String item : iterableMapper) {
            actual.add(item);
        }

        // Then
        assertThat(actual, contains(output1, output2, output3));
    }

    @Test
    public void Cannot_remove_from_a_mapper_iterator() {

        // Given
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage(
            format("Cannot remove from an %s iterator.", iterableMapper.getClass().getSimpleName())
        );

        // When
        iterableMapper.iterator().remove();
    }

    @Test
    public void Can_map_to_a_list() {

        @SuppressWarnings("unchecked")
        final List<String> list = mock(List.class);

        final int input1 = someIntegerBetween(0, 100);
        final int input2 = someIntegerBetween(100, 200);
        final int input3 = someIntegerBetween(200, 300);
        final String output1 = someString();
        final String output2 = someString();
        final String output3 = someString();

        // Given
        given(iterable.iterator()).willReturn(asList(input1, input2, input3).iterator());
        given(mapper.map(input1)).willReturn(output1);
        given(mapper.map(input2)).willReturn(output2);
        given(mapper.map(input3)).willReturn(output3);

        // When
        final List<String> actual = iterableMapper.toList(list);

        // Then
        verify(list).add(output1);
        verify(list).add(output2);
        verify(list).add(output3);
        verifyNoMoreInteractions(list);
        assertThat(actual, is(list));
    }
}