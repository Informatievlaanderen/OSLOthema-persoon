package be.vlaanderen.pocs.oslopersoon.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.net.URI;

/**
 * Serializer die urn:blank:label URIs terugomzet naar JSON-LD blank node identifiers (_:label).
 * Spiegelbeeld van BlankNodeUriDeserializer, zodat de round-trip transparant is.
 */
public class BlankNodeUriSerializer extends StdSerializer<URI> {

    private static final String BLANK_NODE_PREFIX = "_:";
    private static final String URN_BLANK_PREFIX = "urn:blank:";

    public BlankNodeUriSerializer() {
        super(URI.class);
    }

    @Override
    public void serialize(URI value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String uri = value.toString();
        if (uri.startsWith(URN_BLANK_PREFIX)) {
            gen.writeString(BLANK_NODE_PREFIX + uri.substring(URN_BLANK_PREFIX.length()));
        } else {
            gen.writeString(uri);
        }
    }
}
