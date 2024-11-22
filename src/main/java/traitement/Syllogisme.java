package traitement;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * The Syllogisme class represents a logical syllogism with major and minor propositions,
 * a conclusion, a figure number, and a list of invalid rules.
 * It provides methods to access the propositions, set validation rules,
 * and determine the validity of the syllogism by applying logical rules.
 * It also includes mechanisms to manage interesting and uninteresting conclusions.
 */
public class Syllogisme implements Validateur{
    private Proposition majeur;
    private Proposition mineur;
    private Proposition conclusion;
    private int numFigure;
    private ArrayList<String> invalid;

    /**
     * Returns the major proposition.
     *
     * @return the major proposition.
     */
    public Proposition getMajeur() {
        return majeur;
    }

    /**
     * Returns the minor proposition.
     *
     * @return the minor proposition.
     */
    public Proposition getMineur() {
        return mineur;
    }

    /**
     * Returns the list of invalid propositions.
     *
     * @return an ArrayList containing the invalid propositions.
     */
    public ArrayList<String> getInvalid() {
        return invalid;
    }

    /**
     * Default constructor of the Syllogisme class.
     *
     * Initializes the list of invalid rules.
     */
    public Syllogisme(){
        this.invalid = new ArrayList<>();
    }

    /**
     * Constructor to create a syllogism with detailed information for each proposition (major, minor, conclusion).
     *
     * @param quantifMajeur Quantifier of the major premise.
     * @param sujetMajeur Subject of the major premise.
     * @param predicatMajeur Predicate of the major premise.
     * @param estAffirmativeMajeur Boolean indicating if the major premise is affirmative.
     * @param quantifMineur Quantifier of the minor premise.
     * @param sujetMineur Subject of the minor premise.
     * @param predicatMineur Predicate of the minor premise.
     * @param estAffirmativeMineur Boolean indicating if the minor premise is affirmative.
     * @param quantifConclusion Quantifier of the conclusion.
     * @param sujetConclusion Subject of the conclusion.
     * @param predicatConclusion Predicate of the conclusion.
     * @param estAffirmativeConclusion Boolean indicating if the conclusion is affirmative.
     */
    public Syllogisme(Quantificateur quantifMajeur, String sujetMajeur, String predicatMajeur, boolean estAffirmativeMajeur,
                      Quantificateur quantifMineur, String sujetMineur, String predicatMineur, boolean estAffirmativeMineur,
                      Quantificateur quantifConclusion, String sujetConclusion, String predicatConclusion, boolean estAffirmativeConclusion
                      ) {



        this.majeur = new Proposition(sujetMajeur, predicatMajeur, quantifMajeur, estAffirmativeMajeur);
        this.mineur = new Proposition(sujetMineur, predicatMineur, quantifMineur, estAffirmativeMineur);
        this.conclusion = new Proposition(sujetConclusion, predicatConclusion, quantifConclusion, estAffirmativeConclusion);



        // Initialization of the list of invalidities
        this.invalid = new ArrayList<>();
    }


    /**
     * Constructor to create a syllogism based on the figure of the syllogism.
     *
     * @param quantifPremise1 Quantifier of the major premise.
     * @param quantifPremise2 Quantifier of the minor premise.
     * @param quantifConclusion Quantifier of the conclusion.
     * @param sujet Subject of the conclusion.
     * @param predicat Predicate of the conclusion.
     * @param termeMoyen Middle term between the premises.
     * @param estAffirmativePremise1 Boolean indicating if the major premise is affirmative.
     * @param estAffirmativePremise2 Boolean indicating if the minor premise is affirmative.
     * @param estAffirmativeConclusion Boolean indicating if the conclusion is affirmative.
     * @param numFigure Figure number of the syllogism (must be between 1 and 4).
     * @throws IllegalArgumentException if the figure number is outside the range [1, 4].
     */
    public Syllogisme(Quantificateur quantifPremise1, Quantificateur quantifPremise2, Quantificateur quantifConclusion,
                      String sujet, String predicat, String termeMoyen,
                      boolean estAffirmativePremise1, boolean estAffirmativePremise2, boolean estAffirmativeConclusion,
                      int numFigure) {


        if (numFigure < 1 || numFigure > 4) {
            throw new IllegalArgumentException("Le numéro de figure doit être entre 1 et 4.");
        }
        this.numFigure = numFigure;


        // Creation of the premises based on the figure.
        switch (numFigure) {
            case 1:
                this.majeur = new Proposition(termeMoyen, predicat, quantifPremise1, estAffirmativePremise1);
                this.mineur = new Proposition(sujet, termeMoyen, quantifPremise2, estAffirmativePremise2);
                break;
            case 2:
                this.majeur = new Proposition(predicat, termeMoyen, quantifPremise1, estAffirmativePremise1);
                this.mineur = new Proposition(sujet, termeMoyen, quantifPremise2, estAffirmativePremise2);
                break;
            case 3:
                this.majeur = new Proposition(termeMoyen, predicat, quantifPremise1, estAffirmativePremise1);
                this.mineur = new Proposition(termeMoyen, sujet, quantifPremise2, estAffirmativePremise2);
                break;
            case 4:
                this.majeur = new Proposition(predicat, termeMoyen, quantifPremise1, estAffirmativePremise1);
                this.mineur = new Proposition(termeMoyen, sujet, quantifPremise2, estAffirmativePremise2);
                break;
        }

        this.conclusion = new Proposition(sujet, predicat, quantifConclusion, estAffirmativeConclusion);


        this.invalid = new ArrayList<>();
    }

    public Proposition getConclusion() {
        return conclusion;
    }

    //----------------------------------------------------------------//
    //---------------------SETTERS-----------------------------------//
    //--------------------------------------------------------------//

    /**
     * Defines the major premise of the syllogism.
     */
    public void setMajeur(String premierTerme, String deuxiemeTerme, Quantificateur quantificateur, boolean estAffirmative) {
        this.majeur = new Proposition(premierTerme, deuxiemeTerme, quantificateur, estAffirmative);
    }

    /**
     * Defines the minor premise of the syllogism.
     */
    public void setMineur(String premierTerme, String deuxiemeTerme, Quantificateur quantificateur, boolean estAffirmative) {
        this.mineur = new Proposition(premierTerme, deuxiemeTerme, quantificateur, estAffirmative);
    }

    /**
     * Defines the conclusion of the syllogism.
     */
    public void setConclusion(String premierTerme, String deuxiemeTerme, Quantificateur quantificateur, boolean estAffirmative) {
        this.conclusion = new Proposition(premierTerme, deuxiemeTerme, quantificateur, estAffirmative);
    }

    /**
     * Defines the figure number of the syllogism.
     */
    public void setNumFigure(int numFigure) {
        this.numFigure = numFigure;
    }

    //-----------------------------------------------------------------//
    //----------------------MANAGEMENT OF FIGURES----------------------//
    //-----------------------------------------------------------------//

    /**
     * Returns the middle term in the major premise based on the figure.
     */
    public Terme getMoyenTermeMajeur() {
        if(numFigure == 1 || numFigure == 3){
            return (getMajeur()).getPremierTerme();}
        else{
            return (getMineur()).getDeuxiemeTerme();}
    }

    /**
     * Returns the middle term in the minor premise based on the figure.
     */
    public Terme getMoyenTermeMineur() {
        if(numFigure == 1 || numFigure == 2){
            return getMineur().getDeuxiemeTerme();}
        else{
            return getMajeur().getPremierTerme();}
    }

    /**
     * Returns the predicate based on the figure of the syllogism.
     */
    public Terme getPredicat() {
        if(numFigure == 1 || numFigure == 3){
            return getMajeur().getDeuxiemeTerme();
        }
        else{
            return getMajeur().getPremierTerme();
        }
    }

    /**
     * Returns the subject based on the figure of the syllogism.
     */
    public Terme getSujet() {
        if(numFigure == 1 || numFigure == 2){
            return getMineur().getPremierTerme();
        }
        else{
            return getMineur().getDeuxiemeTerme();
        }
    }

    /**
     * Detects the figure of the syllogism based on the terms of the major and minor premises.
     *
     * @return the figure number (1, 2, 3, or 4) based on the relationships between the terms of the premises.
     *         - 1: If the first term of the major premise equals the second term of the minor premise.
     *         - 2: If the second term of the major premise equals the second term of the minor premise.
     *         - 3: If the first term of the major premise equals the first term of the minor premise.
     *         - 4: If the second term of the major premise equals the first term of the minor premise.
     *         Returns 0 if no match is found.
     */
    public int DetecterFigure(){
        if(Objects.equals(majeur.getPremierTerme().getExpression(), mineur.getDeuxiemeTerme().getExpression())){
            return 1;
        }
        if(Objects.equals(majeur.getDeuxiemeTerme().getExpression(), mineur.getDeuxiemeTerme().getExpression())){
            return 2;
        }
        if(Objects.equals(majeur.getPremierTerme().getExpression(), mineur.getPremierTerme().getExpression())){
            return 3;
        }
        if(Objects.equals(majeur.getDeuxiemeTerme().getExpression(), mineur.getPremierTerme().getExpression())){
            return 4;
        }
        return 0;
    }


    //------------------------------------------------------------------//
    //-------------------------VALIDATION RULES-------------------------//
    //------------------------------------------------------------------//

    /**
     * The middle term rule states that for a syllogism to be valid,
     * at least one of the middle terms must be universal.
     * If the rule is not valid, it is added to the list of invalid rules.
     * */
    @Override
    public void regleMoyenTerme() {

        if (!getMoyenTermeMajeur().estUniverselle() && !getMoyenTermeMineur().estUniverselle()) { //<If the middle term is particular in both premises, it is false.
            (this.invalid).add("Moyen Terme");
        }
    }

    /**
     * The Latius rule states that if a term is universal in the conclusion,
     * it must also be universal in the premise.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void regleLatius() {

        if (conclusion.getPremierTerme().estUniverselle()) { //< If the subject of the conclusion is universal,
            if (!getSujet().estUniverselle()) { //< The subject in the premise must also be universal.
                invalid.add("Regle Latius");
            }
        }

        if (conclusion.getDeuxiemeTerme().estUniverselle()) { //< If the predicate of the conclusion is universal,
            if (!getPredicat().estUniverselle()) { //< The predicate in the premise must also be universal.
                invalid.add("Regle Latius");
            }
        }

    }

    /**
     * The rNN rule states that if both premises are negative, no conclusion can be drawn.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rNN() {
        if(!majeur.estAffirmative() && !mineur.estAffirmative()){
            invalid.add("rNN");
        }
    }

    /**
     * The rN rule states that if one premise is negative, the conclusion must also be negative.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rN() {
        if (majeur.estAffirmative() && mineur.estAffirmative()) {
            return; //< Both premises are affirmative, so the rN rule does not apply.
        }

        // Checks if the conclusion is affirmative when it should be negative.
        if (conclusion.estAffirmative()) {
            invalid.add("rN");
        }
    }

    /**
     * The rAA rule states that if both premises are positive, the conclusion cannot be negative.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rAA() {
        if(majeur.estAffirmative() && mineur.estAffirmative()) {
            if(!conclusion.estAffirmative()) {
                invalid.add("rAA");
            }
        }
    }

    /**
     * The rPP rule states that if both premises are particular, then no conclusion can be drawn.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rPP() {
        if(!majeur.estUniverselle() && !mineur.estUniverselle()) { //<If both are particular.
            invalid.add("rPP");
        }
    }

    /**
     * The rP rule states that if one premise is particular, the conclusion must also be particular.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rP() {
        if(!majeur.estUniverselle() || !mineur.estUniverselle()) { //< If one of the premises is particular.
            if(conclusion.estUniverselle()) { //< And if the conclusion is not particular.
                invalid.add("rP");
            }
        }
    }

    /**
     * The rUU rule states that if both premises are universal, the conclusion must also be universal.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rUU() {
        if(majeur.estUniverselle() && mineur.estUniverselle()) {
            if(!conclusion.estUniverselle()) {
                invalid.add("rUU");
            }
        }
    }

    /**
     * Validates the syllogism by applying logical rules and determines if the conclusion is interesting.
     */
     @Override
    public Reponse valider() {
        // The list of invalid rules is cleared (in case you want to test multiple times after modifications).
         invalid.clear();

        // We apply all the rules.
        regleMoyenTerme();
        regleLatius();
        rNN();
        rN();
        rAA();
        rPP();
        rP();
        rUU();

        Proposition nouvelleConclusion = convertConclusion(); //< The new conclusion if the previous one is not interesting.

        boolean isValid = invalid.isEmpty(); // If the list of invalid rules is empty, it is valid.

        String message;
        if (isValid) {
            message = "Ok!"; //<We just return.
        } else {
            message = "Les règles non respectées sont :";
            for (String s : invalid) {
                message += s + "; ";
            }
        }
        return new Reponse(message, isValid, nouvelleConclusion);

    }
/*****************************************************************************************************/
    /**
     * Allows choosing the rule that we want to submit for validation.
     */
    public Reponse validRule(ArrayList<String> check){
        invalid.clear();
        Proposition nouvelleConclusion = convertConclusion(); //< The new conclusion if the previous one is not interesting.
        boolean isValid = invalid.isEmpty();

        for(String isCheck : check){
            if("regleMoyenTerme".equals(isCheck)){
                regleMoyenTerme();
            }else if("regleLatius".equals(isCheck)){
                regleLatius();
            }else if("rNN".equals(isCheck)){
                rNN();
            }else if("rN".equals(isCheck)){
                rN();
            }else if("rAA".equals(isCheck)){
                rAA();
            }else if("rPP".equals(isCheck)){
                rPP();
            }else if("rP".equals(isCheck)){
                rP();
            }else if("rUU".equals(isCheck)){
                rUU();
            }

        }
        String message;
        if (isValid) {
            message = "Every rules chosen are validated";
        }else{
            message = "The rules that are not validated are:";
            for (String s : invalid) {
                message += s + "; ";
            }
        }
        return new Reponse(message, isValid, nouvelleConclusion);
    }

    //------------------------------------------------------------------//
    //---------------MANAGEMENT OF INTERESTING CONCLUSIONS--------------//
    //------------------------------------------------------------------//

    /**
     * Method to recognize uninteresting syllogisms.
     * @return returns true if the syllogism is not interesting, and false otherwise.
     * **/

    public boolean estIninteressant() {
        if(majeur.isA() && mineur.isA()) {//< If both propositions are affirmative and universal.
            if (conclusion.isA()) { //< And if the conclusion is also universal.
                return false; //< The syllogism is interesting, so we return false.
            }
        }
        return true;
    }

    /**
     * Method that returns an interesting conclusion if it is not interesting by default.
     * **/
    public Proposition convertConclusion() {
        if(estIninteressant()) { // If the syllogism is uninteresting, we need to change the conclusion.

            // Here we call the constructor to create a new proposition with the quantifier from the major premise.
            // Why don't we return a modified version of the conclusion with just the quantifier changed?
            // Because the terms have quantities, and since we are changing the quantifier, the quantity of the term (subject)
            // is also modified. If you want, you can create a new constructor that allows us to copy a proposition
            // while only modifying the quantifier. Otherwise, you can leave it as is.
            return new Proposition(conclusion.getPremierTermeString(), conclusion.getDeuxiemeTermeString(),
                    majeur.getQuantificateur(), conclusion.estAffirmative()); //< We return the new conclusion.
        }
        return null; //< If the conclusion is not interesting, return null
    }




}
