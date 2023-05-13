package com.example.newsapi.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Source source;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public News(String title, String description, Source source, Topic topic) {
        this.source = source;
        this.topic = topic;
        this.title = title;
        this.description = description;
    }

    public News() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", source_name=" + source.getName() +
                ", source_id=" + source.getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
