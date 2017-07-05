package io.buedchen.server.events.project;

import io.buedchen.server.Dashboard;

import java.util.Objects;

public class ProjectDashboardAdded {

    private final String projectId;
    private final Dashboard dashboard;

    public ProjectDashboardAdded(String projectId, Dashboard dashboard) {
        this.projectId = projectId;
        this.dashboard = dashboard;
    }

    public String getProjectId() {
        return projectId;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDashboardAdded that = (ProjectDashboardAdded) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(dashboard, that.dashboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, dashboard);
    }
}
