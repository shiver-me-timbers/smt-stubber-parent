package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class StubbedRequest {

    private final String name;
    private final StubbedHeaders headers;
    private final Object body;

    StubbedRequest(String name, StubbedHeaders headers, Object body) {
        this.name = name;
        this.headers = headers;
        this.body = body;
    }
}
