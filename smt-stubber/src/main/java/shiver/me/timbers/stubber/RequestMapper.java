package shiver.me.timbers.stubber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry;

/**
 * @author Karl Bennett
 */
class RequestMapper {

    private final Iterables iterables;
    private final Paths paths;

    RequestMapper(Iterables iterables, Paths paths) {
        this.iterables = iterables;
        this.paths = paths;
    }

    List<StubbedRequest> read(String path, List<String> filePaths) {
        final Map<String, Entry<String, String>> requestFileMap = iterables.filter(filePaths, new IsRequestFile(paths))
            .map(new AgainstRequestName()).reduce(new ToRequestFilePairs())
            .getOrElse(Collections.<String, Entry<String, String>>emptyMap());
        return iterables.map(requestFileMap.entrySet(), new ToStubbedRequest(path))
            .toList(new ArrayList<StubbedRequest>());
    }
}
