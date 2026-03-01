package cz.jull.models.npc;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.mechanics.dialog.Dialog;
import cz.jull.mechanics.dialog.DialogOnEnd;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a non-hostile entity within the game world.
 */
@NoArgsConstructor
@Getter
@Setter
public class FriendlyNPC extends NPC {

    public FriendlyNPC(String id, String name, String description, List<Item> items) {
        super(id, name, description, items);
    }

    /**
     * Entry point for player interaction. Switches between specific dialog
     * trees based on the NPC's ID.
     * @param game The main game instance
     */
    @Override
    public void interact(Game game) {
        Dialog root = switch (getId()) {
            case "npc_friendly_miller" -> createMillerDialog(game);
            case "npc_friendly_arthur" -> createArthurDialog(game);
            case "npc_friendly_eleanor_clarke" -> createEleanorDialog(game);
            default -> new Dialog("...", null, null);
        };
        game.getDialogManager().startDialog(game, this, root);
    }

    /**
     * Determines if this NPC is detectable by the EMF reader.
     * @return Always false for FriendlyNPCs as they are living/physical entities.
     */
    @Override
    public boolean isDetectableByEmf() {
        return false;
    }

    /**
     * Creates the dialogue tree for Miller, involving a trade for batteries.
     * @param game The main game instance.
     * @return A {@link Dialog} object representing the start of Miller's interaction.
     */
    protected Dialog createMillerDialog(Game game) {
        boolean hasBatteries = playerHasItem(game.getPlayer(), "item_batteries");

        Dialog endSuccess = new Dialog("Great. I needed these. Here, take this mask, I have no use for it anymore.", null, (g) -> {
            takeItemFromPlayer(g, "item_batteries");

            if (game.getPlayer().getInventory().size() >= 6) {
                putItemOnGround(g, "item_oxygen_mask");
            } else {
                giveItemToPlayer(g, "item_oxygen_mask");
            }
            removeSelf(g);
        });

        Dialog endFail = new Dialog("Damn it... I really need those batteries.", null, null);

        List<DialogOnEnd.AskQuestion.Answer> options = new ArrayList<>();

        if (hasBatteries) {
            options.add(new DialogOnEnd.AskQuestion.Answer("Give Batteries", endSuccess));
        }
        options.add(new DialogOnEnd.AskQuestion.Answer("I don't have them / No", endFail));

        return new Dialog("Hey you... you look like a scavenger. I need batteries. Do you have any?",
                new DialogOnEnd.AskQuestion("Do you have batteries?", options.toArray(DialogOnEnd.AskQuestion.Answer[]::new)), null);
    }

    /**
     * Creates the dialogue tree for Arthur, who provides items without conditions.
     * @param game The main game instance.
     * @return A {@link Dialog} object where Arthur gifts items to the player.
     */
    protected Dialog createArthurDialog(Game game) {
        Dialog reward = new Dialog("Also take this... it will help you in this city", null, (g) -> {
            if (game.getPlayer().getInventory().size() >= 6) {
                putItemOnGround(g, "item_alcohol");
                putItemOnGround(g, "item_drugs");
            } else {
                giveItemToPlayer(g, "item_alcohol");
                giveItemToPlayer(g, "item_drugs");
            }
            removeSelf(g);
        });

        Dialog place = new Dialog("Okay, if you're gonna go to a Tunnel go to its end, there is a Bunker. \n" +
                "But its locked. You will probably need some keys. Try to find them.", new DialogOnEnd.Continue(reward));

        List<DialogOnEnd.AskQuestion.Answer> options = new ArrayList<>();
        options.add(new DialogOnEnd.AskQuestion.Answer("Yes", place));

        return new Dialog("This place is pretty dangerous. But i know about a place that can save you.\nWanna know about it?",
                new DialogOnEnd.AskQuestion(null, options.toArray(DialogOnEnd.AskQuestion.Answer[]::new)));
    }

    /**
     * Creates the dialogue tree for Eleanor Clarke, involving complex item-based branching.
     * @param game The main game instance.
     * @return A {@link Dialog} object representing Eleanor's plea for help.
     */
    protected Dialog createEleanorDialog(Game game) {
        if (playerHasItem(game.getPlayer(), "item_second_key")) {
            return new Dialog("Thank you for your help.", null);
        }

        boolean hasAlcohol = playerHasItem(game.getPlayer(), "item_alcohol");
        boolean hasDrugs = playerHasItem(game.getPlayer(), "item_drugs");

        Dialog giveKeyOnly = new Dialog("Take this key, it will help you.", null,
                (g) -> {
                    if (game.getPlayer().getInventory().size() >= 6) {
                        putItemOnGround(g, "item_second_key");
                    } else {
                        giveItemToPlayer(g, "item_second_key");
                    }
                    removeSelf(g);
                }
        );

        List<DialogOnEnd.AskQuestion.Answer> options = getAnswers(hasAlcohol, hasDrugs, giveKeyOnly, game);

        return new Dialog("My head... I need something to dull the pain.",
                new DialogOnEnd.AskQuestion("Help her?", options.toArray(DialogOnEnd.AskQuestion.Answer[]::new)));
    }

    /**
     * Generates a list of dialogue answers based on the player's current inventory.
     * @param hasAlcohol Whether the player possesses alcohol.
     * @param hasDrugs Whether the player possesses drugs.
     * @param giveKeyOnly The fallback dialogue if no items are provided.
     * @return A list of {@link DialogOnEnd.AskQuestion.Answer} available to the player.
     */
    protected List<DialogOnEnd.AskQuestion.Answer> getAnswers(boolean hasAlcohol, boolean hasDrugs, Dialog giveKeyOnly, Game game) {
        Dialog giveFullReward = new Dialog("Thank you. Take this key and medkit.", null, (g) -> {
            if (game.getPlayer().getInventory().size() >= 6) {
                putItemOnGround(g, "item_second_key");
                putItemOnGround(g, "item_medkit");
            } else {
                giveItemToPlayer(g, "item_second_key");
                giveItemToPlayer(g, "item_medkit");
            }
            removeSelf(g);
        });

        List<DialogOnEnd.AskQuestion.Answer> options = new ArrayList<>();

        if (hasAlcohol) {
            options.add(new DialogOnEnd.AskQuestion.Answer("Give Alcohol",
                    new Dialog("Needed that...", new DialogOnEnd.Continue(giveFullReward),
                            (g) -> takeItemFromPlayer(g, "item_alcohol"))));
        }
        if (hasDrugs) {
            options.add(new DialogOnEnd.AskQuestion.Answer("Give Drugs",
                    new Dialog("The pain is fading...", new DialogOnEnd.Continue(giveFullReward),
                            (g) -> takeItemFromPlayer(g, "item_drugs"))));
        }

        options.add(new DialogOnEnd.AskQuestion.Answer("I have nothing", giveKeyOnly));
        return options;
    }

    /**
     * Checks if a player has a specific item in their inventory.
     * @param player The player whose inventory is being inspected.
     * @param id The ID of the item to search for.
     * @return {@code true} if the player has at least one instance of the item; {@code false} otherwise.
     */
    protected boolean playerHasItem(Player player, String id) {
        return player.getInventory().stream().anyMatch(i -> i.getId().equals(id));
    }

    /**
     * Transfers an item from this NPC to the player.
     * @param game The main game instance.
     * @param itemId The ID of the item to be transferred.
     */
    protected void giveItemToPlayer(Game game, String itemId) {
        Optional<Item> itemOpt = this.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            this.getItems().remove(item);
            game.getPlayer().addItemToInventory(item);
            System.out.println("You received: " + item.getName());
        } else {
            System.out.println("NPC missing item in JSON: " + itemId);
        }
    }

    /**
     * Transfers an item from this NPC to the current side.
     * @param game The main game instance.
     * @param itemId The ID of the item to be transferred.
     */
    protected void putItemOnGround(Game game, String itemId) {
        Optional<Item> itemOpt = this.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            this.getItems().remove(item);
            game.getPlayer().getCurrentSide().getItems().add(item);
            System.out.println("Item fell on the ground because you have full inventory: " + item.getName());
        } else {
            System.out.println("NPC missing item in JSON: " + itemId);
        }
    }

    /**
     * Removes a specific item from the player's inventory.
     * @param game The main game instance.
     * @param itemId The ID of the item to be removed from the player.
     */
    protected void takeItemFromPlayer(Game game, String itemId) {
        Player player = game.getPlayer();
        Optional<Item> itemToRemove = player.getInventory().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        if (itemToRemove.isPresent()) {
            player.removeItemFromInventory(itemToRemove.get());
            System.out.println("You gave " + this.getName() + ": " + itemToRemove.get().getName());
        }
    }

    /**
     * Removes this NPC from the current location (Side).
     */
    protected void removeSelf(Game game) {
        game.getPlayer().getCurrentSide().getNpcs().remove(this);
        System.out.println(this.getName() + " left.");
    }
}
