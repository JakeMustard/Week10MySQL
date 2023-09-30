package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import projects.entity.Project;
import projects.service.ProjectService;
import projects.dao.ProjectDao;

public class ProjectsApp {
    private final ProjectService projectService;

    private ProjectsApp() {
        projectService = new ProjectService(new ProjectDao());
    }

    public static void main(String[] args) {
        ProjectsApp app = new ProjectsApp();
        app.processUserSelections();
    }

    private void processUserSelections() {
        boolean done = false;
        Scanner scanner = new Scanner(System.in);

        while (!done) {
            printOperations();

            int selection = getUserSelection(scanner);

            switch (selection) {
                case 1:
                    addProject(scanner);
                    break;
                case 2:
                    listProjects();
                    break;
                case 3:
                    selectProject(scanner);
                    break;
                case 0:
                    done = true;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("\nError: " + selection + " is not a valid selection. Try again.");
                    break;
            }
        }

        scanner.close();
    }

    private void printOperations() {
        System.out.println("\nThese are the available selections. Enter the number to choose an option:");
        System.out.println("1) Add Project");
        System.out.println("2) List Projects");
        System.out.println("3) Select Project");
        System.out.println("0) Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserSelection(Scanner scanner) {
        int selection = -1;
        try {
            selection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Handle non-integer input
        }
        return selection;
    }

    private void addProject(Scanner scanner) {
      System.out.println("Enter project details:");
      System.out.print("Project Name: ");
      String projectName = scanner.nextLine();
      System.out.print("Estimated Hours: ");
      BigDecimal estimatedHours = new BigDecimal(scanner.nextLine());
      System.out.print("Actual Hours: ");
      BigDecimal actualHours = new BigDecimal(scanner.nextLine());
      System.out.print("Difficulty: ");
      int difficulty = Integer.parseInt(scanner.nextLine());
      System.out.print("Notes: ");
      String notes = scanner.nextLine();

      // Create a new Project object with the entered details
      Project project = new Project(projectName, estimatedHours, actualHours, difficulty, notes);

      // Use the ProjectService to add the project to the database
      Project addedProject = projectService.addProject(project);

      if (addedProject != null) {
          System.out.println("Project added successfully with ID: " + addedProject.getId());
      } else {
          System.out.println("Failed to add the project.");
      }
  }


    private void listProjects() {
        List<Project> projects = projectService.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("\nNo projects found.");
        } else {
            System.out.println("\nProjects:");
            for (Project project : projects) {
                System.out.println("ID: " + project.getId() + ", Name: " + project.getProjectName());
            }
        }
    }

    private void selectProject(Scanner scanner) {
        System.out.print("\nEnter a project ID to select a project: ");
        int projectId = getUserSelection(scanner);

        Project selectedProject = projectService.getProjectById(projectId);

        if (selectedProject != null) {
            // Display project details here
            System.out.println("\nYou are working with project:");
            System.out.println("ID: " + selectedProject.getId());
            System.out.println("Name: " + selectedProject.getProjectName());
            System.out.println("Estimated Hours: " + selectedProject.getEstimatedHours());
            System.out.println("Actual Hours: " + selectedProject.getActualHours());
            System.out.println("Difficulty: " + selectedProject.getDifficulty());
            System.out.println("Notes: " + selectedProject.getNotes());
        } else {
            System.out.println("\nInvalid project ID selected.");
        }
    }
}
