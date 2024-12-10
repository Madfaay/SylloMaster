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


public class SyllogismeRedactionController {
    public Label labelTitle,premisse1;
    public Button btnBack, btnSwitch;

    @FXML private AnchorPane pane;

    Syllogism syllogism;
    String quantifPremise1, subjectPremise1, predicatPremise1;
    Boolean negatifPremise1;

    String quantifPremise2, subjectPremise2, predicatPremise2;
    Boolean negatifPremise2;

    String quantifConclusion, subjectConclusion, predicatConclusion;
    Boolean negatifConclusion;

    RadioButton buttonSubject, buttonPredicat;

    Boolean hypothesis;

    List<String> quantiflistExist = new ArrayList<>();
    List<String> quantiflistUniv = new ArrayList<>();
    ArrayList<String> reglelist = new ArrayList<>();

    @FXML MenuButton myquantifPremise1;
    @FXML TextField mysubjectPremise1, mypredicatPremise1;
    @FXML CheckBox mynegatifPremise1;
    @FXML HBox myHBoxsubjectPremise1, myHBoxpredicatPremise1;

    @FXML Label myTextValid;

    @FXML MenuButton myquantifPremise2;
    @FXML RadioButton mysubjectPremise2Subject, mysubjectPremise2Predicat, mysubjectPremise2New, mypredicatPremise2Subject, mypredicatPremise2Predicat, mypredicatPremise2New;
    @FXML TextField mysubjectPremise2, mypredicatPremise2;
    @FXML CheckBox mynegatifPremise2;
    @FXML HBox myHBoxquantifPremise2, myHBoxsubjectPremise2, myHBoxpredicatPremise2;
    @FXML ToggleGroup subject, predicat;

    @FXML MenuButton myquantifConclusion;
    @FXML TextField mysubjectConclusion, mypredicatConclusion;
    @FXML CheckBox mynegatifConclusion;
    @FXML HBox myHBoxquantifConclusion, myHBoxsubjectConclusion, myHBoxpredicatConclusion;

    @FXML Button myverif;
    @FXML CheckBox myregleMediumTerm, myregleLatus, myrNN, myrN, myrAA, myrPP, myrP, myrUU;

    @FXML Button btnArray;

    private final File languageFile = new File("language.json");

    private String language ;

    /**
     * Method to initialize the controller and load the language settings
     */
    @FXML
    void initialize()
    {
        loadLanguageFromJson();
        if(!this.language.equals("English")) {
            System.out.println("laaaaaaaaaaa");
            setLabelsToFrench();
        }

        retrieve();
        populateQuantifierMenus();

    }
    /**
     * Checks for duplicate subject-predicate pairs in the premises.
     * This method ensures that the subject-predicate pairs in the two premises are distinct.
     *
     * @param subject1 the subject of the first premise
     * @param predicate1 the predicate of the first premise
     * @param subject2 the subject of the second premise
     * @param predicate2 the predicate of the second premise
     * @return true if there are duplicate subject-predicate pairs between the two premises, false otherwise
     *
     */
    private boolean hasDuplicatePremiseCouples(String subject1, String predicate1, String subject2, String predicate2) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();

        frequencyMap.put(subject1, 0);
        frequencyMap.put(predicate1, 0);
        frequencyMap.put(subject2, 0);
        frequencyMap.put(predicate2, 0);

        frequencyMap.put(subject1, frequencyMap.get(subject1)+1);
        frequencyMap.put(predicate1, frequencyMap.get(predicate1)+1);
        frequencyMap.put(subject2, frequencyMap.get(subject2)+1);
        frequencyMap.put(predicate2, frequencyMap.get(predicate2)+1);

        boolean twoEquals = false;

        for (int frequency : frequencyMap.values()) {
            if(frequency == 2) {
                twoEquals = true;
            } else if(frequency > 2) {
                return true;
            }
        }
        return !(twoEquals && (frequencyMap.size() == 3));


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
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyllogismeRedactionSimple.fxml"));
            Parent acceuilContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            pane.getChildren().setAll(acceuilContent);
            AnchorPane.setTopAnchor(acceuilContent, 0.0);
            AnchorPane.setBottomAnchor(acceuilContent, 0.0);
            AnchorPane.setLeftAnchor(acceuilContent, 0.0);
            AnchorPane.setRightAnchor(acceuilContent, 0.0);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
    * Replaces the current AnchorPane content with the interface from "poly-or-syllogisme.fxml" and adjusts its layout.
    */
    @FXML
    private void polyOrShow() {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("poly-or-syllogisme.fxml"));
            Parent polyorsylloContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            pane.getChildren().setAll(polyorsylloContent);

            // Optionnel : ajustez la position et la taille du contenu ajouté
            AnchorPane.setTopAnchor(polyorsylloContent, 0.0);
            AnchorPane.setBottomAnchor(polyorsylloContent, 0.0);
            AnchorPane.setLeftAnchor(polyorsylloContent, 0.0);
            AnchorPane.setRightAnchor(polyorsylloContent, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                myTextValid.setText("Error : The subject-predicate pairs in the premises cannot be identical.");
                myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
            } else {
                myTextValid.setText("Erreur : Les couples de sujet-prédicat dans les prémisses ne peuvent pas être identiques.");
                myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
            }

            return;
        }


        System.out.println("Verif ");
        System.out.println("Premise 1 " + quantifPremise1 + " " + subjectPremise1 + " " + predicatPremise1 + " " + negatifPremise1);
        System.out.println("Premise 2 " + quantifPremise2 + " " + subjectPremise2 + " " + predicatPremise2 + " " + negatifPremise2);
        System.out.println("Conclusion " + quantifConclusion + " " + subjectConclusion + " " + predicatConclusion + " " + negatifConclusion);

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
        Quantifier q2 = new Quantifier(quantifPremise2, q1univ);

        boolean qCuniv = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifConclusion)) {
                qCuniv = true;
                break;
            }
        }
        Quantifier qC = new Quantifier(quantifConclusion, q1univ);

        Syllogism syllo = new Syllogism(  q1,subjectPremise1,predicatPremise1, !negatifPremise1,
                q2,subjectPremise2,predicatPremise2, !negatifPremise2,
                qC, subjectConclusion, predicatConclusion, !negatifConclusion , this.language);

        this.syllogism = syllo;
        Response r = syllo.validRule(reglelist);
        if (r.getConclusion() == null)
            myTextValid.setText(r.getMessage());
        else
            myTextValid.setText(r.getMessage() + " " + r.getConclusion());
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
