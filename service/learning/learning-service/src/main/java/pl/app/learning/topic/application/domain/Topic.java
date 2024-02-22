package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaSnapshotableDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.learning.topic_revision.query.TopicRevisionQuery;

import java.util.*;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_topic")
public class Topic extends BaseJpaSnapshotableDomainAggregateRoot<Topic, TopicSnapshot> {
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TopicHasCategory> categories = new LinkedHashSet<>();
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TopicHasReference> references = new LinkedHashSet<>();

    @SuppressWarnings("unused")
    protected Topic() {
        super();
    }

    public Topic(String name, String content, TopicStatus status, List<AggregateId> categories) {
        this.name = name;
        this.status = status;
        this.content = content;
        categories.forEach(this::addCategory);
    }

    public void updateContent(String name, String content) {
        this.verifyThatTopicIsInDraftStatus();
        this.name = name;
        this.content = content;
    }

    private void verifyThatTopicIsInDraftStatus() {
        if (!Objects.equals(TopicStatus.DRAFT, this.status)) {
            throw new TopicException.TopicWrongStatusException();
        }
    }

    public void addReferences(List<AggregateId> references) {
        references.forEach(this::addReference);
    }

    public void addReference(AggregateId reference) {
        if (getReference(reference).isPresent()) {
            return;
        }
        TopicHasReference newTopicHasReference = new TopicHasReference(this, reference);
        this.references.add(newTopicHasReference);
    }

    public void removeReferences(List<AggregateId> references) {
        references.forEach(this::removeReference);
    }

    public void removeReference(AggregateId reference) {
        getReference(reference)
                .ifPresent(topicHasReference -> this.references.remove(topicHasReference));
    }


    public Optional<TopicHasReference> getReference(AggregateId reference) {
        return this.references.stream()
                .filter(topicHasReference -> Objects.equals(topicHasReference.getReferenceId(), reference.getId()))
                .findAny();
    }

    public void addCategories(List<AggregateId> categories) {
        categories.forEach(this::addCategory);
    }

    public void addCategory(AggregateId category) {
        if (getCategory(category).isPresent()) {
            return;
        }
        TopicHasCategory newTopicHasCategory = new TopicHasCategory(this, category);
        this.categories.add(newTopicHasCategory);
    }

    public void removeCategories(List<AggregateId> categories) {
        categories.forEach(this::removeCategory);
    }

    public void removeCategory(AggregateId category) {
        getCategory(category)
                .ifPresent(topicHasCategory -> this.categories.remove(topicHasCategory));
    }

    public Optional<TopicHasCategory> getCategory(AggregateId category) {
        return this.categories.stream()
                .filter(topicHasCategory -> Objects.equals(topicHasCategory.getCategoryId(), category.getId()))
                .findAny();
    }

    public void changeStatus(TopicStatus status) {
        this.status = status;
    }

    public void mergeRevision(TopicRevisionQuery revision) {
        this.name = revision.getName();
        this.content = revision.getContent();
    }

    @Override
    public TopicSnapshot makeSnapshot() {
        return new TopicSnapshot(
                this,
                this.name,
                this.content,
                this.status
        );
    }

    @Override
    public Topic revertSnapshot(TopicSnapshot snapshot) {
        this.name = snapshot.getName();
        this.content = snapshot.getContent();
        this.status = snapshot.getStatus();
        return this;
    }
}

