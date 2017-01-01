package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class StubbedRequest {

    private final String name;
    private final String path;

    StubbedRequest(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
