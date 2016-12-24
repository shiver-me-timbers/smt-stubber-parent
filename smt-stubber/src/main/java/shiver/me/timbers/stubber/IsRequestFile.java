package shiver.me.timbers.stubber;

/**
 * @author Karl Bennett
 */
class IsRequestFile implements Condition<String> {

    private final Paths paths;

    IsRequestFile(Paths paths) {
        this.paths = paths;
    }

    @Override
    public boolean eval(String path) {
        return paths.fileName(path).startsWith("Request");
    }
}
