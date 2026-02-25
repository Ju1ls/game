package cz.jull.models;

import com.fasterxml.jackson.annotation.*;
import cz.jull.Game;
import cz.jull.Player;
import cz.jull.models.npc.NPC;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * Represents a tangible object within the game world that the player can interact with.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Item {
    private String id;
    private String name;
    private String description;
    private boolean hidden;

    @JsonProperty("single_use")
    private boolean singleUse;

    @JsonIgnore
    private boolean usable = true;

    @JsonCreator
    public Item(String id) {
        this.id = id;
    }

    /**
     * Executes the specific effect associated with this item's ID.
     * @param game The main game instance.
     * @throws Exception if specific setter logic fails.
     */
    public void useItem(Game game) throws Exception {
        if (!usable) {
            return;
        }
        Player player = game.getPlayer();
        switch (id) {
            case "item_emf_detector" -> {
                if (player.getDetectorBatteryLevel() <= 0) {
                    return;
                }

                boolean newFreq = !player.isEmfHighFrequency();
                player.setEmfHighFrequency(newFreq);
                player.setDetectorBatteryLevel(player.getDetectorBatteryLevel() - 2);

                boolean monsterDetected = false;
                if (player.getCurrentLocation().getSides() != null) {
                    monsterDetected = player.getCurrentLocation().getSides().values().stream()
                            .filter(side -> side.getNpcs() != null)
                            .flatMap(side -> side.getNpcs().stream())
                            .anyMatch(NPC::isDetectableByEmf);
                }

                String mode = newFreq ? "HIGH FREQUENCY" : "LOW FREQUENCY";
                System.out.println("Detector switched to " + mode + " band");

                if (monsterDetected) {
                    if (newFreq) {
                        System.out.println("Detector is beeping fast.");
                    } else {
                        System.out.println("Detector is beeping slowly.");
                    }
                } else {
                    System.out.println("All good.");
                }
            }
            case "item_oxygen_mask" -> {
                usable = false;
                player.setHasOxygenMask(true);
                game.getScheduledTaskManager().scheduler.schedule(() -> {
                    player.setHasOxygenMask(false);
                    try {
                        player.setOxygen(100);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 10, TimeUnit.MINUTES);
            }
            case "item_knife", "item_glass_shard" -> {
                player.setEquippedItem(this);
                if (player.getMentalHealth() <= 30 && player.getMentalHealth() > 20) {
                    player.setHealth(player.getHealth() - 10);
                }
                if (player.getMentalHealth() <= 20 && player.getMentalHealth() > 10) {
                    player.setHealth(player.getHealth() - 20);
                }
                if (player.getMentalHealth() <= 10) {
                    player.setHealth(0);
                }
            }
            case "item_batteries" -> {
                player.setDetectorBatteryLevel(100);
            }
            case "item_medkit" -> {
                player.setHealth(player.getHealth() + 10);
            }
            case "item_drugs", "item_alcohol" -> {
                player.setMentalHealth(player.getMentalHealth() + 10);
                player.setHealth(player.getHealth() - 15);
            }
        }
    }

    @Override
    public String toString() {
        return " \n" +
                "Name: " + name + "\n";
    }
}
