package traitement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//
class SyllogismeTest {

    /**
     * Test to detect the figure of a syllogism.
     * In this case, we check if the syllogism correctly detects the figure based on the given quantifiers and terms.
     */
    @Test
    void TestFigure() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme(quantifUniv,"Adnane","Dinh",true,quantifExist,
                "omar","Adnane",true,quantifUniv,"Adnane","Dinh",true);

        assertEquals(1,syllo.FigureDetect(), "ici");

    }
    /**
     * Test to detect the figure of a syllogism with different terms.
     * This method tests if figure detection works correctly with different sets of terms.
     */
    @Test
    void TestFigure1() {
        Quantifier quantifUniv = new Quantifier("Tous", true);
        Quantifier quantifExist = new Quantifier(" ", false);

        Syllogisme syllo = new Syllogisme(quantifUniv, "homme", "mortel", true,
                quantifExist, "Socrate", "homme", true,
                quantifExist, "Socrate", "mortel", true);

        assertEquals(1,syllo.FigureDetect(),"Doit etre figure 1");
    }

    /**
     * Test the rule for the middle term in a syllogism.
     * This method checks if the middle term rule is applied correctly and if the figure is correctly identified.
     */
    @Test
    void testMoyenTerme() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.MiddleTermRule();
        assertEquals(0, syllo.getInvalid().size(), "Moyen terme devrait être valide");
        assertEquals(2, syllo.FigureDetect(), "bonne figure");



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

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, true);
        syllo.setFigureNum(2);
        syllo.MiddleTermRule();
        assertEquals(1, syllo.getInvalid().size(), "Moyen terme devrait être invalide");




    }
    /**
     * Test the "Latius" rule for syllogisms.
     * This method ensures that the syllogism follows the Latius rule correctly.
     */
    @Test
    void TestLatiusRule() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.LatiusRule();
        assertEquals(0, syllo.getInvalid().size(), "Latius devrait être valide");


    }

    /**
     * Test the "Latius" rule for an invalid syllogism.
     * This method checks if an invalid syllogism is detected properly when applying the Latius rule.
     */
    @Test
    void TestLatiusRuleInvalid() {
        Quantifier quantifUniv = new Quantifier("tous", false);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.LatiusRule();
        assertEquals(1, syllo.getInvalid().size(), "Latius devrait être Invalide");


    }


    /**
     * Test the "rNN" rule for syllogisms.
     * This method checks if the syllogism follows the "rNN" rule correctly.
     */
    @Test
    void rNN() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.rNN();
        assertEquals(0, syllo.getInvalid().size(), "rNN devrait être valide");


    }

    /**
     * Test the "rNN" rule for an invalid syllogism.
     * This method ensures that the system correctly detects an invalid syllogism under the "rNN" rule.
     */
    @Test
    void rNNInvalid() {
        Quantifier quantifUniv = new Quantifier("tous", true);
        Quantifier quantifExist = new Quantifier("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.rNN();
        assertEquals(1, syllo.getInvalid().size(), "rNN devrait être Invalide");


    }

    /**
     * Test the "rN" rule for valid syllogisms.
     * This method checks if the "rN" rule is applied correctly with the right premises.
     */
    @Test
    void testRnValide() {
        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("Tous", true);
        Quantifier particulier = new Quantifier("Certains", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, false); // Négative

        syllogisme.setFigureNum(1);


        syllogisme.rN();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");



    }


    /**
     * Test the "rN" rule for an invalid syllogism.
     * This method ensures that the system correctly detects an invalid "rN" syllogism where a premise is negative but the conclusion is affirmative.
     */
    @Test
    void testRnInvalide() {
        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("Tous", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, true); // Affirmative

        syllogisme.setFigureNum(1);


        syllogisme.rN();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");



    }
    /**
     * Test for the rAA rule (Affirmative major premise, affirmative minor premise, conclusion in the same form).
     * In this test, the syllogism follows the rAA rule.
     */
    @Test
    void rAA() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true); // Affirmative
        syllogisme.setMineur("Socrates", "men", particulier, true); // Negative

        syllogisme.setConclusion("Socrates", "mortal", particulier, true); // Affirmative

        syllogisme.setFigureNum(2);

        syllogisme.rAA();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rAA rule.");

    }
    /**
     * Test for the rAA rule (Invalid case: Affirmative major premise, negative minor premise, conclusion should not be affirmative).
     */
    @Test
    void rAAInvalid() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rAA();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rAA rule.");

    }
    /**
     * Test for the rPP rule (Particular major premise, particular minor premise).
     * This test ensures the syllogism does not respect the rPP rule.
     */
    @Test
    void rPP() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", false);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rPP();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rPP rule.");

    }
    /**
     * Test for the rPP rule (Invalid case: particular major premise and particular minor premise with a particular conclusion).
     */
    @Test
    void rPPInvalid() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", false);
        Quantifier particulier = new Quantifier(" ", true);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rPP();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rPP rule.");

    }
    /**
     * Test for the rP rule (Universal major premise, particular minor premise).
     * This test ensures the syllogism respects the rP rule.
     */
    @Test
    void rP() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rP();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rP rule.");

    }
    /**
     * Test for the rP rule (Invalid case: Universal major premise, particular minor premise, conclusion should not be universal).
     */
    @Test
    void rPInvalid() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", universel, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rP();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rP rule.");

    }
    /**
     * Test for the rUU rule (Universal major premise, universal minor premise).
     * This test ensures the syllogism respects the rUU rule.
     */
    @Test
    void rUU() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);


        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", universel, true);

        syllogisme.setConclusion("Socrates", "mortal", universel, false);

        syllogisme.setFigureNum(2);

        syllogisme.rUU();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rUU rule.");

    }
    /**
     * Test for the rUU rule (Invalid case: universal major and minor premises, but particular conclusion).
     */
    @Test
    void rUUInvalid() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier universel = new Quantifier("All", true);
        Quantifier particulier = new Quantifier(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", universel, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rUU();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rUU rule.");

    }

    @Test
    void valider() {
        /*
         * Validate a syllogism:
         * All lions with short fur resemble foxes with rough fur.
         * All rhinoceroses with coarse fur resemble lions with short fur.
         * All rhinoceroses with coarse fur resemble foxes with rough fur.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantifier tout = new Quantifier("All", true);
        syllogisme.setFigureNum(1);

        syllogisme.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogisme.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogisme.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);

        Reponse reponse = syllogisme.valider();

        assertTrue(reponse.isValid(), "The syllogism should be valid.");

    }
    /**
     * Test for determining whether a syllogism is 'uninteresting' (i.e., a trivially true conclusion).
     * The syllogism concludes that there exists a mortal human.
     */
    @Test
    void testIninteressant() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier tous = new Quantifier("All", true);
        Quantifier existe = new Quantifier("There exists", false);

        syllogisme.setMajeur("mammals", "mortal", tous, true); // All mammals are mortal
        syllogisme.setMineur("humans", "mammals", tous, false); // All humans are mammals

        syllogisme.setConclusion("human", "mortal", existe, true); // There exists a mortal human

        syllogisme.setFigureNum(1);

        assertTrue(syllogisme.estIninteressant(), "The syllogism should be considered uninteresting.");

    }
    /**
     * Test for converting the conclusion of a syllogism to universal form.
     */
    @Test
    void convertConclusion() {

        Syllogisme syllogisme = new Syllogisme();
        Quantifier tous = new Quantifier("All", true);
        Quantifier existe = new Quantifier("There exists", false);

        syllogisme.setMajeur("mammals", "mortal", tous, true); // Universal positive
        syllogisme.setMineur("humans", "mammals", tous, true); // Universal positive

        syllogisme.setConclusion("human", "mortal", existe, true); // Particular positive

        syllogisme.setFigureNum(1);

        assertTrue(!syllogisme.getConclusion().isUniversal() && syllogisme.convertConclusion().isUniversal(), "The conclusion should become universal.");

    }

    @Test
    void validerForRules() {
        /*
         * Validate a syllogism with specific rules applied:
         * All lions with short fur resemble foxes with rough fur.
         * All rhinoceroses with coarse fur resemble lions with short fur.
         * All rhinoceroses with coarse fur resemble foxes with rough fur.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantifier tout = new Quantifier("All", true);
        syllogisme.setFigureNum(1);

        syllogisme.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogisme.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogisme.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);
        ArrayList<String> check = new ArrayList<>();
        check.add("regleMoyenTerme");
        check.add("rNN");
        check.add("rP");
        Reponse reponse = syllogisme.validRule(check);

        assertTrue(reponse.isValid(), "The syllogism should be valid.");

    }

    //Figure 1
    @Test
    void testAAAFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); //<Universelle affirmative

        // AAA-1 : Prémisses et conclusion sont universelles affirmatives
        Syllogisme syllo = new Syllogisme(
                tout, tout, tout,             // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", //< >Termes : sujet, prédicat, terme moyen
                true, true, true,             // Les prémisses et la conclusion sont affirmatives
                1                             // Figure 1
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAEFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // EAE-1 : Majeure universelle négative, mineure universelle affirmative, conclusion universelle négative
        Syllogisme syllo = new Syllogisme(
                aucun, tout, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                1                             // Figure 1
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAIIFig1() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // AII-1 : Majeure universelle affirmative, mineure particulière affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                tout, certains, certains,     // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, true, true,             // Les prémisses et la conclusion sont affirmatives
                1                             // Figure 1
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig1() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-1 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, certains, certains,    // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                1                             // Figure 1
        );
        Reponse r = syllo.valider();
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
        Syllogisme syllo = new Syllogisme(
                aucun, tout, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                2                             // Figure 2
        );
        Reponse r = syllo.valider();

        boolean isValid = r.isValid();
        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAEEFig2() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // AEE-2 : Majeure universelle affirmative, mineure universelle négative, conclusion universelle négative
        Syllogisme syllo = new Syllogisme(
                tout, aucun, aucun,           // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, false, false,           // Affirmativité : prémisse majeure (affirmative), mineure (négative), conclusion (négative)
                2                             // Figure 2
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig2() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-2 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, certains, certains,    // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                false, true, false,           // Affirmativité : prémisse majeure (négative), mineure (affirmative), conclusion (négative)
                2                             // Figure 2
        );
        Reponse r = syllo.valider();

        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAOOFig2() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière négative

        // AOO-2 : Majeure universelle affirmative, mineure particulière négative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                tout, certains, certains,     // Quantificateurs pour les deux prémisses et la conclusion
                "subject", "predicate", "medium term", // Termes : sujet, prédicat, terme moyen
                true, false, false,           // Affirmativité : prémisse majeure (affirmative), mineure (négative), conclusion (négative)
                2                             // Figure 2
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }


    //Figure 3
    @Test
    void testAAIFig3() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // AAI-3 : Majeure universelle affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                tout, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",        // Termes
                true, true, true,                             // Affirmativité
                3                                             // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testIAIFig3() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // IAI-3 : Majeure particulière affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                3                              // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAIIFig3() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // AII-3 : Majeure universelle affirmative, mineure particulière affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                tout, certains, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                3                              // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAOFig3() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // EAO-3 : Majeure universelle négative, mineure universelle affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",         // Termes
                false, true, false,                            // Affirmativité
                3                                              // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testOAOFig3() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // OAO-3 : Majeure particulière négative, mineure universelle affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                3                              // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig3() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-3 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, certains, certains,     // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                3                              // Figure 3
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }



    //Figure 4
    @Test
    void testAAIFig4() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // AAI-4 : Majeure universelle affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                tout, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",        // Termes
                true, true, true,                             // Affirmativité
                4                                             // Figure 4
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testAEEFig4() {
        // Quantificateurs
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative
        Quantifier aucun = new Quantifier("No", true); // Universelle négative

        // AEE-4 : Majeure universelle affirmative, mineure universelle négative, conclusion universelle négative
        Syllogisme syllo = new Syllogisme(
                tout, aucun, aucun,           // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, false, false,           // Affirmativité
                4                              // Figure 4
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testIAIFig4() {
        // Quantificateurs
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // IAI-4 : Majeure particulière affirmative, mineure universelle affirmative, conclusion particulière affirmative
        Syllogisme syllo = new Syllogisme(
                certains, tout, certains,      // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                true, true, true,              // Affirmativité
                4                              // Figure 4
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEAOFig4() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier tout = new Quantifier("All", true); // Universelle affirmative

        // EAO-4 : Majeure universelle négative, mineure universelle affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, tout, new Quantifier("Some", false), // Quantificateurs
                "subject", "predicate", "medium term",         // Termes
                false, true, false,                            // Affirmativité
                4                                              // Figure 4
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }

    @Test
    void testEIOFig4() {
        // Quantificateurs
        Quantifier aucun = new Quantifier("No", true); // Universelle négative
        Quantifier certains = new Quantifier("Some", false); // Particulière affirmative

        // EIO-4 : Majeure universelle négative, mineure particulière affirmative, conclusion particulière négative
        Syllogisme syllo = new Syllogisme(
                aucun, certains, certains,     // Quantificateurs
                "subject", "predicate", "medium term", // Termes
                false, true, false,            // Affirmativité
                4                              // Figure 4
        );
        Reponse r = syllo.valider();
        boolean isValid = r.isValid();

        assertTrue(isValid, "It must be valid.");
    }
}