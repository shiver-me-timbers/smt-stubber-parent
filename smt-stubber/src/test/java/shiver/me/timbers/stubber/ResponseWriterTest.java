package shiver.me.timbers.stubber;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.InOrder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.matchers.Matchers.hasField;

public class ResponseWriterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_write_a_response() throws IOException {

        final ResponseResolver responseResolver = mock(ResponseResolver.class);
        final Iterables iterables = mock(Iterables.class);
        final IO io = mock(IO.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final StubbedRequest request = mock(StubbedRequest.class);

        final StubbedResponse stubbedResponse = mock(StubbedResponse.class);
        final Map<String, List<String>> headers = mock(Map.class);
        final Set<Entry<String, List<String>>> headersEntrySet = mock(Set.class);
        final InputStream inputStream = mock(InputStream.class);
        final ServletOutputStream outputStream = mock(ServletOutputStream.class);

        // Given
        given(responseResolver.resolveResponse(request)).willReturn(stubbedResponse);
        given(stubbedResponse.getHeaders()).willReturn(headers);
        given(headers.entrySet()).willReturn(headersEntrySet);
        given(stubbedResponse.getInputStream()).willReturn(inputStream);
        given(response.getOutputStream()).willReturn(outputStream);

        // When
        new ResponseWriter(responseResolver, iterables, io).write(response, request);

        // Then
        final InOrder order = inOrder(iterables, io);
        order.verify(iterables).forEach(eq(headersEntrySet), argThat(allOf(
            Matchers.<ApplyHeaders>instanceOf(ApplyHeaders.class),
            hasField("response", response)
        )));
        order.verify(io).write(inputStream, outputStream);
    }
}