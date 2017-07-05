package io.buedchen.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.buedchen.server.api.Providers;
import io.buedchen.server.events.project.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProvidersTest extends JerseyTest{


    private Projects projects;
    private EventBusWrapper eventBus;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected Application configure(){

        projects = Mockito.mock(Projects.class);
        eventBus = Mockito.mock(EventBusWrapper.class);
        return new ResourceConfig()
                .register(new Providers(projects, eventBus));

    }

    @Test
    public void getProvidersTest() throws Exception {

        Project project = new Project("testProject","testProject Description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);
        when(projects.getProjects()).thenReturn(projectMap);

        Response response = target("v1/providers").request().get();
        assertThat(response.getStatus(), is(200));

        String jsonStr = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        Project[] projectObj = mapper.readValue(jsonStr, Project[].class);

        assertThat(projectObj[0].getProjectId(), is("testProject"));
        assertThat(projectObj[0].getProjectDescription(), is("testProject Description"));
    }

    @Test
    public void getProjectTest() throws Exception {

        Project project = new Project("testProject","testProject description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);
        when(projects.getProject("testProject")).thenReturn(project);

        Response get = target("v1/providers/testProject").request().get();

        assertThat(get.getStatus(), is(200));

        String jsonStr = get.readEntity(String.class);

        Project projectObj = mapper.readValue(jsonStr, Project.class);

        assertThat(projectObj.getProjectId(), is("testProject"));
        assertThat(projectObj.getProjectDescription(), is("testProject description"));

    }

    @Test
    public void createProject() throws Exception {

        Project project = new Project("testProject","testProject description");
        String jsonString = mapper.writeValueAsString(project);

        Response get = target("v1/providers").request().post(Entity.entity(jsonString, MediaType.APPLICATION_JSON));
        assertThat(get.getStatus(), is(200));

    }

    @Test
    public void updateProjectDeleteTest() throws Exception {
        Project project = new Project("testProject","testProject description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);

        Response get = target("v1/providers/testProject").request().delete();
        assertThat(get.getStatus(), is(200));

        verify(eventBus,times(1)).post(new ProjectRemoved("testProject"));
    }

    @Test
    public void updateProjectPutTest() throws Exception {

        Project project = new Project("testProject","testProject description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);
        when(projects.getProject("testProject")).thenReturn(project);

        Project projectUpdate = new Project("testProject","testProject description - updated");
        String jsonString = mapper.writeValueAsString(projectUpdate);

        Response get = target("v1/providers/testProject").request().put(Entity.entity(jsonString,MediaType.APPLICATION_JSON));

        assertThat(get.getStatus(), is(200));

    }

    @Test
    public void getProjectDashboardTest() throws Exception {
        Project project = new Project("testProject","testProject description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);

        Map<String, Dashboard> dashboards = new HashMap<>();
        Dashboard dashboard = new Dashboard("https://www.google.com","Link to Google", "Google");
        dashboards.put(dashboard.getUrl(),dashboard);

        when(projects.getProjectDashboards("testProject")).thenReturn(dashboards);

        Response get = target("v1/providers/testProject/dashboards").request().get();
        assertThat(get.getStatus(), is(200));

        String jsonStr = get.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        Dashboard[] dashboardObj = mapper.readValue(jsonStr, Dashboard[].class);

        assertThat(dashboardObj[0].getUrl(), is("https://www.google.com"));
        assertThat(dashboardObj[0].getDescription(), is("Link to Google"));
        assertThat(dashboardObj[0].getTitle(), is("Google"));

    }

    @Test
    public void getProjectDashboardWithUrlTest() throws Exception {

        Map<String, Dashboard> dashboards = new HashMap<>();
        Dashboard dashboard = new Dashboard("https://www.google.com","Link to Google", "Google");
        dashboards.put(dashboard.getUrl(),dashboard);

        when(projects.getProjectDashboards("testProject")).thenReturn(dashboards);

        String encodedUrl = URLEncoder.encode("https://www.google.com", "UTF-8");
        Response get = target("v1/providers/testProject/dashboards/"+encodedUrl).request().get();
        assertThat(get.getStatus(), is(200));

        String jsonStr = get.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();

        Dashboard dashboardObj = mapper.readValue(jsonStr, Dashboard.class);

        assertThat(dashboardObj.getUrl(), is("https://www.google.com"));
        assertThat(dashboardObj.getTitle(), is("Google"));
        assertThat(dashboardObj.getDescription(), is("Link to Google"));

    }

    @Test
    public void addDashboardToProjectTest() throws Exception {

        Project project = new Project("testProject","testProject description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);

        Dashboard dashboard = new Dashboard("http://www.google.com", "Link to Google", "Google");
        String jsonString = mapper.writeValueAsString(dashboard);

        Response get = target("v1/providers/testProject/dashboards").request().post(Entity.entity(jsonString,MediaType.APPLICATION_JSON));
        assertThat(get.getStatus(), is(200));

        verify(eventBus,times(1)).post(new ProjectDashboardAdded("testProject", dashboard));

    }

    @Test
    public void updateProjectDashboardTest() throws Exception {

        Project project = new Project("testProject","Test Project Description");
        Map<String, Project> projectMap = new HashMap<String, Project>();
        projectMap.put("testProject", project);

        Map<String, Dashboard> dashboardMap = new HashMap<>();
        Dashboard dashboard = new Dashboard("https://www.google.com","Link to Google", "Google");
        dashboardMap.put(dashboard.getUrl(),dashboard);

        when(projects.getProjectDashboards("testProject")).thenReturn(dashboardMap);
        when(projects.getProjects()).thenReturn(projectMap);

        Dashboard dashboardUpdate = new Dashboard("https://www.google.com", "Link to Google", "Google updated");
        String jsonString = mapper.writeValueAsString(dashboardUpdate);
        String encodedUrl = URLEncoder.encode("https://www.google.com", "UTF-8");
        Response get = target("v1/providers/testProject/dashboards/"+encodedUrl).request().put(Entity.entity(jsonString,MediaType.APPLICATION_JSON));
        assertThat(get.getStatus(), is(200));

        verify(eventBus,times(1)).post(new ProjectDashboardUpdated("testProject",dashboardUpdate.getUrl(),dashboardUpdate));

    }

    @Test
    public void deleteProjectDashboardTest() throws Exception {
        Project project = new Project("testProject","Test Project Description");
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put("testProject", project);

        when(projects.getProjects()).thenReturn(projectMap);

        Map<String, Dashboard> dashboards = new HashMap<>();
        Dashboard dashboard = new Dashboard("https://www.google.com","Link to Google", "Google");
        dashboards.put(dashboard.getUrl(),dashboard);

        when(projects.getProjectDashboards("testProject")).thenReturn(dashboards);
        String encodedUrl = URLEncoder.encode("https://www.google.com", "UTF-8");
        Response get = target("v1/providers/testProject/dashboards/"+encodedUrl).request().delete();

        assertThat(get.getStatus(), is(200));

        verify(eventBus,times(1)).post(new ProjectDashboardRemoved("testProject",dashboard, dashboard.getUrl()));
    }

}
