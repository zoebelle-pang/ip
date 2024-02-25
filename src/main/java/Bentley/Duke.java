package bentley;

import java.util.Scanner;
import bentley.DialogBox;
import bentley.MainWindow;

/**
 * The main class representing the Duke application.
 */
public class Duke {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a Duke object with the specified file path for storage.
     *
     * @param filePath The file path where tasks will be loaded from and written to.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
        parser = new Parser();
    }

    /**
     * Returns the welcome message for Duke.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    /**
     * Runs the Duke application, accepting user input and providing responses.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("Bye")) {
                ui.getByeMessage();
                storage.writeTasks(taskList.getTasks());
                break;
            } else {
                String response = getResponse(userInput);
                System.out.println(response);
                storage.writeTasks(taskList.getTasks());
            }
        }
        scanner.close();
    }

    /**
     * Generates a response to user input using the Parser.
     *
     * @param input The user input to be processed.
     * @return The response generated based on the user input.
     */
    public String getResponse(String input) {
        try {
            return Parser.parseCommand(input, taskList, ui, storage);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * The main method to start the Duke application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }

    /**
     * Returns the list of tasks in the Duke application.
     *
     * @return The list of tasks as a formatted string.
     */
    public String getTaskList() {
        return taskList.listTasks();
    }
}
