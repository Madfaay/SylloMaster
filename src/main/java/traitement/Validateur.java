package traitement;

/**
 * Interface for validating logical rules in a syllogism.
 * This interface defines methods to apply various logical rules and validate the syllogism.
 */
public interface Validateur {

    /**
     * Applies the "Moyen Terme" rule.
     * This rule checks the relationship of the middle term in the syllogism.
     */
    void MiddleTermRule();

    /**
     * Applies the "Latius" rule.
     * This rule checks if a universal term in the conclusion must also be universal in the premises.
     */
    void LatiusRule();

    /**
     * Applies the "rNN" rule.
     * This rule checks if both premises are negative, meaning no conclusion can be drawn.
     */
    void rNN();

    /**
     * Applies the "rN" rule.
     * This rule checks if one premise is negative, the conclusion must also be negative.
     */
    void rN();

    /**
     * Applies the "rAA" rule.
     * This rule checks that if both premises are affirmative, the conclusion cannot be negative.
     */
    void rAA();

    /**
     * Applies the "rPP" rule.
     * This rule checks that if both premises are particular, no valid conclusion can be drawn.
     */
    void rPP();

    /**
     * Applies the "rP" rule.
     * This rule checks if a particular premise implies that the conclusion must also be particular.
     */
    void rP();

    /**
     * Applies the "rUU" rule.
     * This rule checks that if both premises are universal, the conclusion must also be universal.
     */
    void rUU();

    /**
     * Validates the syllogism by applying the defined rules and returns the result.
     *
     * @return a response indicating whether the syllogism is valid or not.
     */
    Reponse valider();
}
