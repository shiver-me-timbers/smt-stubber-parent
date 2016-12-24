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

    private final Lists lists;
    private final IsRequestFile isRequestFile;
    private final AgainstRequestName againstRequestName;
    private final ToRequestFilePairs toRequestFilePairs;
    private final ToStubbedRequest toStubbedRequest;

    RequestMapper(
        Lists lists,
        IsRequestFile isRequestFile,
        AgainstRequestName againstRequestName,
        ToRequestFilePairs toRequestFilePairs,
        ToStubbedRequest toStubbedRequest
    ) {
        this.lists = lists;
        this.isRequestFile = isRequestFile;
        this.againstRequestName = againstRequestName;
        this.toRequestFilePairs = toRequestFilePairs;
        this.toStubbedRequest = toStubbedRequest;
    }

    List<StubbedRequest> read(List<String> paths) {
        final Map<String, Entry<String, String>> requestFileMap = lists.filter(paths, isRequestFile)
            .map(againstRequestName).reduce(toRequestFilePairs)
            .getElse(Collections.<String, Entry<String, String>>emptyMap());

        return lists.map(requestFileMap.entrySet(), toStubbedRequest).toList(new ArrayList<StubbedRequest>());
//        final List<StubbedRequest> requests = new ArrayList<>();
//        for (String name : requestFileMap.keySet()) {
//            final Entry<String, String> entry = requestFileMap.get(name);
//            requests.add(new StubbedRequest(
//                name, requestHeaderMapper.read(entry.getKey()), requestBodyMapper.read(entry.getValue())
//            ));
//        }
    }
}
