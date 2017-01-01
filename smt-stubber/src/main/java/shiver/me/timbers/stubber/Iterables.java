package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class Iterables {

    <T> IterableFilter<T> filter(Iterable<T> iterable, Condition<T> condition) {
        return new IterableFilter<>(iterable, condition);
    }

    <I, O> IterableMapper<I, O> map(Iterable<I> iterable, Mapper<I, O> mapper) {
        return new IterableMapper<>(iterable, mapper);
    }

    <T> IterableForEach forEach(Iterable<T> iterable, Apply<T> apply) {
        return new IterableForEach<>(iterable, apply);
    }
}
