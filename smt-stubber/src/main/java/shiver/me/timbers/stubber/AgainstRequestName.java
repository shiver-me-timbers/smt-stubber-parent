package shiver.me.timbers.stubber;

import static java.util.Map.Entry;

/**
 * @author Karl Bennett
 */
class AgainstRequestName implements Mapper<String, Entry<String, String>> {

    @Override
    public Entry<String, String> map(String input) {
        throw new UnsupportedOperationException();
    }
}
