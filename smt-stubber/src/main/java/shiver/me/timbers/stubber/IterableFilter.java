package shiver.me.timbers.stubber;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class IterableFilter<T> implements Iterable<T> {

    private final Iterable<T> iterable;
    private final Condition<T> condition;

    IterableFilter(Iterable<T> iterable, Condition<T> condition) {
        this.iterable = iterable;
        this.condition = condition;
    }

    @Override
    public Iterator<T> iterator() {
        final Iterator<T> iterator = iterable.iterator();
        return new Iterator<T>() {

            private T next;

            @Override
            public boolean hasNext() {
                return (next = findNext()) != null;
            }

            @Override
            public T next() {
                T next = consumeNext();
                if (next != null) {
                    return next;
                }

                next = findNext();
                if (next != null) {
                    return next;
                }

                throw new NoSuchElementException(
                    format("There are no more elements in the %s iterator.", IterableFilter.class.getSimpleName())
                );
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException(
                    format("Cannot remove from an %s iterator.", IterableFilter.class.getSimpleName())
                );
            }

            private T consumeNext() {
                T next = this.next;
                this.next = null;
                return next;
            }

            private T findNext() {
                T next;
                while (iterator.hasNext()) {
                    next = iterator.next();
                    if (condition.eval(next)) {
                        return next;
                    }
                }
                return null;
            }
        };
    }

    <O> IterableMapper<T, O> map(Mapper<T, O> mapper) {
        return new IterableMapper<>(this, mapper);
    }
}
