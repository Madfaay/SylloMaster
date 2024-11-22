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

    private boolean estAffirmative;
    private Terme premierTerme;
    private Terme deuxiemeTerme;
    private Quantificateur quantificateur;

    /**
     * Constructor to create a new proposition.
     *
     * @param premierTerme The string representing the first term of the proposition.
     * @param deuxiemeTerme The string representing the second term of the proposition.
     * @param quantificateur The quantifier associated with the proposition (for example, universal or existential).
     * @param estAffirmative Indicates if the proposition is affirmative (true) or negative (false).
     */
    public Proposition(String premierTerme, String deuxiemeTerme, Quantificateur quantificateur, boolean estAffirmative) {
        if(estAffirmative){
            this.deuxiemeTerme = new Terme(deuxiemeTerme,false);
        } else {
            this.deuxiemeTerme = new Terme(deuxiemeTerme,true);
        }
        this.premierTerme = new Terme(premierTerme,quantificateur.estUniverselle());

        this.quantificateur = quantificateur;
        this.estAffirmative = estAffirmative;
    }

    /**
     * Gets the first term of the proposition.
     *
     * @return the first term (premierTerme) of the proposition.
     */
    public Terme getPremierTerme() {
        return premierTerme;
    }

    /**
     * Gets the second term of the proposition.
     *
     * @return the second term (deuxiemeTerme) of the proposition.
     */
    public Terme getDeuxiemeTerme() {
        return deuxiemeTerme;
    }

    /**
     * Gets the first term as a string.
     *
     * @return a string representation of the first term.
     */
    public String getPremierTermeString() {
        return premierTerme.toString();
    }

    /**
     * Gets the second term as a string.
     *
     * @return a string representation of the second term.
     */
    public String getDeuxiemeTermeString() {
        return deuxiemeTerme.toString();
    }

    /**
     * Gets the quantifier of the proposition.
     *
     * @return the quantifier (Quantificateur) of the proposition.
     */
    public Quantificateur getQuantificateur() {
        return quantificateur;
    }

    /**
     * Checks if the proposition is affirmative.
     *
     * @return true if the proposition is affirmative, false otherwise.
     */
    public boolean estAffirmative() {
        return estAffirmative;
    }

    /**
     * Checks if the proposition's quantifier is universal.
     *
     * @return true if the quantifier is universal, false if it is existential.
     */
    public boolean estUniverselle() {
        return quantificateur.estUniverselle();
    }

    // Methods to recognize the form of the proposition.

    /**
     * Method to check if the proposition is universal affirmative.
     * **/
    boolean isA() {
        return estAffirmative && estUniverselle();
    }

    /**
     * Method to check if the proposition is universal negative.
     * **/
    public boolean isE() {
        return estAffirmative && estUniverselle();
    }

    /**
     * Method to check if the proposition is existential affirmative.
     * **/
    public boolean isI() {
        return estAffirmative && !estUniverselle();
    }
    /**
     * Method to check if the proposition is existential negative.
     * **/
    public boolean isO() {
        return !estAffirmative && !estUniverselle();
    }

    @Override
    public String toString()
    {
        return ("voici mon premier terme : " + this.getPremierTermeString()
                + " deuxieme terme : " + this.getDeuxiemeTermeString() +
                " quantifcateur : " + this.getQuantificateur().getNom()

        );
    }
}
