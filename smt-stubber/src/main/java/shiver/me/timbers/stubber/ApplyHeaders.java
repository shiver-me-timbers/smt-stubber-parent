package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.Map.Entry;

/**
 * @author Karl Bennett
 */
class ApplyHeaders implements Apply<Entry<String, List<String>>> {

    private final HttpServletResponse response;

    ApplyHeaders(HttpServletResponse response) {
        this.response = response;
    }
}
