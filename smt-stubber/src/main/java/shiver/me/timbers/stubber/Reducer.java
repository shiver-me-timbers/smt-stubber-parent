package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
interface Reducer<I, O> {

    O reduce(O lastResult, I nextInput);
}
