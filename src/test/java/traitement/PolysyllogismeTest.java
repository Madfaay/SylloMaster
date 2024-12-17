package traitement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PolysyllogismeTest {
    /**
     * Sets the conclusion of the poly.
     *
     * @param Quantificateur the quantifier of the conclusion (e.g., "All", "Some").
     */


    /**
     * Tests the retrieval of isolated conditions when none are set.
     * Ensures that the first and last isolated conditions return {@code null}.
     */
    @Test
    void dynamicTestgGetIsolatedCondition() {
        Polysyllogism poly = new Polysyllogism("English");
        assertNull(poly.getFstIsolated());
        assertNull(poly.getLstIsolated());
    }

    /**
     * Tests the behavior of {@code getIsolatedTerm} when there are no isolated terms.
     * Ensures that the method returns {@code null}.
     */
    @Test
    void dynamicTestGetIsolatedTermNoIsolated() {
        Polysyllogism poly = new Polysyllogism("English");
        Proposition p1 = new Proposition("terme", "terme2", new Quantifier("quantif", true), true);
        Proposition p2 = new Proposition("terme3", "terme4", new  Quantifier("quantif", true), true);
        assertNull(poly.getIsolatedTerm(p1, p2));
    }

    /**
     * Tests the {@code termesEgaux} method for terms that are not equal.
     * Ensures that the method returns {@code false}.
     */
    @Test
    void fonctionalTestTermeEgauxFalse() {
        Polysyllogism poly = new Polysyllogism("English");
        assertFalse(poly.EqualTerms("mm", "nn"));
    }

    /**
     * Tests the {@code termesEgaux} method for terms that are equal.
     * Ensures that the method returns {@code true}.
     */
    @Test
    void fonctionalTestTermeEgauxTrue() {
        Polysyllogism poly = new Polysyllogism("English");
        assertTrue(poly.EqualTerms("mm", "mm"));
    }

    /**
     * Tests the {@code deuxPropsValide} method with valid propositions.
     * Ensures that the method returns {@code true} for valid inputs.
     */
    @Test
    void fonctionalPropValidAllTermsPos() {
        Polysyllogism poly = new Polysyllogism("English");
        // Test with various valid configurations of propositions
        Proposition p1 = new Proposition("commun", "commun2", new Quantifier("commun", true), true);
        Proposition p2 = new Proposition("commun", "commun3", new Quantifier("commun", true), true);
        assertTrue(poly.twoValidProps(p1.getFirstTerm().getExpression(), p1.getSecondTerm().getExpression(),
                p2.getFirstTerm().getExpression(), p2.getSecondTerm().getExpression()));
        // Additional cases are also validated below
    }

    /**
     * Tests the {@code deuxPropsValide} method with invalid propositions.
     * Ensures that the method returns {@code false} for invalid inputs.
     */
    @Test
    void fonctionalPropInvalidAllTermsPos() {
        Polysyllogism poly = new Polysyllogism("English");
        // Test with various invalid configurations of propositions
        Proposition p1 = new Proposition("commun", "commun2", new Quantifier("commun", true), true);
        Proposition p2 = new Proposition("commun", "commun2", new Quantifier("commun", true), true);
        assertFalse(poly.twoValidProps(p1.getFirstTerm().getExpression(), p1.getSecondTerm().getExpression(),
                p2.getFirstTerm().getExpression(), p2.getSecondTerm().getExpression()));
        // Additional cases are also validated below
    }

    /**
     * Tests the {@code conclusionRespected} method with a valid conclusion.
     * Ensures that the method returns {@code true} when the conclusion respects the premises.
     */
    @Test
    void fonctionalTestConclusionRespected() {
        Polysyllogism poly = new Polysyllogism("English");
        // Setup valid premises and conclusion
        Proposition prop1 = new Proposition("a", "b", new Quantifier("", true), true);
        Proposition prop2 = new Proposition("b", "c", new Quantifier("", true), true);
        Proposition prop3 = new Proposition("c", "d", new Quantifier("", true), true);
        Proposition prop4 = new Proposition("d", "vélo", new Quantifier("", true), true);
        Proposition conclusion = new Proposition("a", "vélo", new Quantifier("", true), true);
        List<Proposition> propositions = new ArrayList<>();
        propositions.add(prop1);
        propositions.add(prop2);
        propositions.add(prop3);
        propositions.add(prop4);
        poly.setConclusion(conclusion);
        poly.setPremises(propositions);
        assertTrue(poly.conclusionRespected());
    }

    /**
     * Tests the {@code conclusionRespected} method with an invalid conclusion.
     * Ensures that the method returns {@code false} when the conclusion does not respect the premises.
     */
    @Test
    void fonctionalTestConclusionUnRespected() {
        Polysyllogism poly = new Polysyllogism("English");
        // Setup premises and an invalid conclusion
        Proposition prop1 = new Proposition("mammifère", "animal", new Quantifier("", true), true);
        Proposition prop2 = new Proposition("fox à poils durs", "fox", new Quantifier("", true), true);
        Proposition prop3 = new Proposition("vélo", "animal", new Quantifier("", true), true);
        Proposition prop4 = new Proposition("mini-vélo", "vélo", new Quantifier("", true), true);
        Proposition prop5 = new Proposition("fox", "chien", new Quantifier("", true), true);
        Proposition prop6 = new Proposition("chien", "mammifère", new Quantifier("", true), true);
        Proposition conclusion = new Proposition("mini-vélo", "fox à poil durs", new Quantifier("", true), true);
        List<Proposition> propositions = new ArrayList<>();
        propositions.add(prop1);
        propositions.add(prop2);
        propositions.add(prop3);
        propositions.add(prop4);
        propositions.add(prop5);
        propositions.add(prop6);
        poly.setPremises(propositions);
        poly.setConclusion(conclusion);
        assertFalse(poly.conclusionRespected());
    }

    //----------------------------------------------------------------//
    //---------------------TESTS RULES-------------------------------//
    //--------------------------------------------------------------//

    // Medium Term Tests



/**
 * Test the medium term rule with valid premises and conclusions.
 * This test verifies that the rule of medium term is respected when valid premises
 * and conclusions are provided. It checks that no invalid results are returned.
 */
    @Test
    void testMediumTerm() {
        Polysyllogism polysyllogism = new Polysyllogism("English");

        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n'est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.MiddleTermRule();

        assertEquals(0, polysyllogism.getInvalid().size(), "Medium Term rule must be respected.");

    }

    /**
     * Test the medium term rule with invalid premises or conclusions.
     * This test verifies that the medium term rule is not respected when invalid premises
     * or conclusions are provided. It checks that one invalid result is returned.
     */
    @Test
    void testMediumTermInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism("English");

        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);


        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Aucun fox est un chien.

        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        // here animal is the medium is existential in both premise so it's false.
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",true); //< Tout vélo est un animal
        //------------------
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo n'est pas un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.MiddleTermRule();

        assertEquals(1, polysyllogism.getInvalid().size(), "Medium Term rule must be not respected");

    }

    //Test of latius rule

    /**
     * Test the Latius rule with valid premises and conclusions.
     * This test verifies that the Latius rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testLatius() {
        Polysyllogism polysyllogism = new Polysyllogism("English");

        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        // 2. In this premise, the term "fox à poils durs" is universal so the rule is respected
        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox

        polysyllogism.addPremise(tout,"fox", "chien",true); //< Aucun fox est un chien.
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo

        // 1. In the conclusion, the term 'fox à poils durs' is universal, so it must be universal in the premise
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.LatiusRule();
        System.out.println();
        assertEquals(0, polysyllogism.getInvalid().size(), "Medium Term rule must be not respected");

    }


    /**
     * Test the Latius rule with invalid premises or conclusions.
     * This test verifies that the Latius rule is not respected when invalid premises or
     * conclusions are provided. It checks that one invalid result is returned.
     */
    @Test
    void testLatiusInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism("English");

        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);
        Quantifier certain = new Quantifier("Certain", false);

        //2. The term "fox à poils dur" is not universal here so the rule is not respected
        polysyllogism.addPremise(certain,"fox à poils durs", "fox",true); //< Certain fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.

        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo

        //1. The term "fox à poil dur" is universal so it must also be in the premise.
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs.

        polysyllogism.LatiusRule();

        assertEquals(1, polysyllogism.getInvalid().size(), "Latius hos rule should not be respected ");

    }
    //Test rNN

    /**
     * Test the rNN rule with valid premises and conclusions.
     *
     * This test verifies that the rNN rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testRnn() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Aucun fox à poils durs n'est un fox.
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère.
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal.
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal.
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo.
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rNN();

        assertEquals(0, polysyllogism.getInvalid().size(), "The rule (rNN) must be respected. ");
    }

    /**
     * Test the rNN rule with invalid premises or conclusions.
     * This test verifies that the rNN rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testRnnInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(aucun,"fox à poils durs", "fox",false); //< Aucun fox à poils durs n'est un fox.
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère.
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal.
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal.
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo.
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rNN();

        assertEquals(1, polysyllogism.getInvalid().size(), "The rule (rNN) must not be respected. ");
    }
    // Test rN


    /**
     * Test the rN rule with valid premises and conclusions.
     * This test verifies that the rN rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testrN() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);



        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        // There are one negative premise so the conclusion must be negative
        polysyllogism.addPremise(aucun,"vélo", "animal",true); //< Aucun vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        // The conclusion is negative so rN is respected.
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rN();

        assertEquals(0, polysyllogism.getInvalid().size(), "The rule (rN) must be respected ");
    }


    /**
     * Test the rN rule with invalid premises or conclusions.
     * This test verifies that the rN rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testrNInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("Tout", true);
        Quantifier aucun = new Quantifier("Aucun", true);



        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        // There are one negative premise so the conclusion must be negative
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        // The conclusion is negative so rN is respected.
        polysyllogism.addConclusion(tout,"mini-vélo", "fox à poil dur",true); //< Tout mini-vélo n’est un fox à poil durs

        polysyllogism.rN();

        assertEquals(1, polysyllogism.getInvalid().size(), "The rule (rN) must no be respected.");
    }


    // Test rAA

    /**
     * Test the rAA rule with valid premises and conclusions.
     * This test verifies that the rAA rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testrAA() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",true); //< Aucun vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",true); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rAA();

        assertEquals(0, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être correcte.");
    }


    /**
     * Test the rAA rule with invalid premises or conclusions.
     * This test verifies that the rAA rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testrAAInvalid() {
        // Toutes les premises sont universelles mais la conclusion est particulière.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rAA();

        assertEquals(1, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }



    /**
     * Test the rPP rule with valid premises and conclusions.
     * This test verifies that the rPP rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testrPP() {

        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier aucun = new Quantifier("Aucun", true);

        polysyllogism.addPremise(aucun,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(aucun,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(aucun,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(aucun,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rPP();

        assertEquals(0, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }


    /**
     * Test the rPP rule with invalid premises or conclusions.
     * This test verifies that the rPP rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testrPPInvalid() {
        // Toutes les premises sont particulières.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier aucun = new Quantifier("il n'existe", false);

        polysyllogism.addPremise(aucun,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(aucun,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(aucun,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(aucun,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(aucun,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rPP();

        assertEquals(1, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    /**
     * Test the rP rule with valid premises and conclusions.
     * This test verifies that the rP rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testrP(){
        // Toutes les premises sont particulières.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier nexiste = new Quantifier("il n'existe", false);

        polysyllogism.addPremise(nexiste,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(nexiste,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(nexiste,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(nexiste,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(nexiste,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(nexiste,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rP();

        assertEquals(0, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }
    /**
     * Test the rP rule with invalid premises or conclusions.
     *
     * This test verifies that the rP rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testrPInvalid(){
        // Toutes les premises sont particulières.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier nexiste = new Quantifier("il n'existe", false);

        polysyllogism.addPremise(nexiste,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(nexiste,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(nexiste,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(nexiste,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(nexiste,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(tout,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rP();

        assertEquals(1, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }


    /**
     * Test the Ruu rule with valid premises and conclusions.
     *
     * This test verifies that the Ruu rule is respected when valid premises and conclusions
     * are provided. It checks that no invalid results are returned.
     */
    @Test
    void testRuu(){
        // Toutes les premises sont particulières.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier nexiste = new Quantifier("il n'existe", false);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(tout,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rUU();

        assertEquals(0, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    /**
     * Test the Ruu rule with invalid premises or conclusions.
     *
     * This test verifies that the Ruu rule is not respected when invalid premises or conclusions
     * are provided. It checks that one invalid result is returned.
     */
    @Test
    void testRuuInvalid(){
        // Toutes les premises sont particulières.
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier nexiste = new Quantifier("il n'existe", false);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(nexiste,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogism.rUU();

        assertEquals(1, polysyllogism.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    @Test
    void testValidate() {
        Polysyllogism polysyllogism = new Polysyllogism("English");
        Quantifier tout = new Quantifier("TouT", true);
        Quantifier aucun = new Quantifier("il n'existe", true);

        polysyllogism.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogism.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogism.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogism.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogism.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal
        polysyllogism.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogism.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        Response r = polysyllogism.validate();

        for (String rules : polysyllogism.getInvalid() ) {
            System.out.println(rules);
        }

        assertEquals(true, r.isValid(), "Le poly syllogisme doit être correcte.");

    }


}
