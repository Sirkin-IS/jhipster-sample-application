package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TransportPackage.
 */
@Entity
@Table(name = "transport_package")
public class TransportPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "transport_package_id", nullable = false)
    private Integer transportPackageId;

    @NotNull
    @Column(name = "direction_id", nullable = false)
    private Integer directionId;

    @NotNull
    @Column(name = "operator_id", nullable = false)
    private String operatorId;

    @Column(name = "answer_code")
    private Integer answerCode;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "attemps")
    private Integer attemps;

    @Column(name = "last_time_of_attemps")
    private LocalDate lastTimeOfAttemps;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "id")
    private Set<Operators> operatorIds = new HashSet<>();

    @OneToOne(mappedBy = "transportPackageId")
    @JsonIgnore
    private LogicalMessage transportPackageId;

    @ManyToOne
    @JsonIgnoreProperties("transportPackageIds")
    private TransportPackageRepeat id;

    @ManyToOne
    @JsonIgnoreProperties("transportPackageIds")
    private BadIncomingTransportPackage id;

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

    public TransportPackage transportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
        return this;
    }

    public void setTransportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public TransportPackage directionId(Integer directionId) {
        this.directionId = directionId;
        return this;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public TransportPackage operatorId(String operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getAnswerCode() {
        return answerCode;
    }

    public TransportPackage answerCode(Integer answerCode) {
        this.answerCode = answerCode;
        return this;
    }

    public void setAnswerCode(Integer answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public TransportPackage answerContent(String answerContent) {
        this.answerContent = answerContent;
        return this;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Integer getAttemps() {
        return attemps;
    }

    public TransportPackage attemps(Integer attemps) {
        this.attemps = attemps;
        return this;
    }

    public void setAttemps(Integer attemps) {
        this.attemps = attemps;
    }

    public LocalDate getLastTimeOfAttemps() {
        return lastTimeOfAttemps;
    }

    public TransportPackage lastTimeOfAttemps(LocalDate lastTimeOfAttemps) {
        this.lastTimeOfAttemps = lastTimeOfAttemps;
        return this;
    }

    public void setLastTimeOfAttemps(LocalDate lastTimeOfAttemps) {
        this.lastTimeOfAttemps = lastTimeOfAttemps;
    }

    public String getContent() {
        return content;
    }

    public TransportPackage content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public TransportPackage createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Operators> getOperatorIds() {
        return operatorIds;
    }

    public TransportPackage operatorIds(Set<Operators> operators) {
        this.operatorIds = operators;
        return this;
    }

    public TransportPackage addOperatorId(Operators operators) {
        this.operatorIds.add(operators);
        operators.setId(this);
        return this;
    }

    public TransportPackage removeOperatorId(Operators operators) {
        this.operatorIds.remove(operators);
        operators.setId(null);
        return this;
    }

    public void setOperatorIds(Set<Operators> operators) {
        this.operatorIds = operators;
    }

    public LogicalMessage getTransportPackageId() {
        return transportPackageId;
    }

    public TransportPackage transportPackageId(LogicalMessage logicalMessage) {
        this.transportPackageId = logicalMessage;
        return this;
    }

    public void setTransportPackageId(LogicalMessage logicalMessage) {
        this.transportPackageId = logicalMessage;
    }

    public TransportPackageRepeat getId() {
        return id;
    }

    public TransportPackage id(TransportPackageRepeat transportPackageRepeat) {
        this.id = transportPackageRepeat;
        return this;
    }

    public void setId(TransportPackageRepeat transportPackageRepeat) {
        this.id = transportPackageRepeat;
    }

    public BadIncomingTransportPackage getId() {
        return id;
    }

    public TransportPackage id(BadIncomingTransportPackage badIncomingTransportPackage) {
        this.id = badIncomingTransportPackage;
        return this;
    }

    public void setId(BadIncomingTransportPackage badIncomingTransportPackage) {
        this.id = badIncomingTransportPackage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransportPackage)) {
            return false;
        }
        return id != null && id.equals(((TransportPackage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransportPackage{" +
            "id=" + getId() +
            ", transportPackageId=" + getTransportPackageId() +
            ", directionId=" + getDirectionId() +
            ", operatorId='" + getOperatorId() + "'" +
            ", answerCode=" + getAnswerCode() +
            ", answerContent='" + getAnswerContent() + "'" +
            ", attemps=" + getAttemps() +
            ", lastTimeOfAttemps='" + getLastTimeOfAttemps() + "'" +
            ", content='" + getContent() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
