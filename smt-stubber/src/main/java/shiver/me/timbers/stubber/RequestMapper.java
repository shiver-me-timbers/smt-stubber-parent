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
    private final IsRequestFile isRequestFile;
    private final AgainstRequestName againstRequestName;
    private final ToRequestFilePairs toRequestFilePairs;
    private final ToStubbedRequest toStubbedRequest;

    RequestMapper(
        Iterables iterables,
        IsRequestFile isRequestFile,
        AgainstRequestName againstRequestName,
        ToRequestFilePairs toRequestFilePairs,
        ToStubbedRequest toStubbedRequest
    ) {
        this.iterables = iterables;
        this.isRequestFile = isRequestFile;
        this.againstRequestName = againstRequestName;
        this.toRequestFilePairs = toRequestFilePairs;
        this.toStubbedRequest = toStubbedRequest;
    }

    List<StubbedRequest> read(List<String> paths) {
        final Map<String, Entry<String, String>> requestFileMap = iterables.filter(paths, isRequestFile)
            .map(againstRequestName).reduce(toRequestFilePairs)
            .getOrElse(Collections.<String, Entry<String, String>>emptyMap());
        return iterables.map(requestFileMap.entrySet(), toStubbedRequest).toList(new ArrayList<StubbedRequest>());
    }
}
