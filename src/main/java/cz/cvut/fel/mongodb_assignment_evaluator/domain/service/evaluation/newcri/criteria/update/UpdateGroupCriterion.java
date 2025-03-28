package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UpdateGroupCriterion extends EvaluationCriterion<UpdateQuery> {
    private final String requiredOperator;
    private final Set<String> requiredGroups;

    public UpdateGroupCriterion(String requiredOperator, Set<String> requiredGroups) {
        super(UpdateQuery.class);
        this.requiredOperator = requiredOperator;
        this.requiredGroups = new HashSet<>(requiredGroups);
    }

    public UpdateGroupCriterion(Set<String> requiredGroups) {
        super(UpdateQuery.class);
        this.requiredOperator = "";
        this.requiredGroups = new HashSet<>(requiredGroups);
    }

    @Override
    protected void evaluate(UpdateQuery query) {
        if (!requiredOperator.isBlank() && !requiredOperator.equals(query.getOperator())) {
            return;
        }
        List<BsonDocument> updateDocuments = query.getUpdateDocuments();
        if (updateDocuments.stream()
                .anyMatch(d ->
                        !BsonDocumentChecker.getAllRecursive(d, requiredGroups)
                                .isEmpty())) {
            currentScore++;
        }
    }
}
