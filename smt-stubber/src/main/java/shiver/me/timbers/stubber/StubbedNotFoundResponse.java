package shiver.me.timbers.stubber;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * @author Karl Bennett
 */
class StubbedNotFoundResponse extends StubbedResponse {
    StubbedNotFoundResponse() {
        super(SC_NOT_FOUND, Collections.<String, List<String>>emptyMap(), new ByteArrayInputStream("".getBytes()));
    }
}
