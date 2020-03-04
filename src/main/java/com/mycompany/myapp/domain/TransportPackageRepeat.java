package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TransportPackageRepeat.
 */
@Entity
@Table(name = "transport_package_repeat")
public class TransportPackageRepeat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "transport_package_id", nullable = false)
    private Integer transportPackageId;

    @NotNull
    @Column(name = "repeat_num", nullable = false)
    private Integer repeatNum;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "answer_code", nullable = false)
    private Integer answerCode;

    @NotNull
    @Column(name = "answer_content", nullable = false)
    private String answerContent;

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

    public TransportPackageRepeat transportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
        return this;
    }

    public void setTransportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
    }

    public Integer getRepeatNum() {
        return repeatNum;
    }

    public TransportPackageRepeat repeatNum(Integer repeatNum) {
        this.repeatNum = repeatNum;
        return this;
    }

    public void setRepeatNum(Integer repeatNum) {
        this.repeatNum = repeatNum;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public TransportPackageRepeat createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAnswerCode() {
        return answerCode;
    }

    public TransportPackageRepeat answerCode(Integer answerCode) {
        this.answerCode = answerCode;
        return this;
    }

    public void setAnswerCode(Integer answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public TransportPackageRepeat answerContent(String answerContent) {
        this.answerContent = answerContent;
        return this;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Set<TransportPackage> getTransportPackageIds() {
        return transportPackageIds;
    }

    public TransportPackageRepeat transportPackageIds(Set<TransportPackage> transportPackages) {
        this.transportPackageIds = transportPackages;
        return this;
    }

    public TransportPackageRepeat addTransportPackageId(TransportPackage transportPackage) {
        this.transportPackageIds.add(transportPackage);
        transportPackage.setId(this);
        return this;
    }

    public TransportPackageRepeat removeTransportPackageId(TransportPackage transportPackage) {
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
        if (!(o instanceof TransportPackageRepeat)) {
            return false;
        }
        return id != null && id.equals(((TransportPackageRepeat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransportPackageRepeat{" +
            "id=" + getId() +
            ", transportPackageId=" + getTransportPackageId() +
            ", repeatNum=" + getRepeatNum() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", answerCode=" + getAnswerCode() +
            ", answerContent='" + getAnswerContent() + "'" +
            "}";
    }
}
