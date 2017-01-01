package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

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
        writeStatus(stubbedResponse, response);
        writeHeaders(stubbedResponse, response);
        writeBody(stubbedResponse, response);
    }

    private void writeStatus(StubbedResponse stubbedResponse, HttpServletResponse response) {
        response.setStatus(stubbedResponse.getStatus());
    }

    private void writeHeaders(StubbedResponse stubbedResponse, HttpServletResponse response) {
        iterables.forEach(stubbedResponse.getHeaders().entrySet(), new ApplyHeaders(response)).run();
    }

    private void writeBody(StubbedResponse stubbedResponse, HttpServletResponse response) throws IOException {
        try (final InputStream responseBody = stubbedResponse.getInputStream()) {
            io.write(responseBody, response.getOutputStream());
        }
    }
}
