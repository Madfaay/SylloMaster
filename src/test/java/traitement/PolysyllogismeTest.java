package traitement;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PolysyllogismeTest {

    @Test
    void dynamicTestgGetIsolatedCondition()
    {
        Polysyllogisme poly = new Polysyllogisme();
        assertNull(poly.getFstIsolated());
        assertNull(poly.getLstIsolated());

    }

    @Test
    void dynamicTestGetIsolatedTermNoIsolated()
    {
        Polysyllogisme poly = new Polysyllogisme();
        Proposition p1 = new Proposition("terme" , "terme2" , new Quantificateur("quantif" , true) , true);
        Proposition p2 = new Proposition("terme3" , "terme4" , new Quantificateur("quantif" , true) , true);
        assertNull(poly.getIsolatedTerm(p1 , p2));

    }

    @Test
    void fonctionalTestTermeEgauxFalse()
    {
        Polysyllogisme poly = new Polysyllogisme();
        assertFalse(poly.termesEgaux("mm", "nn"));
    }

    @Test
    void fonctionalTestTermeEgauxTrue()
    {
        Polysyllogisme poly = new Polysyllogisme();
        assertTrue(poly.termesEgaux("mm", "mm"));
    }



    @Test
    void fonctionalPropValidAllTermsPos()
    {
        Polysyllogisme poly = new Polysyllogisme();
        Proposition p1 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        Proposition p2 = new Proposition("commun" , "commun3" , new Quantificateur("commun" , true) , true);
        assertTrue(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

        p1 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        p2 = new Proposition("commun3" , "commun" , new Quantificateur("commun" , true) , true);
        assertTrue(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

        p1 = new Proposition("commun2" , "commun" , new Quantificateur("commun" , true) , true);
        p2 = new Proposition("commun3" , "commun" , new Quantificateur("commun" , true) , true);
        assertTrue(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));


        p1 = new Proposition("commun2" , "commun" , new Quantificateur("commun" , true) , true);
        p2 = new Proposition("commun" , "commun3" , new Quantificateur("commun" , true) , true);
        assertTrue(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

    }


    @Test
    void fonctionalPropInvalidAllTermsPos()
    {
        Polysyllogisme poly = new Polysyllogisme();
        Proposition p1 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        Proposition p2 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        assertFalse(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

        p1 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        p2 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        assertFalse(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

        p1 = new Proposition("commun" , "commun2" , new Quantificateur("commun" , true) , true);
        p2 = new Proposition("commun3" , "commun4" , new Quantificateur("commun" , true) , true);
        assertFalse(poly.deuxPropsValide(p1.getPremierTerme().getExpression() , p1.getDeuxiemeTerme().getExpression() ,
                p2.getPremierTerme().getExpression() , p2.getDeuxiemeTerme().getExpression()));

    }



    @Test
    void fonctionalTestConclusionRespected()
    {
        Polysyllogisme poly = new Polysyllogisme();
        Proposition prop1 = new Proposition("a" , "b" ,new Quantificateur("" , true), true);
        Proposition prop2 = new Proposition("b" , "c" ,new Quantificateur("" , true), true);
        Proposition prop3 = new Proposition("c" , "d" ,new Quantificateur("" , true), true);
        Proposition prop4 = new Proposition("d" , "vélo" ,new Quantificateur("" , true), true);

        Proposition conclusion = new Proposition("a" , "vélo",new Quantificateur("" , true), true);

        List<Proposition> propositions = new ArrayList<>();
        propositions.add(prop1);
        propositions.add(prop2);
        propositions.add(prop3);
        propositions.add(prop4);


        poly.setConclusion(conclusion);
        poly.setPremises(propositions);

         conclusion = new Proposition("vélo" , "a",new Quantificateur("" , true), true);
        poly.setConclusion(conclusion);

        assertTrue(poly.conclusionRespected());

    }



    @Test
    void fonctionalTestConclusionUnRespected()
    {
        Polysyllogisme poly = new Polysyllogisme();
        Proposition prop1 = new Proposition("mammifère" , "animal " ,new Quantificateur("" , true), true);
        Proposition prop2 = new Proposition("fox à poils durs" , "fox" ,new Quantificateur("" , true), true);
        Proposition prop3 = new Proposition("vélo" , "animal " ,new Quantificateur("" , true), true);
        Proposition prop4 = new Proposition("mini-vélo" , "vélo" ,new Quantificateur("" , true), true);
        Proposition prop5 = new Proposition("fox" , "chien" ,new Quantificateur("" , true), true);
        Proposition prop6 = new Proposition("chien" , "mammifère" ,new Quantificateur("" , true), true);
        Proposition conclusion = new Proposition("mini-vélo" , "fox à poil durs" ,new Quantificateur("" , true), true);

        List<Proposition> propositions = new ArrayList<Proposition>();
        propositions.add(prop1);
        propositions.add(prop2);
        propositions.add(prop3);
        propositions.add(prop5);
        propositions.add(prop4);
        propositions.add(prop6);

        poly.setPremises(propositions);
        poly.setConclusion(conclusion);
        assertFalse(poly.conclusionRespected());



    }

    //----------------------------------------------------------------//
    //---------------------TESTS RULES-------------------------------//
    //--------------------------------------------------------------//

    // Medium Term Tests
    @Test
    void testMediumTerm() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();

        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n'est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.regleMoyenTerme();

        assertEquals(0, polysyllogisme.getInvalid().size(), "Medium Term rule must be respected.");

    }

    @Test
    void testMediumTermInvalid() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();

        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);


        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Aucun fox est un chien.

        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        // here animal is the medium is existential in both premise so it's false.
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",true); //< Tout vélo est un animal
        //------------------
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo n'est pas un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.regleMoyenTerme();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Medium Term rule must be not respected");

    }

    //Test of latius rule
    @Test
    void testLatius() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();

        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        // 2. In this premise, the term "fox à poils durs" is universal so the rule is respected
        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox

        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Aucun fox est un chien.
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo

        // 1. In the conclusion, the term 'fox à poils durs' is universal, so it must be universal in the premise
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.regleLatius();
        System.out.println();
        assertEquals(0, polysyllogisme.getInvalid().size(), "Medium Term rule must be not respected");

    }

    @Test
    void testLatiusInvalid() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();

        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);
        Quantificateur certain = new Quantificateur("Certain", false);

        //2. The term "fox à poils dur" is not universal here so the rule is not respected
        polysyllogisme.addPremise(certain,"fox à poils durs", "fox",true); //< Certain fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.

        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo

        //1. The term "fox à poil dur" is universal so it must also be in the premise.
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs.

        polysyllogisme.regleLatius();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Latius hos rule should not be respected ");

    }
    //Test rNN
    @Test
    void testRnn() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Aucun fox à poils durs n'est un fox.
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère.
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal.
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal.
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo.
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rNN();

        assertEquals(0, polysyllogisme.getInvalid().size(), "The rule (rNN) must be respected. ");
    }

    @Test
    void testRnnInvalid() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(aucun,"fox à poils durs", "fox",false); //< Aucun fox à poils durs n'est un fox.
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien.
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère.
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal.
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal.
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo.
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rNN();

        assertEquals(1, polysyllogisme.getInvalid().size(), "The rule (rNN) must not be respected. ");
    }
    // Test rN

    @Test
    void testrN() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);



        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        // There are one negative premise so the conclusion must be negative
        polysyllogisme.addPremise(aucun,"vélo", "animal",true); //< Aucun vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        // The conclusion is negative so rN is respected.
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rN();

        assertEquals(0, polysyllogisme.getInvalid().size(), "The rule (rN) must be respected ");
    }

    @Test
    void testrNInvalid() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);



        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        // There are one negative premise so the conclusion must be negative
        polysyllogisme.addPremise(aucun,"vélo", "animal",false); //< Aucun vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        // The conclusion is negative so rN is respected.
        polysyllogisme.addConclusion(tout,"mini-vélo", "fox à poil dur",true); //< Tout mini-vélo n’est un fox à poil durs

        polysyllogisme.rN();

        assertEquals(1, polysyllogisme.getInvalid().size(), "The rule (rN) must no be respected.");
    }


    // Test rAA

    @Test
    void testrAA() {
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",true); //< Aucun vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",true); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rAA();

        assertEquals(0, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être correcte.");
    }

    @Test
    void testrAAInvalid() {
        // Toutes les premises sont universelles mais la conclusion est particulière.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",true); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rAA();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }


    @Test
    void testrPP() {

        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur aucun = new Quantificateur("Aucun", true);

        polysyllogisme.addPremise(aucun,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(aucun,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(aucun,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(aucun,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rPP();

        assertEquals(0, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    @Test
    void testrPPInvalid() {
        // Toutes les premises sont particulières.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur aucun = new Quantificateur("il n'existe", false);

        polysyllogisme.addPremise(aucun,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(aucun,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(aucun,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(aucun,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(aucun,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(aucun,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(aucun,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rPP();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }
    @Test
    void testrP(){
        // Toutes les premises sont particulières.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur nexiste = new Quantificateur("il n'existe", false);

        polysyllogisme.addPremise(nexiste,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(nexiste,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(nexiste,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(nexiste,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(nexiste,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(nexiste,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rP();

        assertEquals(0, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    @Test
    void testrPInvalid(){
        // Toutes les premises sont particulières.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur nexiste = new Quantificateur("il n'existe", false);

        polysyllogisme.addPremise(nexiste,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(nexiste,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(nexiste,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(nexiste,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(nexiste,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(tout,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rP();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    @Test
    void testRuu(){
        // Toutes les premises sont particulières.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur nexiste = new Quantificateur("il n'existe", false);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(tout,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rUU();

        assertEquals(0, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }

    @Test
    void testRuuInvalid(){
        // Toutes les premises sont particulières.
        Polysyllogisme polysyllogisme = new Polysyllogisme();
        Quantificateur tout = new Quantificateur("TouT", true);
        Quantificateur nexiste = new Quantificateur("il n'existe", false);

        polysyllogisme.addPremise(tout,"fox à poils durs", "fox",true); //< Tout fox à poils durs est un fox
        polysyllogisme.addPremise(tout,"fox", "chien",true); //< Tout fox est un chien
        polysyllogisme.addPremise(tout,"chien", "mammifère",true); //< Tout chien est un mammifère
        polysyllogisme.addPremise(tout,"mammifère", "animal",true); //< Tout mammifère est un animal
        polysyllogisme.addPremise(tout,"vélo", "animal",true); //< Tout vélo n’est un animal
        polysyllogisme.addPremise(tout,"mini-vélo", "vélo",false); //< Tout mini-vélo est un vélo
        polysyllogisme.addConclusion(nexiste,"mini-vélo", "fox à poil dur",false); //< Aucun mini-vélo n’est un fox à poil durs

        polysyllogisme.rUU();

        assertEquals(1, polysyllogisme.getInvalid().size(), "Le poly syllogisme doit être incorrecte.");
    }


}
