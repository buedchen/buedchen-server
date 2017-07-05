package io.buedchen.server.events.project;

import io.buedchen.server.Project;

import java.util.Objects;

public class ProjectUpdated {
    public final Project project;

    public ProjectUpdated(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectUpdated)) return false;

        ProjectUpdated that = (ProjectUpdated) o;

        return project != null ? project.equals(that.project) : that.project == null;

    }

    @Override
    public int hashCode() {
        return project != null ? project.hashCode() : 0;
    }
}
