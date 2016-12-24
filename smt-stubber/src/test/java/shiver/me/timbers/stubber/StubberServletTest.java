package shiver.me.timbers.stubber;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class StubberServletTest {

    @Test
    public void Can_stub_a_request() throws ServletException, IOException {

        final PathResolver pathResolver = mock(PathResolver.class);
        final RequestResolver requestResolver = mock(RequestResolver.class);
        final ResponseWriter responseWriter = mock(ResponseWriter.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        final String path = someString();
        final StubbedRequest stubbedRequest = mock(StubbedRequest.class);

        // Given
        given(pathResolver.resolvePath(request)).willReturn(path);
        given(requestResolver.resolveRequest(request, path)).willReturn(stubbedRequest);

        // When
        new StubberServlet(pathResolver, requestResolver, responseWriter).service(request, response);

        // Then
        verify(responseWriter).write(response, stubbedRequest);
    }

}