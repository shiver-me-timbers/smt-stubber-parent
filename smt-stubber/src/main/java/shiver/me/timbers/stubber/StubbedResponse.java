package shiver.me.timbers.stubber;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Bennett
 */
class StubbedResponse {

    Map<String, List<String>> getHeaders() {
        throw new UnsupportedOperationException();
    }

    InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }
}
