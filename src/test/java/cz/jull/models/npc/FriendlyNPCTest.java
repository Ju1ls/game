package cz.jull.models.npc;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.mechanics.dialog.Dialog;
import cz.jull.mechanics.dialog.DialogManager;
import cz.jull.models.Item;
import cz.jull.models.locations.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FriendlyNPCTest extends FriendlyNPC {

    private Game dummyGame;
    private Player dummyPlayer;
    private Side dummySide;
    private Dialog capturedDialog;

    @BeforeEach
    void setUpEnvironment() {
        dummyPlayer = new Player();

        dummySide = new Side();
        dummySide.setItems(new ArrayList<>());
        dummySide.setNpcs(new ArrayList<>());
        dummyPlayer.setCurrentSide(dummySide);

        this.setItems(new ArrayList<>());

        DialogManager dummyDialogManager = new DialogManager() {
            @Override
            public void startDialog(Game game, FriendlyNPC npc, Dialog root) {
                capturedDialog = root;
            }
        };

        dummyGame = new Game() {
            @Override
            public Player getPlayer() {
                return dummyPlayer;
            }

            @Override
            public DialogManager getDialogManager() {
                return dummyDialogManager;
            }
        };
    }

    @Test
    void testIsDetectableByEmf() {
        assertFalse(this.isDetectableByEmf(), "Friendly NPCs should return false for EMF detection");
    }

    @Test
    void testInteract_MillerWithNoBatteries() {
        this.setId("npc_friendly_miller");
        this.interact(dummyGame);
        assertNotNull(capturedDialog);
        assertTrue(capturedDialog.getText().contains("scavenger"), "Should start Miller's dialogue");
    }

    @Test
    void testInteract_Arthur() {
        this.setId("npc_friendly_arthur");
        this.interact(dummyGame);
        assertNotNull(capturedDialog);
        assertTrue(capturedDialog.getText().contains("dangerous"), "Should start Arthur's dialogue");
    }

    @Test
    void testInteract_EleanorClarke_NoItems() {
        this.setId("npc_friendly_eleanor_clarke");
        this.interact(dummyGame);
        assertNotNull(capturedDialog);
        assertTrue(capturedDialog.getText().contains("My head..."), "Should start Eleanor's default dialogue");
    }

    @Test
    void testInteract_EleanorClarke_AlreadyHasSecondKey() {
        this.setId("npc_friendly_eleanor_clarke");
        dummyPlayer.addItemToInventory(new Item("item_second_key"));

        this.interact(dummyGame);

        assertNotNull(capturedDialog);
        assertTrue(capturedDialog.getText().contains("Thank you for your help."));
    }

    @Test
    void testInteract_UnknownNPC_Fallback() {
        this.setId("npc_friendly_unknown_guy");
        this.interact(dummyGame);
        assertNotNull(capturedDialog);
        assertEquals("...", capturedDialog.getText(), "Unknown IDs should fallback to '...'");
    }

    @Test
    void testPutItemOnGround_Success() {
        Item itemToDrop = new Item("item_oxygen_mask");
        itemToDrop.setName("Oxygen Mask");
        this.getItems().add(itemToDrop);

        this.putItemOnGround(dummyGame, "item_oxygen_mask");

        assertTrue(this.getItems().isEmpty(), "NPC should no longer have the item");
        assertEquals(1, dummySide.getItems().size(), "Item should be added to the Side");
        assertEquals("item_oxygen_mask", dummySide.getItems().getFirst().getId());
    }

    @Test
    void testPutItemOnGround_ItemMissing() {
        this.putItemOnGround(dummyGame, "item_not_here");
        assertTrue(dummySide.getItems().isEmpty());
    }

    @Test
    void testTakeItemFromPlayer_Success() {
        Item itemToTake = new Item("item_batteries");
        itemToTake.setName("Batteries");
        dummyPlayer.addItemToInventory(itemToTake);
        this.setName("Miller");

        this.takeItemFromPlayer(dummyGame, "item_batteries");

        assertTrue(dummyPlayer.getInventory().isEmpty(), "Player should no longer have the item");
    }

    @Test
    void testTakeItemFromPlayer_ItemMissing() {
        this.takeItemFromPlayer(dummyGame, "item_ghost");
        assertTrue(dummyPlayer.getInventory().isEmpty());
    }

    @Test
    void testRemoveSelf_Success() {
        this.setName("Arthur");
        dummySide.getNpcs().add(this);

        assertTrue(dummySide.getNpcs().contains(this));

        this.removeSelf(dummyGame);

        assertFalse(dummySide.getNpcs().contains(this), "NPC should be removed from the Side's NPC list");
    }
}