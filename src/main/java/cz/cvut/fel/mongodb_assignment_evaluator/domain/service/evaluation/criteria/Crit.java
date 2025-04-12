package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Crit {
    private static int counter = 0;
    private int id;
    private String name;
    private String description;
    @Setter
    private int requiredCount;

    public Crit(String name, String description) {
        this.id = counter++;
        this.name = name;
        this.description = description;
        this.requiredCount = 1;
    }
}
