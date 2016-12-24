package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class RequestResolver {

    private final Resources resources;
    private final RequestFinder requestFinder;
    private final Paths paths;

    RequestResolver(Resources resources, RequestFinder requestFinder, Paths paths) {
        this.resources = resources;
        this.requestFinder = requestFinder;
        this.paths = paths;
    }

    StubbedRequest resolveRequest(HttpServletRequest request, String path) throws IOException {
        if (path.isEmpty()) {
            return null;
        }

        final StubbedRequest stubbedRequest = requestFinder.find(resources.listFiles(path), request);
        if (stubbedRequest != null) {
            return stubbedRequest;
        }

        return resolveRequest(request, paths.parentPath(path));
    }
}
