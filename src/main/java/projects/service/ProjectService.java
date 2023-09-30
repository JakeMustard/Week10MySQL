package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

import java.util.List;

public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // Add a new project
    public Project addProject(Project project) {
        // Call the ProjectDao method to insert the project into the database
        return projectDao.insertProject(project);
    }

    // Retrieve a project by its ID
    public Project getProjectById(int projectId) {
        // Call the ProjectDao method to get a project by its ID
        return projectDao.getProjectById(projectId);
    }

    // Retrieve a list of all projects
    public List<Project> getAllProjects() {
        // Call the ProjectDao method to get all projects
        return projectDao.fetchAllProjects();
    }

    // Other business logic methods related to projects can be added here
}
