package io.buedchen.server.events.project;

import io.buedchen.server.Dashboard;

import java.util.Objects;

public class ProjectDashboardRemoved {
    private final String projectId;
    private final Dashboard dashboard;
    private final String dashboardUrl;

    public ProjectDashboardRemoved(String projectId, Dashboard dashboard, String dashboardUrl) {
        this.projectId = projectId;
        this.dashboard = dashboard;
        this.dashboardUrl = dashboardUrl;
    }

    public String getProjectId() {
        return projectId;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDashboardRemoved that = (ProjectDashboardRemoved) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(dashboard, that.dashboard) &&
                Objects.equals(dashboardUrl, that.dashboardUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, dashboard, dashboardUrl);
    }
}
