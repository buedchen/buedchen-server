package io.buedchen.server;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dashboard {

    private String url;
    private String title;
    private String description;
    private String contact;
    private String status;

    public Dashboard() {

    }

    public Dashboard(String url, String description, String title) {
        this.url = url;
        this.description = description;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dashboard(String url, String description, String title, String contact) {
        this.url = url;
        this.description = description;
        this.title = title;

        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dashboard dashboard = (Dashboard) o;
        return Objects.equals(url, dashboard.url) &&
                Objects.equals(title, dashboard.title) &&
                Objects.equals(description, dashboard.description) &&
                Objects.equals(contact, dashboard.contact) &&
                Objects.equals(status, dashboard.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, description, contact, status);
    }
}
