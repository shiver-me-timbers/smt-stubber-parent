package shiver.me.timbers.stubber;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Bennett
 */
class StubbedResponse {

    private final int status;
    private final Map<String, List<String>> headers;
    private final InputStream inputStream;

    StubbedResponse(int status, Map<String, List<String>> headers, InputStream inputStream) {
        this.status = status;
        this.headers = headers;
        this.inputStream = inputStream;
    }

    int getStatus() {
        return status;
    }

    Map<String, List<String>> getHeaders() {
        return headers;
    }

    InputStream getInputStream() throws IOException {
        return inputStream;
    }
}
