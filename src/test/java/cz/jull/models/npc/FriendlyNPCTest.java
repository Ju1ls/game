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

/**
 * Unit tests for the {@link FriendlyNPC} class.
 * Validates unique interaction trees for specific story characters,
 * inventory transfers between NPCs and players, and removal logic.
 *
 * @author Julie Šefl
 */
class FriendlyNPCTest extends FriendlyNPC {

    private Game dummyGame;
    private Player dummyPlayer;
    private Side dummySide;
    private DialogManagerSpy dialogSpy;

    /**
     * Initializes a fresh test environment before each test case.
     * Sets up a {@link TestGame} with a {@link DialogManagerSpy} to capture NPC interactions.
     */
    @BeforeEach
    void setUpEnvironment() {
        dialogSpy = new DialogManagerSpy();
        dummyGame = new TestGame(dialogSpy);

        dummyPlayer = dummyGame.getPlayer();

        dummySide = new Side();
        dummySide.setItems(new ArrayList<>());
        dummySide.setNpcs(new ArrayList<>());
        dummyPlayer.setCurrentSide(dummySide);

        this.setItems(new ArrayList<>());
    }

    /**
     * Verifies that friendly NPCs do not trigger the EMF detector,
     * as they lack the supernatural signature of hostile entities.
     */
    @Test
    void testIsDetectableByEmf() {
        assertFalse(this.isDetectableByEmf(), "Friendly NPCs should return false for EMF detection");
    }

    /**
     * Tests Miller's specific dialogue branch.
     * Verifies that his ID triggers the "scavenger" dialogue root.
     */
    @Test
    void testInteract_MillerWithNoBatteries() {
        this.setId("npc_friendly_miller");
        this.interact(dummyGame);
        assertNotNull(dialogSpy.capturedDialog, "The dialog should have been captured by the spy");
        assertTrue(dialogSpy.capturedDialog.getText().contains("scavenger"));
    }

    /**
     * Tests Arthur's specific dialogue branch.
     * Verifies that his ID triggers the "dangerous" dialogue warning.
     */
    @Test
    void testInteract_Arthur() {
        this.setId("npc_friendly_arthur");
        this.interact(dummyGame);
        assertNotNull(dialogSpy.capturedDialog);
        assertTrue(dialogSpy.capturedDialog.getText().contains("dangerous"));
    }

    /**
     * Verifies Eleanor Clarke's default interaction when the player does
     * not have the required quest item.
     */
    @Test
    void testInteract_EleanorClarke_NoItems() {
        this.setId("npc_friendly_eleanor_clarke");
        this.interact(dummyGame);
        assertNotNull(dialogSpy.capturedDialog);
        assertTrue(dialogSpy.capturedDialog.getText().contains("My head..."), "Should start Eleanor's default dialogue");
    }

    /**
     * Verifies Eleanor Clarke's quest-completion interaction when the
     * player has the second key in their inventory.
     */
    @Test
    void testInteract_EleanorClarke_AlreadyHasSecondKey() {
        this.setId("npc_friendly_eleanor_clarke");
        dummyPlayer.addItemToInventory(new Item("item_second_key"));

        this.interact(dummyGame);

        assertNotNull(dialogSpy.capturedDialog);
        assertTrue(dialogSpy.capturedDialog.getText().contains("Thank you for your help."));
    }

    /**
     * Validates fallback behavior for NPCs with unrecognized or null IDs.
     */
    @Test
    void testInteract_UnknownNPC_Fallback() {
        this.setId("npc_friendly_unknown_guy");
        this.interact(dummyGame);
        assertNotNull(dialogSpy.capturedDialog);
        assertEquals("...", dialogSpy.capturedDialog.getText(), "Unknown IDs should fallback to '...'");
    }

    /**
     * Verifies successful item transfer from an NPC to the current game world (Side).
     */
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

    /**
     * Verifies that attempting to drop an item not possessed by the NPC results in no world changes.
     */
    @Test
    void testPutItemOnGround_ItemMissing() {
        this.putItemOnGround(dummyGame, "item_not_here");
        assertTrue(dummySide.getItems().isEmpty());
    }

    /**
     * Verifies that an NPC can successfully remove an item from the player's inventory.
     */
    @Test
    void testTakeItemFromPlayer_Success() {
        Item itemToTake = new Item("item_batteries");
        itemToTake.setName("Batteries");
        dummyPlayer.addItemToInventory(itemToTake);
        this.setName("Miller");

        this.takeItemFromPlayer(dummyGame, "item_batteries");

        assertTrue(dummyPlayer.getInventory().isEmpty(), "Player should no longer have the item");
    }

    /**
     * Ensures taking a non-existent item from the player does not cause errors or inventory changes.
     */
    @Test
    void testTakeItemFromPlayer_ItemMissing() {
        this.takeItemFromPlayer(dummyGame, "item_ghost");
        assertTrue(dummyPlayer.getInventory().isEmpty());
    }

    /**
     * Validates that the NPC can successfully remove itself from the list of entities
     * on the current {@link Side}.
     */
    @Test
    void testRemoveSelf_Success() {
        this.setName("Arthur");
        dummySide.getNpcs().add(this);

        assertTrue(dummySide.getNpcs().contains(this));

        this.removeSelf(dummyGame);

        assertFalse(dummySide.getNpcs().contains(this), "NPC should be removed from the Side's NPC list");
    }
}

/**
 * A specialized {@link DialogManager} that captures the root {@link Dialog} for testing purposes.
 */
class DialogManagerSpy extends DialogManager {
    public Dialog capturedDialog;

    @Override
    public void startDialog(Game game, FriendlyNPC npc, Dialog root) {
        this.capturedDialog = root;
    }
}

/**
 * A "Test" version of Game that returns spy manager.
 */
class TestGame extends Game {
    private final DialogManagerSpy spy;

    public TestGame(DialogManagerSpy spy) {
        this.spy = spy;
    }

    @Override
    public DialogManager getDialogManager() {
        return this.spy;
    }
}