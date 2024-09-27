package splprime.lexer;

import java.util.HashMap;
import java.util.Map;

import static splprime.lexer.TokenType.*;

/** The class is responsible for the mapping between the literal values of the keywords and the corresponding Tokentypes
 *
 * @author Maurice Amon
 */

public final class KeywordMap {

    private static final String AND_CONSTANT = "and";

    private static final String OR_CONSTANT = "or";

    private static final String IF_CONSTANT = "if";

    private static final String ELSE_CONSTANT = "else";

    private static final String TRUE_CONSTANT = "true";

    private static final String FALSE_CONSTANT = "false";

    private static final String WHILE_CONSTANT = "while";

    private static final String PRINT_CONSTANT = "print";

    private static final String VAR_CONSTANT = "var";

    public static final Map<String, TokenType> map = new HashMap<>();
    static {
        map.put(AND_CONSTANT, AND_OPERATOR);
        map.put(OR_CONSTANT, OR_OPERATOR);
        map.put(IF_CONSTANT, IF_CONDITION);
        map.put(ELSE_CONSTANT, ELSE_CONDITION);
        map.put(TRUE_CONSTANT, TRUE_VALUE);
        map.put(FALSE_CONSTANT, FALSE_VALUE);
        map.put(WHILE_CONSTANT, WHILE_LOOP);
        map.put(PRINT_CONSTANT, PRINT);
        map.put(VAR_CONSTANT, VAR);
    }


}
