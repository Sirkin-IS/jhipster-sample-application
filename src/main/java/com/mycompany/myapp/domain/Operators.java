package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Operators.
 */
@Entity
@Table(name = "operators")
public class Operators implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "operator_id", nullable = false)
    private String operatorId;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "second_url", nullable = false)
    private String secondUrl;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @ManyToOne
    @JsonIgnoreProperties("operatorIds")
    private TransportPackage id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public Operators operatorId(String operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getUrl() {
        return url;
    }

    public Operators url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecondUrl() {
        return secondUrl;
    }

    public Operators secondUrl(String secondUrl) {
        this.secondUrl = secondUrl;
        return this;
    }

    public void setSecondUrl(String secondUrl) {
        this.secondUrl = secondUrl;
    }

    public String getTitle() {
        return title;
    }

    public Operators title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public Operators alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public TransportPackage getId() {
        return id;
    }

    public Operators id(TransportPackage transportPackage) {
        this.id = transportPackage;
        return this;
    }

    public void setId(TransportPackage transportPackage) {
        this.id = transportPackage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operators)) {
            return false;
        }
        return id != null && id.equals(((Operators) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Operators{" +
            "id=" + getId() +
            ", operatorId='" + getOperatorId() + "'" +
            ", url='" + getUrl() + "'" +
            ", secondUrl='" + getSecondUrl() + "'" +
            ", title='" + getTitle() + "'" +
            ", alias='" + getAlias() + "'" +
            "}";
    }
}
