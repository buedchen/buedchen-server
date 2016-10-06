package io.buedchen.server.events.project;

import io.buedchen.server.Dashboard;

import java.util.Objects;

public class ProjectDashboardUpdated {
    private final String projectId;
    private final String dashboardUrl;
    private final Dashboard dashboard;

    public ProjectDashboardUpdated(String projectId, String dashboardUrl, Dashboard dashboard) {
        this.projectId = projectId;
        this.dashboardUrl = dashboardUrl;
        this.dashboard = dashboard;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDashboardUpdated that = (ProjectDashboardUpdated) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(dashboardUrl, that.dashboardUrl) &&
                Objects.equals(dashboard, that.dashboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, dashboardUrl, dashboard);
    }
}
