package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
interface Condition<T> {

    boolean eval(T input);
}
