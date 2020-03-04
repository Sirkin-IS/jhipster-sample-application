package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DocumentHistory.
 */
@Entity
@Table(name = "document_history")
public class DocumentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @NotNull
    @Column(name = "direction_id", nullable = false)
    private Integer directionId;

    @NotNull
    @Column(name = "document_id", nullable = false)
    private Integer documentId;

    @NotNull
    @Column(name = "status_id", nullable = false)
    private Integer statusId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "id")
    private Set<DocumentStatus> statusIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public DocumentHistory ownerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public DocumentHistory directionId(Integer directionId) {
        this.directionId = directionId;
        return this;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public DocumentHistory documentId(Integer documentId) {
        this.documentId = documentId;
        return this;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public DocumentHistory statusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDate getDate() {
        return date;
    }

    public DocumentHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<DocumentStatus> getStatusIds() {
        return statusIds;
    }

    public DocumentHistory statusIds(Set<DocumentStatus> documentStatuses) {
        this.statusIds = documentStatuses;
        return this;
    }

    public DocumentHistory addStatusId(DocumentStatus documentStatus) {
        this.statusIds.add(documentStatus);
        documentStatus.setId(this);
        return this;
    }

    public DocumentHistory removeStatusId(DocumentStatus documentStatus) {
        this.statusIds.remove(documentStatus);
        documentStatus.setId(null);
        return this;
    }

    public void setStatusIds(Set<DocumentStatus> documentStatuses) {
        this.statusIds = documentStatuses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentHistory)) {
            return false;
        }
        return id != null && id.equals(((DocumentHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentHistory{" +
            "id=" + getId() +
            ", ownerId=" + getOwnerId() +
            ", directionId=" + getDirectionId() +
            ", documentId=" + getDocumentId() +
            ", statusId=" + getStatusId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
