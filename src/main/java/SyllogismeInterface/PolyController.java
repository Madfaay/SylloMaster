package SyllogismeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PolyController {
    @FXML
    private Button goToLastOne;
    @FXML
    private Button premisse;
    @FXML
    private Button goToBack;
    @FXML
    private Button goToBackSum;
    @FXML
    private Button structureValid;
    @FXML
    private Button doneButton;
    @FXML
    private Button validateStructure;
    @FXML
    private Label titre;
    //Pour stocker les valeurs entré par l'utilisateur
    @FXML
    private TextField premierterme;
    @FXML
    private TextField deuxiemeterme;
    @FXML
    private ComboBox<String> quantificateurs ;
    @FXML
    private TextField firstConclusion;
    @FXML
    private TextField secondConclusion;
    @FXML
    private ComboBox<String> quantifConclusion;
    @FXML
    private VBox premissesContainer = new VBox(); // Conteneur pour afficher les prémisses
    @FXML
    private Label number;


    private int nbpremise = 1;


    private ArrayList<String> first = new ArrayList<>();
    private ArrayList<String> second = new ArrayList<>();
    private ArrayList<String> quant = new ArrayList<>();

    // Compteur pour créer des fichiers uniques
    private int pageCounter = 1;


    @FXML
    public void initialize() {
        /////////////////////////////////////////////////////////////////////////////////
        if (this.number != null)
            number.setText(Integer.toString(nbpremise++));
        if(this.quantificateurs != null)
            quantificateurs.getItems().addAll("Option 1", "Option 2", "Option 3");
        if(this.quantifConclusion != null)
            quantifConclusion.getItems().addAll("Option 1", "Option 2", "Option 3");
        ///////////////////////////////////////////////////////////////////////////////////
    }
    // Méthode pour afficher les prémisses et la conclusion

    private void displayPremissesAndConclusion() {
        try {
            // Prepare the premises and conclusion content
            ArrayList<String> premisses = new ArrayList<>();
            ArrayList<String> conclusion = new ArrayList<>();

            // Add premises to the list
            for (int i = 0; i < first.size() - 1; i++) {
                String premisseText = String.format("Premisse %d: %s %s %s",
                        i + 1, first.get(i), quant.get(i), second.get(i));
                premisses.add(premisseText);
            }

            // Add conclusion to the list
            if (!first.isEmpty() && !second.isEmpty()) {
                String conclusionText = String.format("Conclusion: %s %s %s",
                        first.get(first.size() - 1),
                        quant.get(quant.size() - 1),
                        second.get(second.size() - 1));
                conclusion.add(conclusionText);
            }

            // Create dynamic FXML content for the new page
            String fxmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<?import javafx.scene.control.*?>\n" +
                    "<?import javafx.scene.layout.*?>\n" +
                    "<AnchorPane stylesheets=\"@style.css\" xmlns=\"http://javafx.com/javafx/21\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"org.example.pollyinterface.PolyController\">\n" +
                    "    <children>\n" +
                    "        <Label layoutX=\"20.0\" layoutY=\"20.0\" text=\"Premises:\" />\n" +
                    "        <VBox fx:id=\"premisesBox\" layoutX=\"20.0\" layoutY=\"50.0\" spacing=\"10.0\" />\n" +
                    "        <Label fx:id=\"conclusionLabel\" layoutX=\"20.0\" layoutY=\"180.0\" />\n" +
                    "        <Label fx:id=\"isValid\" layoutX=\"20.0\" layoutY=\"280.0\" />\n" +
                    "        <Button fx:id=\"backButton\" layoutX=\"20.0\" layoutY=\"220.0\" text=\"Back\" />\n" +
                    "        <Button fx:id=\"validationPoly\" layoutX=\"120.0\" layoutY=\"220.0\" text=\"Structure VALIDATION\" />\n" +
                    "        <Label fx:id=\"structureValid\" layoutX=\"20.0\" layoutY=\"280.0\" />\n" +
                    "    </children>\n" +
                    "</AnchorPane>";

            // Specify the destination directory for the new FXML
            String pathToResources = "src/main/resources/org/example/pollyinterface";
            File directory = new File(pathToResources);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create a unique FXML file for this page
            String fxmlFileName = "PremisseConclusionPage.fxml";
            File fxmlFile = new File(directory, fxmlFileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fxmlFile));
            writer.write(fxmlContent);
            writer.close();

            // Load the generated FXML
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent pageRoot = fxmlLoader.load();

            // Get elements from the FXML
            VBox premisesBox = (VBox) fxmlLoader.getNamespace().get("premisesBox");
            Label conclusionLabel = (Label) fxmlLoader.getNamespace().get("conclusionLabel");
            Label isValid = (Label)fxmlLoader.getNamespace().get("isValid");
            Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
            Button validationPoly = (Button) fxmlLoader.getNamespace().get("validationPoly");
            Label structureValid = (Label)fxmlLoader.getNamespace().get("structureValid");

            boolean valid = false;

            if (valid) {
                isValid.setText("The polysyllogism is valid");
                // Ajouter la classe CSS pour l'état valide
                isValid.getStyleClass().removeAll("invalid"); // Supprime l'ancien style (le cas échéant)
                isValid.getStyleClass().add("valid");        // Ajoute le style "valid"
            } else {
                String rules = "The rules: -----";
                isValid.setText("The polysyllogism is not valid\n" + rules);
                // Ajouter la classe CSS pour l'état invalide
                isValid.getStyleClass().removeAll("valid"); // Supprime l'ancien style (le cas échéant)
                isValid.getStyleClass().add("invalid");     // Ajoute le style "invalid"
            }


            // Populate the premises list in the VBox
            for (String premisse : premisses) {
                premisesBox.getChildren().add(new Label(premisse));
            }

            // Display the conclusion
            if (!conclusion.isEmpty()) {
                conclusionLabel.setText(conclusion.get(0));
            }

            // Add a back button event to load the previous page
            backButton.setOnAction(event -> {
                try {
                    // Load the PremissePage.fxml and show it
                    FXMLLoader premissePageLoader = new FXMLLoader(getClass().getResource("PremissePage.fxml"));
                    Parent premissePageRoot = premissePageLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(premissePageRoot));
                    stage.setTitle("Premisse Page");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Show the new page with premises and conclusion
            Stage newStage = new Stage();
            newStage.setScene(new Scene(pageRoot));
            newStage.setTitle("Premises and Conclusion");

            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void afficherListes() {
        System.out.print("Liste des premiers termes: [");
        for (String terme : first) {
            System.out.print(terme + " ");
        }
        System.out.print("]\n\n");

        System.out.print("Liste des deuxièmes termes: [");
        for (String terme : second) {
            System.out.print(terme + " ");
        }
        System.out.print("]\n\n");

        System.out.print("Liste des quantificateurs: [");
        for (String quantificateur : quant) {
            System.out.print(quantificateur + " ");
        }
        System.out.print("]\n");
    }


    @FXML
    private void actionButtonNewPremisse(){
        boolean valid = true;
        if(premierterme.getText().isEmpty()){
            premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else {
            premierterme.setStyle(null);
        }
        if(deuxiemeterme.getText().isEmpty()){
            deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            deuxiemeterme.setStyle(null);
        }
        if (quantificateurs.getValue().isEmpty()){
            quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            quantificateurs.setStyle(null);
        }

        if (valid) {
            number.setText(Integer.toString(nbpremise++));
            pageCounter++;
            if (pageCounter >= 3) {
                goToLastOne.setDisable(false);

            }
            // Get text from the TextFields
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();

            // Get the selected item from the ComboBox
            String selectedQuantificateur = quantificateurs.getValue();

            // Ajouter les prémisses dans les listes
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Effacer les champs après ajout
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);

            // Afficher les listes après ajout
            System.out.println("*********************************");
            afficherListes();
            System.out.println("*********************************");
        }
    }


    /****************************************************************************************************/

    /*** Methode generique pour la redirection de page par un boutton ***/

    private void loadNewPageByButton(String path, Button actionButton) {
        try {
            // Charger la deuxième page à partir de depart
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent page2Parent = loader.load();

            // Obtenir le contrôleur associé à la page courante
            PolyController page2Controller = loader.getController();

            // Obtenir la scène actuelle et sa taille
            Stage stage = (Stage) actionButton.getScene().getWindow();
            Scene currentScene = stage.getScene();
            double currentWidth = currentScene.getWidth();
            double currentHeight = currentScene.getHeight();

            // Afficher la scène de la deuxième page avec la taille actuelle
            Scene newScene = new Scene(page2Parent, currentWidth, currentHeight);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la page
        }
    }

    /******************************************************************************/

    @FXML
    private void handleButtonLast() {
        structureValid.setVisible(true);
        premisse.setVisible(false);
        titre.setText("Conclusion");
        number.setText("");
        premisse.setDisable(true);
        goToLastOne.setDisable(true);

        boolean valid = true;
        if(premierterme.getText().isEmpty()){
            premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else {
            premierterme.setStyle(null);
        }
        if(deuxiemeterme.getText().isEmpty()){
            deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            deuxiemeterme.setStyle(null);
        }
        if (quantificateurs.getValue().isEmpty()){
            quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            quantificateurs.setStyle(null);
        }

        if (valid) {
            // Get text from the TextFields
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();

            // Get the selected item from the ComboBox
            String selectedQuantificateur = quantificateurs.getValue();

            // Ajouter les prémisses dans les listes
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Effacer les champs après ajout
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);

            // Afficher les listes après ajout
            System.out.println("*********************************");
            afficherListes();
            System.out.println("*********************************");
        }

        // Effacer les champs après ajout
        premierterme.clear();
        deuxiemeterme.clear();
        quantificateurs.setValue(null);

        // Afficher les listes après ajout
        System.out.println("*********************************");
        afficherListes();
        System.out.println("*********************************");
    }

    @FXML
    private void handleButtonBack(){

        String path = "PremissePage.fxml";
        loadNewPageByButton(path,goToBack);
    }

    @FXML
    private void handleButtonBackSum(){
        String path = "Conclusion.fxml";
        loadNewPageByButton(path,goToBackSum);
    }

    //Bouton de validation de la structure du polysyllogisme
    @FXML
    private void handleValidation(){
        boolean valid = true;
        if(premierterme.getText().isEmpty()){
            premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else {
            premierterme.setStyle(null);
        }
        if(deuxiemeterme.getText().isEmpty()){
            deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            deuxiemeterme.setStyle(null);
        }
        if (quantificateurs.getValue().isEmpty()){
            quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            quantificateurs.setStyle(null);
        }

        if (valid) {
            // Get text from the TextFields
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();

            // Get the selected item from the ComboBox
            String selectedQuantificateur = quantificateurs.getValue();

            // Ajouter les prémisses dans les listes
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Effacer les champs après ajout
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);
        }

        //On ferme la page de saisie
        Stage currentStage = (Stage) doneButton.getScene().getWindow();
        currentStage.close();
        // Afficher les prémisses et la conclusion ensemble
        displayPremissesAndConclusion();
    }

    @FXML
    private void handleValidStructure(){
        System.out.println("bouton validation structure");
        doneButton.setDisable(false);
        structureValid.setDisable(true);

    }



}