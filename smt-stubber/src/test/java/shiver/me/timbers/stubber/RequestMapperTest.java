package shiver.me.timbers.stubber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;

public class RequestMapperTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_read_all_request_objects() {

        final Lists lists = mock(Lists.class);
        final IsRequestFile isRequestFile = mock(IsRequestFile.class);
        final AgainstRequestName againstRequestName = mock(AgainstRequestName.class);
        final ToRequestFilePairs toRequestFilePairs = mock(ToRequestFilePairs.class);
        final ToStubbedRequest toStubbedRequest = mock(ToStubbedRequest.class);
        final List<String> paths = mock(List.class);

        final IterableFilter<String> filter = mock(IterableFilter.class);
        final IterableMapper<String, Entry<String, String>> mapper = mock(IterableMapper.class);
        final IterableReducer<Entry<String, String>, Map<String, Entry<String, String>>> iterableReducer =
            mock(IterableReducer.class);
        final Map<String, Entry<String, String>> requestFileMap = mock(Map.class);
        final Set<Entry<String, Entry<String, String>>> requestFileEntries = mock(Set.class);
        final IterableMapper<Entry<String, Entry<String, String>>, StubbedRequest> stubbedRequestMapper =
            mock(IterableMapper.class);

        final List<StubbedRequest> expected = mock(List.class);

        // Given
        given(lists.filter(paths, isRequestFile)).willReturn(filter);
        given(filter.map(againstRequestName)).willReturn(mapper);
        given(mapper.reduce(toRequestFilePairs)).willReturn(iterableReducer);
        given(iterableReducer.getElse(Collections.<String, Entry<String, String>>emptyMap())).willReturn(requestFileMap);
        given(requestFileMap.entrySet()).willReturn(requestFileEntries);
        given(lists.map(requestFileEntries, toStubbedRequest)).willReturn(stubbedRequestMapper);
        given(stubbedRequestMapper.toList(isA(ArrayList.class))).willReturn(expected);

        // When
        final List<StubbedRequest> actual = new RequestMapper(
            lists,
            isRequestFile,
            againstRequestName,
            toRequestFilePairs,
            toStubbedRequest
        ).read(paths);

        // Then
        assertThat(actual, is(expected));
    }

}