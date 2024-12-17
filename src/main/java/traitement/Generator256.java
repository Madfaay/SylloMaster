package traitement;


import java.util.ArrayList;
import java.util.List;

public class Generator256 {
    private Syllogism syllogisme;
    private String[] types = {"A", "E", "I", "O"};
    private int[] figures = {1, 2, 3, 4};

    // Attributs pour stocker les résultats
    private final List<Syllogism> syllogismes = new ArrayList<>();
    public List<List<String>> syllogismesDetails = new ArrayList<>();

    private int valideCount = 0;
    private int interessantCount = 0;



    public Generator256(Syllogism s) {
        syllogisme = s;
        int nombre = 0;

        for (String type1 : types) {
            for (String type2 : types) {
                for (String type3 : types) {
                    for (int figure : figures) {
                        Proposition maj;
                        Proposition min;
                        Proposition c;

                        switch (figure) {
                            case 1 :
                                maj = createProposition(type1, syllogisme.getMajorMiddleTerm(), syllogisme.getPredicat());
                                min = createProposition(type2, syllogisme.getSujet(), syllogisme.getMinorMiddleterm());
                                c = createProposition(type3, syllogisme.getSujet(), syllogisme.getPredicat());
                                break;
                            case 2 :
                                maj = createProposition(type1, syllogisme.getPredicat(), syllogisme.getMajorMiddleTerm());
                                min = createProposition(type2, syllogisme.getSujet(), syllogisme.getMinorMiddleterm());
                                c = createProposition(type3, syllogisme.getSujet(), syllogisme.getPredicat());
                                break;
                            case 3 :
                                maj = createProposition(type1, syllogisme.getMajorMiddleTerm(), syllogisme.getPredicat());
                                min = createProposition(type2, syllogisme.getMinorMiddleterm(), syllogisme.getSujet());
                                c = createProposition(type3, syllogisme.getSujet(), syllogisme.getPredicat());
                                break;
                            case 4 :
                                maj = createProposition(type1, syllogisme.getPredicat(), syllogisme.getMajorMiddleTerm());
                                min = createProposition(type2, syllogisme.getMinorMiddleterm(), syllogisme.getSujet());
                                c = createProposition(type3, syllogisme.getSujet(), syllogisme.getPredicat());
                                break;
                            default:
                                maj = createProposition(type1, syllogisme.getPredicat(), syllogisme.getMajorMiddleTerm());
                                min = createProposition(type2, syllogisme.getMinorMiddleterm(), syllogisme.getSujet());
                                c = createProposition(type3, syllogisme.getSujet(), syllogisme.getPredicat());
                                break;
                        }


                        // Création du nouveau syllogisme
                        Syllogism nouveau = new Syllogism(maj, min, c, "English");
                        syllogismes.add(nouveau);

                        // Vérifications supplémentaires
                        boolean isValidSyllo = nouveau.validate().getResult();
                        boolean isIninteressant = nouveau.isUninteresting();


                        nombre++;
                        // Pour chaque syllogisme
                        List<String> detail = new ArrayList<>();
                        detail.add(Integer.toString(nombre));
                        detail.add(Integer.toString(figure));
                        detail.add(type1+type2+type3);

                        ArrayList<Boolean> r = nouveau.getValidRules();
                        for(Boolean b : r) {
                            if(b) {
                                detail.add("TRUE");
                            }else{
                                detail.add("FALSE");
                            }
                        }


                        if (isValidSyllo) {
                            detail.add("Valide");
                            valideCount++;
                            if (isIninteressant) {
                                detail.add("Inintéressant");
                            } else {
                                detail.add("Intéressant");
                                interessantCount++;
                            }
                        } else {
                            detail.add("Invalide");
                            detail.add("NULL");
                            /*if (!nouveau.getInvalid().isEmpty()) {
                                detail.add("Règles invalides :");
                                for (String regle : nouveau.getInvalid()) {
                                    // Ajout de chaque règle invalide dans la liste des détails
                                    detail.add("- " + regle);
                                }
                            }*/
                        }



                        // Ajout du détail du syllogisme dans la liste principale
                        syllogismesDetails.add(detail);



                    }
                }
            }
        }
    }

    /**
     * Méthode pour afficher les 256 syllogismes générés dans le terminal
     * Affiche le nombre total de syllogismes, le nombre de syllogismes valides et intéressants,
     * ainsi que les détails de chaque syllogisme généré.
     */
    public void afficherSyllogismes() {

        System.out.println("Nombre total de syllogismes : " + syllogismes.size());
        System.out.println("Nombre de syllogismes valides : " + valideCount);
        System.out.println("Nombre de syllogismes intéressants : " + interessantCount);
        System.out.println("Détails des syllogismes :\n");

        int i = 0;
        for (List<String> detail : syllogismesDetails) {
            i++;
            System.out.println(i);

            for(String val : detail){
                System.out.println(val);
            }

        }
    }


    /** * Méthode pour créer une proposition en fonction de son type. * * @param type Le type de la proposition ("A", "E", "I", "O"). * @return Une nouvelle proposition. */
    /**
     * Méthode pour créer une proposition en fonction de son type.
     *
     * @param type Le type de la proposition ("A", "E", "I", "O").
     * @return Une nouvelle proposition.
     */
    private Proposition createProposition(String type, Term premierTerme, Term deuxiemeTerme) {
        Proposition p = null;
        Quantifier quantificateur;


        // On initialise les quantificateurs et les termes en fonction du type de syllogisme
        switch (type) {
            case "A":
                // Quantificateur pour "Tous les" (universel affirmatif)
                quantificateur = new Quantifier("Tous les ", true);
                premierTerme.setUniversal(true);  // Le premier terme est universel
                p = new Proposition(premierTerme.toString(), deuxiemeTerme.toString(), quantificateur, true);
                break;

            case "E":
                // Quantificateur pour "Aucun" (universel négatif)
                quantificateur = new Quantifier("Aucun ", true);
                premierTerme.setUniversal(true);  // Le premier terme est universel
                p = new Proposition(premierTerme.toString(), deuxiemeTerme.toString(), quantificateur, false);
                break;

            case "I":
                // Quantificateur pour "Certains" (existentiel affirmatif)
                quantificateur = new Quantifier("Certains ", false);
                premierTerme.setUniversal(false);  // Le premier terme n'est pas universel
                p = new Proposition(premierTerme.toString(), deuxiemeTerme.toString(), quantificateur, true);
                break;

            case "O":
                // Quantificateur pour "Certains" (existentiel négatif)
                quantificateur = new Quantifier("Certains ", false);
                premierTerme.setUniversal(false);  // Le premier terme n'est pas universel
                p = new Proposition(premierTerme.toString(), deuxiemeTerme.toString(), quantificateur, false);
                break;

            default:
                System.out.println("Entrez un type valide : A, E, I, O");
                break;
        }

        return p;
    }


    public void afficherSyllogismesDetails() {
        // Check if the list is empty
        if (syllogismesDetails.isEmpty()) {
            System.out.println("La liste des syllogismes est vide.");
            return;
        }

        System.out.println("Contenu de syllogismesDetails :");
        int index = 1; // Index for each sublist

        // Iterate through each sublist in syllogismesDetails
        for (List<String> syllogisme : syllogismesDetails) {
            System.out.print("Syllogisme " + index + ": [");

            // Print each element in the sublist, separated by commas
            for (int i = 0; i < syllogisme.size(); i++) {
                System.out.print(syllogisme.get(i));
                if (i < syllogisme.size() - 1) {
                    System.out.print(", "); // Add comma except for the last element
                }
            }
            System.out.println("]");
            index++; // Increment index for next sublist
        }
    }

    public static void main(String[] args) {
        // Création des quantificateurs
        Quantifier quantifMajeur = new Quantifier("Tous les", true); // Universelle affirmative (A)
        Quantifier quantifMineur = new Quantifier("Il existe", false); // Particulière négative (O)
        Quantifier quantifConclusion = new Quantifier("Il existe", false); // Particulière négative (O)

        // Définition des sujets et prédicats
        String sujetMajeur = "chats";
        String predicatMajeur = "gris";

        String sujetMineur = "animal";
        String predicatMineur = "gris";

        String sujetConclusion = "animal";
        String predicatConclusion = "chat";

        // Création des propositions
        Proposition majeur = new Proposition(sujetMajeur, predicatMajeur, quantifMajeur, true); // Affirmative
        Proposition mineur = new Proposition(sujetMineur, predicatMineur, quantifMineur, false); // Négative (n'est pas gris)
        Proposition conclusion = new Proposition(sujetConclusion, predicatConclusion, quantifConclusion, false); // Négative (n'est pas un chat)

        // Création du syllogisme avec les propositions
        Syllogism syllogisme1 = new Syllogism(majeur, mineur, conclusion,"English");

        // Création du syllogisme avec les paramètres directement
        Syllogism s = new Syllogism(
                quantifMajeur, sujetMajeur, predicatMajeur, true,
                quantifMineur, sujetMineur, predicatMineur, false,
                quantifConclusion, sujetConclusion, predicatConclusion, false
                ,"English"
        );

        // Initialisation du générateur
        Generator256 g = new Generator256(syllogisme1);
        g.afficherSyllogismesDetails();



    }



}

