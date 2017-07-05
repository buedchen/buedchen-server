package io.buedchen.server.events.project;

import java.util.Objects;

public class ProjectRemoved {

    private final String projectId;

    public ProjectRemoved(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRemoved that = (ProjectRemoved) o;
        return Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}
