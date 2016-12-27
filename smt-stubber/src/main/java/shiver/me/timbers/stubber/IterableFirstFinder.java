package shiver.me.timbers.stubber;

import java.util.Iterator;

/**
 * @author Karl Bennett
 */
class IterableFirstFinder<T> {

    private final Iterable<T> iterable;

    IterableFirstFinder(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    T getOrElse(T other) {
        final Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return other;
        }
        return iterator.next();
    }
}
