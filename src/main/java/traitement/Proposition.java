package traitement;

/**
 * The Proposition class represents a logical proposition in the context of syllogisms.
 * It includes a first term, a second term, a quantifier, and an indicator of affirmation or negation.
 *
 * Propositions can be of four types:
 * - A (universal affirmative)
 * - E (universal negative)
 * - I (existential affirmative)
 * - O (existential negative)
 *
 */
public class Proposition {

    private boolean isAffirmative;
    private Term FirstTerm;
    private Term SecondTerm;
    private Quantifier quantifier;

    /**
     * Constructor to create a new proposition.
     *
     * @param FirstTerm The string representing the first term of the proposition.
     * @param SecondTerm The string representing the second term of the proposition.
     * @param quantifier The quantifier associated with the proposition (for example, universal or existential).
     * @param isAffirmative Indicates if the proposition is affirmative (true) or negative (false).
     */
    public Proposition(String FirstTerm, String SecondTerm, Quantifier quantifier, boolean isAffirmative) {
        if(isAffirmative){
            this.SecondTerm = new Term(SecondTerm,false);
        } else {
            this.SecondTerm = new Term(SecondTerm,true);
        }
        this.FirstTerm = new Term(FirstTerm, quantifier.isUniversal());

        this.quantifier = quantifier;
        this.isAffirmative = isAffirmative;
    }

    /**
     * Gets the first term of the proposition.
     *
     * @return the first term (premierTerme) of the proposition.
     */
    public Term getFirstTerm() {
        return FirstTerm;
    }

    /**
     * Gets the second term of the proposition.
     *
     * @return the second term (deuxiemeTerme) of the proposition.
     */
    public Term getSecondTerm() {
        return SecondTerm;
    }

    /**
     * Gets the first term as a string.
     *
     * @return a string representation of the first term.
     */
    public String getFirstTermString() {
        return FirstTerm.toString();
    }

    /**
     * Gets the second term as a string.
     *
     * @return a string representation of the second term.
     */
    public String getSecondTermString() {
        return SecondTerm.toString();
    }

    /**
     * Gets the quantifier of the proposition.
     *
     * @return the quantifier (Quantificateur) of the proposition.
     */
    public Quantifier getQuantificator() {
        return quantifier;
    }

    /**
     * Checks if the proposition is affirmative.
     *
     * @return true if the proposition is affirmative, false otherwise.
     */
    public boolean isAffirmative() {
        return isAffirmative;
    }

    /**
     * Checks if the proposition's quantifier is universal.
     *
     * @return true if the quantifier is universal, false if it is existential.
     */
    public boolean isUniversal() {
        return quantifier.isUniversal();
    }

    // Methods to recognize the form of the proposition.

    /**
     * Method to check if the proposition is universal affirmative.
     * **/
    boolean isA() {
        return isAffirmative && isUniversal();
    }

    /**
     * Method to check if the proposition is universal negative.
     * **/
    public boolean isE() {
        return !isAffirmative && isUniversal();
    }

    /**
    /**
     * Method to check if the proposition is existential affirmative.
     * **/
    public boolean isI() {
        return isAffirmative && !isUniversal();
    }

    public boolean isO() {
        return !isAffirmative && !isUniversal();
    }

    /**
     * Returns a string representation of the proposition.
     *
     * This method generates a string that describes the proposition in a human-readable format,
     * combining the quantifier name and the two terms involved in the proposition.
     *
     * @return A string representation of the proposition, including the quantifier and both terms.
     */
    @Override
    public String toString() {
        return this.getQuantificator().getName() + " " + this.getFirstTermString() + " ...  " + this.getSecondTermString();
    }
}
