package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

public class QueryEndState extends ParserState {
    public QueryEndState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.startsWith(";")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.saveQuery();
            context.transition(new ScriptState(context));
        } else if (iterator.startsWith(".")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryBodyState(context, this, true));
        } else if (iterator.hasNext()) {
            throw new IncorrectParserSyntax("Was expecting an end of the query ('.' or ';'), but got '" + iterator.peek() + "'");
        }
    }
}
