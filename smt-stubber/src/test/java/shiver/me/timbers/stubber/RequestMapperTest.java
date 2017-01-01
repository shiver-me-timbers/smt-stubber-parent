package shiver.me.timbers.stubber;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class RequestMapperTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_read_all_request_objects() {

        final Iterables iterables = mock(Iterables.class);
        final Paths paths = mock(Paths.class);
        final String path = someString();
        final List<String> filePaths = mock(List.class);

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
        given(iterables.filter(
            eq(filePaths),
            argThat(allOf(Matchers.<Condition>instanceOf(IsRequestFile.class), hasField("paths", paths)))
        )).willReturn(filter);
        given(filter.map(isA(AgainstRequestName.class))).willReturn(mapper);
        given(mapper.reduce(isA(ToRequestFilePairs.class))).willReturn(iterableReducer);
        given(iterableReducer.getOrElse(Collections.<String, Entry<String, String>>emptyMap()))
            .willReturn(requestFileMap);
        given(requestFileMap.entrySet()).willReturn(requestFileEntries);
        given(iterables.map(
            eq(requestFileEntries),
            argThat(allOf(Matchers.<Mapper>instanceOf(ToStubbedRequest.class), hasField("path", path)))
        )).willReturn(stubbedRequestMapper);
        given(stubbedRequestMapper.toList(isA(ArrayList.class))).willReturn(expected);

        // When
        final List<StubbedRequest> actual = new RequestMapper(iterables, paths).read(path, filePaths);

        // Then
        assertThat(actual, is(expected));
    }

}