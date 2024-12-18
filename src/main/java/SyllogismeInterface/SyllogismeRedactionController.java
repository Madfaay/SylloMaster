package SyllogismeInterface;

import javafx.scene.Scene;
import javafx.stage.Stage;
import traitement.Generator256;
import traitement.Quantifier;
import traitement.Response;
import traitement.Syllogism;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**Controller of fst mode syllgosim validaiton */

public class SyllogismeRedactionController {
    /**
     * Label for the main title.
     */
    public Label labelTitle;

    /**
     * Label for the first premise.
     */
    public Label premisse1;

    /**
     * Button to navigate back in the application.
     */
    public Button btnBack;

    /**
     * Button to switch views or modes.
     */
    public Button btnSwitch;

    /**
     * AnchorPane for organizing UI components, linked to FXML.
     */
    @FXML
    private AnchorPane pane;

    /**
     * Instance of the Syllogism class representing the current syllogism.
     */
    Syllogism syllogism;

    /**
     * Quantifier for the first premise.
     */
    String quantifPremise1;

    /**
     * Subject of the first premise.
     */
    String subjectPremise1;

    /**
     * Predicate of the first premise.
     */
    String predicatPremise1;

    /**
     * Boolean flag indicating whether the first premise is negative.
     */
    Boolean negatifPremise1;

    /**
     * Quantifier for the second premise.
     */
    String quantifPremise2;

    /**
     * Subject of the second premise.
     */
    String subjectPremise2;

    /**
     * Predicate of the second premise.
     */
    String predicatPremise2;

    /**
     * Boolean flag indicating whether the second premise is negative.
     */
    Boolean negatifPremise2;

    /**
     * Quantifier for the conclusion.
     */
    String quantifConclusion;

    /**
     * Subject of the conclusion.
     */
    String subjectConclusion;

    /**
     * Predicate of the conclusion.
     */
    String predicatConclusion;

    /**
     * Boolean flag indicating whether the conclusion is negative.
     */
    Boolean negatifConclusion;

    /**
     * RadioButton for selecting the subject option.
     */
    RadioButton buttonSubject;

    /**
     * RadioButton for selecting the predicate option.
     */
    RadioButton buttonPredicat;

    /**
     * Boolean flag indicating the state of a hypothesis.
     */
    Boolean hypothesis;

    /**
     * List of strings representing existential quantifiers.
     */
    List<String> quantiflistExist = new ArrayList<>();

    /**
     * List of strings representing universal quantifiers.
     */
    List<String> quantiflistUniv = new ArrayList<>();

    /**
     * ArrayList containing rules for validation or processing.
     */
    ArrayList<String> reglelist = new ArrayList<>();

    /**
     * MenuButton for selecting the quantifier for the first premise, linked to FXML.
     */
    @FXML
    MenuButton myquantifPremise1;

    /**
     * TextField for entering the subject of the first premise, linked to FXML.
     */
    @FXML
    TextField mysubjectPremise1;

    /**
     * TextField for entering the predicate of the first premise, linked to FXML.
     */
    @FXML
    TextField mypredicatPremise1;

    /**
     * CheckBox to indicate negativity of the first premise, linked to FXML.
     */
    @FXML
    CheckBox mynegatifPremise1;

    /**
     * HBox container for managing the subject of the first premise, linked to FXML.
     */
    @FXML
    HBox myHBoxsubjectPremise1;

    /**
     * HBox container for managing the predicate of the first premise, linked to FXML.
     */
    @FXML
    HBox myHBoxpredicatPremise1;

    /**
     * Label to display validation messages, linked to FXML.
     */
    @FXML
    Label myTextValid;

    /**
     * MenuButton for selecting the quantifier for the second premise, linked to FXML.
     */
    @FXML
    MenuButton myquantifPremise2;

    /**
     * RadioButton for selecting "subject" as the type of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mysubjectPremise2Subject;

    /**
     * RadioButton for selecting "predicate" as the type of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mysubjectPremise2Predicat;

    /**
     * RadioButton for selecting "new term" as the type of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mysubjectPremise2New;

    /**
     * RadioButton for selecting "subject" as the predicate of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mypredicatPremise2Subject;

    /**
     * RadioButton for selecting "predicate" as the predicate of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mypredicatPremise2Predicat;

    /**
     * RadioButton for selecting "new term" as the predicate of the second premise, linked to FXML.
     */
    @FXML
    RadioButton mypredicatPremise2New;

    /**
     * TextField for entering the subject of the second premise, linked to FXML.
     */
    @FXML
    TextField mysubjectPremise2;

    /**
     * TextField for entering the predicate of the second premise, linked to FXML.
     */
    @FXML
    TextField mypredicatPremise2;

    /**
     * CheckBox to indicate negativity of the second premise, linked to FXML.
     */
    @FXML
    CheckBox mynegatifPremise2;

    /**
     * HBox container for managing the quantifier of the second premise, linked to FXML.
     */
    @FXML
    HBox myHBoxquantifPremise2;

    /**
     * HBox container for managing the subject of the second premise, linked to FXML.
     */
    @FXML
    HBox myHBoxsubjectPremise2;

    /**
     * HBox container for managing the predicate of the second premise, linked to FXML.
     */
    @FXML
    HBox myHBoxpredicatPremise2;

    /**
     * ToggleGroup for managing radio button selections for the subject, linked to FXML.
     */
    @FXML
    ToggleGroup subject;

    /**
     * ToggleGroup for managing radio button selections for the predicate, linked to FXML.
     */
    @FXML
    ToggleGroup predicat;

    /**
     * MenuButton for selecting the quantifier for the conclusion, linked to FXML.
     */
    @FXML
    MenuButton myquantifConclusion;

    /**
     * TextField for entering the subject of the conclusion, linked to FXML.
     */
    @FXML
    TextField mysubjectConclusion;

    /**
     * TextField for entering the predicate of the conclusion, linked to FXML.
     */
    @FXML
    TextField mypredicatConclusion;

    /**
     * CheckBox to indicate negativity of the conclusion, linked to FXML.
     */
    @FXML
    CheckBox mynegatifConclusion;

    /**
     * HBox container for managing the quantifier of the conclusion, linked to FXML.
     */
    @FXML
    HBox myHBoxquantifConclusion;

    /**
     * HBox container for managing the subject of the conclusion, linked to FXML.
     */
    @FXML
    HBox myHBoxsubjectConclusion;

    /**
     * HBox container for managing the predicate of the conclusion, linked to FXML.
     */
    @FXML
    HBox myHBoxpredicatConclusion;

    /**
     * Button to verify the correctness of the syllogism, linked to FXML.
     */
    @FXML
    Button myverif;

    /**
     * CheckBox for validating the medium term rule, linked to FXML.
     */
    @FXML
    CheckBox myregleMediumTerm;

    /**
     * CheckBox for validating the latus rule, linked to FXML.
     */
    @FXML
    CheckBox myregleLatus;

    /**
     * CheckBox for validating the rNN rule, linked to FXML.
     */
    @FXML
    CheckBox myrNN;

    /**
     * CheckBox for validating the rN rule, linked to FXML.
     */
    @FXML
    CheckBox myrN;

    /**
     * CheckBox for validating the rAA rule, linked to FXML.
     */
    @FXML
    CheckBox myrAA;

    /**
     * CheckBox for validating the rPP rule, linked to FXML.
     */
    @FXML
    CheckBox myrPP;

    /**
     * CheckBox for validating the rP rule, linked to FXML.
     */
    @FXML
    CheckBox myrP;

    /**
     * CheckBox for validating the rUU rule, linked to FXML.
     */
    @FXML
    CheckBox myrUU;

    /**
     * Button for managing arrays, linked to FXML.
     */
    @FXML
    Button btnArray;

    /**
     * File object pointing to the language configuration file (language.json).
     */
    private final File languageFile = new File("language.json");

    /**
     * String representing the current language.
     */
    private String language;

    /**
     * Method to initialize the controller and load the language settings
     */
    @FXML
    void initialize()
    {
        myTextValid.setWrapText(true);
        loadLanguageFromJson();
        if(!this.language.equals("English")) {
            System.out.println("laaaaaaaaaaa");
            setLabelsToFrench();
        }

        retrieve();
        populateQuantifierMenus();

    }
    /**
     * Checks for duplicate values among four given strings.
     * Specifically:
     * - Returns {@code true} if any string appears more than twice.
     * - Returns {@code false} if exactly two strings appear with a frequency of 2.
     * - Returns {@code true} in all other cases.
     *
     * @param subject1   The first subject string.
     * @param predicate1 The first predicate string.
     * @param subject2   The second subject string.
     * @param predicate2 The second predicate string.
     * @return {@code true} if any string appears more than twice or the duplicate
     *         condition is not met; {@code false} if exactly two strings appear
     *         with a frequency of 2.
     */
    private boolean hasDuplicatePremiseCouples(String subject1, String predicate1, String subject2, String predicate2) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Comptage des occurrences des 4 termes
        frequencyMap.put(subject1, frequencyMap.getOrDefault(subject1, 0) + 1);
        frequencyMap.put(predicate1, frequencyMap.getOrDefault(predicate1, 0) + 1);
        frequencyMap.put(subject2, frequencyMap.getOrDefault(subject2, 0) + 1);
        frequencyMap.put(predicate2, frequencyMap.getOrDefault(predicate2, 0) + 1);

        int countFrequency1 = 0;
        int countFrequency2 = 0;
        int countFrequencyMore = 0;

        // Comptage des fréquences
        for (int freq : frequencyMap.values()) {
            if (freq == 1) {
                countFrequency1++;
            } else if (freq == 2) {
                countFrequency2++;
            } else if (freq > 2) {
                countFrequencyMore++;
            }
        }

        // Schéma autorisé : 1 terme avec fréquence 2, 2 termes avec fréquence 1 et aucun terme > 2
        // soit countFrequency2 == 1, countFrequency1 == 2, countFrequencyMore == 0
        if (countFrequency2 == 1 && countFrequency1 == 2 && countFrequencyMore == 0) {
            return false; // Le schéma (AA B C) est respecté, pas de problème
        } else {
            return true;  // Tous les autres schémas sont interdits
        }
    }



    /**
     * Method to populate quantifier menu items
     */
    private void populateQuantifierMenus() {
        int size = quantiflistExist.size();
        for (int i = 0; i < size; i++) {
            MenuItem quantif = new MenuItem(quantiflistExist.get(i));
            quantif.setOnAction(this::recoverPremise1);
            myquantifPremise1.getItems().add(quantif);
        }

        int t = quantiflistUniv.size();
        for (int i = 0; i < t; i++) {
            MenuItem quantif = new MenuItem(quantiflistUniv.get(i));
            quantif.setOnAction(this::recoverPremise1);
            myquantifPremise1.getItems().add(quantif);
        }

        for (int i = 0; i < quantiflistExist.size(); i++) {
            MenuItem quantif = new MenuItem(quantiflistExist.get(i));
            quantif.setOnAction(this::recoverPremise2);
            myquantifPremise2.getItems().add(quantif);
        }

        for (int i = 0; i < quantiflistUniv.size(); i++) {
            MenuItem quantif = new MenuItem(quantiflistUniv.get(i));
            quantif.setOnAction(this::recoverPremise2);
            myquantifPremise2.getItems().add(quantif);
        }

        for (int i = 0; i < quantiflistExist.size(); i++) {
            MenuItem quantif = new MenuItem(quantiflistExist.get(i));
            quantif.setOnAction(this::recoverConclusion);
            myquantifConclusion.getItems().add(quantif);
        }

        for (int i = 0; i < quantiflistUniv.size(); i++) {
            MenuItem quantif = new MenuItem(quantiflistUniv.get(i));
            quantif.setOnAction(this::recoverConclusion);
            myquantifConclusion.getItems().add(quantif);
        }
    }

    /**
     * Method to handle the action of switching to another FXML interface
     */
    @FXML
    public void switchToSyllogismeRedactionSimple(ActionEvent event) throws IOException {
        HelloApplication.getPageManager().goToPageNoHistory("mode");
    }



    /**
    * Replaces the current AnchorPane content with the interface from "poly-or-syllogisme.fxml" and adjusts its layout.
    */
    @FXML
    private void polyOrShow() {
        HelloApplication.getPageManager().goBack();
    }

    @FXML
    private void array(ActionEvent event) throws IOException {
        // Chargement du fichier FXML
        if(this.syllogism!=null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tableau.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur associé au fichier FXML
            TableauController controller = loader.getController();

            // Ajouter des lignes au tableau via le contrôleur

            Generator256 generator256 = new Generator256(this.syllogism);

            for (List<String> syllogisme : generator256.syllogismesDetails) {
                controller.addRow(syllogisme);
            }

            // Configuration de la scène et affichage
            Stage stage = new Stage();
            stage.setTitle("Tableau");

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Method to handle premise 1 quantifier selection
     */
    @FXML
    public void recoverPremise1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise1 = menuItem.getText();
        myquantifPremise1.setText(quantifPremise1);
        myHBoxsubjectPremise1.setDisable(false);
    }

    /**
     * Enables the predicate input for premise 1 and retrieves the subject text entered by the user.
     */
    @FXML
    public void nextSubjectPremise1(ActionEvent event) throws IOException {
        myHBoxpredicatPremise1.setDisable(false);
        subjectPremise1 = mysubjectPremise1.getText();
    }

    /**
     * Enables the quantifier input for premise 2 and retrieves the predicate text entered for premise 1.
     */
    @FXML
    public void nextPredicatPremise1(ActionEvent event) throws IOException {
        myHBoxquantifPremise2.setDisable(false);
        predicatPremise1 = mypredicatPremise1.getText();
    }

    /**
     * Retrieves the selected quantifier for premise 2 from a menu item, updates the corresponding text field,
     * and enables the input for the subject of premise 2.
     */
    @FXML
    public void recoverPremise2(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise2 = menuItem.getText();
        myquantifPremise2.setText(quantifPremise2);
        myHBoxsubjectPremise2.setDisable(false);
    }

    /**
     * Handles the selection of the subject for premise 2 based on the chosen radio button.
     * Updates the state of related input fields, adjusts the premise and conclusion values,
     * and manages the enabling/disabling of relevant controls.
     */
    @FXML
    public void choiceSubjectPremise2(ActionEvent event) throws IOException {
        buttonSubject = (RadioButton) event.getSource();
        myHBoxpredicatPremise2.setDisable(false);
        if (buttonSubject == mysubjectPremise2Subject || buttonSubject == mysubjectPremise2Predicat) {
            mypredicatPremise2Subject.setDisable(true);
            mypredicatPremise2Predicat.setDisable(true);
            mysubjectPremise2.setDisable(true);
            mypredicatPremise2New.setSelected(true);
            mypredicatPremise2.setDisable(false);

            mysubjectPremise2.setText("");
            mysubjectConclusion.setText("");
            mypredicatConclusion.setText("");

            subjectConclusion = mysubjectConclusion.getText();

        }
        if (buttonSubject == mysubjectPremise2New) {
            mypredicatPremise2Subject.setDisable(false);
            mypredicatPremise2Predicat.setDisable(false);
            mypredicatPremise2.setDisable(true);
            myHBoxpredicatPremise2.setDisable(true);
            mysubjectPremise2.setDisable(false);
            mypredicatPremise2New.setDisable(true);
            mypredicatPremise2New.setSelected(false);

            mysubjectConclusion.setText("");
            mypredicatConclusion.setText("");
        }
        if (buttonSubject == mysubjectPremise2Subject) {
            mysubjectConclusion.setText(predicatPremise1);
            subjectConclusion = predicatPremise1;
            subjectPremise2 = subjectPremise1;
        }
        if (buttonSubject == mysubjectPremise2Predicat) {
            mysubjectConclusion.setText(subjectPremise1);
            subjectConclusion = subjectPremise1;
            subjectPremise2 = predicatPremise1;
        }
    }

    /**
     * Updates the subject and conclusion for premise 2 when the user proceeds.
     * It also enables the predicate input for premise 2 and stores the values of the premise and conclusion.
     */
    @FXML
    public void nextSubjectPremise2(ActionEvent event) throws IOException {
        myHBoxpredicatPremise2.setDisable(false);
        mysubjectConclusion.setText(mysubjectPremise2.getText());
        subjectConclusion = mysubjectConclusion.getText();
        subjectPremise2 = mysubjectPremise2.getText();
    }

    /**
     * Handles the selection of the predicate for premise 2. Based on the choice of the user, it updates the predicate conclusion
     * and stores the appropriate values for premise 2. It also enables or disables the quantity input for the conclusion.
     */
    @FXML
    public void chocePredicatPremise2(ActionEvent event) throws IOException {
        buttonPredicat = (RadioButton) event.getSource();
        if (buttonPredicat == mypredicatPremise2Subject) {
            predicatConclusion = predicatPremise1;
            mypredicatConclusion.setText(predicatConclusion);
            predicatPremise2 = subjectPremise1;

            myHBoxquantifConclusion.setDisable(false);
        }
        if (buttonPredicat == mypredicatPremise2Predicat) {
            predicatConclusion = subjectPremise1;
            mypredicatConclusion.setText(predicatConclusion);
            predicatPremise2 = predicatPremise1;

            myHBoxquantifConclusion.setDisable(false);
        }
        if (buttonPredicat == mypredicatPremise2New) {
            myHBoxquantifConclusion.setDisable(true);
        }
    }

    /**
     * Handles the transition to the next step for predicate in premise 2. It updates the predicate conclusion and stores the
     * selected predicate for premise 2. It also enables the quantity input for the conclusion.
     */
    @FXML
    public void nextPredicatPremise2(ActionEvent event) throws IOException {
        myHBoxquantifConclusion.setDisable(false);
        mypredicatConclusion.setText(mypredicatPremise2.getText());
        predicatConclusion = mypredicatPremise2.getText();
        predicatPremise2 = mypredicatPremise2.getText();
    }

    /**
     * Handles the recovery of the conclusion's quantity. It updates the quantity conclusion and enables the inputs for subject,
     * predicate, and the verification button in the conclusion section.
     */
    @FXML
    public void recoverConclusion(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifConclusion = menuItem.getText();
        myquantifConclusion.setText(quantifConclusion);

        myHBoxsubjectConclusion.setDisable(false);
        myHBoxpredicatConclusion.setDisable(false);
        myverif.setDisable(false);

    }

    /**
     * Method to handle verification action (checking syllogism validity)
     */
    @FXML
    public void actionVerif(){
        reglelist.clear();
        myTextValid.setText("");

        negatifPremise1 = mynegatifPremise1.isSelected();
        negatifPremise2 = mynegatifPremise2.isSelected();
        negatifConclusion = mynegatifConclusion.isSelected();

        if (hasDuplicatePremiseCouples(subjectPremise1, predicatPremise1, subjectPremise2, predicatPremise2)) {
            if (language.equals("English")) {
                myTextValid.setText("Error: The subject, predicate, and middle term must be different.");
                myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
            } else {
                myTextValid.setText("Erreur : Le sujet, le prédicat et le terme moyen doivent être différents.");
                myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
            }

            return;
        }


        if(myregleMediumTerm.isSelected()) {
            reglelist.add("regleMoyenTerme");
        }
        if(myregleLatus.isSelected()) {
            reglelist.add("regleLatus");
        }
        if(myrNN.isSelected()) {
            reglelist.add("rNN");
        }
        if(myrN.isSelected()) {
            reglelist.add("rN");
        }
        if(myrAA.isSelected()) {
            reglelist.add("rAA");
        }
        if(myrPP.isSelected()) {
            reglelist.add("rPP");
        }
        if(myrP.isSelected()) {
            reglelist.add("rP");
        }
        if(myrUU.isSelected()) {
            reglelist.add("rUU");
        }


        boolean q1univ = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifPremise1)) {
                q1univ = true;
                break;
            }
        }
        Quantifier q1 = new Quantifier(quantifPremise1, q1univ);

        boolean q2univ = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifPremise2)) {
                q2univ = true;
                break;
            }
        }
        Quantifier q2 = new Quantifier(quantifPremise2, q2univ);

        boolean qCuniv = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifConclusion)) {
                qCuniv = true;
                break;
            }
        }
        Quantifier qC = new Quantifier(quantifConclusion, qCuniv);

        Syllogism syllo = new Syllogism(  q1,subjectPremise1,predicatPremise1, !negatifPremise1,
                q2,subjectPremise2,predicatPremise2, !negatifPremise2,
                qC, subjectConclusion, predicatConclusion, !negatifConclusion , this.language);

        this.syllogism = syllo;
        Response r = syllo.validRule(reglelist);
        if ((r.isUninteresting() && syllo.getInvalid().size() == 1 && syllo.getInvalid().contains("rUU"))) {
            if(language.equals("English")) {
                myTextValid.setText("Uninteresting conclusion try this conclusion :" + r.getConclusion());
            } else {
                myTextValid.setText("Conclusion inintéressant essayez cette conclusion :" + r.getConclusion());
            }

        } else {
            myTextValid.setText(r.getMessage());
        }
        myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");


    }

    /**
     *  Method to load language settings from a JSON file
     */
    private void loadLanguageFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        if (languageFile.exists()) {
            try {
                // Lire la langue depuis le fichier JSON
                Map<String, String> data = mapper.readValue(languageFile, Map.class);
                String savedLanguage = data.get("language");

                // Si une langue a été enregistrée, la définir comme valeur par défaut de la ComboBox
                if (savedLanguage != null) {
                    this.language = savedLanguage;
                    System.out.println("Langue chargée depuis language.json : " + savedLanguage);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors du chargement de la langue.");
            }
        }
    }

    /**
     * Method to set UI labels to French
     */
    public void setLabelsToFrench() {
        // Titre principal
        labelTitle.setText("Rédaction d'un syllogisme");

        // Premise 1
        myquantifPremise1.setText("Quantificateur");
        mynegatifPremise1.setText("Négatif");

        // Sujet et prédicat pour la première prémisse
        ((Label) myHBoxsubjectPremise1.getChildren().get(0)).setText("Sujet");
        ((Label) myHBoxpredicatPremise1.getChildren().get(0)).setText("Prédicat");

        // Premise 2
        myquantifPremise2.setText("Quantificateur");
        mynegatifPremise2.setText("Négatif");

        // Options de sujet et prédicat pour la deuxième prémisse
        ((Label) myHBoxsubjectPremise2.getChildren().get(0)).setText("Sujet : ");
        mysubjectPremise2Subject.setText("Sujet de p1");
        mysubjectPremise2Predicat.setText("Prédicat de p1");
        mysubjectPremise2New.setText("Nouveau");
        ((Label) myHBoxpredicatPremise2.getChildren().get(0)).setText("Prédicat : ");
        mypredicatPremise2Subject.setText("Sujet de p1");
        mypredicatPremise2Predicat.setText("Prédicat de p1");
        mypredicatPremise2New.setText("Nouveau");

        // Conclusion
        myquantifConclusion.setText("Quantificateur");
        mynegatifConclusion.setText("Négatif");

        // Sujet et prédicat pour la conclusion
        ((Label) myHBoxsubjectConclusion.getChildren().get(0)).setText("Sujet");
        ((Label) myHBoxpredicatConclusion.getChildren().get(0)).setText("Prédicat");

        // Boutons et autres éléments
        myverif.setText("Vérification");
        btnBack.setText("Retour");
        btnSwitch.setText("->");
        premisse1.setText("Prémisse n°1");
    }

    /**
     * Method to load data from a JSON file
     */
    private List<Map<String, String>> loadData() throws IOException {
        File file = new File("data.json");
        ObjectMapper mapper = new ObjectMapper();

        // Vérifier si le fichier existe et s'il n'est pas vide
        if (file.exists() && file.length() > 0) {
            // Lire les données si le fichier contient des données
            return mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        } else {
            // Retourner une liste vide si le fichier n'existe pas ou est vide
            return new ArrayList<>();
        }
    }

    /**
     * Method to retrieve quantifier data from a JSON file
     */
    private void retrieve() {
        try {
            // Lire les données depuis le fichier JSON
            List<Map<String, String>> dataList = loadData();

            // Deux listes pour stocker les données classées

            // Parcourir les données et les classer
            for (Map<String, String> data : dataList) {
                String selectedQuantif = data.get("selectedQuantif");
                String quantif = data.get("quantif");

                if(this.language.equals("English")) {
                    if ("Universal".equals(selectedQuantif)) {
                        quantiflistUniv.add(quantif);
                    }  if ("Existential".equals(selectedQuantif)) {
                        quantiflistExist.add(quantif);
                    }
                }

                else {
                    if ("Universel".equals(selectedQuantif)) {
                        quantiflistUniv.add(quantif);

                    }  if ("Existentiel".equals(selectedQuantif)) {
                        quantiflistExist.add(quantif);


                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
