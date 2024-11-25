package traitement;

/**
 * Class that represents a term in a proposition.
 * This class allows defining and managing the term,
 * as well as its quantity (universal or particular).
 */
public class Terme {
    /**
     * Name or content of the term, such as "human", "animal", etc.
     */
    private String expression;
    /**
     * Indicates the quantity of the term, whether the term is universal (true) or particular (false).
     */
    private boolean isUniversal;


    /**
     * Constructor of the Term class.
     *
     * @param expression the name or content of the term.
     * @param isUniversal boolean indicating if the term is universal.
     */
    public Terme(String expression, boolean isUniversal) {
        this.expression = expression;
        this.isUniversal = isUniversal;
    }


    /**
     * Checks if the term is universal.
     *
     * @return true if the term is universal, false otherwise.
     */
    public boolean isUniversal(){
        return isUniversal;
    }

    /**
     * Allows retrieving the name or content of the term.
     *
     * @return the string of the term.
     */
    public String getExpression() {
        return expression;
    }


    /**
     * Allows modifying the name or content of the term.
     *
     * @param terme the new name or content of the term.
     */
    public void setExpression(String terme) {
        this.expression = terme;
    }

    /**
     * Allows modifying the scope of the term, indicating if it is universal.
     *
     * @param universal boolean indicating if the term is universal or particular.
     */
    public void setUniversal(boolean universal) {
        isUniversal = universal;
    }

    /**
     * Returns a string representation of the term.
     *
     * @return the string representing the term.
     */
    @Override
    public String toString() {
        return expression;
    }
}
