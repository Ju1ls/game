# 📻 19 Hz

> *"The result is 'The Hum'. A permanent phenomenon causing visual hallucinations, paranoia, and cognitive failure."*

## ℹ️ About the Game

The year is 2067. Following a massive solar flare, 
the Earth is enveloped in a global standing electromagnetic wave at 19 Hz, 
which interferes with the human brain.

You play as Aris, a high school student whose rare neurological disorder makes her brain ignore the
Hum. Waking up confused in an abandoned store with only an EMF detector and
a knife, you must explore the ruined city. Your ultimate goal is to keep
Aris's physical and mental health intact, find four hidden keys, and
safely reach the underground bunker.

## 🌟 Highlights

- **Text-Based Survival**: Navigate a post-apocalyptic city through terminal commands.
- **Sanity Mechanics**: Being near "The Hum" or monsters decreases your sanity. If it hits zero, the game ends.
- **EMF Detector:** A vital tool for detecting monsters hidden in the fog. 
It consumes batteries and can be switched between frequency bands.
- **Tactical Constraints**: Engage in turn-based combat and carefully manage a
  strict 6-slot item inventory.

## 🚀 Usage Instructions

The game is controlled entirely via console text commands. Here are the commands you can use to
interact with the world:

| Command                                      | Action                                                           |
|:---------------------------------------------|:-----------------------------------------------------------------|
| `go north`, `go south`, `go east`, `go west` | Move in locations                                                |
| `enter`                                      | Enter a location                                                 |
| `search`                                     | Search the area for hidden items                                 |
| `take <item>`                                | Pick up an item from the location                                |
| `use <item>`                                 | Use an item from your inventory (e.g. use knife)                 |
| `throw <item> <direction>`                   | Throw an item into a specific direction (e.g. throw knife north) |
| `inventory`                                  | Display current inventory content                                |
| `talk <character>`                           | Start a dialogue with an NPC (e.g. talk Miller)                  |
| `answer <choice>`                            | Select a response during dialogue                                |
| `stop dialog`                                | End the current conversation                                     |
| `attack` / `defense`                         | Attack or defend during turn-based combat                        |
| `hold breath`                                | Hold breath (crucial for toxic zones)                            |
| `help`                                       | Display the list of available commands and player stats          |
| `clue`                                       | Show a hint for the current situation                            |
| `exit`                                       | Quit the game                                                    |

## ⬇️ Installation

To run this game locally, you will need **Java 24**.

1. Download the latest game `.jar` file from the repository
2. Open your terminal or command prompt.
3. Navigate to the folder where you downloaded the file:
   ```bash
   cd path/to/your/folder
   ```
4. Run the compiled application using Java:
   ```bash
   java -jar game.jar
   ```


Libraries used:
* [Lombok (v1.18.38)](https://projectlombok.org/)
* [Jackson Databind (v2.15.1)](https://github.com/FasterXML/jackson-databind) 


