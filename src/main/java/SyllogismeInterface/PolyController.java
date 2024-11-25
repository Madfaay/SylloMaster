package SyllogismeInterface;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import traitement.Polysyllogisme;
import traitement.Proposition;
import traitement.Quantificateur;
import traitement.Reponse;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolyController {
    public AnchorPane anchor;
    public CheckBox Negative;
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

    @FXML
    private Label resultStruct ;

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
    private ArrayList<Boolean> booleans = new ArrayList<>();

    private ArrayList<String> quantiflistUniv  = new ArrayList<>();

    private ArrayList<String> quantiflistExist  = new ArrayList<>();


    private int pageCounter = 1;

    private final File languageFile = new File("language.json");

    private String language ;

    private Polysyllogisme poly ;


    /**
     * This method is called when the controller is initialized.
     * It sets the initial values for the number label and the ComboBoxes.
     */
    @FXML
    public void initialize() {
        loadLanguageFromJson();
        retrieve();

        structureValid.setDisable(true);
        // Set the number label text to the current premise number and increment it
        if (this.number != null)
            number.setText(Integer.toString(nbpremise++));

        // Add options to the 'quantificateurs' ComboBox
        if (this.quantificateurs != null) {
            for(String quantif : quantiflistUniv)
            quantificateurs.getItems().add(quantif);
            for(String quantif : quantiflistExist ){
                quantificateurs.getItems().add(quantif);
            }

        }

        // Add options to the 'quantifConclusion' ComboBox
    }

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
     * This method generates and displays a new page showing premises and conclusion
     * for the current logical argument. It creates a dynamic FXML file, populates it with data,
     * and displays it in a new window.
     *
     * The method:
     * 1. Prepares lists of premises and conclusion.
     * 2. Generates a dynamic FXML layout and writes it to a file.
     * 3. Loads the FXML and displays it in a new window.
     * 4. Provides the option to go back to the previous page.
     * 5. Displays whether the polysyllogism is valid or not based on a boolean check.
     */
    private void displayPremissesAndConclusion() {
        try {
            // Prepare premises and conclusion lists
            ArrayList<String> premisses = new ArrayList<>();
            ArrayList<String> conclusion = new ArrayList<>();

            // Add premises to the list
            for (int i = 0; i < first.size() - 1; i++) {
                String premisseText = String.format("Premisse %d: %s %s %s",
                        i + 1, first.get(i), quant.get(i), second.get(i));
                premisses.add(premisseText);
            }

            // Add conclusion to the list if applicable
            if (!first.isEmpty() && !second.isEmpty()) {
                String conclusionText = String.format("Conclusion: %s %s %s",
                        first.get(first.size() - 1),
                        quant.get(quant.size() - 1),
                        second.get(second.size() - 1));
                conclusion.add(conclusionText);
            }

            // Create dynamic FXML content
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

            // Write FXML to a file
            String pathToResources = "src/main/resources/org/example/pollyinterface";
            File directory = new File(pathToResources);
            if (!directory.exists()) {
                directory.mkdirs();
            }

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
            Label isValid = (Label) fxmlLoader.getNamespace().get("isValid");
            Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
            Button validationPoly = (Button) fxmlLoader.getNamespace().get("validationPoly");
            Label structureValid = (Label) fxmlLoader.getNamespace().get("structureValid");

            // Check validity of the argument
            boolean valid = false;

            if (valid) {
                isValid.setText("The polysyllogism is valid");
                isValid.getStyleClass().removeAll("invalid"); // Remove old invalid style
                isValid.getStyleClass().add("valid");        // Add valid style
            } else {
                String rules = "The rules: -----";
                isValid.setText("The polysyllogism is not valid\n" + rules);
                isValid.getStyleClass().removeAll("valid"); // Remove old valid style
                isValid.getStyleClass().add("invalid");     // Add invalid style
            }

            // Populate the VBox with premises
            for (String premisse : premisses) {
                premisesBox.getChildren().add(new Label(premisse));
            }

            // Display conclusion
            if (!conclusion.isEmpty()) {
                conclusionLabel.setText(conclusion.get(0));
            }

            // Handle back button event
            backButton.setOnAction(event -> {
                try {
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

    /**
     * This method prints the contents of three lists: the first terms, the second terms,
     * and the quantifiers. Each list is displayed in the format:
     * [item1 item2 item3 ...] with each list shown on a new line.
     */
    private void afficherListes() {
        // Print the list of first terms
        System.out.print("Liste des premiers termes: [");
        for (String terme : first) {
            System.out.print(terme + " ");
        }
        System.out.print("]\n\n");

        // Print the list of second terms
        System.out.print("Liste des deuxièmes termes: [");
        for (String terme : second) {
            System.out.print(terme + " ");
        }
        System.out.print("]\n\n");

        // Print the list of quantifiers
        System.out.print("Liste des quantificateurs: [");
        for (String quantificateur : quant) {
            System.out.print(quantificateur + " ");
        }
        System.out.print("]\n");
        System.out.print("Liste des negatives: [");
        for (Boolean boo : booleans) {
            System.out.print(boo + " ");
        }
        System.out.print("]\n");
    }



    /**
     * This method handles the action when the "New Premise" button is clicked.
     * It validates the input fields (first term, second term, and quantifier).
     * If any field is empty, it highlights the corresponding field with a red border.
     * If all fields are valid, it adds the terms and quantifier to the lists,
     * updates the premise number, and clears the input fields.
     * It also prints the updated lists to the console.
     */
    @FXML
    private void actionButtonNewPremisse(){
        boolean valid = true;

        // Validate the first term input field
        if(premierterme.getText().isEmpty()){
            //premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else {
            premierterme.setStyle(null);
        }

        // Validate the second term input field
        if(deuxiemeterme.getText().isEmpty()){
            //deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            deuxiemeterme.setStyle(null);
        }

        // Validate the quantifier ComboBox
        if (quantificateurs.getValue().isEmpty()){
            //quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }
        else{
            quantificateurs.setStyle(null);
        }

        if (quantificateurs.getValue().isEmpty()){
            //quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        }


        // If all fields are valid
        if (valid) {
            this.booleans.add(this.Negative.isSelected());
            // Update premise number and page counter
            number.setText(Integer.toString(nbpremise++));
            pageCounter++;

            // Enable "Go to Last One" button if page counter is 3 or more
            if (pageCounter >= 3) {
                goToLastOne.setDisable(false);
            }

            // Get text from the TextFields
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();

            // Get the selected quantifier
            String selectedQuantificateur = quantificateurs.getValue();

            // Add premise data to the lists
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Clear the input fields after adding
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);

            // Print the updated lists to the console
            System.out.println("*********************************");
            afficherListes();
            System.out.println("*********************************");
        }
    }

    /**
     * This method loads a new page when a button is clicked.
     * It loads the FXML file of the new page, retrieves its controller,
     * and sets the new page's scene to be the same size as the current scene.
     * The new page is then displayed on the current stage.
     *
     * @param path The path to the FXML file of the new page.
     * @param actionButton The button that triggered the page change.
     */
    private void loadNewPageByButton(String path, Button actionButton) {
        try {
            // Load the new page from the specified path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent page2Parent = loader.load();

            // Get the controller for the new page
            PolyController page2Controller = loader.getController();

            // Get the current stage and its scene size
            Stage stage = (Stage) actionButton.getScene().getWindow();
            Scene currentScene = stage.getScene();
            double currentWidth = currentScene.getWidth();
            double currentHeight = currentScene.getHeight();

            // Set the new page's scene with the current size and show it
            Scene newScene = new Scene(page2Parent, currentWidth, currentHeight);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any errors in loading the page
        }
    }

    /**
     * This method is triggered when the "Last" button is clicked. It handles the validation of input fields,
     * updates the interface for showing the conclusion, and processes the input data by adding it to the lists.
     * It also hides certain UI elements and disables the button to prevent further modifications.
     */
    @FXML
    private void handleButtonLast() {
        // Show structure validation label and hide the premise input section
        premisse.setDisable(true);
        titre.setText("Conclusion");
        number.setText("");
        premisse.setDisable(true);
        goToLastOne.setDisable(true);

        // Validate input fields
        boolean valid = true;
        if(premierterme.getText().isEmpty()){
           // premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;"); // Set red border if empty
            valid = false;
        } else {
            premierterme.setStyle(null); // Clear the red border
        }
        if(deuxiemeterme.getText().isEmpty()){
           // deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        } else {
            deuxiemeterme.setStyle(null);
        }
        if (quantificateurs.getValue().isEmpty()){
           // quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        } else {
            quantificateurs.setStyle(null);
        }

        // If the inputs are valid, process the data
        if (valid) {
            this.booleans.add(this.Negative.isSelected());

            // Get text from the TextFields
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();

            // Get the selected quantifier from the ComboBox
            String selectedQuantificateur = quantificateurs.getValue();

            // Add the premise data to the lists
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Clear input fields after adding the premise
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);

            // Print the lists after adding the premise
            System.out.println("*********************************");
            afficherListes();
            System.out.println("*********************************");
            this.structureValid.setDisable(false);
        }

        // Clear the input fields again (redundant after the above logic)
        premierterme.clear();
        deuxiemeterme.clear();
        quantificateurs.setValue(null);

        // Print the lists again (redundant as it has already been done)
        System.out.println("*********************************");
        afficherListes();
        System.out.println("*********************************");
    }


    /**
     * This method is triggered when the "Back" button is clicked. It loads the previous page (PremissePage.fxml)
     * and displays it by calling the loadNewPageByButton method.
     */
    @FXML
    private void handleButtonBack() {
        // Define the path to the previous page's FXML
        String path = "PremissePage.fxml";

        // Load and display the previous page using the helper method
        loadNewPageByButton(path, goToBack);
    }

    /**
     * This method is triggered when the "Back" button is clicked on the summary page.
     * It loads the conclusion page (Conclusion.fxml) and displays it by calling the loadNewPageByButton method.
     */
    @FXML
    private void handleButtonBackSum() {
        // Define the path to the conclusion page's FXML
        String path = "Conclusion.fxml";

        // Load and display the conclusion page using the helper method
        loadNewPageByButton(path, goToBackSum);
    }

    /**
     * This method is triggered when the "Done" button is clicked to validate the input.
     * It checks if all the required fields (premierterme, deuxiemeterme, and quantificateurs) are filled.
     * If any field is empty, it highlights the field with a red border.
     * If all fields are valid, the input data is added to the lists and the fields are cleared.
     * After validation, the current page is closed, and the premises and conclusion are displayed together.
     */
    @FXML
    private void handleValidation() {
        Reponse rep = this.poly.valider();
        this.resultStruct.setText(rep.getMessage());

    }


    private void handle() {
        // Flag to check if all inputs are valid
        boolean valid = true;

        // Check if the first term is empty, highlight if invalid
        if (premierterme.getText().isEmpty()) {
            //premierterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        } else {
            premierterme.setStyle(null);
        }

        // Check if the second term is empty, highlight if invalid
        if (deuxiemeterme.getText().isEmpty()) {
            //deuxiemeterme.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        } else {
            deuxiemeterme.setStyle(null);
        }

        // Check if the quantifier is selected, highlight if invalid
        if (quantificateurs.getValue().isEmpty()) {
            //quantificateurs.setStyle("-fx-border-color: #F15C5C ; -fx-border-radius: 5px;");
            valid = false;
        } else {
            quantificateurs.setStyle(null);
        }

        // If all fields are valid, process the data
        if (valid) {
            this.booleans.add(this.Negative.isSelected());
            // Get text from the TextFields and the selected quantifier
            String premierTermeText = premierterme.getText();
            String deuxiemeTermeText = deuxiemeterme.getText();
            String selectedQuantificateur = quantificateurs.getValue();

            // Add the premise data to the lists
            first.add(premierTermeText);
            second.add(deuxiemeTermeText);
            quant.add(selectedQuantificateur);

            // Clear the input fields after adding the data
            premierterme.clear();
            deuxiemeterme.clear();
            quantificateurs.setValue(null);
            this.structureValid.setDisable(false);
        }



    }


    public boolean universel(String quantif)
    {
        for(String univ : this.quantiflistUniv ) {
            if (univ.equals(quantif)) {
                return true;
            }
        }
        return false ;

    }

    /**
     * This method is triggered when the "Validate Structure" button is clicked.
     * It enables the "Done" button and disables the "Validate Structure" button.
     * This allows the user to proceed with submitting the valid structure.
     */
    @FXML
    private void handleValidStructure() {

        this.handle();
        // Log message indicating that the structure validation button was clicked
        System.out.println("bouton validation structure");

        // Enable the "Done" button and disable the "Validate Structure" button
        doneButton.setDisable(true);
        structureValid.setDisable(true);

        Polysyllogisme poly = new Polysyllogisme();
        int length = this.first.size() ;
        System.out.println("LE LENGTH  : "+ length);

        System.out.println("LE LENGTH  : "+ this.second.size());

        System.out.println("LE quantif  : "+ this.quant.size());


        List<Proposition> propositions = new ArrayList<>();
        for(int i =0 ; i < length-1 ; i++ ){
            String firstTerm = this.first.get(i);
            String secondTerm = this.second.get(i);
            Quantificateur quantificateur = new Quantificateur(this.quant.get(i), universel(this.quant.get(i)));
            boolean isAffirmative = true;

            Proposition p = new Proposition(firstTerm, secondTerm, quantificateur, this.booleans.get(i));

            propositions.add(p);
        }
        String firstTerm = this.first.get(length-1);
        String secondTerm = this.second.get(length-1);
        Quantificateur quantificateur = new Quantificateur(this.quant.get(length-1), true); // Create a new Quantificateur using the value from 'quant' at index 'length'
        boolean isAffirmative = true;



        Proposition conclusion = new Proposition(firstTerm, secondTerm, quantificateur, this.booleans.get(length-1));

        poly.setPremises(propositions);
        poly.setConclusion(conclusion);
        this.poly = poly;
        if(this.poly.structValid())
        {
            this.doneButton.setDisable(false);
            this.resultStruct.setText("La structure est correcte ! Nous pouvons maintenant procéder à la validation des règles.");
        }
        else {
            if(this.poly.structCorrection()) {
                this.resultStruct.setText("Nous avons corrigé votre structure Visualiser le résultat à gauche ! Nous pouvons maintenant procéder à la validation des règles.");
                Double x = 5.0;
                Double y = 190.0;
                for(Proposition p : poly.getPremises())
                {
                    this.createLabelBelow(this.anchor , x , y , p.toString());
                    y+=30.0;
                }
                this.createLabelBelow(this.anchor , x , y , poly.getConclusion().toString());
                this.doneButton.setDisable(false);


            }
            else
                this.resultStruct.setText("Impossible de corriger la structure revoyez votre polysyllogisme ");
        }




    }

    public void createLabelBelow(AnchorPane parentPane, double layoutX, double layoutY, String text) {
        // Crée un nouveau label
        Label newLabel = new Label(text);

        // Positionne le label 10px en dessous du label d'origine
        newLabel.setLayoutX(layoutX); // Même position horizontale
        newLabel.setLayoutY(layoutY);
        // Position verticale + 10px
        newLabel.setTextFill(javafx.scene.paint.Color.WHITE); // Couleur blanche
        // Ajoute le label au pane parent
        parentPane.getChildren().add(newLabel);
    }

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