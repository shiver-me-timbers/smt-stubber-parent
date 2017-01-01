package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class ResponseResolverTest {

    private ResponseFinder responseFinder;
    private ResponseResolver resolver;

    @Before
    public void setUp() {
        responseFinder = mock(ResponseFinder.class);
        resolver = new ResponseResolver(responseFinder);
    }

    @Test
    public void Will_respond_with_a_404_not_found_if_no_request_was_stubbed() {

        // When
        final StubbedResponse actual = resolver.resolveResponse(null);

        // Then
        assertThat(actual, instanceOf(StubbedNotFoundResponse.class));
        verifyZeroInteractions(responseFinder);
    }

    @Test
    public void Can_resolve_a_response_that_matches_the_stubbed_request_name() {

        final StubbedRequest request = mock(StubbedRequest.class);

        final StubbedResponse expected = mock(StubbedResponse.class);

        // Given
        given(responseFinder.findByName(request)).willReturn(expected);

        // When
        final StubbedResponse actual = resolver.resolveResponse(request);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_resolve_a_default_response() {

        final StubbedRequest request = mock(StubbedRequest.class);

        final StubbedResponse expected = mock(StubbedResponse.class);

        // Given
        given(responseFinder.findByName(request)).willReturn(null);
        given(responseFinder.findDefault(request)).willReturn(expected);

        // When
        final StubbedResponse actual = resolver.resolveResponse(request);

        // Then
        assertThat(actual, is(expected));
    }
}