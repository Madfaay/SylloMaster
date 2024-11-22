package traitement;

/**
 * Class that represents the quantifier of a proposition.
 * This class allows defining and managing a quantifier,
 * with its quantity and its name.
 */
public class Quantificateur {
    /**
     * Name of the quantifier, such as "all" or "some".
     */
    private String nom;
    /**
     * Indicates if the quantifier is universal (true) or particular (false).
     */
    private boolean estUniverselle;

    /**
     * Constructor of the Quantifier class.
     *
     * @param estUniverselle boolean indicating if the quantifier is universal.
     * @param nom name of the quantifier.
     */
    public Quantificateur(String nom, boolean estUniverselle) {
        this.nom = nom;
        this.estUniverselle = estUniverselle;
    }

    //The setters will be used to modify the quantifiers.
    /**
     * Allows modifying the quantifier's type, indicating if it is universal.
     *
     * @param universelle boolean indicating if the quantifier is universal or particular.
     */
    public void setUniversal(boolean universelle) {
        estUniverselle = universelle;
    }
    /**
     * Allows modifying the name of the quantifier.
     *
     * @param n the new name of the quantifier.
     */
    public void setName(String n) {
        this.nom = n;
    }


    /**
     * Allows getting the name of the quantifier.
     *
     * @return the name of the quantifier.
     */
    public String getNom() {
        return nom;
    }
    /**
     * Checks if the quantifier is universal.
     *
     * @return true if the quantifier is universal, false otherwise.
     */
    public boolean estUniverselle() {
        return estUniverselle;
    }
}
