package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.locations.Side;
import cz.jull.models.npc.HostileNPC;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.Optional;

public class FightManager {
    @Getter
    private HostileNPC currentEnemy;
    private boolean isPlayerDefending = false;

    public boolean isFighting() {
        return currentEnemy != null;
    }

    public void startFight(Game game) {
        Player player = game.getPlayer();
        Side side = player.getCurrentSide();

        if (side == null || side.getNpcs() == null) {
            System.out.println("cant attack, no monster");
            return;
        }

        Optional<NPC> hostile = side.getNpcs().stream()
                .filter(npc -> npc instanceof HostileNPC)
                .findFirst();

        if (hostile.isPresent()) {
            this.currentEnemy = (HostileNPC) hostile.get();
            System.out.println("--- COMBAT STARTED ---");
            System.out.println("u are facing " + currentEnemy.getName());
            System.out.println("health: " + currentEnemy.getHealth() + " | strength: " + currentEnemy.getStrength());
        } else {
            System.out.println("nothing hostile here");
        }
    }

    public PostCommandActionType performAttack(Game game) {
        if (!isFighting()) {
            startFight(game);
            if (!isFighting()) return PostCommandActionType.NONE;
        }

        Player player = game.getPlayer();

        int damage = player.getAttackDamage();

        System.out.println("u attacked " + currentEnemy.getName() + " with " +
                (player.getEquippedItem() != null ? player.getEquippedItem().getName() : "fists") +
                " for " + damage + " damage"
        );

        currentEnemy.setHealth(currentEnemy.getHealth() - damage);

        if (currentEnemy.getHealth() <= 0) {
            return handleVictory(game);
        }

        return performMonsterTurn(game);
    }

    public PostCommandActionType performDefense(Game game) {
        if (!isFighting()) {
            System.out.println("there is nothing to defend against");
            return PostCommandActionType.NONE;
        }

        System.out.println("u brace yourself for an incoming attack...");
        this.isPlayerDefending = true;

        PostCommandActionType result = performMonsterTurn(game);

        this.isPlayerDefending = false;
        return result;
    }

    private PostCommandActionType performMonsterTurn(Game game) {
        Player player = game.getPlayer();
        int damage = currentEnemy.getStrength();

        if (isPlayerDefending) {
            damage = Math.max(1, damage / 2);
            System.out.println("u blocked part of the attack");
        }

        System.out.println(currentEnemy.getName() + " attacks you dealing " + damage + " damage");

        int newHealth = Math.max(0, player.getHealth() - damage);

        try {
            player.setHealth(newHealth);
            System.out.println("ur health: " + player.getHealth() + "/100");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (player.getHealth() <= 0) {
            return PostCommandActionType.DEAD;
        }

        return PostCommandActionType.NONE;
    }

    private PostCommandActionType handleVictory(Game game) {
        System.out.println("u have defeated " + currentEnemy.getName());

        game.getPlayer().getCurrentSide().getNpcs().remove(currentEnemy);

        currentEnemy = null;
        isPlayerDefending = false;

        return PostCommandActionType.NONE;
    }
}

