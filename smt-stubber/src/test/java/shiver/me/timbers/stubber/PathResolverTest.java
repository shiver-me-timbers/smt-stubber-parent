package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class PathResolverTest {

    private Paths paths;
    private PathResolver resolver;

    @Before
    public void setUp() {
        paths = mock(Paths.class);
        resolver = new PathResolver(paths);
    }

    @Test
    public void Can_resolve_the_path_from_a_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String method = someString();
        final String path = someString();

        final String expected = method + path;

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getServletPath()).willReturn(path);
        given(paths.exists(expected)).willReturn(true);

        // When
        final String actual = resolver.resolvePath(request);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_resolve_the_default_path_from_a_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String method = someString();
        final String path = someString();
        final String requestPath = method + path;
        final String parentPath = someString();

        final String expected = parentPath + File.separator + '_';

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getServletPath()).willReturn(path);
        given(paths.exists(requestPath)).willReturn(false);
        given(paths.parentPath(requestPath)).willReturn(parentPath);
        given(paths.exists(expected)).willReturn(true);

        // When
        final String actual = resolver.resolvePath(request);

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_fail_to_resolve_a_path_from_a_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String method = someString();
        final String path = someString();
        final String requestPath = method + path;
        final String parentPath = someString();
        final String defaultPath = parentPath + File.separator + '_';

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getServletPath()).willReturn(path);
        given(paths.exists(requestPath)).willReturn(false);
        given(paths.parentPath(requestPath)).willReturn(parentPath);
        given(paths.exists(defaultPath)).willReturn(false);

        // When
        final String actual = resolver.resolvePath(request);

        // Then
        assertThat(actual, nullValue());
    }
}