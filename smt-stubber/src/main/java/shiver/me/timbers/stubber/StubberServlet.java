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

    private final PathResolver pathResolver;
    private final RequestResolver requestResolver;
    private final ResponseWriter responseWriter;

    public StubberServlet() {
        this(
            new PathResolver(),
            new RequestResolver(
                new Resources(),
                new RequestFinder(new RequestMapper(), new RequestMatcher()),
                new Paths()
            ),
            new ResponseWriter()
        );
    }

    StubberServlet(PathResolver pathResolver, RequestResolver requestResolver, ResponseWriter responseWriter) {
        this.pathResolver = pathResolver;
        this.requestResolver = requestResolver;
        this.responseWriter = responseWriter;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        responseWriter.write(requestResolver.resolveRequest(pathResolver.resolvePath(request), request), response);
    }
}
