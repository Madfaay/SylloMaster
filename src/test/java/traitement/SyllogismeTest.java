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
        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme(quantifUniv,"Adnane","Dinh",true,quantifExist,
                "omar","Adnane",true,quantifUniv,"Adnane","Dinh",true);

        assertEquals(1,syllo.DetecterFigure(), "ici");

        System.out.println("test: OK");
    }
    /**
     * Test to detect the figure of a syllogism with different terms.
     * This method tests if figure detection works correctly with different sets of terms.
     */
    @Test
    void TestFigure1() {
        Quantificator quantifUniv = new Quantificator("Tous", true);
        Quantificator quantifExist = new Quantificator(" ", false);

        Syllogisme syllo = new Syllogisme(quantifUniv, "homme", "mortel", true,
                quantifExist, "Socrate", "homme", true,
                quantifExist, "Socrate", "mortel", true);

        assertEquals(1,syllo.DetecterFigure(),"Doit etre figure 1");
    }

    /**
     * Test the rule for the middle term in a syllogism.
     * This method checks if the middle term rule is applied correctly and if the figure is correctly identified.
     */
    @Test
    void testMoyenTerme() {
        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.regleMoyenTerme();
        assertEquals(0, syllo.getInvalid().size(), "Moyen terme devrait être valide");
        assertEquals(2, syllo.DetecterFigure(), "bonne figure");

        System.out.println("testMoyenTerme: OK");

    }

    /**
     * Test the middle term rule for an invalid case.
     * This method checks if the system correctly identifies an invalid middle term.
     */
    @Test
    public void testMoyenTermeInvalide() {
        /** Syllogisme tester :
         * Tous les chats sont gris.
         * Il existe un animal gris.
         * Il existe un animal qui est un chat.
         * */

        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, true);
        syllo.setFigureNum(2);
        syllo.regleMoyenTerme();
        assertEquals(1, syllo.getInvalid().size(), "Moyen terme devrait être invalide");

        System.out.println("testMoyenTermeInvalide: OK");


    }
    /**
     * Test the "Latius" rule for syllogisms.
     * This method ensures that the syllogism follows the Latius rule correctly.
     */
    @Test
    void TestRegleLatius() {
        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.regleLatius();
        assertEquals(0, syllo.getInvalid().size(), "Latius devrait être valide");

        System.out.println("testRegleLatius: OK");
    }

    /**
     * Test the "Latius" rule for an invalid syllogism.
     * This method checks if an invalid syllogism is detected properly when applying the Latius rule.
     */
    @Test
    void TestRegleLatiusInvalid() {
        Quantificator quantifUniv = new Quantificator("tous", false);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setFigureNum(2);
        syllo.regleLatius();
        assertEquals(1, syllo.getInvalid().size(), "Latius devrait être Invalide");

        System.out.println("testRegleLatiusInvalid: OK");
    }


    /**
     * Test the "rNN" rule for syllogisms.
     * This method checks if the syllogism follows the "rNN" rule correctly.
     */
    @Test
    void rNN() {
        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
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
        Quantificator quantifUniv = new Quantificator("tous", true);
        Quantificator quantifExist = new Quantificator("il existe", false);

        Syllogisme syllo = new Syllogisme();
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
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("Tous", true);
        Quantificator particulier = new Quantificator("Certains", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, false); // Négative

        syllogisme.setFigureNum(1);


        syllogisme.rN();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 premise négative donc conclusion négative ) : OK");

    }


    /**
     * Test the "rN" rule for an invalid syllogism.
     * This method ensures that the system correctly detects an invalid "rN" syllogism where a premise is negative but the conclusion is affirmative.
     */
    @Test
    void testRnInvalide() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("Tous", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, true); // Affirmative

        syllogisme.setFigureNum(1);


        syllogisme.rN();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 prémise négative mais conclusion affirmative ) : OK");

    }
    @Test
    void rAA() {
        /**
         * Test for the rAA rule (Affirmative major premise, affirmative minor premise, conclusion in the same form).
         * In this test, the syllogism follows the rAA rule.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true); // Affirmative
        syllogisme.setMineur("Socrates", "men", particulier, true); // Negative

        syllogisme.setConclusion("Socrates", "mortal", particulier, true); // Affirmative

        syllogisme.setFigureNum(2);

        syllogisme.rAA();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rAA rule.");
        System.out.println("testRaa: OK");
    }

    @Test
    void rAAInvalid() {
        /**
         * Test for the rAA rule (Invalid case: Affirmative major premise, negative minor premise, conclusion should not be affirmative).
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rAA();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rAA rule.");
        System.out.println("testRaaInvalid: OK");
    }

    @Test
    void rPP() {
        /**
         * Test for the rPP rule (Particular major premise, particular minor premise).
         * This test ensures the syllogism does not respect the rPP rule.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", false);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rPP();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rPP rule.");
        System.out.println("testRppInvalid: OK");
    }

    @Test
    void rPPInvalid() {
        /**
         * Test for the rPP rule (Invalid case: particular major premise and particular minor premise with a particular conclusion).
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", false);
        Quantificator particulier = new Quantificator(" ", true);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rPP();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rPP rule.");
        System.out.println("testRppInvalid: OK");
    }

    @Test
    void rP() {
        /**
         * Test for the rP rule (Universal major premise, particular minor premise).
         * This test ensures the syllogism respects the rP rule.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false);

        syllogisme.setFigureNum(2);

        syllogisme.rP();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rP rule.");
        System.out.println("testRpValid: OK");
    }

    @Test
    void rPInvalid() {
        /**
         * Test for the rP rule (Invalid case: Universal major premise, particular minor premise, conclusion should not be universal).
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", particulier, true);

        syllogisme.setConclusion("Socrates", "mortal", universel, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rP();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rP rule.");
        System.out.println("testRpInvalid: OK");
    }

    @Test
    void rUU() {
        /**
         * Test for the rUU rule (Universal major premise, universal minor premise).
         * This test ensures the syllogism respects the rUU rule.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", universel, true);

        syllogisme.setConclusion("Socrates", "mortal", universel, false);

        syllogisme.setFigureNum(2);

        syllogisme.rUU();

        assertEquals(0, syllogisme.getInvalid().size(), "The syllogism should respect the rUU rule.");
        System.out.println("testRUU: OK");
    }

    @Test
    void rUUInvalid() {
        /**
         * Test for the rUU rule (Invalid case: universal major and minor premises, but particular conclusion).
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator universel = new Quantificator("All", true);
        Quantificator particulier = new Quantificator(" ", false);

        syllogisme.setMajeur("men", "mortal", universel, true);
        syllogisme.setMineur("Socrates", "men", universel, true);

        syllogisme.setConclusion("Socrates", "mortal", particulier, false); // Invalid conclusion

        syllogisme.setFigureNum(2);

        syllogisme.rUU();

        assertEquals(1, syllogisme.getInvalid().size(), "The syllogism should not respect the rUU rule.");
        System.out.println("testRUUInvalid: OK");
    }

    @Test
    void valider() {
        /**
         * Validate a syllogism:
         * All lions with short fur resemble foxes with rough fur.
         * All rhinoceroses with coarse fur resemble lions with short fur.
         * All rhinoceroses with coarse fur resemble foxes with rough fur.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator tout = new Quantificator("All", true);
        syllogisme.setFigureNum(1);

        syllogisme.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogisme.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogisme.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);

        Reponse reponse = syllogisme.valider();

        assertEquals(true, reponse.isValid(), "The syllogism should be valid.");
        System.out.println("Validation of syllogism: OK");
    }

    @Test
    void testIninteressant() {
        /**
         * Test for determining whether a syllogism is 'uninteresting' (i.e., a trivially true conclusion).
         * The syllogism concludes that there exists a mortal human.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator tous = new Quantificator("All", true);
        Quantificator existe = new Quantificator("There exists", false);

        syllogisme.setMajeur("mammals", "mortal", tous, true); // All mammals are mortal
        syllogisme.setMineur("humans", "mammals", tous, false); // All humans are mammals

        syllogisme.setConclusion("human", "mortal", existe, true); // There exists a mortal human

        syllogisme.setFigureNum(1);

        assertEquals(true, syllogisme.estIninteressant(), "The syllogism should be considered uninteresting.");
        System.out.println("testIninteressant() (true case): OK");
    }

    @Test
    void convertConclusion() {
        /**
         * Test for converting the conclusion of a syllogism to universal form.
         */
        Syllogisme syllogisme = new Syllogisme();
        Quantificator tous = new Quantificator("All", true);
        Quantificator existe = new Quantificator("There exists", false);

        syllogisme.setMajeur("mammals", "mortal", tous, true); // Universal positive
        syllogisme.setMineur("humans", "mammals", tous, true); // Universal positive

        syllogisme.setConclusion("human", "mortal", existe, true); // Particular positive

        syllogisme.setFigureNum(1);

        assertEquals(true, !syllogisme.getConclusion().isUniversal() && syllogisme.convertConclusion().isUniversal(), "The conclusion should become universal.");
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
        Syllogisme syllogisme = new Syllogisme();
        Quantificator tout = new Quantificator("All", true);
        syllogisme.setFigureNum(1);

        syllogisme.setMajeur("lion with short fur", "fox with rough fur", tout, true);
        syllogisme.setMineur("rhinoceros with coarse fur", "lion with short fur", tout, true);

        syllogisme.setConclusion("rhinoceros with coarse fur", "fox with rough fur", tout, true);
        ArrayList<String> check = new ArrayList<>();
        check.add("regleMoyenTerme");
        check.add("rNN");
        check.add("rP");
        Reponse reponse = syllogisme.validRule(check);

        assertEquals(true, reponse.isValid(), "The syllogism should be valid.");
        System.out.println("Validation of syllogism for rules: OK");
    }
}