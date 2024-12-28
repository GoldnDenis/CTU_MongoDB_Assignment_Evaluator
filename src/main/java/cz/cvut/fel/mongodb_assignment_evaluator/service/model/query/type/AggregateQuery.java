package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.BsonString;

import java.util.List;

@Getter
public class AggregateQuery extends Query {
    private final List<DocumentParameter> aggregationPipeline;
    private final DocumentParameter options;

    public AggregateQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<DocumentParameter> aggregationPipeline, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.aggregationPipeline = aggregationPipeline;
        this.options = options;
    }

    public boolean containsAggregator(Aggregators aggregator) {
        return aggregationPipeline.stream()
                .anyMatch(d -> BsonDocumentChecker.getRecursive(d.getDocument(), aggregator.getValue()).isPresent());
    }

    public boolean containsStage(Stages stage) {
        return aggregationPipeline.stream()
                .anyMatch(d -> BsonDocumentChecker.getRecursive(d.getDocument(), stage.getValue(), stage.getLevel()).isPresent());
    }
}
