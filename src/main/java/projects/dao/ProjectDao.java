package projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import projects.entity.Project;

public class ProjectDao {

    // Existing code...

    // Insert a project into the database and return the inserted project
    public Project insertProject(Project project) {
        String sql = "INSERT INTO projects (projectName, estimatedHours, actualHours, difficulty, notes) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setBigDecimal(2, project.getEstimatedHours());
            preparedStatement.setBigDecimal(3, project.getActualHours());
            preparedStatement.setInt(4, project.getDifficulty());
            preparedStatement.setString(5, project.getNotes());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    project.setId(generatedId); // Set the ID of the project
                    return project;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return null; // Return null if the project insertion fails
    }

    // Fetch all projects from the database
    public List<Project> fetchAllProjects() {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM projects");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Project project = mapResultSetToProject(resultSet);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return projects;
    }

    // Retrieve a project by its ID from the database
    public Project getProjectById(int projectId) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM projects WHERE id = ?")) {

            preparedStatement.setInt(1, projectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProject(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return null; // Return null if no project with the given ID is found
    }

    // Helper method to map a ResultSet row to a Project object
    private static Project mapResultSetToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setProjectName(resultSet.getString("projectName"));
        project.setEstimatedHours(resultSet.getBigDecimal("estimatedHours"));
        project.setActualHours(resultSet.getBigDecimal("actualHours"));
        project.setDifficulty(resultSet.getInt("difficulty"));
        project.setNotes(resultSet.getString("notes"));
        return project;
    }

    // ... Existing code for other methods ...
}
