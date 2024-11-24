package traitement;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing a syllogism with multiple premises and a conclusion.
 * This class implements the `Validateur` interface to validate syllogistic reasoning.
 */
public class Polysyllogisme implements Validateur {
    protected Proposition conclusion;
    protected List<Proposition> premises = new ArrayList<>();
    private List<String> invalid = new ArrayList<>();
    private int taillePremisses = 0 ;


    /**
     * Gets the conclusion of the syllogism.
     * @return The conclusion proposition.
     */
    public Proposition getConclusion() {
        return conclusion;
    }

    /**
     * Gets the list of premises.
     * @return The list of premise propositions.
     */
    public List<Proposition> getPremises() {
        return premises;
    }

    /**
     * Sets the conclusion and updates the premises size.
     * @param conclusion The conclusion proposition.
     */
    public void setConclusion(Proposition conclusion) {
        this.conclusion = conclusion;
        this.taillePremisses = this.premises.size();
    }

    /**
     * Sets the list of premises.
     * @param premises The list of premise propositions.
     */
    public void setPremises(List<Proposition> premises) {
        this.premises = premises;
    }

    /**
     * Checks if two terms are equal.
     * @param termeA The first term.
     * @param termeB The second term.
     * @return True if the terms are equal, otherwise false.
     */
    public boolean termesEgaux(String termeA, String termeB) {
        return termeA.equals(termeB);
    }

    /**
     * Validates if two propositions are valid based on their terms.
     * @param premierTermeTmp The first term of the first proposition.
     * @param deuxiemeTermeTmp The second term of the first proposition.
     * @param premierTerme The first term of the second proposition.
     * @param deuxiemeTerme The second term of the second proposition.
     * @return True if valid, otherwise false.
     */
    public boolean deuxPropsValide(String premierTermeTmp, String deuxiemeTermeTmp, String premierTerme, String deuxiemeTerme) {
        boolean communA = termesEgaux(deuxiemeTermeTmp, premierTerme) || termesEgaux(deuxiemeTermeTmp, deuxiemeTerme);
        boolean communB = termesEgaux(premierTermeTmp, premierTerme) || termesEgaux(premierTermeTmp, deuxiemeTerme);

        return (communA != communB) && (!termesEgaux(deuxiemeTermeTmp, premierTermeTmp) && !termesEgaux(premierTerme, deuxiemeTerme));
    }

    /**
     * Gets the isolated term from two propositions based on their terms.
     * @param p1 The first proposition.
     * @param p2 The second proposition.
     * @return The isolated term, or null if no isolated term is found.
     */
    public Terme getIsolatedTerm(Proposition p1, Proposition p2) {
        String un = p1.getPremierTerme().getExpression();
        String deux = p1.getDeuxiemeTerme().getExpression();
        String trois = p2.getPremierTerme().getExpression();
        String quatre = p2.getDeuxiemeTerme().getExpression();

        System.out.println(un + deux + trois + quatre);

        if (termesEgaux(un, trois) && !termesEgaux(deux, quatre)) {
            return p1.getDeuxiemeTerme();
        }
        if (termesEgaux(un, quatre) && !termesEgaux(deux, trois)) {
            return p1.getDeuxiemeTerme();
        }
        if (termesEgaux(deux, trois) && !termesEgaux(un, quatre)) {
            return p1.getPremierTerme();
        }

        if (termesEgaux(deux, quatre) && !termesEgaux(un, trois)) {
            return p1.getPremierTerme();
        }

        return null;
    }

    /**
     * Gets the first isolated term.
     * @return The first isolated term.
     */
    public Terme getFstIsolated() {
        if (premises.size() < 2) return null;
        return getIsolatedTerm(premises.get(0), premises.get(1));
    }

    /**
     * Gets the last isolated term.
     * @return The last isolated term.
     */
    public Terme getLstIsolated() {
        if (premises.size() < 2) return null;
        Proposition last = premises.removeLast();
        Proposition secondLast = premises.removeLast();

        premises.add(secondLast);
        premises.add(last);

        return getIsolatedTerm(last, secondLast);
    }

    /**
     * Validates if the conclusion is respected based on isolated terms.
     * @return True if conclusion is respected, otherwise false.
     */
    public boolean conclusionRespected() {
        Terme predicat = getFstIsolated();
        Terme sujet = getLstIsolated();
        System.out.println(predicat);
        System.out.println(sujet);

        if (predicat == null || sujet == null) return false;

        String premierTerme = conclusion.getPremierTerme().getExpression();
        String deuxiemeTerme = conclusion.getDeuxiemeTerme().getExpression();

        System.out.println("104" + premierTerme + " " + deuxiemeTerme + " " + predicat + " " + sujet);

        boolean predicatEgalPremierTerme = termesEgaux(predicat.getExpression(), premierTerme);
        boolean sujetEgalDeuxiemeTerme = termesEgaux(sujet.getExpression(), deuxiemeTerme);
        boolean predicatEgalDeuxiemeTerme = termesEgaux(predicat.getExpression(), deuxiemeTerme);
        boolean sujetEgalPremierTerme = termesEgaux(sujet.getExpression(), premierTerme);


        return (predicatEgalPremierTerme && sujetEgalDeuxiemeTerme) ||
                (predicatEgalDeuxiemeTerme && sujetEgalPremierTerme);
    }

    /**
     * Validates the structure of the premises and the conclusion.
     * @return True if the structure is valid, otherwise false.
     */
    public boolean structValid() {
        Proposition tmpProp = null;

        for (Proposition p : premises) {
            if (tmpProp != null) {
                String premierTermeTmp = tmpProp.getPremierTerme().getExpression();
                String deuxiemeTermeTmp = tmpProp.getDeuxiemeTerme().getExpression();
                String premierTerme = p.getPremierTerme().getExpression();
                String deuxiemeTerme = p.getDeuxiemeTerme().getExpression();

                if (!deuxPropsValide(premierTermeTmp, deuxiemeTermeTmp, premierTerme, deuxiemeTerme)) {
                    return false;
                }
            }
            tmpProp = p;
        }
        return conclusionRespected();
    }

    /**
     * Retrieves the second term (predicate) of the conclusion.
     *
     * This method returns the second term of the conclusion in a syllogism, which represents the predicate
     * of the conclusion proposition.
     *
     * @return Terme - The second term (predicate) of the conclusion.
     */
    public Terme getPredicatConclusion() {
        return this.conclusion.getDeuxiemeTerme();
    }

    /**
     * Retrieves the first term (subject) of the conclusion.
     *
     * This method returns the first term of the conclusion in a syllogism, which represents the subject
     * of the conclusion proposition.
     *
     * @return Terme - The first term (subject) of the conclusion.
     */
    public Terme getSujetConclusion() {
        return this.conclusion.getPremierTerme();
    }


    /**
     * Swaps two premises in the premises list.
     * @param indice The first index.
     * @param indice2 The second index.
     */
    public void swap(int indice , int indice2) {
        Proposition tmp = premises.get(indice);
        this.premises.set(indice , this.premises.get(indice2));
        this.premises.set(indice2 , tmp);

    }

    /**
     * Places the premise that matches the subject of the conclusion at the last position in the list of premises.
     *
     * This method checks each premise to see if its first or second term matches the subject of the conclusion.
     * If a match is found, it swaps that premise with the last premise in the list.
     *
     * @return boolean - Returns true if a matching premise was found and swapped; otherwise, returns true even if no swap occurs.
     */
    public boolean placeLast() {
        Terme sujet = getSujetConclusion();
        int indice = 0;
        for (Proposition p : premises) {
            if (termesEgaux(sujet.getExpression(), p.getPremierTerme().getExpression())
                    || termesEgaux(sujet.getExpression(), p.getDeuxiemeTerme().getExpression())) {
                this.swap(this.taillePremisses - 1, indice);
                return true;
            }
            indice++;
        }
        return true;
    }

    /**
     * Attempts to place a given proposition at a specific position in the list of premises.
     * It checks if the proposition can be swapped with another based on term validity.
     *
     * This method iterates through the premises, and if the proposition is valid to be placed
     * at the specified position, it swaps the proposition with the current premise.
     * The method uses recursion to move the proposition to the desired index.
     *
     * @param prop - The proposition to place in the premises list.
     * @param indice - The index where the proposition should be placed.
     * @return boolean - Returns true if the proposition is successfully placed; otherwise, returns false.
     */
    public boolean placeProp(Proposition prop , int indice) {
        for (int i = 0; i < premises.size(); i++) {
            Proposition p = premises.get(i);
            String premierTermeTmp = prop.getPremierTerme().getExpression();
            String deuxiemeTermeTmp = prop.getDeuxiemeTerme().getExpression();
            String premierTerme = p.getPremierTerme().getExpression();
            String deuxiemeTerme = p.getDeuxiemeTerme().getExpression();
            if (deuxPropsValide(premierTermeTmp, deuxiemeTermeTmp, premierTerme, deuxiemeTerme)) {
                System.out.println(premierTerme+ deuxiemeTerme + premierTermeTmp + deuxiemeTermeTmp + i + indice);
                if(indice == 1)
                    return true ;
                this.swap(indice -1 , i );
                boolean next = placeProp(p , indice -1);
                if(next)
                    return true ;
            }
        }
        return  false ;

    }

    /**
     * Attempts to correct the structure of the premises by placing the last premise correctly.
     * It first tries to place the last premise using the `placeLast()` method, and then attempts
     * to place it at the desired position using the `placeProp()` method.
     *
     * @return boolean - Returns true if the structure correction is successful; otherwise, returns false.
     */
    public boolean structCorrection () {
        if(placeLast()) {  // Try placing the last premise in its correct position
            return placeProp(this.premises.getLast(), this.taillePremisses - 1);  // Correct the structure by placing the last premise
        }
        return false;  // Return false if placement fails
    }

    /**
     * Determines the medium term shared between two propositions.
     * It compares the terms in both propositions and returns a pair of terms
     * that are shared between them. These terms are the "medium terms" in a syllogism.
     *
     * @param p1 The first proposition.
     * @param p2 The second proposition.
     * @return Pair<Terme, Terme> - A pair containing the two medium terms, or null if no medium term is found.
     */
    public Pair<Terme, Terme> getMediumTerm (Proposition p1, Proposition p2) {
        Pair<Terme, Terme> mediumTerms;

        if (p1.getPremierTermeString().equals(p2.getPremierTermeString())) {  // Case where the medium term is the first term in both propositions
            mediumTerms = new Pair<>(p1.getPremierTerme(), p2.getPremierTerme());
            return mediumTerms;
        } else if (p1.getPremierTermeString().equals(p2.getDeuxiemeTermeString())) {  // Case where the medium term is the first term in the first premise and the second in the second
            mediumTerms = new Pair<>(p1.getPremierTerme(), p2.getDeuxiemeTerme());
            return mediumTerms;
        } else if (p1.getDeuxiemeTermeString().equals(p2.getPremierTermeString())) {  // Case where the medium term is the second term in the first premise and the first in the second
            mediumTerms = new Pair<>(p1.getDeuxiemeTerme(), p2.getPremierTerme());
            return mediumTerms;
        } else if (p1.getDeuxiemeTermeString().equals(p2.getDeuxiemeTermeString())) {  // Case where the medium term is the second term in both propositions
            mediumTerms = new Pair<>(p1.getDeuxiemeTerme(), p2.getDeuxiemeTerme());
            return mediumTerms;
        } else {
            System.out.println("No medium term: invalid structure");
            return null;  // Return null if no medium term is found
        }
    }


    //------------------------------------------------------------------//
    //--------------------------------RULES----------------------------//
    //----------------------------------------------------------------//

    /**
     * Validates the reasoning process and returns the result.
     * @return The validation result with message and new conclusion if needed.
     */
    @Override
    public void regleMoyenTerme() {
        for (int i = 0; i < premises.size() - 1; i++) {
            Pair<Terme, Terme> mediumTerms = getMediumTerm(premises.get(i), premises.get(i + 1));
            Terme firstMediumTerm = mediumTerms.getKey();
            Terme secondMediumTerm = mediumTerms.getValue();

            if (!firstMediumTerm.estUniverselle() && !secondMediumTerm.estUniverselle()) {
                invalid.add("ENG: Medium Term Invalid / FR: Moyen Terme Invalide");
                return; //< Si on trouve que la règle est invalide on quitte la vérification.
            }
        }
    }


    /**
     * Applies the "Latius" rule to validate the relationship between the conclusion
     * and the premises based on universality.
     *
     * The rule checks if the first and second terms of the conclusion are universal.
     * If either of them is universal, the corresponding premise's first term
     * must also be universal for the syllogism to be valid.
     *
     * If any of these conditions are violated, an invalid message is added to the
     * list of invalid rules.
     */
    @Override
    public void regleLatius() {
        if (conclusion.getPremierTerme().estUniverselle()) {  // Check if the first term of the conclusion is universal
            if (!premises.getLast().getPremierTerme().estUniverselle()) {  // Check if the first term of the last premise is universal
                invalid.add("ENG: Latius Invalid / FR: Latius Invalide");
                return;
            }
        }
        if (conclusion.getDeuxiemeTerme().estUniverselle()) {  // Check if the second term of the conclusion is universal
            if (!premises.getFirst().getPremierTerme().estUniverselle()) {  // Check if the first term of the first premise is universal
                invalid.add("ENG: Latius Invalid / FR: Latius Invalide");
            }
        }
    }

    /**
     * Applies the "RNN" rule to validate the structure of the premises.
     *
     * The rule ensures that there are no more than one negative premise in a syllogism.
     * If there are two or more negative premises, the syllogism is invalid.
     *
     * If the rule is violated (i.e., there are two or more negative premises),
     * an invalid message is added to the list of invalid rules.
     */
    @Override
    public void rNN() {
        int i = 0;
        for (Proposition p : premises) {
            if (!p.estAffirmative()) {  // Check if the proposition is negative
                i++;
            }
            if (i >= 2) {  // If there are two or more negative propositions
                invalid.add("ENG: Rnn Invalid / FR: Rnn Invalide");
                return;  // Return immediately as the rule is violated
            }
        }
    }

    /**
     * Applies the "RN" rule to validate the structure of the syllogism.
     *
     * The rule ensures that a negative premise cannot lead to an affirmative conclusion.
     * If there is any negative premise and the conclusion is affirmative, the syllogism is invalid.
     *
     * If the rule is violated (i.e., a negative premise leads to an affirmative conclusion),
     * an invalid message is added to the list of invalid rules.
     */
    @Override
    public void rN() {
        for(Proposition p : premises) {
            if(!p.estAffirmative() && conclusion.estAffirmative()) {  // If there is a negative premise and the conclusion is affirmative
                invalid.add("ENG: Rn Invalid / FR: Rn Invalide");
                return;  // Return immediately as the rule is violated
            }
        }
    }

    /**
     * Applies the "RAA" rule to validate the structure of the syllogism.
     *
     * The rule ensures that if all premises are affirmative, the conclusion must also be affirmative.
     * If all premises are affirmative and the conclusion is negative, the syllogism is invalid.
     *
     * If the rule is violated (i.e., all premises are affirmative but the conclusion is negative),
     * an invalid message is added to the list of invalid rules.
     */
    @Override
    public void rAA() {
        int i = 0;
        // Count the number of affirmative premises
        for(Proposition a : premises) {
            if(a.estAffirmative()) {
                i++;
            }
        }
        // If all premises are affirmative and the conclusion is negative, the rule is violated
        if(i == premises.size() && !conclusion.estAffirmative()) {
            invalid.add("ENG: Raa Invalid / FR: Raa Invalide");
        }
    }

    /**
     * Applies the "RPP" rule to validate the structure of the syllogism.
     *
     * The rule ensures that at least one premise must be universal. If all premises are particular,
     * the syllogism is invalid.
     *
     * If the rule is violated (i.e., all premises are particular), an invalid message is added
     * to the list of invalid rules.
     */
    @Override
    public void rPP() {
        int i = 0;
        // Count the number of particular premises
        for(Proposition a : premises) {
            if(!a.estUniverselle()) {
                i++;
            }
        }
        // If all premises are particular, the rule is violated
        if(i == premises.size()) { // If all premises are particular
            invalid.add("ENG: Rpp Invalid / FR: Rpp Invalide");
        }
    }

    /**
     * Applies the "RP" rule to validate the structure of the syllogism.
     *
     * The rule checks that if at least one premise is particular (not universal),
     * the conclusion must also be particular. If the conclusion is universal while
     * at least one premise is particular, the syllogism is invalid.
     *
     * If the rule is violated, an invalid message is added to the list of invalid rules.
     */
    @Override
    public void rP() {
        int i = 0;
        // Check if there is at least one particular premise
        for(Proposition a : premises) {
            if(!a.estUniverselle()){ // If one of the premises is particular
                break;
            }
        }
        // If the conclusion is universal and there's a particular premise, the rule is violated
        if(conclusion.estUniverselle()) { // If the conclusion is universal
            invalid.add("ENG: Rp Invalid / FR: Rp Invalide");
        }
    }


    /**
     * Applies the "RUU" rule to validate the structure of the syllogism.
     *
     * The rule checks that if all premises are universal, the conclusion must also be universal.
     * If all premises are universal but the conclusion is particular, the syllogism is invalid.
     *
     * If the rule is violated, an invalid message is added to the list of invalid rules.
     */
    @Override
    public void rUU() {
        int i = 0;
        // Check how many of the premises are universal
        for(Proposition a : premises) {
            if(a.estUniverselle()){ // If the premise is universal
                i++;
            }
        }
        // If all premises are universal and the conclusion is not universal, the rule is violated
        if(i == premises.size()) { // All premises are universal
            if(!conclusion.estUniverselle()) { // If conclusion is not universal
                invalid.add("ENG: Ruu Invalid / FR: Ruu Invalide");
            }
        }
    }

    /**
     * Checks if the syllogism is uninteresting based on the premises and the conclusion.
     *
     * A syllogism is considered uninteresting if all premises are affirmative (A-type),
     * and the conclusion is an existential negative (I-type). If these conditions are met,
     * the syllogism is deemed uninteresting, and the method returns true.
     *
     * @return true if the syllogism is uninteresting, false otherwise.
     */
    public boolean estIninteressant() {
        int i = 0;
        // Count how many premises are of type A (affirmative)
        for(Proposition a : premises){
            if(a.isA()){ // Check if the premise is of type A (affirmative)
                i++;
            }
        }
        // If all premises are affirmative (A-type) and the conclusion is I (existential negative)
        if(i == premises.size()) {
            if(conclusion.isI()) { // If conclusion is of type I (existential negative)
                return true; // The syllogism is uninteresting
            }
        }
        return false; // The syllogism is not uninteresting
    }

    /**
     * Converts the conclusion of the syllogism if the syllogism is uninteresting.
     *
     * If the syllogism is uninteresting (all premises are affirmative and the conclusion is
     * existential negative), the conclusion is converted to a new proposition using the
     * quantifier of the first premise, with the same terms as the original conclusion.
     *
     * If the syllogism is interesting, the conclusion remains unchanged and null is returned.
     *
     * @return a new Proposition object representing the converted conclusion, or null if the syllogism is interesting.
     */
    public Proposition convertConclusion() {
        // Check if the syllogism is uninteresting
        if(estIninteressant()) {
            // Create a new Proposition with the quantifier of the first premise and the same terms as the conclusion
            return new Proposition(conclusion.getPremierTermeString(), conclusion.getDeuxiemeTermeString(),
                    premises.getFirst().getQuantificateur(), conclusion.estAffirmative());
        }
        return null; // If the syllogism is interesting, return null
    }

    /**
     * Validates the syllogism by applying all the logical rules and checking the validity of the conclusion.
     *
     * This method applies a series of rules to verify whether the premises and conclusion follow
     * the logical structure of valid syllogisms. If any rule is violated, the method logs the violations
     * in the `invalid` list. If the syllogism is valid, it returns a positive message; otherwise, it
     * returns a message indicating which rules were violated.
     *
     * The method also checks if the conclusion needs to be converted using the `convertConclusion()` method.
     * If the conclusion is uninteresting, it is updated accordingly.
     *
     * @return a Reponse object containing the validation message, validity status, and the potentially updated conclusion.
     */
    @Override
    public Reponse valider() {
        invalid.clear(); // Clear any previous invalid rules.

        // Apply all the validation rules.
        regleMoyenTerme(); // Check for the middle term rule.
        regleLatius();     // Check for the Latius rule.
        rNN();             // Check for the RNN rule.
        rN();              // Check for the RN rule.
        rAA();             // Check for the RAA rule.
        rPP();             // Check for the RPP rule.
        rP();              // Check for the RP rule.
        rUU();             // Check for the RUU rule.

        // Convert the conclusion if necessary (if it is uninteresting).
        Proposition nouvelleConclusion = convertConclusion();

        // Check if there are no invalid rules (meaning the syllogism is valid).
        boolean isValid = invalid.isEmpty();

        // Generate the validation message based on the validity of the syllogism.
        String message;
        if (isValid) {
            message = "Ok!"; // The syllogism is valid.
        } else {
            message = "Les règles non respectées sont :"; // List of violated rules.
            for (String s : invalid) {
                message += s + "; "; // Append the violated rules to the message.
            }
        }

        // Return a Reponse object with the message, validity status, and the new conclusion.
        return new Reponse(message, isValid, nouvelleConclusion);
    }


    /**
     * Constructs a new Polysyllogisme object.
     *
     * This constructor initializes a new Polysyllogisme object and creates an empty list
     * to store invalid rules. The list of invalid rules is used to track which logical
     * rules have been violated during the validation process.
     *
     * The constructor does not take any parameters and initializes the `invalid` list
     * to an empty ArrayList.
     */
    public Polysyllogisme() {
        this.invalid = new ArrayList<>(); // Initialize an empty list of invalid rules.
    }


    /**
     * Adds a premise to the list of premises.
     * @param quantifier The quantifier of the premise.
     * @param firstTerm The first term of the premise.
     * @param secondTerm The second term of the premise.
     * @param isAffirmatif Whether the premise is affirmative.
     */
    public void addPremise(Quantificateur quantifier,String firstTerm, String secondTerm, boolean isAffirmatif) {
        Proposition premise = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
        premises.add(premise);

    }

    /**
     * Adds a conclusion to the syllogism.
     * @param quantifier The quantifier of the conclusion.
     * @param firstTerm The first term of the conclusion.
     * @param secondTerm The second term of the conclusion.
     * @param isAffirmatif Whether the conclusion is affirmative.
     */
    public void addConclusion(Quantificateur quantifier,String firstTerm, String secondTerm, boolean isAffirmatif) {
        Proposition conclusion = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
        this.conclusion = conclusion;
    }

    /**
     * Gets the list of invalid rules.
     * @return The list of invalid rules.
     */
    public List<String> getInvalid(){
        return invalid;
    }
}
