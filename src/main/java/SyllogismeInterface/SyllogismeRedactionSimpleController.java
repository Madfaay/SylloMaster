package SyllogismeInterface;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyllogismeRedactionSimpleController {
    public AnchorPane pane;
    public Label labelTitle, labelTypeFigure, labelSubject, labelConclusion, labelPremise2, labelPremise1;
    public Button actionVerif, btnBack, btnSwitch, btnArray;
    public MenuItem menuItemConclusion_2,menuItemConclusion_1, menuItemPremise2_2, menuItemPremise2_1, menuItemPremise1_1, menuItemPremise1_2;
    public Label mypredicateConclusionLabel;
    public Label mymediumTermLabel;
    public Button mytypeFigureLabel;

    String quantifPremise1;
    String quantifPremise2;
    String quantifConclusion;
    String subject;
    String predicatConclusion;
    String mediumTerm;
    int typeFigure;
    Boolean negatifPremise1;
    Boolean negatifPremise2;
    Boolean negatifConclusion;
    Boolean hypothesis;

    Syllogism syllogism;
    List<String> quantiflistUniv = new ArrayList<>();
    List<String> quantiflistExist = new ArrayList<>();

    ArrayList<String> reglelist = new ArrayList<>();

    @FXML MenuButton textPremise1;
    @FXML MenuButton textPremise2;
    @FXML MenuButton textConclusion;
    @FXML TextField mysubject;
    @FXML TextField mypredicatConclusion;
    @FXML TextField mymediumTerm;
    @FXML TextField mytypeFigure;
    @FXML CheckBox mynegatifPremise1;
    @FXML CheckBox mynegatifPremise2;
    @FXML CheckBox mynegatifConclusion;
    @FXML CheckBox myhypothesis;
    @FXML Label myTextValid;
    @FXML CheckBox myregleMediumTerm, myregleLatus, myrNN, myrN, myrAA, myrPP, myrP, myrUU;

    public String language ;

    private final File languageFile = new File("language.json");

    /**
     * Initializes the controller by loading the language from a JSON file, setting labels in French if the language is not English,
     * and populating the menu items for premises and conclusions based on predefined quantifiers.
     *
     * This method iterates through two lists of quantifiers (`quantiflistExist` and `quantiflistUniv`), creating `MenuItem` objects
     * for each quantifier, and adding them to the corresponding menus (`textPremise1`, `textPremise2`, and `textConclusion`).
     * Additionally, event handlers are set to handle actions when the quantifiers are selected.
     */
    @FXML
    void initialize() {
        this.loadLanguageFromJson();
        if(!this.language.equals("English"))
            this.setLabelsToFrench();

        retrieve();

        int taille = quantiflistExist.size();
        for (int i = 0; i < taille; i++) {
            MenuItem quantif = new MenuItem(quantiflistExist.get(i));
            quantif.setOnAction(this::recoverPremise1);
            MenuItem quantif2 = new MenuItem(quantiflistExist.get(i));
            quantif2.setOnAction(this::recoverPremise2);
            MenuItem quantif3 = new MenuItem(quantiflistExist.get(i));
            quantif3.setOnAction(this::recoverConclusion);
            textPremise1.getItems().add(quantif);
            textPremise2.getItems().add(quantif2);
            textConclusion.getItems().add(quantif3);
        }
        int t = quantiflistUniv.size();
        for (int i = 0; i < t; i++) {
            MenuItem quantif = new MenuItem(quantiflistUniv.get(i));
            quantif.setOnAction(this::recoverPremise1);
            MenuItem quantif2 = new MenuItem(quantiflistUniv.get(i));
            quantif2.setOnAction(this::recoverPremise2);
            MenuItem quantif3 = new MenuItem(quantiflistUniv.get(i));
            quantif3.setOnAction(this::recoverConclusion);
            textPremise1.getItems().add(quantif);
            textPremise2.getItems().add(quantif2);
            textConclusion.getItems().add(quantif3);
        }
    }

    /**
     * Switches to the "SyllogismeRedaction" interface by loading the associated FXML file and updating the current content
     * of the AnchorPane.
     *
     * This method attempts to load the "SyllogismeRedaction.fxml" file, clearing the existing content of the AnchorPane
     * and adding the new content. The new content is anchored to the edges of the AnchorPane to ensure proper layout.
     * In case of an error during the loading of the FXML file, the exception is printed to the console.
     */
    @FXML
    public void switchToSyllogismeRedaction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyllogismeRedaction.fxml"));
            Parent acceuilContent = fxmlLoader.load();

            pane.getChildren().clear(); // Efface les éléments existants
            pane.getChildren().add(acceuilContent); // Ajoute l'interface des paramètres
            AnchorPane.setTopAnchor(acceuilContent, 0.0);
            AnchorPane.setBottomAnchor(acceuilContent, 0.0);
            AnchorPane.setLeftAnchor(acceuilContent, 0.0);
            AnchorPane.setRightAnchor(acceuilContent, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the "poly-or-syllogisme" interface in the current AnchorPane.
     *
     * This method attempts to load the "poly-or-syllogisme.fxml" file and updates the content of the AnchorPane by clearing
     * its current children and adding the new interface. The new content is anchored to all sides of the AnchorPane to ensure
     * proper layout. If an error occurs while loading the FXML file, the exception is printed to the console.
     */
    @FXML
    private void polyOrShow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("poly-or-syllogisme.fxml"));
            Parent polyorsylloContent = fxmlLoader.load();

            pane.getChildren().setAll(polyorsylloContent);

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
            if(syllogism.isPdiffM() && syllogism.isPdiffS() && syllogism.isSdiffM())
            { stage.show();}
        }
    }

    /**
     * Switches to the "TypeFigure" screen by loading the corresponding FXML file and displaying it in a new window.
     *
     * This method loads the "TypeFigure.fxml" file and creates a new stage (window) to display the content. The stage is
     * titled "Type Figure", and a new scene is created with the loaded FXML content. The scene is then set for the stage,
     * and the stage is shown to the user.
     */
    @FXML
    public void switchToTypeFigure(ActionEvent event) throws IOException {
        Parent root ;
        if(this.language.equals("English"))
             root = FXMLLoader.load(getClass().getResource("TypeFigure.fxml"));
        else
             root = FXMLLoader.load(getClass().getResource("TypeFigureFr.fxml"));


        Stage stage = new Stage();
        stage.setTitle("Type Figure");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Retrieves the selected quantifier for Premise 1 and updates the corresponding text field.
     *
     * This method is triggered when a quantifier is selected from the menu in the interface. The selected quantifier
     * is retrieved from the menu item and stored in the `quantifPremise1` variable. The text field `textPremise1`
     * is then updated to display the selected quantifier.
     */
    @FXML
    public void recoverPremise1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise1 = menuItem.getText();
        textPremise1.setText(quantifPremise1);
    }

    /**
     * Retrieves the selected quantifier for Premise 2 and updates the corresponding text field.
     *
     * This method is triggered when a quantifier is selected from the menu in the interface. The selected quantifier
     * is retrieved from the menu item and stored in the `quantifPremise2` variable. The text field `textPremise2`
     * is then updated to display the selected quantifier.
     */
    @FXML
    public void recoverPremise2(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise2 = menuItem.getText();
        textPremise2.setText(quantifPremise2);
    }

    /**
     * Retrieves the selected quantifier for the Conclusion and updates the corresponding text field.
     *
     * This method is triggered when a quantifier is selected from the menu in the interface. The selected quantifier
     * is retrieved from the menu item and stored in the `quantifConclusion` variable. The text field `textConclusion`
     * is then updated to display the selected quantifier.
     */
    @FXML
    public void recoverConclusion(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifConclusion = menuItem.getText();
        textConclusion.setText(quantifConclusion);
    }

    /**
     * Validates the premises, conclusion, and other input fields based on user selections and displays
     * the result message in the interface.
     *
     * This method checks if all required fields are filled, validates the quantifiers, and processes the data
     * related to premises, conclusion, and other terms. It also updates the UI by highlighting empty fields
     * with a red border. The selected information is used to create a `Syllogisme` object, which is then validated
     * to return a response that is displayed in the interface.
     */
    @FXML
    public void actionVerif(ActionEvent event) throws IOException {
        reglelist.clear();
        myTextValid.setText("");

        negatifPremise1 = mynegatifPremise1.isSelected();
        negatifPremise2 = mynegatifPremise2.isSelected();
        negatifConclusion = mynegatifConclusion.isSelected();

        hypothesis = myhypothesis.isSelected();

        if (quantifPremise1 == null){
            textPremise1.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else{
            textPremise1.setStyle(null);
        }

        if (quantifPremise2 == null){
            textPremise2.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else{
            textPremise2.setStyle(null);
        }

        if (quantifConclusion == null){
            textConclusion.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else {
            textConclusion.setStyle(null);
        }

        if (mysubject.getText().isEmpty()){
            mysubject.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else {
            mysubject.setStyle(null);
            subject = mysubject.getText();
        }

        if (mypredicatConclusion.getText().isEmpty()){
            mypredicatConclusion.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else {
            mypredicatConclusion.setStyle(null);
            predicatConclusion = mypredicatConclusion.getText();
        }

        if (mymediumTerm.getText().isEmpty()){
            mymediumTerm.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else {
            mymediumTerm.setStyle(null);
            mediumTerm = mymediumTerm.getText();
        }

        if (mytypeFigure.getText().isEmpty()){
            mytypeFigure.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
        }
        else {
            if (mytypeFigure.getText().matches("\\d+")) {
                typeFigure = Integer.parseInt(mytypeFigure.getText());
                if (typeFigure >= 1 && typeFigure <= 4 ) {
                    mytypeFigure.setStyle(null);
                }
                else {
                    mytypeFigure.setText("Un entier entre 1 et 4");
                    mytypeFigure.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
                }
            }
            else {
                mytypeFigure.setText("Un entier entre 1 et 4");
                mytypeFigure.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            }
        }

        System.out.println("Verif ");
        System.out.println("Premise 1 " + quantifPremise1 + " " + negatifPremise1);
        System.out.println("Premise 2 " + quantifPremise2 + " "  + negatifPremise2);
        System.out.println("Conclusion " + quantifConclusion + " "  + negatifConclusion);

        System.out.println("Subject " + subject);
        System.out.println("Precate Conclusion " + predicatConclusion);
        System.out.println("Medium Term " + mediumTerm);
        System.out.println("Type Figure " + typeFigure);

        System.out.println("Hypothesis " + hypothesis);

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

        Syllogism syllo = new Syllogism(q1,q2,qC,subject,predicatConclusion,mediumTerm,!negatifPremise1,!negatifPremise2,!negatifConclusion,typeFigure,this.language );
        this.syllogism = syllo;

        Response r = syllo.validRule(reglelist);
        if (r.getConclusion() == null)
            myTextValid.setText(r.getMessage());
        else
            myTextValid.setText(r.getMessage() + " " + r.getConclusion());
        myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");



        if(!syllogism.isPdiffM() ){
            if(language.equals("English")){
                myTextValid.setText("Error : Predicate and Middle term are similar");
            }
            else{
                myTextValid.setText("Erreur : Predicat et Moyen term sont identique");
            }
        }
        if(!syllogism.isPdiffS() ){
            if(language.equals("English")){
                myTextValid.setText("Error : Predicate and Subject are similar");
            }
            else{
                myTextValid.setText("Erreur : Predicat et Moyen term sont identique");
            }
        }
        if(!syllogism.isSdiffM() ){
            if(language.equals("English")){
                myTextValid.setText("Error : Subject and Middle term are similar");
            }
            else{
                myTextValid.setText("Erreur : Subject et Moyen term sont identique");
            }
        }
    }

    /**
     * Method to set UI labels to French
     */
    public void setLabelsToFrench() {
        labelTitle.setText("Rédaction d'un syllogisme");

        textPremise1.setText("Quantificateur");
        mynegatifPremise1.setText("Négatif");

        textPremise2.setText("Quantificateur");
        mynegatifPremise2.setText("Négatif");

        textConclusion.setText("Quantificateur");
        mynegatifConclusion.setText("Négatif");

        labelSubject.setText("Sujet");
        mypredicateConclusionLabel.setText("Prédicat de la conclusion");
        mymediumTermLabel.setText("moyen Terme");
        labelTypeFigure.setText("Type de la figure");

        actionVerif.setText("Vérification");
        myhypothesis.setText("Hypothèse d'existence");
        btnBack.setText("Retour");
        btnSwitch.setText("->");
        labelPremise1.setText("Prémisse n°1");
        labelPremise2.setText("Prémisse n°2");

    }

    /**
     *  Method to load language settings from a JSON file
     */
    private void loadLanguageFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        if (languageFile.exists()) {
            try {
                Map<String, String> data = mapper.readValue(languageFile, Map.class);
                String savedLanguage = data.get("language");

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
     * Method to load data from a JSON file
     */
    private List<Map<String, String>> loadData() throws IOException {
        File file = new File("data.json");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists() && file.length() > 0) {
            return mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Method to retrieve quantifier data from a JSON file
     */
    private void retrieve() {
        try {
            List<Map<String, String>> dataList = loadData();
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
