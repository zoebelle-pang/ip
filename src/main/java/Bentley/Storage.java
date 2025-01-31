package bentley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class responsible for loading tasks from and writing tasks to a file.
 */
public class Storage {

    private String filePath;
    private boolean fileIsOpen;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path where tasks are loaded from and written to.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.fileIsOpen = true;
    }

    /**
     * Loads tasks from the file specified in the constructor.
     *
     * @return An ArrayList of Task objects loaded from the file.
     */

    /**
     * Checks if the file is open.
     *
     * @return true if the file is open, false otherwise.
     */
    public boolean checkFileIsOpen() {
        return fileIsOpen;
    }

    /**
     * Loads tasks from the file specified in the constructor.
     *
     * @return An ArrayList of Task objects loaded from the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String taskData = scanner.nextLine().substring(2);  // Remove first two characters

                Task task = new Task(taskData);
                tasks.add(task);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            fileIsOpen = false;
        }
        return tasks;
    }

    /**
     * Writes tasks from the provided ArrayList to the file.
     *
     * @param tasks The ArrayList of tasks to be written to the file.
     */
    public void writeTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);

            // Check if the file and its parent directory exist, create them if needed
            if (!file.exists()) {
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();  // Create parent directories if they don't exist
                }
                file.createNewFile();  // Create the file
            }

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < tasks.size(); i++) {
                // Include the task number (index + 1) in the output
                writer.write((i + 1) + ". " + tasks.get(i).toString() + "\n");
            }
            writer.close();
            System.out.println("Tasks written to file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing tasks to file: " + e.getMessage());
            fileIsOpen = false;  // Set fileIsOpen to false on error
        }
    }


}
