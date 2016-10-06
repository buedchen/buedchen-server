package io.buedchen.server.events.project;

import io.buedchen.server.Project;

import java.util.Objects;

public class ProjectCreated {
    private final Project project;

    public ProjectCreated(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectCreated that = (ProjectCreated) o;
        return Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }
}
