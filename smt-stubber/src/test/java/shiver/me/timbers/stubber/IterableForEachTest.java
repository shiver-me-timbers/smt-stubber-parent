package shiver.me.timbers.stubber;

import org.junit.Test;
import org.mockito.InOrder;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static shiver.me.timbers.data.random.RandomDoubles.someDouble;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class IterableForEachTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_run_a_for_each() {

        final Iterable<Object> iterable = mock(Iterable.class);
        final Apply<Object> apply = mock(Apply.class);

        final Object one = someInteger();
        final Object two = someDouble();
        final Object three = someString();

        // Given
        given(iterable.iterator()).willReturn(asList(one, two, three).iterator());

        // When
        new IterableForEach<>(iterable, apply).run();

        // Then
        final InOrder order = inOrder(apply);
        order.verify(apply).to(one);
        order.verify(apply).to(two);
        order.verify(apply).to(three);
        verifyNoMoreInteractions(apply);
    }
}