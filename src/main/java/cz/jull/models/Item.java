package cz.jull.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.jull.Game;
import cz.jull.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String description;
    private boolean hidden;

    @JsonIgnore
    private boolean usable = true;

    @JsonCreator
    public Item(String id) {
        this.id = id;
    }

    public void useItem(Game game) throws Exception {
        if (!usable) {
            return;
        }
        Player player = game.getPlayer();
        switch (id) {
            case "item_emf_detector" -> {

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
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hidden=" + hidden +
                '}';
    }
}
