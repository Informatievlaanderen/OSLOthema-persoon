package be.vlaanderen.pocs.oslopersoon.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.URI;

/**
 * Deserializer die JSON-LD blank node identifiers (_:label) omzet naar een geldige URI (urn:blank:label).
 * Wanneer blank nodes later echte linked data URIs worden, blijft deze deserializer transparant werken.
 */
public class BlankNodeUriDeserializer extends StdDeserializer<URI> {

    private static final String BLANK_NODE_PREFIX = "_:";
    private static final String URN_BLANK_PREFIX = "urn:blank:";

    public BlankNodeUriDeserializer() {
        super(URI.class);
    }

    @Override
    public URI deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value != null && value.startsWith(BLANK_NODE_PREFIX)) {
            return URI.create(URN_BLANK_PREFIX + value.substring(BLANK_NODE_PREFIX.length()));
        }
        return URI.create(value);
    }
}