package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class AggregateBuilder extends QueryBuilder {
    private final List<BsonDocument> aggregationPipeline;
//    private BsonDocument options;

    public AggregateBuilder() {
        aggregationPipeline = new ArrayList<>();
//        this.options = new BsonDocument();
    }

    @Override
    public AggregateQuery build() {
        return new AggregateQuery(
                line, column,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                aggregationPipeline
        );
//        return new AggregateQuery(
//                line, column,
//                comment, query, type,
//                operation,
//                parameters, modifiers,
//                collection,
//                aggregationPipeline, options
//        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        aggregationPipeline.add(parameter.getDocument());
//        switch (parameters.size()) {
//            case 0 -> aggregationPipeline.add(parameter.getDocument());
//            case 1 -> options = parameter.getDocument();
//            default -> throw new IllegalArgumentException();
//        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (!aggregationPipeline.isEmpty()) {
            throw new IllegalArgumentException(); // todo exception
        }
        aggregationPipeline.addAll(parameter.getDocumentList());
    }
}
