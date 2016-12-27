package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Karl Bennett
 */
class RequestFinder {

    private final RequestMapper requestMapper;
    private final Iterables iterables;

    RequestFinder(RequestMapper requestMapper, Iterables iterables) {
        this.requestMapper = requestMapper;
        this.iterables = iterables;
    }

    StubbedRequest find(List<String> paths, HttpServletRequest request) {
        return iterables.filter(requestMapper.read(paths), new MatchesRequest(request)).findFirst()
            .getOrElse(null);
    }
}
