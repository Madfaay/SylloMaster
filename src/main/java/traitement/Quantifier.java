package traitement;

/**
 * Class that represents the quantifier of a proposition.
 * This class allows defining and managing a quantifier,
 * with its quantity and its name.
 */
public class Quantifier {
    /**
     * Name of the quantifier, such as "all" or "some".
     */
    private String name;
    /**
     * Indicates if the quantifier is universal (true) or particular (false).
     */
    private boolean isUniversal;

    /**
     * Constructor of the Quantifier class.
     *
     * @param isUniversal boolean indicating if the quantifier is universal.
     * @param name name of the quantifier.
     */
    public Quantifier(String name, boolean isUniversal) {
        this.name = name;
        this.isUniversal = isUniversal;
    }

    //The setters will be used to modify the quantifiers.
    /**
     * Allows modifying the quantifier's type, indicating if it is universal.
     *
     * @param universelle boolean indicating if the quantifier is universal or particular.
     */
    public void setUniversal(boolean universelle) {
        isUniversal = universelle;
    }
    /**
     * Allows modifying the name of the quantifier.
     *
     * @param n the new name of the quantifier.
     */
    public void setName(String n) {
        this.name = n;
    }


    /**
     * Allows getting the name of the quantifier.
     *
     * @return the name of the quantifier.
     */
    public String getName() {
        return name;
    }
    /**
     * Checks if the quantifier is universal.
     *
     * @return true if the quantifier is universal, false otherwise.
     */
    public boolean isUniversal() {
        return isUniversal;
    }
}
