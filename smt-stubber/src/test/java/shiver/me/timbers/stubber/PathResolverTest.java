package shiver.me.timbers.stubber;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class PathResolverTest {

    @Test
    public void Can_resolve_the_path_from_a_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final String method = someString();
        final String path = someString();

        // Given
        given(request.getMethod()).willReturn(method);
        given(request.getServletPath()).willReturn(path);

        // When
        final String actual = new PathResolver().resolvePath(request);

        // Then
        assertThat(actual, equalTo(method + path));
    }
}