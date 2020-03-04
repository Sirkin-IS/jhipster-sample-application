package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MessageType.
 */
@Entity
@Table(name = "message_type")
public class MessageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("messageTypeIds")
    private LogicalMessage id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MessageType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogicalMessage getId() {
        return id;
    }

    public MessageType id(LogicalMessage logicalMessage) {
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
        if (!(o instanceof MessageType)) {
            return false;
        }
        return id != null && id.equals(((MessageType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MessageType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
