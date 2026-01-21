package cz.jull.json_loader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.jull.models.npc.FriendlyNPC;
import cz.jull.models.npc.HostileNPC;
import cz.jull.models.npc.NPC;

import java.io.IOException;

/**
 * A custom Jackson deserializer for the {@link NPC} abstract class hierarchy.
 * <p>
 * This class handles polymorphic deserialization by inspecting the {@code "id"} field
 * in the JSON data. Instead of relying on specific fields or explicit type properties,
 * it deduces the subclass based on naming conventions found within the ID string
 * (e.g., checking for the substring "hostile").
 * </p>
 */
public class NPCDeserializer extends StdDeserializer<NPC> {

    /**
     * Default constructor required by Jackson.
     */
    public NPCDeserializer() {
        this(null);
    }

    /**
     * Constructor used by the framework with a specific class type.
     *
     * @param vc The value class.
     */
    public NPCDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Deserializes a JSON object into a specific subclass of {@link NPC}.
     * <p>
     * The logic follows these steps:
     * <ol>
     * <li>Reads the JSON content into a {@link JsonNode} tree.</li>
     * <li>Extracts the {@code "id"} field.</li>
     * <li>If the ID contains the substring {@code "hostile"}, maps to {@link HostileNPC}.</li>
     * <li>If the ID contains {@code "friendly"} (or as a default fallback), maps to {@link FriendlyNPC}.</li>
     * </ol>
     * </p>
     *
     * @param jsonParser The JsonParser used to read JSON content.
     * @param context The deserialization context.
     * @return An instance of {@link HostileNPC} or {@link FriendlyNPC}.
     * @throws IOException             If a low-level I/O problem (unexpected end-of-input, network error) occurs.
     * @throws JsonProcessingException If the JSON content is invalid or cannot be mapped to the target class.
     */
    @Override
    public NPC deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);

        if (node.has("id")) {
            String id = node.get("id").asText();

            if (id.contains("hostile")) {
                return mapper.treeToValue(node, HostileNPC.class);
            } else if (id.contains("friendly")) {
                return mapper.treeToValue(node, FriendlyNPC.class);
            }
        }

        return mapper.treeToValue(node, FriendlyNPC.class);
    }
}
