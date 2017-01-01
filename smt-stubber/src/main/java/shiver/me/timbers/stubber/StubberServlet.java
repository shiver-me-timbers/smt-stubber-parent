package shiver.me.timbers.stubber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
public class StubberServlet extends HttpServlet {

    private final RequestResolver requestResolver;
    private final ResponseWriter responseWriter;

    public StubberServlet() {
        this(new Resources(), new Paths(), new Iterables());
    }

    private StubberServlet(Resources resources, Paths paths, Iterables iterables) {
        this(
            new RequestResolver(
                new PathResolver(paths),
                resources,
                new RequestFinder(
                    new RequestMapper(
                        iterables,
                        paths
                    ),
                    iterables
                )
            ),
            new ResponseWriter(new ResponseResolver(new ResponseFinder()), iterables, new IO())
        );
    }

    StubberServlet(RequestResolver requestResolver, ResponseWriter responseWriter) {
        this.requestResolver = requestResolver;
        this.responseWriter = responseWriter;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        responseWriter.write(response, requestResolver.resolveRequest(request));
    }
}
