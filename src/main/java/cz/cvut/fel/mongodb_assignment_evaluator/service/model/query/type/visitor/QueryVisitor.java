package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.*;

public interface QueryVisitor {
    void visitCreateCollection(CreateCollectionQuery createCollectionQuery);
    void visitInsertQuery(InsertQuery insertQuery);
    void visitReplaceOneQuery(ReplaceOneQuery replaceOneQuery);
    void visitUpdateQuery(UpdateQuery updateQuery);
    void visitAggregateQuery(AggregateQuery aggregateQuery);
    void visitFindQuery(FindQuery findQuery);
    void visitQuery(Query query);
}
