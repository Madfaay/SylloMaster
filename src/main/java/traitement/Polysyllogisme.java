package traitement;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Polysyllogisme implements Validateur {


    public static void main(String[] args) {
        Polysyllogisme poly = new Polysyllogisme("English");
        Proposition p1 = new Proposition("mammifère" , "animal" , new Quantifier("test" , true ) , true);
        Proposition p2 = new Proposition("fox à poils durs" , "fox" , new Quantifier("test" , true ) , true);
        Proposition p3 = new Proposition("vélo" , "animal" , new Quantifier("test" , true ) , true);
        Proposition p4 = new Proposition("fox" , "chien" , new Quantifier("test" , true ) , true);
        Proposition p5 = new Proposition("mini-vélo" , "vélo" , new Quantifier("test" , true ) , true);
        Proposition p6 = new Proposition("chien" , "mammifère" , new Quantifier("test" , true ) , true);

        Proposition conclusion = new Proposition("mini-vélo" , "fox à poils durs" , new Quantifier("test" , true ) , true);

        List<Proposition> propositions = new ArrayList<>();
        propositions.add(p1);
        propositions.add(p2);
        propositions.add(p3);
        propositions.add(p4);
        propositions.add(p5);
        propositions.add(p6);

        poly.setPremises(propositions);

        poly.setConclusion(conclusion);


        System.out.println(poly.structCorrection());

        System.out.println(poly.conclusionRespected());








    }

    protected Proposition conclusion;
    protected List<Proposition> premises = new ArrayList<>();
    private List<String> invalid = new ArrayList<>();
    private int taillePremisses = 0 ;
    private String language ;



    /**
     * Retrieves the conclusion of the syllogism.
     *
     * @return the conclusion as a {@link Proposition} object.
     */
    public Proposition getConclusion() {
        return conclusion;
    }


    /**
     * Retrieves the list of premises of the syllogism.
     *
     * @return the list of premises as a {@link List} of {@link Proposition} objects.
     */
    public List<Proposition> getPremises() {
        return premises;
    }
    /**
     * Sets the conclusion of the syllogism and updates the size of the premises.
     *
     * @param conclusion the conclusion to set as a {@link Proposition}.
     */
    public void setConclusion(Proposition conclusion) {
        this.conclusion = conclusion;
        this.taillePremisses = this.premises.size();
    }


    /**
     * Sets the list of premises for the syllogism.
     *
     * @param premises the list of premises to set as a {@link List} of {@link Proposition} objects.
     */
    public void setPremises(List<Proposition> premises) {
        this.premises = premises;
    }


    /**
     * Compares two terms for equality.
     *
     * @param termeA the first term to compare.
     * @param termeB the second term to compare.
     * @return {@code true} if the terms are equal, {@code false} otherwise.
     */
    public boolean termesEgaux(String termeA, String termeB) {
        return termeA.equals(termeB);
    }


    /**
     * Checks if two propositions are valid based on their terms.
     *
     * @param premierTermeTmp the first term of the first proposition.
     * @param deuxiemeTermeTmp the second term of the first proposition.
     * @param premierTerme the first term of the second proposition.
     * @param deuxiemeTerme the second term of the second proposition.
     * @return {@code true} if the propositions are valid, {@code false} otherwise.
     */
    public boolean deuxPropsValide(String premierTermeTmp, String deuxiemeTermeTmp, String premierTerme, String deuxiemeTerme) {
        boolean communA = termesEgaux(deuxiemeTermeTmp, premierTerme) || termesEgaux(deuxiemeTermeTmp, deuxiemeTerme);
        boolean communB = termesEgaux(premierTermeTmp, premierTerme) || termesEgaux(premierTermeTmp, deuxiemeTerme);

        return (communA != communB) && (!termesEgaux(deuxiemeTermeTmp, premierTermeTmp) && !termesEgaux(premierTerme, deuxiemeTerme));
    }


    /**
     * Gets the isolated term between two propositions.
     *
     * @param p1 the first proposition.
     * @param p2 the second proposition.
     * @return the isolated term as a {@link Terme}, or {@code null} if no isolated term is found.
     */
    public Terme getIsolatedTerm(Proposition p1, Proposition p2) {
        String one = p1.getFirstTerm().getExpression();
        String two = p1.getSecondTerm().getExpression();
        String Three = p2.getFirstTerm().getExpression();
        String four = p2.getSecondTerm().getExpression();

        System.out.println(one + two + Three + four);

        if (termesEgaux(one, Three) && !termesEgaux(two, four)) {
            return p1.getSecondTerm();
        }
        if (termesEgaux(one, four) && !termesEgaux(two, Three)) {
            return p1.getSecondTerm();
        }
        if (termesEgaux(two, Three) && !termesEgaux(one, four)) {
            return p1.getFirstTerm();
        }

        if (termesEgaux(two, four) && !termesEgaux(one, Three)) {
            return p1.getFirstTerm();
        }

        return null;
    }

    /**
     * Retrieves the first isolated term from the premises.
     *
     * @return the isolated term as a {@link Terme}, or {@code null} if there are fewer than two premises.
     */
    public Terme getFstIsolated() {
        if (premises.size() < 2) return null;
        return getIsolatedTerm(premises.get(0), premises.get(1));
    }


    /**
     * Retrieves the last isolated term from the premises.
     *
     * @return the isolated term as a {@link Terme}, or {@code null} if there are fewer than two premises.
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
     * Checks if the conclusion is respected based on the isolated terms of the premises.
     *
     * @return {@code true} if the conclusion is respected, {@code false} otherwise.
     */
    public boolean conclusionRespected() {
        Terme predicat = getFstIsolated();
        Terme sujet = getLstIsolated();
        System.out.println(predicat);
        System.out.println(sujet);

        if (predicat == null || sujet == null) return false;

        String premierTerme = conclusion.getFirstTerm().getExpression();
        String deuxiemeTerme = conclusion.getSecondTerm().getExpression();

        System.out.println("104" + premierTerme + " " + deuxiemeTerme + " " + predicat + " " + sujet);

        boolean predicatEgalPremierTerme = termesEgaux(predicat.getExpression(), premierTerme);
        boolean sujetEgalDeuxiemeTerme = termesEgaux(sujet.getExpression(), deuxiemeTerme);
        boolean predicatEgalDeuxiemeTerme = termesEgaux(predicat.getExpression(), deuxiemeTerme);
        boolean sujetEgalPremierTerme = termesEgaux(sujet.getExpression(), premierTerme);


        return (predicatEgalPremierTerme && sujetEgalDeuxiemeTerme) ||
                (predicatEgalDeuxiemeTerme && sujetEgalPremierTerme);
    }

    /**
     * Validates the structure of the syllogism by checking the premises and conclusion.
     *
     * @return {@code true} if the structure is valid, {@code false} otherwise.
     */
    public boolean structValid() {
        Proposition tmpProp = null;

        for (Proposition p : premises) {
            if (tmpProp != null) {
                String premierTermeTmp = tmpProp.getFirstTerm().getExpression();
                String deuxiemeTermeTmp = tmpProp.getSecondTerm().getExpression();
                String premierTerme = p.getFirstTerm().getExpression();
                String deuxiemeTerme = p.getSecondTerm().getExpression();

                if (!deuxPropsValide(premierTermeTmp, deuxiemeTermeTmp, premierTerme, deuxiemeTerme)) {
                    return false;
                }
            }
            tmpProp = p;
        }
        return conclusionRespected();
    }

    /**
     * Retrieves the predicate of the conclusion.
     *
     * @return the predicate as a {@link Terme}.
     */
    public Terme getPredicatConclusion()
    {
        return this.conclusion.getSecondTerm();
    }

    /**
     * Retrieves the subject of the conclusion.
     *
     * @return the subject as a {@link Terme}.
     */
    public Terme getSujetConclusion()
    {
        return this.conclusion.getFirstTerm();
    }

    /**
     * Swaps two premises in the list of premises.
     *
     * @param indice the index of the first premise.
     * @param indice2 the index of the second premise.
     */
    public void swap(int indice , int indice2)
    {
        Proposition tmp = premises.get(indice);
        this.premises.set(indice , this.premises.get(indice2));
        this.premises.set(indice2 , tmp);

    }



    /**
     * Places the premise with the subject of the conclusion at the last position.
     *
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public boolean placeLast() {
        Terme sujet = getSujetConclusion();
        int indice = 0 ;
        for (Proposition p : premises) {
            if(termesEgaux(sujet.getExpression() , p.getFirstTerm().getExpression())
                    || termesEgaux(sujet.getExpression() , p.getSecondTerm().getExpression()))
            {
                this.swap(this.taillePremisses -1  , indice);
                return true;
            }
            indice ++ ;

        }
        return true ;

    }

    /**
     * Places a proposition at the specified index in the list of premises.
     *
     * @param prop the proposition to place.
     * @param indice the index at which to place the proposition.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public boolean placeProp(Proposition prop , int indice)
    {


        for (int i = 0; i < premises.size(); i++) {
            Proposition p = premises.get(i);
            String premierTermeTmp = prop.getFirstTerm().getExpression();
            String deuxiemeTermeTmp = prop.getSecondTerm().getExpression();
            String premierTerme = p.getFirstTerm().getExpression();
            String deuxiemeTerme = p.getSecondTerm().getExpression();
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
     * Corrects the structure of the syllogism if necessary by moving propositions.
     *
     * @return {@code true} if the structure was corrected, {@code false} otherwise.
     */
    public boolean structCorrection () {
        if(placeLast()) {
            return placeProp(this.premises.getLast(), this.taillePremisses - 1);
        }
        return  false;
    }

    /**
     * Method that allow the user to get a pair of medium term from two premises
     * */
    public Pair<Terme, Terme> getMediumTerm (Proposition p1, Proposition p2){
        Pair<Terme, Terme> mediumTerms;
        if (p1.getFirstTermString().equals(p2.getFirstTermString())) { //< cas ou le moyen terme est le premier terme dans les deux.
            mediumTerms = new Pair(p1.getFirstTerm(), p2.getFirstTerm());
            return mediumTerms;
        } else if (p1.getFirstTermString().equals(p2.getSecondTermString())) { //< cas ou le moyen terme est premier terme dans la premiere premise et deuxième dans la seconde.
            mediumTerms = new Pair(p1.getFirstTerm(), p2.getSecondTerm());
            return mediumTerms;
        } else if (p1.getSecondTermString().equals(p2.getFirstTermString())) {
            mediumTerms = new Pair<>(p1.getSecondTerm(), p2.getFirstTerm());
            return mediumTerms;
        } else if (p1.getSecondTermString().equals(p2.getSecondTermString())) {
            mediumTerms = new Pair<>(p1.getSecondTerm(), p2.getSecondTerm());
            return mediumTerms;
        } else {
            System.out.println("Pas de moyen terme : structure invalide");
            return null;
        }

    }

    //------------------------------------------------------------------//
    //--------------------------------RULES----------------------------//
    //----------------------------------------------------------------//


    // Cela risque d'être pas ouf si on doit parcourir la liste a chaque regle
    // TODO : essayer de faire en sorte qu'on parcourt une seul fois pour tester toutes les regles.


    /**
     * Verifies the rule for the medium term of the syllogism.
     * The medium term should be universal in at least one of the premises.
     * If the rule is invalid, an error message is added to the invalid list.
     */
    @Override
    public void MiddleTermRule() {
        for (int i = 0; i < premises.size() - 1; i++) {
            Pair<Terme, Terme> mediumTerms = getMediumTerm(premises.get(i), premises.get(i + 1));
            Terme firstMediumTerm = mediumTerms.getKey();
            Terme secondMediumTerm = mediumTerms.getValue();

            if (!firstMediumTerm.isUniversal() && !secondMediumTerm.isUniversal()) {
                if(Objects.equals(language, "English")){
                invalid.add("Middle Term");}
                if(Objects.equals(language, "French")){
                    invalid.add("Moyen Terme");}
                return; //< Si on trouve que la règle est invalide on quitte la vérification.
            }
        }
    }



    /**
     * Verifies the Latius rule of the syllogism.
     * The Latius rule requires that if the subject or predicate of the conclusion is universal,
     * the corresponding term in the premises must also be universal.
     */
    @Override
    public void LatiusRule() {
        if (conclusion.getFirstTerm().isUniversal()) {
            if (!premises.getLast().getFirstTerm().isUniversal()) {
                invalid.add("Latius");
                return;
            }
        }
        if (conclusion.getSecondTerm().isUniversal()) {
            if(!premises.getFirst().getFirstTerm().isUniversal()) {
                invalid.add("Latius");
            }

        }
    }


    /**
     * Verifies the rule of RNN (Rule of Negative Terms).
     * The rule checks that there must be no more than one negative premise in the syllogism.
     * If more than one negative premise is found, the rule is invalid.
     */
    @Override
    public void rNN() {
        int i = 0;
        for (Proposition p : premises) {
            if (!p.isAffirmative()) {
                i++;
            }
            if (i >= 2) {
                invalid.add("rNN");
                return;
            }
        }
    }

    /**
     * Verifies the rule of RN (Rule of Negation).
     * This rule checks that if the conclusion is affirmative and at least one premise is negative, the rule is invalid.
     */
    @Override
    public void rN() {
        for(Proposition p : premises) {
            if(!p.isAffirmative() && conclusion.isAffirmative()) {
                invalid.add("rN");
                return;
            }
        }
    }


    /**
     * Verifies the rule of RAA (Rule of Affirmative Terms).
     * The rule checks that if all premises are affirmative, the conclusion should also be affirmative.
     * If this is not the case, the rule is invalid.
     */
    @Override
    public void rAA() {
        int i = 0;
        for(Proposition a : premises) {
            if(a.isAffirmative()){
                i++;
            }
        }
        if(i == premises.size() && !conclusion.isAffirmative()){
            invalid.add("rAA");
        }
    }

    /**
     * Verifies the rule of RPP (Rule of Particular Premises).
     * The rule checks that if all premises are particular, the rule is invalid.
     */
    @Override
    public void rPP() {
        int i = 0;
        for(Proposition a : premises) {
            if(!a.isUniversal()){
                i++;
            }
        }
        if(i==premises.size()) { //< Si toutes les prémises sont particulières.
            invalid.add("rPP");
        }
    }

    /**
     * Verifies the rule of RP (Rule of Universal Premises).
     * The rule checks that if any premise is particular, the conclusion cannot be universal.
     */
    @Override
    public void rP() {
        int i = 0;
        for(Proposition a : premises) {
            if(!a.isUniversal()){  //si l'une des prémisses est particuliers
                break;
            }
        }
        if(conclusion.isUniversal()) { //< Et si la conclusion ne l'est pas.
            invalid.add("rP");
        }
    }

    /**
     * Verifies the rule of RUU (Rule of Universal Premises).
     * The rule checks that if all premises are universal, the conclusion must also be universal.
     */
    @Override
    public void rUU() {
        int i = 0;
        for(Proposition a : premises) {
            if(a.isUniversal()){
                i++;
            }
        }
        if(i==premises.size()) {
            if(!conclusion.isUniversal()) {
                invalid.add("rUU");
            }
        }
    }

    /**
     * Checks if the syllogism is uninteresting.
     * A syllogism is considered uninteresting if all premises are affirmative and universal,
     * and the conclusion is existential negative.
     *
     * @return {@code true} if the syllogism is uninteresting, {@code false} otherwise.
     */
    public boolean estIninteressant() {
        int i = 0;
        for(Proposition a : premises){
            if(a.isA()){
                i++;
            }
        }
        if(i == premises.size()) {//< If all propositions are affirmative and universal.
            if(conclusion.isI()) { //< And if the conclusion is existential negative.
                return true; //< The syllogism is not interesting, so we return true.
            }
        }
        return false;
    }

    /**
     * Converts the conclusion of the syllogism if it is uninteresting.
     * If the syllogism is uninteresting, the conclusion is modified.
     *
     * @return a new {@link Proposition} object for the conclusion, or {@code null} if no change is needed.
     */
    public Proposition convertConclusion() {
        if(estIninteressant()) { // If the syllogism is uninteresting, we need to change the conclusion.
            return new Proposition(conclusion.getFirstTermString(), conclusion.getSecondTermString(),
                    premises.getFirst().getQuantificator(), conclusion.isAffirmative()); //< We return the new conclusion.
        }
        return null; //< If the conclusion is interesting, return null
    }

    /**
     * Validates the syllogism by applying all the verification rules.
     * If all the rules are followed correctly, the syllogism is valid.
     *
     * @return a {@link Reponse} object with the result of validation and any new conclusion if required.
     */
    // STOP
    @Override
    public Reponse valider() {
        invalid.clear();

        // We apply all the rules.
        MiddleTermRule();
        LatiusRule();
        rNN();
        rN();
        rAA();
        rPP();
        rP();
        rUU();

        Proposition nouvelleConclusion = convertConclusion(); //< The new conclusion if the previous one is not interesting.

        boolean isValid = invalid.isEmpty(); // If the list of invalid rules is empty, it is valid.

        String message = "";
        if (isValid) {
            message = "Ok!"; //<We just return.
        } else {
            if(Objects.equals(this.language, "English")){
            message = "These Rules are not respected :";
            for (String s : invalid) {
                message += s + "; ";
            }
           }
            else{
                    message = "Les règles non respectées sont :";
                    for (String s : invalid) {
                        message += s + "; ";
                    }
            }
    }
        return new Reponse(message, isValid, nouvelleConclusion);
    }

    public Polysyllogisme(String language) {

        this.invalid = new ArrayList<>();
        this.language = language;
    }

    public void addPremise(Quantifier quantifier, String firstTerm, String secondTerm, boolean isAffirmatif) {
        Proposition premise = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
        premises.add(premise);

    }

    /**
     * Sets the conclusion of the poly.
     *
     * @param quantifier the quantifier of the conclusion (e.g., "All", "Some").
     * @param firstTerm the first term of the conclusion.
     * @param secondTerm the second term of the conclusion.
     * @param isAffirmatif {@code true} if the conclusion is affirmative, {@code false} otherwise.
     */
    public void addConclusion(Quantifier quantifier, String firstTerm, String secondTerm, boolean isAffirmatif) {
        Proposition conclusion = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
        this.conclusion = conclusion;
    }

    /**
     * Retrieves the list of invalid rules.
     *
     * @return a {@link List} of strings representing the invalid rules.
     */
    public List<String> getInvalid(){
        return invalid;
    }
}
