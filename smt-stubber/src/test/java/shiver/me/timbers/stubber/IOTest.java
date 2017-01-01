package shiver.me.timbers.stubber;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class IOTest {

    @Test
    public void Can_write_an_input_stream_to_an_output_stream() throws IOException {

        // Given
        final String expected = someString();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When
        new IO().write(new ByteArrayInputStream(expected.getBytes()), outputStream);

        // Then
        assertThat(new String(outputStream.toByteArray()), equalTo(expected));
    }
}