package shiver.me.timbers.stubber;

import static java.util.Map.Entry;

/**
 * @author Karl Bennett
 */
class ToStubbedRequest implements Mapper<Entry<String, Entry<String, String>>, StubbedRequest> {

    @Override
    public StubbedRequest map(Entry<String, Entry<String, String>> input) {
        throw new UnsupportedOperationException();
    }
}
