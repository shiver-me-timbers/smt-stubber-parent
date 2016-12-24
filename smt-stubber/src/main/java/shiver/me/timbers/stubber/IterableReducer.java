package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class IterableReducer<I, O> {

    private final Iterable<I> iterable;
    private final Reducer<I, O> reducer;

    IterableReducer(Iterable<I> iterable, Reducer<I, O> reducer) {
        this.iterable = iterable;
        this.reducer = reducer;
    }

    O getElse(O other) {
        if (!iterable.iterator().hasNext()) {
            return other;
        }

        O result = null;
        for (I item : iterable) {
            result = reducer.reduce(result, item);
        }
        return result;
    }
}
