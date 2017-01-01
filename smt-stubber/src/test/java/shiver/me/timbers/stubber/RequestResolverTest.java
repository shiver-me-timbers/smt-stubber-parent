package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.matchers.Matchers.hasFieldThat;

public class RequestResolverTest {

    private PathResolver pathResolver;
    private Resources resources;
    private RequestFinder requestFinder;
    private RequestResolver resolver;

    @Before
    public void setUp() {
        pathResolver = mock(PathResolver.class);
        resources = mock(Resources.class);
        requestFinder = mock(RequestFinder.class);
        resolver = new RequestResolver(pathResolver, resources, requestFinder);
    }

    @Test
    public void Can_find_the_relevant_request() throws IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String path = someString();
        @SuppressWarnings("unchecked")
        final List<String> filePaths = mock(List.class);

        final StubbedRequest expected = mock(StubbedRequest.class);

        // Given
        given(pathResolver.resolvePath(request)).willReturn(path);
        given(resources.listFiles(path)).willReturn(filePaths);
        given(requestFinder.find(path, filePaths, request)).willReturn(expected);

        // When
        final StubbedRequest actual = resolver.resolveRequest(request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_fail_to_find_any_stubbing_for_the_request() throws IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(pathResolver.resolvePath(request)).willReturn(null);

        // When
        final StubbedRequest actual = resolver.resolveRequest(request);

        // Then
        assertThat(actual, nullValue());
        verifyZeroInteractions(resources, requestFinder);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_fail_to_find_the_relevant_request() throws IOException {

        final String path = someString();
        final HttpServletRequest request = mock(HttpServletRequest.class);

        final List<String> filePaths = mock(List.class);

        // Given
        given(pathResolver.resolvePath(request)).willReturn(path);
        given(resources.listFiles(path)).willReturn(filePaths);
        given(requestFinder.find(path, filePaths, request)).willReturn(null);

        // When
        final StubbedRequest actual = resolver.resolveRequest(request);

        // Then
        assertThat(actual, allOf(hasFieldThat("name", isEmptyString()), hasField("path", path)));
    }
}