package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomThings.someThing;

/**
 * @author Karl Bennett
 */
public class RequestFinderTest {

    private RequestMapper requestMapper;
    private RequestMatcher requestMatcher;
    private RequestFinder finder;

    @Before
    public void setUp() {
        requestMapper = mock(RequestMapper.class);
        requestMatcher = mock(RequestMatcher.class);
        finder = new RequestFinder(requestMapper, requestMatcher);
    }

    @Test
    public void Can_find_a_matching_request() {

        @SuppressWarnings("unchecked")
        final List<String> paths = mock(List.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final StubbedRequest stubbedRequest1 = mock(StubbedRequest.class);
        final StubbedRequest stubbedRequest2 = mock(StubbedRequest.class);
        final StubbedRequest stubbedRequest3 = mock(StubbedRequest.class);

        final StubbedRequest expected = someThing(stubbedRequest1, stubbedRequest2, stubbedRequest3);

        // Given
        given(requestMapper.read(paths)).willReturn(asList(stubbedRequest1, stubbedRequest2, stubbedRequest3));
        given(requestMatcher.matches(expected, request)).willReturn(true);

        // When
        final StubbedRequest actual = finder.find(paths, request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_fail_to_find_a_matching_request() {

        @SuppressWarnings("unchecked")
        final List<String> paths = mock(List.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(requestMapper.read(paths))
            .willReturn(asList(mock(StubbedRequest.class), mock(StubbedRequest.class), mock(StubbedRequest.class)));
        given(requestMatcher.matches(any(StubbedRequest.class), eq(request))).willReturn(false);

        // When
        final StubbedRequest actual = finder.find(paths, request);

        // Then
        assertThat(actual, nullValue());
    }

}