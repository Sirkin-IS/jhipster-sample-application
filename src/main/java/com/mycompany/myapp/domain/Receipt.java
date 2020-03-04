package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cms_id", nullable = false)
    private Integer cmsId;

    @NotNull
    @Column(name = "receipt_type_id", nullable = false)
    private Integer receiptTypeId;

    @NotNull
    @Column(name = "receipt_date", nullable = false)
    private LocalDate receiptDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @OneToOne(mappedBy = "receiptId")
    @JsonIgnore
    private LogicalMessage id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmsId() {
        return cmsId;
    }

    public Receipt cmsId(Integer cmsId) {
        this.cmsId = cmsId;
        return this;
    }

    public void setCmsId(Integer cmsId) {
        this.cmsId = cmsId;
    }

    public Integer getReceiptTypeId() {
        return receiptTypeId;
    }

    public Receipt receiptTypeId(Integer receiptTypeId) {
        this.receiptTypeId = receiptTypeId;
        return this;
    }

    public void setReceiptTypeId(Integer receiptTypeId) {
        this.receiptTypeId = receiptTypeId;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public Receipt receiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
        return this;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Receipt createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LogicalMessage getId() {
        return id;
    }

    public Receipt id(LogicalMessage logicalMessage) {
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
        if (!(o instanceof Receipt)) {
            return false;
        }
        return id != null && id.equals(((Receipt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + getId() +
            ", cmsId=" + getCmsId() +
            ", receiptTypeId=" + getReceiptTypeId() +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
