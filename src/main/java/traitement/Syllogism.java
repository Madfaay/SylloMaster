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
public class Syllogism implements Validator {
    private Proposition major;
    private Proposition minor;
    private Proposition conclusion;
    private int FigureNum;
    private ArrayList<String> invalid;
    private  String language = "English" ;

    /**
     * Returns the major proposition.
     *
     * @return the major proposition.
     */
    public Proposition getMajor() {
        return major;
    }

    /**
     * Returns the minor proposition.
     *
     * @return the minor proposition.
     */
    public Proposition getMinor() {
        return minor;
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
    public Syllogism(){
        this.invalid = new ArrayList<>();
    }

    /**
     * Constructor to create a syllogism with detailed information for each proposition (major, minor, conclusion).
     *
     * @param MajorQuantif Quantifier of the major premise.
     * @param MajorSubject Subject of the major premise.
     * @param MajorPredicat Predicate of the major premise.
     * @param isMajorAffirmative Boolean indicating if the major premise is affirmative.
     * @param MinorQuantif Quantifier of the minor premise.
     * @param MinorSubject Subject of the minor premise.
     * @param MinorPredicat Predicate of the minor premise.
     * @param isMinorAffirmative Boolean indicating if the minor premise is affirmative.
     * @param ConclusionQuantif Quantifier of the conclusion.
     * @param ConclusionSubject Subject of the conclusion.
     * @param ConclusionPredicate Predicate of the conclusion.
     * @param isConclusionAffirmative Boolean indicating if the conclusion is affirmative.
     */
    public Syllogism(Quantifier MajorQuantif, String MajorSubject, String MajorPredicat, boolean isMajorAffirmative,
                     Quantifier MinorQuantif, String MinorSubject, String MinorPredicat, boolean isMinorAffirmative,
                     Quantifier ConclusionQuantif, String ConclusionSubject, String ConclusionPredicate, boolean isConclusionAffirmative,
                     String language
                      ) {



        this.major = new Proposition(MajorSubject, MajorPredicat, MajorQuantif, isMajorAffirmative);
        this.minor = new Proposition(MinorSubject, MinorPredicat, MinorQuantif, isMinorAffirmative);
        this.conclusion = new Proposition(ConclusionSubject, ConclusionPredicate, ConclusionQuantif, isConclusionAffirmative);
        this.language = language;


        // Initialization of the list of invalidities
        this.invalid = new ArrayList<>();
    }


    /**
     * Constructor to create a syllogism based on the figure of the syllogism.
     *
     * @param quantifPremise1 Quantifier of the major premise.
     * @param quantifPremise2 Quantifier of the minor premise.
     * @param quantifConclusion Quantifier of the conclusion.
     * @param Subject Subject of the conclusion.
     * @param predicat Predicate of the conclusion.
     * @param MiddleTerm Middle term between the premises.
     * @param isAffirmativePremis1 Boolean indicating if the major premise is affirmative.
     * @param isAffirmativePremiss2 Boolean indicating if the minor premise is affirmative.
     * @param isAffirmativeConclusion Boolean indicating if the conclusion is affirmative.
     * @param FigureNum Figure number of the syllogism (must be between 1 and 4).
     * @throws IllegalArgumentException if the figure number is outside the range [1, 4].
     */
    public Syllogism(Quantifier quantifPremise1, Quantifier quantifPremise2, Quantifier quantifConclusion,
                     String Subject, String predicat, String MiddleTerm,
                     boolean isAffirmativePremis1, boolean isAffirmativePremiss2, boolean isAffirmativeConclusion,
                     int FigureNum, String language) {


        if (FigureNum < 1 || FigureNum > 4) {
            throw new IllegalArgumentException("Le numéro de figure doit être entre 1 et 4.");
        }
        this.FigureNum = FigureNum;


        // Creation of the premises based on the figure.
        switch (FigureNum) {
            case 1:
                this.major = new Proposition(MiddleTerm, predicat, quantifPremise1, isAffirmativePremis1);
                this.minor = new Proposition(Subject, MiddleTerm, quantifPremise2, isAffirmativePremiss2);
                break;
            case 2:
                this.major = new Proposition(predicat, MiddleTerm, quantifPremise1, isAffirmativePremis1);
                this.minor = new Proposition(Subject, MiddleTerm, quantifPremise2, isAffirmativePremiss2);
                break;
            case 3:
                this.major = new Proposition(MiddleTerm, predicat, quantifPremise1, isAffirmativePremis1);
                this.minor = new Proposition(MiddleTerm, Subject, quantifPremise2, isAffirmativePremiss2);
                break;
            case 4:
                this.major = new Proposition(predicat, MiddleTerm, quantifPremise1, isAffirmativePremis1);
                this.minor = new Proposition(MiddleTerm, Subject, quantifPremise2, isAffirmativePremiss2);
                break;
        }

        this.conclusion = new Proposition(Subject, predicat, quantifConclusion, isAffirmativeConclusion);
        this.language = language;
        this.invalid = new ArrayList<>();
    }

    Syllogism(Proposition maj, Proposition min, Proposition conclusion, String language) {
        this.conclusion = conclusion;
        this.major = maj;
        this.minor = min;
        this.FigureNum = figureDetect();
        this.invalid = new ArrayList<>();
        this.language = language;
    }
    //----------------------------------------------------------------//
    //---------------------GETTERS-----------------------------------//
    //--------------------------------------------------------------//

    public Proposition getConclusion() {
        return conclusion;
    }

    //----------------------------------------------------------------//
    //---------------------SETTERS-----------------------------------//
    //--------------------------------------------------------------//

    /**
     * Defines the major premise of the syllogism.
     */
    public void setMajeur(String FirstTerm, String SecondTerm, Quantifier quantifier, boolean isAffirmative) {
        this.major = new Proposition(FirstTerm, SecondTerm, quantifier, isAffirmative);
    }

    /**
     * Defines the minor premise of the syllogism.
     */
    public void setMineur(String FirstTerm, String SecondTerm, Quantifier quantifier, boolean estAffirmative) {
        this.minor = new Proposition(FirstTerm, SecondTerm, quantifier, estAffirmative);
    }

    /**
     * Defines the conclusion of the syllogism.
     */
    public void setConclusion(String FirstTerm, String SecondTerm, Quantifier quantifier, boolean isAffirmative) {
        this.conclusion = new Proposition(FirstTerm, SecondTerm, quantifier, isAffirmative);
    }

    /**
     * Defines the figure number of the syllogism.
     */
    public void setFigureNum(int figureNum) {
        this.FigureNum = figureNum;
    }

    //-----------------------------------------------------------------//
    //----------------------MANAGEMENT OF FIGURES----------------------//
    //-----------------------------------------------------------------//

    /**
     * Returns the middle term in the major premise based on the figure.
     */
    public Term getMajorMiddleTerm() {
        if(FigureNum == 1 || FigureNum == 3){
            return (getMajor()).getFirstTerm();}
        else{
            return (getMajor()).getSecondTerm();}
    }

    /**
     * Returns the middle term in the minor premise based on the figure.
     */
    public Term getMinorMiddleterm(){
        if(FigureNum == 1 || FigureNum == 2){
            return getMinor().getSecondTerm();}
        else{
            return getMinor().getFirstTerm();}
    }

    /**
     * Returns the predicate based on the figure of the syllogism.
     */
    public Term getPredicat() {
        if(FigureNum == 1 || FigureNum == 3){
            return getMajor().getSecondTerm();
        }
        else{
            return getMajor().getFirstTerm();
        }
    }

    /**
     * Returns the subject based on the figure of the syllogism.
     */
    public Term getSujet() {
        if(FigureNum == 1 || FigureNum == 2){
            return getMinor().getFirstTerm();
        }
        else{
            return getMinor().getSecondTerm();
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
    public int figureDetect(){
        if(Objects.equals(major.getFirstTerm().getExpression(), minor.getSecondTerm().getExpression())){
            return 1;
        }
        if(Objects.equals(major.getSecondTerm().getExpression(), minor.getSecondTerm().getExpression())){
            return 2;
        }
        if(Objects.equals(major.getFirstTerm().getExpression(), minor.getFirstTerm().getExpression())){
            return 3;
        }
        if(Objects.equals(major.getSecondTerm().getExpression(), minor.getFirstTerm().getExpression())){
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
    public void MiddleTermRule() {

        if (!getMajorMiddleTerm().isUniversal() && !getMinorMiddleterm().isUniversal()) { //<If the middle term is particular in both premises, it is false.
            if(this.language.equals("English")){
                (this.invalid).add("Middle Term");
            }
            else{
                (this.invalid).add("Moyen Terme");
            }
        }
    }

    /**
     * The Latius rule states that if a term is universal in the conclusion,
     * it must also be universal in the premise.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void LatiusRule() {

        if (conclusion.getFirstTerm().isUniversal()) { //< If the subject of the conclusion is universal,
            if (!getSujet().isUniversal()) { //< The subject in the premise must also be universal.
                invalid.add("Latius");
            }
        }

        if (conclusion.getSecondTerm().isUniversal()) { //< If the predicate of the conclusion is universal,
            if (!getPredicat().isUniversal()) { //< The predicate in the premise must also be universal.
                invalid.add("Latius");
            }
        }

    }

    /**
     * The rNN rule states that if both premises are negative, no conclusion can be drawn.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rNN() {
        if(!major.isAffirmative() && !minor.isAffirmative()){
            invalid.add("rNN");
        }
    }

    /**
     * The rN rule states that if one premise is negative, the conclusion must also be negative.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rN() {
        if (major.isAffirmative() && minor.isAffirmative()) {
            return; //< Both premises are affirmative, so the rN rule does not apply.
        }

        // Checks if the conclusion is affirmative when it should be negative.
        if (conclusion.isAffirmative()) {
            invalid.add("rN");
        }
    }

    /**
     * The rAA rule states that if both premises are positive, the conclusion cannot be negative.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rAA() {
        if(major.isAffirmative() && minor.isAffirmative()) {
            if(!conclusion.isAffirmative()) {
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
        if(!major.isUniversal() && !minor.isUniversal()) { //<If both are particular.
            invalid.add("rPP");
        }
    }

    /**
     * The rP rule states that if one premise is particular, the conclusion must also be particular.
     * If the rule is not valid, it is added to the list of invalid rules.
     */
    @Override
    public void rP() {
        if(!major.isUniversal() || !minor.isUniversal()) { //< If one of the premises is particular.
            if(conclusion.isUniversal()) { //< And if the conclusion is not particular.
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
        if(major.isUniversal() && minor.isUniversal()) {
            if(!conclusion.isUniversal()) {
                invalid.add("rUU");
            }
        }
    }

    /**
     * Validates the syllogism by applying logical rules and determines if the conclusion is interesting.
     */
     @Override
    public Response valider() {
        // The list of invalid rules is cleared (in case you want to test multiple times after modifications).
         invalid.clear();

        // We apply all the rules.
        MiddleTermRule();
        LatiusRule();
        rNN();
        rN();
        rAA();
        rPP();
        rP();
        //rUU();

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
        return new Response(message, isValid, nouvelleConclusion);

    }
/*****************************************************************************************************/
    /**
     * Allows choosing the rule that we want to submit for validation.
     */
    public Response validRule(ArrayList<String> check){
        if(check.isEmpty()) {
            return new Response("No rules selected", true, null);
        }
        invalid.clear();
        Proposition nouvelleConclusion = convertConclusion(); //< The new conclusion if the previous one is not interesting.


        for(String isCheck : check){
            if("regleMoyenTerme".equals(isCheck)){
                MiddleTermRule();
            }else if("regleLatius".equals(isCheck)){
                LatiusRule();
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
        boolean isValid = invalid.isEmpty();
        String message;
        if (isValid) {
            message = "Every rules chosen are validated";
        }else{
            message = "The rules that are not validated are: ";
            for (String s : invalid) {
                message += s + "; ";
            }
        }
        return new Response(message, isValid, nouvelleConclusion);
    }

    //------------------------------------------------------------------//
    //---------------MANAGEMENT OF INTERESTING CONCLUSIONS--------------//
    //------------------------------------------------------------------//

    /**
     * Method to recognize uninteresting syllogisms.
     * @return returns true if the syllogism is not interesting, and false otherwise.
     * **/

    public boolean estIninteressant() {

        if (major.isUniversal() && minor.isUniversal() && !conclusion.isUniversal()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that returns an interesting conclusion if it is not interesting by default.
     * **/
    public Proposition convertConclusion() {
        if(estIninteressant()) {
            return new Proposition(conclusion.getFirstTermString(), conclusion.getSecondTermString(),
                    major.getQuantificator(), conclusion.isAffirmative());
        }
        return null;
    }




}
