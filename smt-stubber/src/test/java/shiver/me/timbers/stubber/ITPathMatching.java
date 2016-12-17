package shiver.me.timbers.stubber;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StubberConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITPathMatching {

    @Autowired
    private Client client;

    @Value("${local.server.port}")
    private int port;

    private WebTarget stub;

    @Before
    public void setUp() {
        stub = client.target("http://localhost:" + port);
    }

    @Test
    public void Can_stub_a_get_request() {

        // When
        final Response actual = stub.path("one").path("two").path("three").request().get();

        // Then
        assertThat(actual.getStatus(), equalTo(OK.getStatusCode()));
        assertThat(actual.getHeaderString("stubbed"), equalTo("header"));
        assertThat(actual.readEntity(String.class), equalTo("A stubbed body."));
    }
}
