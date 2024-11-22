package traitement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//
class SyllogismeTest {

    @Test
    void TestFigure() {
        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme(quantifUniv,"Adnane","Dinh",true,quantifExist,
                "omar","Adnane",true,quantifUniv,"Adnane","Dinh",true);

        assertEquals(1,syllo.DetecterFigure(), "ici");

        System.out.println("test: OK");
    }

    @Test
    void TestFigure1() {
        Quantificateur quantifUniv = new Quantificateur("Tous", true);
        Quantificateur quantifExist = new Quantificateur(" ", false);

        Syllogisme syllo = new Syllogisme(quantifUniv, "homme", "mortel", true,
                quantifExist, "Socrate", "homme", true,
                quantifExist, "Socrate", "mortel", true);

        assertEquals(1,syllo.DetecterFigure(),"Doit etre figure 1");
    }

    @Test
    void testMoyenTerme() {
        /** Syllogisme tester :
         * Tous les chats sont gris. //< Moyen terme gris  -> particulier
         * Il existe un animal qui n'est pas gris. //< Moyen terme gris -> universelle
         * Il existe un animal qui n'est pas un chat
         * */
        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setNumFigure(2);
        syllo.regleMoyenTerme();
        assertEquals(0, syllo.getInvalid().size(), "Moyen terme devrait être valide");
        assertEquals(2, syllo.DetecterFigure(), "bonne figure");

        System.out.println("testMoyenTerme: OK");

    }

    @Test
    public void testMoyenTermeInvalide() {
        /** Syllogisme tester :
         * Tous les chats sont gris.
         * Il existe un animal gris.
         * Il existe un animal qui est un chat.
         * */

        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, true);
        syllo.setNumFigure(2);
        syllo.regleMoyenTerme();
        assertEquals(1, syllo.getInvalid().size(), "Moyen terme devrait être invalide");

        System.out.println("testMoyenTermeInvalide: OK");


    }

    @Test
    void TestRegleLatius() {
        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setNumFigure(2);
        syllo.regleLatius();
        assertEquals(0, syllo.getInvalid().size(), "Latius devrait être valide");

        System.out.println("testRegleLatius: OK");
    }

    @Test
    void TestRegleLatiusInvalid() {
        Quantificateur quantifUniv = new Quantificateur("tous", false);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, true);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setNumFigure(2);
        syllo.regleLatius();
        assertEquals(1, syllo.getInvalid().size(), "Latius devrait être Invalide");

        System.out.println("testRegleLatiusInvalid: OK");
    }

    @Test
    void rNN() {
        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, true);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setNumFigure(2);
        syllo.rNN();
        assertEquals(0, syllo.getInvalid().size(), "rNN devrait être valide");

        System.out.println("testRegleLatius: OK");
    }

    @Test
    void rNNInvalid() {
        Quantificateur quantifUniv = new Quantificateur("tous", true);
        Quantificateur quantifExist = new Quantificateur("il existe", false);

        Syllogisme syllo = new Syllogisme();
        syllo.setMajeur("les chats", "gris", quantifUniv, false);
        syllo.setMineur("animal", "gris", quantifExist, false);
        syllo.setConclusion("animal", "chat", quantifExist, false);
        syllo.setNumFigure(2);
        syllo.rNN();
        assertEquals(1, syllo.getInvalid().size(), "rNN devrait être Invalide");

        System.out.println("testRNNInvalid: OK");
    }

    @Test
    void testRnValide() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur("Certains", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, false); // Négative

        syllogisme.setNumFigure(1);


        syllogisme.rN();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 premise négative donc conclusion négative ) : OK");

    }


    @Test
    void testRnInvalide() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, false); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, true); // Affirmative

        syllogisme.setNumFigure(1);


        syllogisme.rN();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle rN.");

        System.out.println("testRn (1 prémise négative mais conclusion affirmative ) : OK");

    }

    @Test
    void rAA() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true); // Affirmative
        syllogisme.setMineur("Socrate", "hommes", particulier, true); // Négative

        syllogisme.setConclusion("Socrate", "mortel", particulier, true); // Affirmative

        syllogisme.setNumFigure(2);


        syllogisme.rAA();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme devrait respecter la règle raa.");

        System.out.println("testRaa : OK");
    }

    @Test
    void rAAInvalid() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", particulier, true);

        syllogisme.setConclusion("Socrate", "mortel", particulier, false);

        syllogisme.setNumFigure(2);


        syllogisme.rAA();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme ne doit pas respecter la règle raa.");

        System.out.println("testRaaInvalid : OK");
    }

    @Test
    void rPP() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", false);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", particulier, true);

        syllogisme.setConclusion("Socrate", "mortel", particulier, false);

        syllogisme.setNumFigure(2);


        syllogisme.rPP();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme ne doit pas respecter la règle raa.");

        System.out.println("testRppInvalid : OK");
    }

    @Test
    void rPPInvalid() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", false);
        Quantificateur particulier = new Quantificateur(" ", true);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", particulier, true);

        syllogisme.setConclusion("Socrate", "mortel", particulier, false);

        syllogisme.setNumFigure(2);


        syllogisme.rPP();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme ne doit pas respecter la règle raa.");

        System.out.println("testRppInvalid : OK");
    }

    @Test
    void rP() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", particulier, true);

        syllogisme.setConclusion("Socrate", "mortel", particulier, false);

        syllogisme.setNumFigure(2);


        syllogisme.rP();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme respecter la règle rp.");

        System.out.println("testRpValid : OK");
    }

    @Test
    void rPInvalid() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", particulier, true);

        syllogisme.setConclusion("Socrate", "mortel", universel, false);

        syllogisme.setNumFigure(2);


        syllogisme.rP();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme ne respecte pas la règle rp.");

        System.out.println("testRpInValid : OK");
    }

    @Test
    void rUU() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", universel, true);

        syllogisme.setConclusion("Socrate", "mortel", universel, false);

        syllogisme.setNumFigure(2);


        syllogisme.rUU();


        assertEquals(0, syllogisme.getInvalid().size(), "Le syllogisme doit respecter la règle RUU.");

        System.out.println("testRUU : OK");
    }

    @Test
    void rUUInvalid() {
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur universel = new Quantificateur("Tous", true);
        Quantificateur particulier = new Quantificateur(" ", false);

        syllogisme.setMajeur("hommes", "mortels", universel, true);
        syllogisme.setMineur("Socrate", "hommes", universel, true);

        syllogisme.setConclusion("Socrate", "mortel", particulier, false);

        syllogisme.setNumFigure(2);


        syllogisme.rUU();


        assertEquals(1, syllogisme.getInvalid().size(), "Le syllogisme ne doit pas respecter la règle RUU.");

        System.out.println("testRUUInvalid : OK");
    }

    @Test
    void valider() {
        /**
         * Tout lion à poils ras ressemble à un fox à poils durs.
         * Tout rhinocéros à poils drus ressemble à un lion à poils ras.
         * Tout rhinocéros à poils drus ressemble à un fox à poils durs.
         * **/
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        syllogisme.setNumFigure(1);

        syllogisme.setMajeur("lion à poils ras", "fox à poils durs" , tout, true);
        syllogisme.setMineur("rhinocéros à poils drus", "lion à poils ras", tout, true);

        syllogisme.setConclusion("rhinocéros à poils drus", "fox à poils durs", tout, true);

        Reponse reponse = syllogisme.valider();


        assertEquals(true, reponse.isValid(), "Le syllogisme doit être valide");

        System.out.println("Validation du syllogisme : OK");
    }

    @Test
    void testIninteressant() {
        /* Syllogisme tester :
         * - Tous les mamifères sont mortels
         * - Tous les être humains sont des mamifères
         * - Il existe un être humain qui est mortel */

        Syllogisme syllogisme = new Syllogisme();

        Quantificateur tous = new Quantificateur("Tous", true);
        Quantificateur existe = new Quantificateur("Il existe", false);


        syllogisme.setMajeur("mamifères", "mortels", tous, true); //< Tous les mamifères sont mortels (universelle affirmative)
        syllogisme.setMineur("être humains", "mamifères", tous, false); //< Tous les être humains sont des mamifères (universelle affirmative)

        syllogisme.setConclusion("être humain", "mortel", existe, true); //< Il existe un être humain qui est mortel (particulier affirmative)

        syllogisme.setNumFigure(1);



        assertEquals(true, syllogisme.estIninteressant(), "Normalement il devrait dire qu'il est ininterassant.");

        System.out.println("testIninteressant() (cas true) : OK");


    }

    @Test
    void convertConclusion() {
        Syllogisme syllogisme = new Syllogisme();

        Quantificateur tous = new Quantificateur("Tous", true);
        Quantificateur existe = new Quantificateur("Il existe", false);


        syllogisme.setMajeur("mamifères", "mortels", tous, true); //< universelle positive
        syllogisme.setMineur("être humains", "mamifères", tous, true); //< universelle positive

        syllogisme.setConclusion("être humain", "mortel", existe, true); //< particulier positive

        syllogisme.setNumFigure(1);

        assertEquals(true,  !syllogisme.getConclusion().estUniverselle() && syllogisme.convertConclusion().estUniverselle(), "La conclusion doit devenir universelle");

        System.out.println("testConvertConclusion : OK");
    }

    @Test
    void validerForRules() { // non
        /**
         * Tout lion à poils ras ressemble à un fox à poils durs.
         * Tout rhinocéros à poils drus ressemble à un lion à poils ras.
         * Tout rhinocéros à poils drus ressemble à un fox à poils durs.
         * **/
        Syllogisme syllogisme = new Syllogisme();
        Quantificateur tout = new Quantificateur("Tout", true);
        syllogisme.setNumFigure(1);

        syllogisme.setMajeur("lion à poils ras", "fox à poils durs" , tout, true);
        syllogisme.setMineur("rhinocéros à poils drus", "lion à poils ras", tout, true);

        syllogisme.setConclusion("rhinocéros à poils drus", "fox à poils durs", tout, true);
        ArrayList<String> check = new ArrayList<>();
        check.add("regleMoyenTerme");
        check.add("rNN");
        check.add("rP");
        Reponse reponse = syllogisme.validRule(check);


        assertEquals(true, reponse.isValid(), "Le syllogisme doit être valide");

        System.out.println("Validation du syllogisme pour x regles: OK");
    }
}