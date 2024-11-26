package traitement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//
class SyllogismTest {

    /**
     * Test to detect the figure of a syllogism.
     * In this case, we check if the syllogism correctly detects the figure based on the given quantifiers and terms.
     */
    @Test
    void TestFigure() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism(quantifUniv,"Adnane","Dinh",true,quantifExist,
                "omar","Adnane",true,quantifUniv,"Adnane","Dinh",true,"English");

        assertEquals(1,syllo.figureDetect(), "ici");

        System.out.println("test: OK");
    }
    /**
     * Test to detect the figure of a syllogism with different terms.
     * This method tests if figure detection works correctly with different sets of terms.
     */
    @Test
    void TestFigure1() {
        Quantifier quantifUniv = new Quantifier("Tous", true);
        Quantifier quantifExist = new Quantifier(" ", false);

        Syllogism syllo = new Syllogism(quantifUniv, "homme", "mortel", true,
                quantifExist, "Socrate", "homme", true,
                quantifExist, "Socrate", "mortel", true,"English");

        assertEquals(1,syllo.figureDetect(),"Doit etre figure 1");
    }

    /**
     * Test the rule for the middle term in a syllogism.
     * This method checks if the middle term rule is applied correctly and if the figure is correctly identified.
     */
    @Test
    void testMoyenTerme() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.MiddleTermRule();
        assertEquals(0, syllo.getInvalid().size(), "Moyen terme devrait être valide");
        assertEquals(2, syllo.figureDetect(), "bonne figure");

        System.out.println("testMoyenTerme: OK");

    }

    /**
     * Test the middle term rule for an invalid case.
     * This method checks if the system correctly identifies an invalid middle term.
     */
    @Test
    public void testMoyenTermeInvalide() {
        /* Syllogisme tester :
         * Tous les chats sont gris.
         * Il existe un animal gris.
         * Il existe un animal qui est un chat.
         * */

        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, true);
        syllo.setFigureNum(2);
        syllo.MiddleTermRule();
        assertEquals(1, syllo.getInvalid().size(), "Moyen terme devrait être invalide");

        System.out.println("testMoyenTermeInvalide: OK");


    }
    /**
     * Test the "Latius" rule for syllogisms.
     * This method ensures that the syllogism follows the Latius rule correctly.
     */
    @Test
    void TestLatiusRule() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.LatiusRule();
        assertEquals(0, syllo.getInvalid().size(), "Latius devrait être valide");

        System.out.println("testRegleLatius: OK");
    }

    /**
     * Test the "Latius" rule for an invalid syllogism.
     * This method checks if an invalid syllogism is detected properly when applying the Latius rule.
     */
    @Test
    void TestLatiusRuleInvalid() {
        Quantifier quantifUniv = new Quantifier("tous", false);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.LatiusRule();
        assertEquals(1, syllo.getInvalid().size(), "Latius devrait être Invalide");

        System.out.println("testRegleLatiusInvalid: OK");
    }


    /**
     * Test the "rNN" rule for syllogisms.
     * This method checks if the syllogism follows the "rNN" rule correctly.
     */
    @Test
    void rNN() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.rNN();
        assertEquals(0, syllo.getInvalid().size(), "rNN devrait être valide");

        System.out.println("testRegleLatius: OK");
    }

    /**
     * Test the "rNN" rule for an invalid syllogism.
     * This method ensures that the system correctly detects an invalid syllogism under the "rNN" rule.
     */
    @Test
    void rNNInvalid() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogism syllo = new Syllogism();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.rNN();
        assertEquals(1, syllo.getInvalid().size(), "rNN devrait être Invalide");

        System.out.println("testRNNInvalid: OK");
    }

    /**
     * Test the "rN" rule for valid syllogisms.
     * This method checks if the "rN" rule is applied correctly with the right premises.
     */
    @Test
    void testRnValide() {
        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("Tous", true);
        Quantifier particulier = new Quantifier("Certains", false);

        syllogism.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogism.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogism.setConclusion("Socrate", "mortel", particulier, false); // Négative

        syllogism.setFigureNum(1);


        syllogism.rN();


        assertEquals(0, syllogism.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 premise négative donc conclusion négative ) : OK");

    }


    /**
     * Test the "rN" rule for an invalid syllogism.
     * This method ensures that the system correctly detects an invalid "rN" syllogism where a premise is negative but the conclusion is affirmative.
     */
    @Test
    void testRnInvalide() {
        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("Tous", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogism.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogism.setConclusion("Socrate", "mortel", particulier, true); // Affirmative

        syllogism.setFigureNum(1);


        syllogism.rN();


        assertEquals(1, syllogism.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 prémise négative mais conclusion affirmative ) : OK");

    }
    /**
     * Test for the rAA rule (Affirmative major premise, affirmative minor premise, conclusion in the same form).
     * In this test, the syllogism follows the rAA rule.
     */
    @Test
    void rAA() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true); // Affirmative
        syllogism.setMineur("Socrates", "men", particulier, true); // Negative

        syllogism.setConclusion("Socrates", "mortal", particulier, true); // Affirmative

        syllogism.setFigureNum(2);

        syllogism.rAA();

        assertEquals(0, syllogism.getInvalid().size(), "The syllogism should respect the rAA rule.");
        System.out.println("testRaa: OK");
    }
    /**
     * Test for the rAA rule (Invalid case: Affirmative major premise, negative minor premise, conclusion should not be affirmative).
     */
    @Test
    void rAAInvalid() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", particulier, true);

        syllogism.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogism.setFigureNum(2);

        syllogism.rAA();

        assertEquals(1, syllogism.getInvalid().size(), "The syllogism should not respect the rAA rule.");
        System.out.println("testRaaInvalid: OK");
    }
    /**
     * Test for the rPP rule (Particular major premise, particular minor premise).
     * This test ensures the syllogism does not respect the rPP rule.
     */
    @Test
    void rPP() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", false);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", particulier, true);

        syllogism.setConclusion("Socrates", "mortal", particulier, false);

        syllogism.setFigureNum(2);

        syllogism.rPP();

        assertEquals(1, syllogism.getInvalid().size(), "The syllogism should not respect the rPP rule.");
        System.out.println("testRppInvalid: OK");
    }
    /**
     * Test for the rPP rule (Invalid case: particular major premise and particular minor premise with a particular conclusion).
     */
    @Test
    void rPPInvalid() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", false);
        Quantifier particulier = new Quantifier(" ", true);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", particulier, true);

        syllogism.setConclusion("Socrates", "mortal", particulier, false);

        syllogism.setFigureNum(2);

        syllogism.rPP();

        assertEquals(0, syllogism.getInvalid().size(), "The syllogism should respect the rPP rule.");
        System.out.println("testRppInvalid: OK");
    }
    /**
     * Test for the rP rule (Universal major premise, particular minor premise).
     * This test ensures the syllogism respects the rP rule.
     */
    @Test
    void rP() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", particulier, true);

        syllogism.setConclusion("Socrates", "mortal", particulier, false);

        syllogism.setFigureNum(2);

        syllogism.rP();

        assertEquals(0, syllogism.getInvalid().size(), "The syllogism should respect the rP rule.");
        System.out.println("testRpValid: OK");
    }
    /**
     * Test for the rP rule (Invalid case: Universal major premise, particular minor premise, conclusion should not be universal).
     */
    @Test
    void rPInvalid() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", particulier, true);

        syllogism.setConclusion("Socrates", "mortal", universel, false); // Invalid conclusion

        syllogism.setFigureNum(2);

        syllogism.rP();

        assertEquals(1, syllogism.getInvalid().size(), "The syllogism should not respect the rP rule.");
        System.out.println("testRpInvalid: OK");
    }
    /**
     * Test for the rUU rule (Universal major premise, universal minor premise).
     * This test ensures the syllogism respects the rUU rule.
     */
    @Test
    void rUU() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", universel, true);

        syllogism.setConclusion("Socrates", "mortal", universel, false);

        syllogism.setFigureNum(2);

        syllogism.rUU();

        assertEquals(0, syllogism.getInvalid().size(), "The syllogism should respect the rUU rule.");
        System.out.println("testRUU: OK");
    }
    /**
     * Test for the rUU rule (Invalid case: universal major and minor premises, but particular conclusion).
     */
    @Test
    void rUUInvalid() {

        Syllogism syllogism = new Syllogism();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogism.setMajeur("men", "mortal", universel, true);
        syllogism.setMineur("Socrates", "men", universel, true);

        syllogism.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogism.setFigureNum(2);

        syllogism.rUU();

        assertEquals(1, syllogism.getInvalid().size(), "The syllogism should not respect the rUU rule.");
        System.out.println("testRUUInvalid: OK");
    }
    /**
     * Validate a syllogism:
     * All lions with short fur resemble foxes with rough fur.
     * All rhinoceroses with coarse fur resemble lions with short fur.
     * All rhinoceroses with coarse fur resemble foxes with rough fur.
     */
    @Test
    void valider() {

        Syllogism syllogism = new Syllogism();
        Quantifier tout = new Quantifier("All", true);
        syllogism.setFigureNum(1);

        syllogism.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogism.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogism.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);

        Response response = syllogism.valider();

        assertTrue(response.isValid(), "The syllogism should be valid.");
        System.out.println("Validation of syllogism: OK");
    }
    /**
     * Test for determining whether a syllogism is 'uninteresting' (i.e., a trivially true conclusion).
     * The syllogism concludes that there exists a mortal human.
     */
    @Test
    void testIninteressant() {

        Syllogism syllogism = new Syllogism();
        Quantifier tous = new Quantifier("All", true);
        Quantifier existe = new Quantifier("There exists", false);

        syllogism.setMajeur("mammals", "mortal", tous, true); // All mammals are mortal
        syllogism.setMineur("humans", "mammals", tous, false); // All humans are mammals

        syllogism.setConclusion("human", "mortal", existe, true); // There exists a mortal human

        syllogism.setFigureNum(1);

        assertTrue(syllogism.estIninteressant(), "The syllogism should be considered uninteresting.");
        System.out.println("testIninteressant() (true case): OK");
    }
    /**
     * Test for converting the conclusion of a syllogism to universal form.
     */
    @Test
    void convertConclusion() {

        Syllogism syllogism = new Syllogism();
        Quantifier tous = new Quantifier("All", true);
        Quantifier existe = new Quantifier("There exists", false);

        syllogism.setMajeur("mammals", "mortal", tous, true); // Universal positive
        syllogism.setMineur("humans", "mammals", tous, true); // Universal positive

        syllogism.setConclusion("human", "mortal", existe, true); // Particular positive

        syllogism.setFigureNum(1);

        assertEquals(true, !syllogism.getConclusion().isUniversal() && syllogism.convertConclusion().isUniversal(), "The conclusion should become universal.");
        System.out.println("testConvertConclusion: OK");
    }

    @Test
    void validerForRules() {
        /**
         * Validate a syllogism with specific rules applied:
         * All lions with short fur resemble foxes with rough fur.
         * All rhinoceroses with coarse fur resemble lions with short fur.
         * All rhinoceroses with coarse fur resemble foxes with rough fur.
         */
        Syllogism syllogism = new Syllogism();
        Quantifier tout = new Quantifier("All", true);
        syllogism.setFigureNum(1);

        syllogism.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogism.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogism.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);
        ArrayList<String> check = new ArrayList<>();
        check.add("regleMoyenTerme");
        check.add("rNN");
        check.add("rP");
        Response response = syllogism.validRule(check);

        assertTrue(response.isValid(), "The syllogism should be valid.");

    }

    // Test
    //Figure 1
    @Test
    void testAAAFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); //<Universelle affirmative

        // AAA-1 : Prémisses et conclusion sont universelles affirmatives
        Syllogism syllo = new Syllogism(
                tout, tout, tout,             // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", //< >Termes : sujet, prédicat, terme moyen
                true, true, true,             // Les prémisses et la conclusion sont affirmatives
                1                             // Figure 1
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAEFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // EAE-1 : Majeure universelle négative, mineure universelle affirmative, conclusion universelle négative
        Syllogism syllo = new Syllogism(
                aucun, tout, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                1                             // Figure 1
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAIIFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // AII-1 : Majeure universelle affirmative, mineure particulière affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                tout, certains, certains,     // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, true, true,             // Les prémisses et la conclusion sont affirmatives
                1                             // Figure 1
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig1() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-1 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, certains, certains,    // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                1                             // Figure 1
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    //Figure 2
    @Test
    void testEAEFig2() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // EAE-2 : Majeure universelle négative, mineure universelle affirmative, conclusion universelle négative
        Syllogism syllo = new Syllogism(
                aucun, tout, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                2                             // Figure 2
        );
        Response r = syllo.valider();
        System.out.println("Regle invalid testEAEFig2 :");
        for (String invalid : syllo.getInvalid()) {
            System.out.println(invalid);
        }
        System.out.println("\n");

        boolean isValid = r.isValid();
        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAEEFig2() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // AEE-2 : Majeure universelle affirmative, mineure universelle négative, conclusion universelle négative
        Syllogism syllo = new Syllogism(
                tout, aucun, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, false, false,           // Affirmativité : prémisse majeure (affirmative), mineure (négative), conclusion (négative)
                2                             // Figure 2
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig2() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-2 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, certains, certains,    // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                2                             // Figure 2
        );
        Response r = syllo.valider();
        System.out.println("Regle invalid EIOFig2 :");
        for (String invalid : syllo.getInvalid()) {
            System.out.println(invalid);
        }
        System.out.println("\n");

        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAOOFig2() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière négative

        // AOO-2 : Majeure universelle affirmative, mineure particulière négative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                tout, certains, certains,     // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, false, false,           // Affirmativité : prémisse majeure (affirmative), mineure (négative), conclusion (négative)
                2                             // Figure 2
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }


    //Figure 3
    @Test
    void testAAIFig3() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // AAI-3 : Majeure universelle affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                tout, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",        // Termes
                true, true, true,                             // Affirmativité
                3                                             // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testIAIFig3() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // IAI-3 : Majeure particulière affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                3                              // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAIIFig3() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // AII-3 : Majeure universelle affirmative, mineure particulière affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                tout, certains, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                3                              // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAOFig3() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // EAO-3 : Majeure universelle négative, mineure universelle affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",         // Termes
                false, true, false,                            // Affirmativité
                3                                              // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testOAOFig3() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // OAO-3 : Majeure particulière négative, mineure universelle affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                3                              // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig3() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-3 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, certains, certains,     // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                3                              // Figure 3
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }



    //Figure 4
    @Test
    void testAAIFig4() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // AAI-4 : Majeure universelle affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                tout, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",        // Termes
                true, true, true,                             // Affirmativité
                4                                             // Figure 4
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAEEFig4() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // AEE-4 : Majeure universelle affirmative, mineure universelle négative, conclusion universelle négative
        Syllogism syllo = new Syllogism(
                tout, aucun, aucun,           // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, false, false,           // Affirmativité
                4                              // Figure 4
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testIAIFig4() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // IAI-4 : Majeure particulière affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogism syllo = new Syllogism(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                4                              // Figure 4
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAOFig4() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // EAO-4 : Majeure universelle négative, mineure universelle affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",         // Termes
                false, true, false,                            // Affirmativité
                4                                              // Figure 4
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig4() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-4 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogism syllo = new Syllogism(
                aucun, certains, certains,     // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                4                              // Figure 4
        );
        Response r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }
}