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

public class PolyController {
    public AnchorPane anchor;
    public CheckBox Negative;
    public Button btnBack;
    public Label labelDeuxieme;
    public Label labelPremier;
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

    private Polysyllogism poly ;


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
    public void createLabelBelow(AnchorPane parentPane, double layoutX, double layoutY, String text) {
        // Crée un nouveau label avec le texte spécifié
        Label newLabel = new Label(text);

        // Définit la position du label
        newLabel.setLayoutX(layoutX); // Même position horizontale
        newLabel.setLayoutY(layoutY + 10); // Position verticale légèrement en dessous

        // Applique des styles pour améliorer la visibilité
        newLabel.setTextFill(javafx.scene.paint.Color.WHITE); // Texte en blanc
        newLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"); // Texte plus grand et gras

        // Ajoute le label au conteneur parent
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