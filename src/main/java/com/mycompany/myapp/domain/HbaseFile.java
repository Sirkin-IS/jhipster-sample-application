package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A HbaseFile.
 */
@Entity
@Table(name = "hbase_file")
public class HbaseFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Integer fileId;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties("fileIds")
    private CMS fileId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public HbaseFile fileId(Integer fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getContent() {
        return content;
    }

    public HbaseFile content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CMS getFileId() {
        return fileId;
    }

    public HbaseFile fileId(CMS cMS) {
        this.fileId = cMS;
        return this;
    }

    public void setFileId(CMS cMS) {
        this.fileId = cMS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HbaseFile)) {
            return false;
        }
        return id != null && id.equals(((HbaseFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HbaseFile{" +
            "id=" + getId() +
            ", fileId=" + getFileId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
