package splprime.parse;

import splprime.lexer.Token;
import splprime.lexer.TokenType;

import java.util.ArrayList;

public class TokenList extends ArrayList<Token> {

    private Integer currentToken = 0;

    public TokenList() {
        super();
    }

    public void addToken(Token token) {
        this.add(token);
    }

    public Token getToken(int index) {
        return this.get(index);
    }

    public Token consumeToken(TokenType type) {
        if (isType(type)) {
            return nextToken();
        }

        return null;
    }

    public Token previousToken() {
        return this.get(currentToken - 1);
    }

    public Boolean isType(TokenType type) {
        if(!isLastToken()) {
            return getCurrentToken().type == type;
        }
        return false;
    }

    public Token getCurrentToken() {
        return this.get(currentToken);
    }

    public Boolean isLastToken() {
        if(this.size()-1 == currentToken || getCurrentToken().type == TokenType.EOF) {
            return true;
        }
        return false;
    }

    public Token nextToken() {
        if (!isLastToken()) {
            currentToken++;
        }
        return previous();
    }

    public Token previous() {
        return this.get(currentToken - 1);
    }
    public Boolean matchCurrentToken(TokenType type) {
        if(isType(type)) {
            nextToken();
            return true;
        }
        return false;
    }


}
