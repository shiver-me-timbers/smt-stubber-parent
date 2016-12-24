package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class RequestResolverTest {

    private Resources resources;
    private RequestFinder requestFinder;
    private RequestResolver resolver;
    private Paths paths;

    @Before
    public void setUp() {
        resources = mock(Resources.class);
        requestFinder = mock(RequestFinder.class);
        paths = mock(Paths.class);
        resolver = new RequestResolver(resources, requestFinder, paths);
    }

    @Test
    public void Can_find_the_relevant_request() throws IOException {

        final String path = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);

        @SuppressWarnings("unchecked")
        final List<String> paths = mock(List.class);

        final StubbedRequest expected = mock(StubbedRequest.class);

        // Given
        given(resources.listFiles(path)).willReturn(paths);
        given(requestFinder.find(paths, request)).willReturn(expected);

        // When
        final StubbedRequest actual = resolver.resolveRequest(request, path);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_find_the_relevant_request_in_a_parent_path() throws IOException {

        final String path = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final List<String> filePaths = mock(List.class);
        final String parentPath = someString();
        final List<String> parentFilePaths = mock(List.class);

        final StubbedRequest expected = mock(StubbedRequest.class);

        // Given
        given(resources.listFiles(path)).willReturn(filePaths);
        given(requestFinder.find(filePaths, request)).willReturn(null);
        given(paths.parentPath(path)).willReturn(parentPath);
        given(resources.listFiles(parentPath)).willReturn(parentFilePaths);
        given(requestFinder.find(parentFilePaths, request)).willReturn(expected);

        // When
        final StubbedRequest actual = resolver.resolveRequest(request, path);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_fail_to_find_the_relevant_request() throws IOException {

        final String path = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final List<String> filePaths = mock(List.class);

        // Given
        given(resources.listFiles(path)).willReturn(filePaths);
        given(requestFinder.find(filePaths, request)).willReturn(null);
        given(paths.parentPath(path)).willReturn("");

        // When
        final StubbedRequest actual = resolver.resolveRequest(request, path);

        // Then
        assertThat(actual, nullValue());
    }
}