package shiver.me.timbers.stubber;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@SpringBootApplication
public class StubberConfiguration {

    @Bean
    public Client client() {
        return ClientBuilder.newClient();
    }

    @Bean
    public ServletRegistrationBean stubberServlet() {
        return new ServletRegistrationBean(new StubberServlet(), "/");
    }
}
