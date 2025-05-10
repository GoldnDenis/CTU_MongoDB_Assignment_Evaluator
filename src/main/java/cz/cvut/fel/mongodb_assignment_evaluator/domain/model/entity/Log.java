package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "log")
@Getter
@Setter
public class Log extends AbstractEntity implements Cloneable {
    @Column(nullable = false)
    private String output;

    @ManyToOne
    private Query query;

    public Log() {
    }

    public Log(Query query, String output) {
        this.query = query.clone();
        this.output = output;
    }

    @Override
    public Log clone() {
        try {
            Log clone = (Log) super.clone();
            clone.query = query != null ? query.clone() : null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}