package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class ResponseWriter {

    private final ResponseResolver responseResolver;
    private final Iterables iterables;
    private final IO io;

    ResponseWriter(ResponseResolver responseResolver, Iterables iterables, IO io) {
        this.responseResolver = responseResolver;
        this.iterables = iterables;
        this.io = io;
    }

    void write(HttpServletResponse response, StubbedRequest stubbedRequest) throws IOException {
        final StubbedResponse stubbedResponse = responseResolver.resolveResponse(stubbedRequest);
        iterables.forEach(stubbedResponse.getHeaders().entrySet(), new ApplyHeaders(response));
        io.write(stubbedResponse.getInputStream(), response.getOutputStream());
    }
}
