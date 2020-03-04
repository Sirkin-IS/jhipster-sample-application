package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A BadIncomingTransportPackage.
 */
@Entity
@Table(name = "bad_incoming_transport_package")
public class BadIncomingTransportPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "transport_package_id", nullable = false)
    private Integer transportPackageId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "content_content_type", nullable = false)
    private String contentContentType;

    @NotNull
    @Column(name = "answer_code", nullable = false)
    private Integer answerCode;

    @OneToMany(mappedBy = "id")
    private Set<TransportPackage> transportPackageIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransportPackageId() {
        return transportPackageId;
    }

    public BadIncomingTransportPackage transportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
        return this;
    }

    public void setTransportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
    }

    public LocalDate getDate() {
        return date;
    }

    public BadIncomingTransportPackage date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getContent() {
        return content;
    }

    public BadIncomingTransportPackage content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public BadIncomingTransportPackage contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public Integer getAnswerCode() {
        return answerCode;
    }

    public BadIncomingTransportPackage answerCode(Integer answerCode) {
        this.answerCode = answerCode;
        return this;
    }

    public void setAnswerCode(Integer answerCode) {
        this.answerCode = answerCode;
    }

    public Set<TransportPackage> getTransportPackageIds() {
        return transportPackageIds;
    }

    public BadIncomingTransportPackage transportPackageIds(Set<TransportPackage> transportPackages) {
        this.transportPackageIds = transportPackages;
        return this;
    }

    public BadIncomingTransportPackage addTransportPackageId(TransportPackage transportPackage) {
        this.transportPackageIds.add(transportPackage);
        transportPackage.setId(this);
        return this;
    }

    public BadIncomingTransportPackage removeTransportPackageId(TransportPackage transportPackage) {
        this.transportPackageIds.remove(transportPackage);
        transportPackage.setId(null);
        return this;
    }

    public void setTransportPackageIds(Set<TransportPackage> transportPackages) {
        this.transportPackageIds = transportPackages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadIncomingTransportPackage)) {
            return false;
        }
        return id != null && id.equals(((BadIncomingTransportPackage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BadIncomingTransportPackage{" +
            "id=" + getId() +
            ", transportPackageId=" + getTransportPackageId() +
            ", date='" + getDate() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", answerCode=" + getAnswerCode() +
            "}";
    }
}
