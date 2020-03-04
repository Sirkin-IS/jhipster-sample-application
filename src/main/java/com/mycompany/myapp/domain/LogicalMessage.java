package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A LogicalMessage.
 */
@Entity
@Table(name = "logical_message")
public class LogicalMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @NotNull
    @Column(name = "document_id", nullable = false)
    private String documentId;

    @Column(name = "event_id")
    private String eventId;

    @NotNull
    @Column(name = "direction_id", nullable = false)
    private Integer directionId;

    @NotNull
    @Column(name = "cms_id", nullable = false)
    private String cmsId;

    @NotNull
    @Column(name = "cms_directory_name", nullable = false)
    private String cmsDirectoryName;

    @Column(name = "transport_package_id")
    private Integer transportPackageId;

    @Column(name = "receipt_id")
    private String receiptId;

    @Column(name = "message_type_id")
    private Integer messageTypeId;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "attemps")
    private Integer attemps;

    @Column(name = "last_time_of_attemps")
    private LocalDate lastTimeOfAttemps;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private TransportPackage transportPackageId;

    @OneToOne
    @JoinColumn(unique = true)
    private Receipt receiptId;

    @OneToMany(mappedBy = "id")
    private Set<Direction> directionIds = new HashSet<>();

    @OneToMany(mappedBy = "id")
    private Set<CMS> cmsIds = new HashSet<>();

    @OneToMany(mappedBy = "id")
    private Set<MessageType> messageTypeIds = new HashSet<>();

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

    public LogicalMessage ownerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public LogicalMessage documentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEventId() {
        return eventId;
    }

    public LogicalMessage eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public LogicalMessage directionId(Integer directionId) {
        this.directionId = directionId;
        return this;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public String getCmsId() {
        return cmsId;
    }

    public LogicalMessage cmsId(String cmsId) {
        this.cmsId = cmsId;
        return this;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    public String getCmsDirectoryName() {
        return cmsDirectoryName;
    }

    public LogicalMessage cmsDirectoryName(String cmsDirectoryName) {
        this.cmsDirectoryName = cmsDirectoryName;
        return this;
    }

    public void setCmsDirectoryName(String cmsDirectoryName) {
        this.cmsDirectoryName = cmsDirectoryName;
    }

    public Integer getTransportPackageId() {
        return transportPackageId;
    }

    public LogicalMessage transportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
        return this;
    }

    public void setTransportPackageId(Integer transportPackageId) {
        this.transportPackageId = transportPackageId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public LogicalMessage receiptId(String receiptId) {
        this.receiptId = receiptId;
        return this;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    public LogicalMessage messageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
        return this;
    }

    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public LogicalMessage responseCode(Integer responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getAttemps() {
        return attemps;
    }

    public LogicalMessage attemps(Integer attemps) {
        this.attemps = attemps;
        return this;
    }

    public void setAttemps(Integer attemps) {
        this.attemps = attemps;
    }

    public LocalDate getLastTimeOfAttemps() {
        return lastTimeOfAttemps;
    }

    public LogicalMessage lastTimeOfAttemps(LocalDate lastTimeOfAttemps) {
        this.lastTimeOfAttemps = lastTimeOfAttemps;
        return this;
    }

    public void setLastTimeOfAttemps(LocalDate lastTimeOfAttemps) {
        this.lastTimeOfAttemps = lastTimeOfAttemps;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LogicalMessage createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public LogicalMessage updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TransportPackage getTransportPackageId() {
        return transportPackageId;
    }

    public LogicalMessage transportPackageId(TransportPackage transportPackage) {
        this.transportPackageId = transportPackage;
        return this;
    }

    public void setTransportPackageId(TransportPackage transportPackage) {
        this.transportPackageId = transportPackage;
    }

    public Receipt getReceiptId() {
        return receiptId;
    }

    public LogicalMessage receiptId(Receipt receipt) {
        this.receiptId = receipt;
        return this;
    }

    public void setReceiptId(Receipt receipt) {
        this.receiptId = receipt;
    }

    public Set<Direction> getDirectionIds() {
        return directionIds;
    }

    public LogicalMessage directionIds(Set<Direction> directions) {
        this.directionIds = directions;
        return this;
    }

    public LogicalMessage addDirectionId(Direction direction) {
        this.directionIds.add(direction);
        direction.setId(this);
        return this;
    }

    public LogicalMessage removeDirectionId(Direction direction) {
        this.directionIds.remove(direction);
        direction.setId(null);
        return this;
    }

    public void setDirectionIds(Set<Direction> directions) {
        this.directionIds = directions;
    }

    public Set<CMS> getCmsIds() {
        return cmsIds;
    }

    public LogicalMessage cmsIds(Set<CMS> cMS) {
        this.cmsIds = cMS;
        return this;
    }

    public LogicalMessage addCmsId(CMS cMS) {
        this.cmsIds.add(cMS);
        cMS.setId(this);
        return this;
    }

    public LogicalMessage removeCmsId(CMS cMS) {
        this.cmsIds.remove(cMS);
        cMS.setId(null);
        return this;
    }

    public void setCmsIds(Set<CMS> cMS) {
        this.cmsIds = cMS;
    }

    public Set<MessageType> getMessageTypeIds() {
        return messageTypeIds;
    }

    public LogicalMessage messageTypeIds(Set<MessageType> messageTypes) {
        this.messageTypeIds = messageTypes;
        return this;
    }

    public LogicalMessage addMessageTypeId(MessageType messageType) {
        this.messageTypeIds.add(messageType);
        messageType.setId(this);
        return this;
    }

    public LogicalMessage removeMessageTypeId(MessageType messageType) {
        this.messageTypeIds.remove(messageType);
        messageType.setId(null);
        return this;
    }

    public void setMessageTypeIds(Set<MessageType> messageTypes) {
        this.messageTypeIds = messageTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogicalMessage)) {
            return false;
        }
        return id != null && id.equals(((LogicalMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LogicalMessage{" +
            "id=" + getId() +
            ", ownerId=" + getOwnerId() +
            ", documentId='" + getDocumentId() + "'" +
            ", eventId='" + getEventId() + "'" +
            ", directionId=" + getDirectionId() +
            ", cmsId='" + getCmsId() + "'" +
            ", cmsDirectoryName='" + getCmsDirectoryName() + "'" +
            ", transportPackageId=" + getTransportPackageId() +
            ", receiptId='" + getReceiptId() + "'" +
            ", messageTypeId=" + getMessageTypeId() +
            ", responseCode=" + getResponseCode() +
            ", attemps=" + getAttemps() +
            ", lastTimeOfAttemps='" + getLastTimeOfAttemps() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
