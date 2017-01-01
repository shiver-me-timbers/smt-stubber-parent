package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Karl Bennett
 */
class PathResolver {

    private final Paths paths;

    PathResolver(Paths paths) {
        this.paths = paths;
    }

    String resolvePath(HttpServletRequest request) {
        final String requestPath = request.getMethod() + request.getServletPath();
        if (paths.exists(requestPath)) {
            return requestPath;
        }
        final String defaultPath = paths.parentPath(requestPath) + File.separator + '_';
        if (paths.exists(defaultPath)) {
            return defaultPath;
        }
        return null;
    }
}
