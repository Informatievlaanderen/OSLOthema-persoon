package be.vlaanderen.pocs.oslopersoon;

import be.vlaanderen.pocs.oslopersoon.model.VdipEnvelope;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GeregistreerdPersoonJsonLdDeserializationTest
{

    private static final Path OUTPUT_DIR = Path.of("target/test-output/jsonld-roundtrip");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "json/example-00040527291.json",
            "json/example-00061547618.json",
            "json/example-25012021508.json",
            "json/example-29012818180.json",
            "json/example-35060735122.json", // Met Uitzondering
            "json/example-54123035065.json",
            "json/example-64042106496.json",
            "json/example-84121725318.json"
    })
    void shouldDeserializeAndReserializeJsonLd(String resourcePath) throws Exception {
        JsonNode inputJson;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            assertNotNull(is, "Testbestand niet gevonden: " + resourcePath);
            inputJson = objectMapper.readTree(is);
        }

        VdipEnvelope persoon = objectMapper.treeToValue(inputJson, VdipEnvelope.class);
        assertNotNull(persoon, "Deserialisatie gaf null terug voor: " + resourcePath);

        validateResponse(resourcePath, persoon);

        String filename = Path.of(resourcePath).getFileName().toString();
        Files.createDirectories(OUTPUT_DIR);
        Path outputFile = OUTPUT_DIR.resolve(filename);
        objectMapper.writeValue(outputFile.toFile(), persoon);

        JsonNode outputJson = objectMapper.readTree(outputFile.toFile());
        List<String> missing = findMissingFields(inputJson, outputJson, "");
        if (!missing.isEmpty()) {
            System.out.println("Velden aanwezig in input maar ontbrekend of gewijzigd in output (" + resourcePath + "):");
            missing.forEach(f -> System.out.println("  - " + f));
        }
        assertTrue(missing.isEmpty(), "Round-trip dataverlies in " + resourcePath + ":\n" + String.join("\n", missing));

        System.out.println(Files.readString(outputFile));
    }

    private void validateResponse(String resourcePath, VdipEnvelope persoon) {
        Set<ConstraintViolation<VdipEnvelope>> violations = validator.validate(persoon);
        if (!violations.isEmpty()) {
            System.out.println("Validatiefouten in " + resourcePath + ":");
            violations.forEach(v -> System.out.println("  - " + v.getPropertyPath() + ": " + v.getMessage()));
        }
        assertTrue(violations.isEmpty(), "JSR-303 validatie mislukt voor " + resourcePath + ":\n"
                + violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).reduce("", (a, b) -> a + "\n" + b));
    }

    private List<String> findMissingFields(JsonNode input, JsonNode output, String path) {
        List<String> missing = new ArrayList<>();
        if (input.isObject()) {
            input.fields().forEachRemaining(entry -> {
                String fieldPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
                if (!output.has(entry.getKey())) {
                    missing.add(fieldPath + " (ontbreekt volledig)");
                } else {
                    missing.addAll(findMissingFields(entry.getValue(), output.get(entry.getKey()), fieldPath));
                }
            });
        } else if (input.isArray() && output.isArray()) {
            for (int i = 0; i < input.size(); i++) {
                if (i < output.size()) {
                    missing.addAll(findMissingFields(input.get(i), output.get(i), path + "[" + i + "]"));
                } else {
                    missing.add(path + "[" + i + "] (ontbreekt in output)");
                }
            }
        } else if (!input.equals(output)) {
            missing.add(path + " (waarde verschilt: input=" + input + ", output=" + output + ")");
        }
        return missing;
    }
}