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

public class NPCDeserializer extends StdDeserializer<NPC> {

    public NPCDeserializer() {
        this(null);
    }

    public NPCDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Custom deserialization logic to instantiate the correct NPC subclass based on the 'id' field.
     * @param jsonParser The parser reading the JSON data.
     * @param context The deserialization context.
     * @return Instance of an NPC (either Hostile or Friendly).
     * @throws IOException IOException If an I/O error occurs during reading.
     * @throws JsonProcessingException JsonProcessingException If the JSON is invalid.
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
