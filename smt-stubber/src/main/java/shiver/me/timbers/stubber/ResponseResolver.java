package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class ResponseResolver {

    private final ResponseFinder responseFinder;

    ResponseResolver(ResponseFinder responseFinder) {
        this.responseFinder = responseFinder;
    }

    StubbedResponse resolveResponse(StubbedRequest request) {
        if (request == null) {
            return new StubbedNotFoundResponse();
        }

        final StubbedResponse responseByName = responseFinder.findByName(request);
        if (responseByName != null) {
            return responseByName;
        }

        return responseFinder.findDefault(request);
    }
}
