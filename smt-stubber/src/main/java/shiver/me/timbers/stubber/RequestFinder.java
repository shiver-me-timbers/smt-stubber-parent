package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Karl Bennett
 */
class RequestFinder {

    private final RequestMapper requestMapper;
    private final RequestMatcher requestMatcher;

    RequestFinder(RequestMapper requestMapper, RequestMatcher requestMatcher) {
        this.requestMapper = requestMapper;
        this.requestMatcher = requestMatcher;
    }

    StubbedRequest find(List<String> paths, HttpServletRequest request) {
        final List<StubbedRequest> requests = requestMapper.read(paths);
        for (StubbedRequest stubbedRequest : requests) {
            if (requestMatcher.matches(stubbedRequest, request)) {
                return stubbedRequest;
            }
        }
        return null;
    }
}
