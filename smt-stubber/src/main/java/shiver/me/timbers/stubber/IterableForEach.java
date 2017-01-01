package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class IterableForEach<T> implements Runnable {

    private final Iterable<T> iterable;
    private final Apply<T> apply;

    IterableForEach(Iterable<T> iterable, Apply<T> apply) {
        this.iterable = iterable;
        this.apply = apply;
    }

    public void run() {
        for (T item : iterable) {
            apply.to(item);
        }
    }
}
