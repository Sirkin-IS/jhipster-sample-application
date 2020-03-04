package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CMS.
 */
@Entity
@Table(name = "cms")
public class CMS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private String fileId;

    @NotNull
    @Column(name = "message_count", nullable = false)
    private Integer messageCount;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "fileId")
    private Set<HbaseFile> fileIds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("cmsIds")
    private LogicalMessage id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public CMS fileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public CMS messageCount(Integer messageCount) {
        this.messageCount = messageCount;
        return this;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public CMS createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<HbaseFile> getFileIds() {
        return fileIds;
    }

    public CMS fileIds(Set<HbaseFile> hbaseFiles) {
        this.fileIds = hbaseFiles;
        return this;
    }

    public CMS addFileId(HbaseFile hbaseFile) {
        this.fileIds.add(hbaseFile);
        hbaseFile.setFileId(this);
        return this;
    }

    public CMS removeFileId(HbaseFile hbaseFile) {
        this.fileIds.remove(hbaseFile);
        hbaseFile.setFileId(null);
        return this;
    }

    public void setFileIds(Set<HbaseFile> hbaseFiles) {
        this.fileIds = hbaseFiles;
    }

    public LogicalMessage getId() {
        return id;
    }

    public CMS id(LogicalMessage logicalMessage) {
        this.id = logicalMessage;
        return this;
    }

    public void setId(LogicalMessage logicalMessage) {
        this.id = logicalMessage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CMS)) {
            return false;
        }
        return id != null && id.equals(((CMS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CMS{" +
            "id=" + getId() +
            ", fileId='" + getFileId() + "'" +
            ", messageCount=" + getMessageCount() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
