package shiver.me.timbers.stubber;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karl Bennett
 */
class MatchesRequest implements Condition<StubbedRequest> {

    private final HttpServletRequest request;

    MatchesRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean eval(StubbedRequest input) {
        throw new UnsupportedOperationException();
    }
}
