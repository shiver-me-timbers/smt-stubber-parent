package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
interface Mapper<I, O> {

    O map(I input);
}
