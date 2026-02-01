package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;
import java.util.Scanner;

/**
 * Command responsible for managing and displaying the player's inventory.
 */
public class InventoryCommand extends Command {
    @Getter
    private final String name = "inventory";

    /**
     * Executes the inventory management logic.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        List<Item> inventory = game.getPlayer().getInventory();

        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty");
            return PostCommandActionType.NONE;
        }

        System.out.println("--- Your Inventory ---");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).getName());
        }

        System.out.print("\nEnter the number of an item to view details (or 0 to cancel): ");

        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= inventory.size()) {
                Item selectedItem = inventory.get(choice - 1);

                System.out.println("\n--- Item Details ---");
                System.out.println("Name: " + selectedItem.getName());
                System.out.println("Description: " + selectedItem.getDescription());
            } else if (choice != 0) {
                System.out.println("Invalid selection");
            }
        } else {
            scanner.next();
            System.out.println("Invalid input");
        }
        return PostCommandActionType.NONE;
    }
}
