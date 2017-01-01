package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class RequestResolver {

    private final PathResolver pathResolver;
    private final Resources resources;
    private final RequestFinder requestFinder;

    RequestResolver(PathResolver pathResolver, Resources resources, RequestFinder requestFinder) {
        this.pathResolver = pathResolver;
        this.resources = resources;
        this.requestFinder = requestFinder;
    }

    StubbedRequest resolveRequest(HttpServletRequest request) throws IOException {
        final String path = pathResolver.resolvePath(request);
        if (path == null) {
            return null;
        }

        final StubbedRequest stubbedRequest = requestFinder.find(path, resources.listFiles(path), request);
        if (stubbedRequest != null) {
            return stubbedRequest;
        }

        return new StubbedRequest("", path);
    }
}
