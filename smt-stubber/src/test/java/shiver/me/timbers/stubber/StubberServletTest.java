package shiver.me.timbers.stubber;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StubberServletTest {

    @Test
    public void Can_stub_a_request() throws ServletException, IOException {

        final RequestResolver requestResolver = mock(RequestResolver.class);
        final ResponseWriter responseWriter = mock(ResponseWriter.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        final StubbedRequest stubbedRequest = mock(StubbedRequest.class);

        // Given
        given(requestResolver.resolveRequest(request)).willReturn(stubbedRequest);

        // When
        new StubberServlet(requestResolver, responseWriter).service(request, response);

        // Then
        verify(responseWriter).write(response, stubbedRequest);
    }

}