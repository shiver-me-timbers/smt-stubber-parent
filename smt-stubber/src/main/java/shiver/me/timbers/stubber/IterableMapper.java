package shiver.me.timbers.stubber;

import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class IterableMapper<I, O> implements Iterable<O> {

    private final Iterable<I> iterable;
    private final Mapper<I, O> mapper;

    IterableMapper(Iterable<I> iterable, Mapper<I, O> mapper) {
        this.iterable = iterable;
        this.mapper = mapper;
    }

    @Override
    public Iterator<O> iterator() {
        final Iterator<I> iterator = iterable.iterator();
        return new Iterator<O>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public O next() {
                return mapper.map(iterator.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException(
                    format("Cannot remove from an %s iterator.", IterableMapper.class.getSimpleName())
                );
            }
        };
    }

    <RO> IterableReducer<O, RO> reduce(Reducer<O, RO> reducer) {
        return new IterableReducer<>(this, reducer);
    }

    List<O> toList(List<O> list) {
        for (O item : this) {
            list.add(item);
        }
        return list;
    }
}
