package traitement;

/**
 * The Reponse class represents a logical response to a proposition,
 * containing an explanatory message, a validity indicator (true for valid, false for invalid),
 * and a conclusion in the form of a Proposition (which can be null).
 *
 */
public class Response {
    private String message;
    private boolean result;
    private boolean isUninteresting;
    private Proposition conclusion;

    /**
     * Constructor to create a new response.
     *
     * @param message The message associated with the response, explaining or describing the result.
     * @param result The result of the response (true for valid, false for invalid).
     * @param conclusion The proposition concluding the response (can be null if no conclusion is provided).
     * @param isUninteresting boolean true if the conclusion is not interesting
     */
    public Response(String message, boolean result, Proposition conclusion, boolean isUninteresting) {
        this.message = message;
        this.result = result;
        this.conclusion = conclusion;
        this.isUninteresting = isUninteresting;
    }

    /**
     * Gets the result of the response.
     *
     * @return true if the result is positive (valid), false otherwise.
     */
    public boolean getResult() {
        return result;
    }

    /**
     * Gets the message associated with the response.
     *
     * @return the message explaining or describing the response.
     */
    public String getMessage() {
        return message;
    }

    public Proposition getConclusion() {
        return conclusion;
    }

    /**
     * Checks if the response is valid.
     *
     * @return true if the result of the response is positive (valid), false otherwise.
     */
    public boolean isValid() {
        return result;
    }
    /**
     * Checks if the conclusion is interesting.
     *
     * @return true if conclusion is interesting, false otherwise.
     */
    public boolean isUninteresting() {
        return isUninteresting;
    }
}
