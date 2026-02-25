package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.PostCommandActionType;
import cz.jull.command.Response;
import cz.jull.models.Item;
import cz.jull.models.locations.Side;
import cz.jull.models.npc.HostileNPC;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * Manages the combat encounters between the {@link Player} and {@link HostileNPC}s.
 */
public class FightManager {
    @Getter
    private HostileNPC currentEnemy;
    private boolean isPlayerDefending = false;

    /**
     * Checks if a combat encounter is currently active.
     * @return true if there is an active enemy; false otherwise.
     */
    public boolean isFighting() {
        return currentEnemy != null;
    }

    /**
     * Attempts to initiate combat based on the NPCs present at the player's current location side.
     * @param game The main game instance.
     */
    public void startFight(Game game) {
        Player player = game.getPlayer();
        Side side = player.getCurrentSide();

        if (side == null || side.getNpcs() == null) {
            System.out.println("You can't attack, no monster nearby.");
            return;
        }

        Optional<NPC> hostile = side.getNpcs().stream()
                .filter(npc -> npc instanceof HostileNPC)
                .findFirst();

        if (hostile.isPresent()) {
            currentEnemy = (HostileNPC) hostile.get();
            System.out.println("--- COMBAT STARTED ---");
            System.out.println("You are facing " + currentEnemy.getName());
            System.out.println("Health: " + currentEnemy.getHealth() + " | Strength: " + currentEnemy.getStrength());
            System.out.println();
        } else {
            System.out.println("Nothing hostile here.");
        }
    }

    /**
     * Executes a player attack turn. If not currently in combat, attempts to start one.
     * If the enemy survives the attack, triggers the monster's counter-turn.
     * @param game The main game instance.
     * @return {@link Response}
     */
    public Response performAttack(Game game) {
        if (!isFighting()) {
            startFight(game);
            if (!isFighting()) new Response();
        }

        Player player = game.getPlayer();

        int damage = player.getAttackDamage();

        System.out.println("You attacked " + currentEnemy.getName() + " with " +
                (player.getEquippedItem() != null ? player.getEquippedItem().getName() : "fists") +
                " for " + damage + " damage."
        );

        currentEnemy.setHealth(currentEnemy.getHealth() - damage);

        System.out.println(currentEnemy + " health: " + currentEnemy.getHealth() + "/100");

        if (currentEnemy.getHealth() <= 0) {
            return handleVictory(game);
        }

        return performMonsterTurn(game);
    }

    /**
     * Sets the player's state to defending, reducing incoming damage for the next monster attack.
     * @param game The main game instance.
     * @return {@link Response}
     */
    public Response performDefense(Game game) {
        if (!isFighting()) {
            return new Response("There is nothing to defend against.");
        }

        System.out.println("You brace yourself for an incoming attack...");
        isPlayerDefending = true;

        Response result = performMonsterTurn(game);

        isPlayerDefending = false;
        return result;
    }

    /**
     * Logic for the enemy's turn. Calculates damage dealt to the player,
     * @param game The main game instance.
     * @return {@link Response}
     */
    private Response performMonsterTurn(Game game) {
        Player player = game.getPlayer();
        int damage = currentEnemy.getStrength();

        if (isPlayerDefending) {
            damage = Math.max(1, damage / 2);
            System.out.println("You blocked part of the attack");
        }

        System.out.println();
        System.out.println(currentEnemy.getName() + " attacks you dealing " + damage + " damage.");

        int newHealth = Math.max(0, player.getHealth() - damage);

        try {
            player.setHealth(newHealth);
            System.out.println("Your health: " + player.getHealth() + "/100");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (player.getHealth() <= 0) {
            return new Response(PostCommandActionType.DEAD);
        }

        return new Response();
    }

    /**
     * Cleans up the combat state after an enemy is defeated.
     * @param game The main game instance.
     * @return {@link Response}
     */
    private Response handleVictory(Game game) {
        System.out.println("You have defeated " + currentEnemy.getName());

        game.getPlayer().getCurrentSide().getNpcs().remove(currentEnemy);
        List<Item> itemsToGive = currentEnemy.getItems();
        for (Item item : itemsToGive) {
            game.getPlayer().addItemToInventory(item);
            System.out.println("You obtained: " + item.getName());
        }

        currentEnemy = null;
        isPlayerDefending = false;

        return new Response();
    }
}

