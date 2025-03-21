package SyllogismeInterface;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import traitement.Polysyllogism;
import traitement.Proposition;
import traitement.Quantifier;
import traitement.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**Polysyllogism controller e */

public class PolyController {
    /**
     * AnchorPane for organizing UI components.
     */
    public AnchorPane anchor;

    /**
     * CheckBox for selecting a negative option.
     */
    public CheckBox Negative;

    /**
     * Button to navigate back.
     */
    public Button btnBack;

    /**
     * Label for the second term or concept.
     */
    public Label labelDeuxieme;

    /**
     * Label for the first term or concept.
     */
    public Label labelPremier;

    /**
     * Button to navigate to the last step, linked to FXML.
     */
    @FXML
    private Button goToLastOne;

    /**
     * Button for handling the premise step, linked to FXML.
     */
    @FXML
    private Button premisse;

    /**
     * Button to navigate back to the previous step, linked to FXML.
     */
    @FXML
    private Button goToBack;

    /**
     * Button to navigate back to the summary, linked to FXML.
     */
    @FXML
    private Button goToBackSum;

    /**
     * Button to validate the structure of the syllogism, linked to FXML.
     */
    @FXML
    private Button structureValid;

    /**
     * Button to indicate that the action is completed, linked to FXML.
     */
    @FXML
    private Button doneButton;

    /**
     * Button to validate the entire structure, linked to FXML.
     */
    @FXML
    private Button validateStructure;

    /**
     * Label for displaying the main title, linked to FXML.
     */
    @FXML
    private Label titre;

    /**
     * Label to display the result of the structure validation, linked to FXML.
     */
    @FXML
    private Label resultStruct;

    /**
     * TextField for entering the first term of the syllogism.
     */
    @FXML
    private TextField premierterme;

    /**
     * TextField for entering the second term of the syllogism.
     */
    @FXML
    private TextField deuxiemeterme;

    /**
     * ComboBox for selecting quantifiers.
     */
    @FXML
    private ComboBox<String> quantificateurs;

    /**
     * TextField for entering the first part of the conclusion.
     */
    @FXML
    private TextField firstConclusion;

    /**
     * TextField for entering the second part of the conclusion.
     */
    @FXML
    private TextField secondConclusion;

    /**
     * ComboBox for selecting quantifiers for the conclusion.
     */
    @FXML
    private ComboBox<String> quantifConclusion;

    /**
     * VBox container for displaying the premises dynamically.
     */
    @FXML
    private VBox premissesContainer = new VBox();

    /**
     * Label to display the current number of premises.
     */
    @FXML
    private Label number;

    /**
     * Counter for the number of premises, initialized to 1.
     */
    private int nbpremise = 1;

    /**
     * List of strings representing the first set of terms in the syllogism.
     */
    private ArrayList<String> first = new ArrayList<>();

    /**
     * List of strings representing the second set of terms in the syllogism.
     */
    private ArrayList<String> second = new ArrayList<>();

    /**
     * List of strings representing quantifiers.
     */
    private ArrayList<String> quant = new ArrayList<>();

    /**
     * List of booleans representing specific conditions or states for the syllogism.
     */
    private ArrayList<Boolean> booleans = new ArrayList<>();

    /**
     * List of strings representing universal quantifiers.
     */
    private ArrayList<String> quantiflistUniv = new ArrayList<>();

    /**
     * List of strings representing existential quantifiers.
     */
    private ArrayList<String> quantiflistExist = new ArrayList<>();

    /**
     * Counter for the current page, initialized to 1.
     */
    private int pageCounter = 1;

    /**
     * File pointing to the language configuration file (language.json).
     */
    private final File languageFile = new File("language.json");

    /**
     * Current language as a string.
     */
    private String language;

    /**
     * Instance of the Polysyllogism class to manage complex syllogisms.
     */
    private Polysyllogism poly;

    /**
     * This method is called when the controller is initialized.
     * It sets the initial values for the number label and the ComboBoxes.
     */
    @FXML
    public void initialize() {
        loadLanguageFromJson();
        retrieve();

        if(this.language.equals("English")){
            System.out.println("language 106");
            translateLabelsToEnglish();
        }
        structureValid.setDisable(true);
        // Set the number label text to the current premise number and increment it
        if (this.number != null)
            number.setText(Integer.toString(nbpremise));

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

    public void translateLabelsToEnglish() {
        // Traduction des Labels
        if (titre != null) titre.setText("Premise");
        labelPremier.setText("First term");
        labelDeuxieme.setText("Second term");

        // Traduction des Buttons
        if (premisse != null) premisse.setText("Add one more");
        if (goToLastOne != null) goToLastOne.setText("Add conclusion");
        if (doneButton != null) doneButton.setText("Validation of rules");
        if (structureValid != null) structureValid.setText("Structure Validation");
        if (btnBack != null) btnBack.setText("Back");
        this.Negative.setText("negatif");
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

    /**
     * Loads data from the JSON file "data.json" and returns it as a list of maps.
     * Each map represents a set of key-value pairs from the JSON data.
     *
     * If the file does not exist or is empty, an empty list is returned.
     *
     * @return a List of Maps where each map contains key-value pairs representing the data.
     *         Returns an empty list if the file is missing or contains no data.
     *
     * @throws IOException if an error occurs while reading the file.
     *
     * This method performs the following steps:
     * 1. Checks if the file "data.json" exists and has content (non-zero length).
     * 2. If the file is valid, uses Jackson's ObjectMapper to parse the JSON content
     *    into a list of maps.
     * 3. If the file does not exist or is empty, returns an empty list.
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
            this.booleans.add(!this.Negative.isSelected());
            // Update premise number and page counter
            nbpremise+=1 ;
            number.setText(Integer.toString(nbpremise));
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
            this.booleans.add(!this.Negative.isSelected());
            nbpremise++;

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
     * This method is triggered when the "Done" button is clicked to validate the input.
     * It checks if all the required fields (premierterme, deuxiemeterme, and quantificateurs) are filled.
     * If any field is empty, it highlights the field with a red border.
     * If all fields are valid, the input data is added to the lists and the fields are cleared.
     * After validation, the current page is closed, and the premises and conclusion are displayed together.
     */
    @FXML
    private void handleValidation() {
        Response rep = this.poly.validate();
        this.resultStruct.setText(rep.getMessage());
        this.resultStruct.setLayoutX(this.resultStruct.getLayoutX() +100.);


    }



    /**
     * Handles input validation and processes data from the form fields.
     *
     * This method checks if all input fields are valid:
     * - Ensures the first term, second term, and quantifier fields are not empty.
     * - Highlights invalid fields with a specific border style.
     *
     * If all inputs are valid:
     * - Adds the input data to respective lists (`first`, `second`, `quant`).
     * - Stores the negation status based on the `Negative` checkbox.
     * - Clears the input fields and enables the "structureValid" button.
     */

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
            this.booleans.add(!this.Negative.isSelected());
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


    /**
     * Checks if the given quantifier is in the list of universal quantifiers.
     *
     * @param quantif the quantifier to check.
     * @return true if the quantifier exists in the universal quantifiers list, false otherwise.
     *
     * This method also prints the universal quantifiers list for debugging purposes.
     */

    public boolean universel(String quantif)
    {
        System.out.println("les liste de universel");
        for(String univ : this.quantiflistUniv ) {
            System.out.println(univ);
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

        Polysyllogism poly = new Polysyllogism(this.language);
        int length = this.first.size() ;
        System.out.println("LE LENGTH  : "+ length);

        System.out.println("LE LENGTH  : "+ this.second.size());

        System.out.println("LE quantif  : "+ this.quant.size());
        this.afficherListes();


        List<Proposition> propositions = new ArrayList<>();
        for(int i =0 ; i < length-1 ; i++ ){
            String firstTerm = this.first.get(i);
            String secondTerm = this.second.get(i);
            String quantif = this.quant.get(i);

            Quantifier quantifier = new Quantifier(this.quant.get(i), universel(this.quant.get(i)));

            Proposition p = new Proposition(firstTerm, secondTerm, quantifier, this.booleans.get(i));

            propositions.add(p);
        }
        String firstTerm = this.first.get(length-1);
        String secondTerm = this.second.get(length-1);
        Quantifier quantifier = new Quantifier(this.quant.get(length-1), true); // Create a new Quantificateur using the value from 'quant' at index 'length'
        boolean isAffirmative = true;



        Proposition conclusion = new Proposition(firstTerm, secondTerm, quantifier, this.booleans.get(length-1));

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
                this.resultStruct.setText("Nous avons corrigé votre structure Visualiser le résultat à droit ! Nous pouvons maintenant procéder à la validation des règles.");
                this.resultStruct.setLayoutX(this.resultStruct.getLayoutX() -100.);
                Double x = 950.0;
                Double y = 190.0;
                for(Proposition p : poly.getPremises())
                {
                    this.createLabelBelow(this.anchor , x , y , p.toString());
                    y+=30.0;
                }
                this.createLabelBelow(this.anchor , x , y , poly.getConclusion().toString());
                this.doneButton.setDisable(false);


            }
            else {
                this.resultStruct.setText("Impossible de corriger la structure revoyez votre polysyllogisme ");
                this.resultStruct.setLayoutX(this.resultStruct.getLayoutX() - 30.);
            }


        }




    }

    /**
     * Creates a new label below the specified position and adds it to the given AnchorPane.
     *
     * @param parentPane the parent AnchorPane to which the label will be added.
     * @param layoutX the horizontal position of the label.
     * @param layoutY the vertical position above which the label will be placed slightly below.
     * @param text the text to display in the label.
     *
     * This method:
     * - Creates a label with the specified text.
     * - Positions the label slightly below the provided Y-coordinate.
     * - Applies styles for better visibility (white text, larger font, bold).
     * - Adds the label to the parent AnchorPane.
     */
    public void createLabelBelow(AnchorPane parentPane, double layoutX, double layoutY, String text) {
        // Create a new label with the specified text
        Label newLabel = new Label(text);

        // Set the label's position
        newLabel.setLayoutX(layoutX); // Same horizontal position
        newLabel.setLayoutY(layoutY + 10); // Slightly below the vertical position

        // Apply styles to enhance visibility
        newLabel.setTextFill(javafx.scene.paint.Color.WHITE); // White text
        newLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"); // Larger and bold text

        // Add the label to the parent container
        parentPane.getChildren().add(newLabel);
    }


    /**
     * Retrieves data from the JSON file and categorizes it into two lists based on the quantifier type.
     *
     * This method:
     * - Reads data from the JSON file using the `loadData` method.
     * - Iterates through the data and sorts quantifiers into two lists: `quantiflistUniv` for universal quantifiers
     *   and `quantiflistExist` for existential quantifiers.
     * - Handles quantifier classification based on the current language setting (English or French).
     *
     * If an error occurs while reading the file, the stack trace is printed for debugging purposes.
     */
    private void retrieve() {
        try {
            // Read data from the JSON file
            List<Map<String, String>> dataList = loadData();

            // Iterate through the data and classify quantifiers
            for (Map<String, String> data : dataList) {
                String selectedQuantif = data.get("selectedQuantif");
                String quantif = data.get("quantif");

                if (this.language.equals("English")) {
                    if ("Universal".equals(selectedQuantif)) {
                        quantiflistUniv.add(quantif);
                    }
                    if ("Existential".equals(selectedQuantif)) {
                        quantiflistExist.add(quantif);
                    }
                } else {
                    if ("Universel".equals(selectedQuantif)) {
                        quantiflistUniv.add(quantif);
                    }
                    if ("Existentiel".equals(selectedQuantif)) {
                        quantiflistExist.add(quantif);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Handles the "Back" button action to navigate to the previous state or page.
     *
     * This method:
     * - Checks the number of premises (`nbpremise`) to determine whether to enable or disable the "Go to Last One" button.
     * - If there are multiple premises, it restores the last entered values and adjusts the interface accordingly:
     *   - Updates the fields (`premierterme`, `deuxiemeterme`, `quantificateurs`) and the negation state (`Negative`).
     *   - Reverts the title from "Conclusion" to "Prémisse" and translates labels if the language is set to English.
     *   - Decrements the premise counter (`nbpremise`) and updates the displayed number.
     * - If there are no more premises, it navigates back to the previous page using `PageManager`.
     *
     * @param actionEvent the action event triggered by the button click.
     */
    @FXML
    public void back(ActionEvent actionEvent) {
        if(this.nbpremise>=3)
        {
            goToLastOne.setDisable(false);
        }
        else
        {
            goToLastOne.setDisable(true);

        }
        if(this.nbpremise >1)
        {
            if(this.titre.getText().equals("Conclusion" )) {
                this.titre.setText("Prémisse");
                if(this.language.equals("English")) {
                    translateLabelsToEnglish();
                }
                this.premisse.setDisable(false);
                this.structureValid.setDisable(true);
            }
            String terme1 = this.first.getLast();
            String terme2 = this.second.getLast();
            String quantif = this.quant.getLast();
            boolean boolo = this.booleans.getLast();
            premierterme.setText(terme1);
            deuxiemeterme.setText(terme2);
            quantificateurs.setValue(quantif);
            Negative.setSelected(boolo);

            first.removeLast();
            second.removeLast();
            quant.removeLast();
            booleans.removeLast();
            this.nbpremise-=1 ;
            System.out.println(this.nbpremise);
            number.setText(Integer.toString(nbpremise));


        }

        else
        {
            HelloApplication.getPageManager().goBack();
        }
    }
}