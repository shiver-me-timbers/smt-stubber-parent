package shiver.me.timbers.stubber;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;

/**
 * @author Karl Bennett
 */
public class RequestFinderTest {

    private RequestMapper requestMapper;
    private Iterables iterables;
    private RequestFinder finder;

    @Before
    public void setUp() {
        requestMapper = mock(RequestMapper.class);
        iterables = mock(Iterables.class);
        finder = new RequestFinder(requestMapper, iterables);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_find_a_matching_request() {

        final String path = someString();
        final List<String> paths = mock(List.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final List<StubbedRequest> stubbedRequests = mock(List.class);
        final IterableFilter<StubbedRequest> filter = mock(IterableFilter.class);
        final IterableFirstFinder<StubbedRequest> firstFinder = mock(IterableFirstFinder.class);

        final StubbedRequest expected = mock(StubbedRequest.class);

        // Given
        given(requestMapper.read(path, paths)).willReturn(stubbedRequests);
        given(iterables.filter(eq(stubbedRequests), argThat(allOf(
            Matchers.<MatchesRequest>instanceOf(MatchesRequest.class), hasField("request", request)
        )))).willReturn(filter);
        given(filter.findFirst()).willReturn(firstFinder);
        given(firstFinder.getOrElse(null)).willReturn(expected);

        // When
        final StubbedRequest actual = finder.find(path, paths, request);

        // Then
        assertThat(actual, is(expected));
    }
}