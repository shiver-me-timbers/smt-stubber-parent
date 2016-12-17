package shiver.me.timbers.stubber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class Resources {

    List<String> listFiles(String path) throws IOException {
        final InputStream resources = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (resources == null) {
            throw new IOException(format("No directory in the classpath at path (%s).", path));
        }

        final List<String> paths = new ArrayList<>();
        try (
            final BufferedReader br = new BufferedReader(new InputStreamReader(resources))
        ) {
            String resource;
            while ((resource = br.readLine()) != null) {
                paths.add(Paths.get(path, resource).toString());
            }
        }
        return paths;
    }
}
