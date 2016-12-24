package shiver.me.timbers.stubber;

import java.util.Map;

import static java.util.Map.Entry;

/**
 * @author Karl Bennett
 */
class ToRequestFilePairs implements Reducer<Entry<String, String>, Map<String, Entry<String, String>>> {

    @Override
    public Map<String, Entry<String, String>> reduce(
        Map<String, Entry<String, String>> lastResult,
        Entry<String, String> nextInput
    ) {
        throw new UnsupportedOperationException();
    }
}
