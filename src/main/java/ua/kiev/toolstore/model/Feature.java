package ua.kiev.toolstore.model;

import ua.kiev.toolstore.model.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Feature extends AbstractEntity {

    private String title, body, attribute;

    public Feature() {
    }

    public Feature(String title, String body, String attribute) {
        this.title = title;
        this.body = body;
        this.attribute = attribute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Features{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
