package shiver.me.timbers.stubber;

import java.io.File;
import java.nio.file.Path;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class Paths {

    boolean exists(String path) {
        return new File(path).exists();
    }

    String parentPath(String path) {
        final Path parent = java.nio.file.Paths.get(path).getParent();
        if (parent == null) {
            return "";
        }
        return parent.toString();
    }

    String fileName(String path) {
        if (path.endsWith(File.separator)) {
            throw new IllegalArgumentException(format("The path (%s) is not for a file.", path));
        }
        return java.nio.file.Paths.get(path).getFileName().toString();
    }
}
