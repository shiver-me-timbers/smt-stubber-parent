package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karl Bennett
 */
class PathResolver {

    String resolvePath(HttpServletRequest request) {
        return request.getMethod() + request.getServletPath();
    }
}
