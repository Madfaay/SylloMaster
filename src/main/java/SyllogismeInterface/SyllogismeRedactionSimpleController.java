package SyllogismeInterface;

import traitement.Quantificateur;
import traitement.Reponse;
import traitement.Syllogisme;
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
    public Label labelTitle;
    public Button actionVerif;
    public Button btnBack;
    public Button btnSwitch;
    public Label labelTypeFigure;
    public Label labelSubject;
    public MenuItem menuItemConclusion_2;
    public MenuItem menuItemConclusion_1;
    public Label labelConclusion;
    public MenuItem menuItemPremise2_2;
    public MenuItem menuItemPremise2_1;
    public Label labelPremise2;
    public MenuItem menuItemPremise1_1;
    public MenuItem menuItemPremise1_2;
    public Label labelPremise1;
    public Label mypredicateConclusionLabel;
    public Label mymediumTermLabel;
    public Button mytypeFigureLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

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

    List<String> quantiflistUniv = new ArrayList<>();
    List<String> quantiflistExist = new ArrayList<>();

    List<Boolean> reglelist = new ArrayList<>();

    @FXML
    MenuButton textPremise1;
    @FXML
    MenuButton textPremise2;
    @FXML
    MenuButton textConclusion;
    @FXML
    TextField mysubject;
    @FXML
    TextField mypredicatConclusion;
    @FXML
    TextField mymediumTerm;
    @FXML
    TextField mytypeFigure;
    @FXML
    CheckBox mynegatifPremise1;
    @FXML
    CheckBox mynegatifPremise2;
    @FXML
    CheckBox mynegatifConclusion;
    @FXML
    CheckBox myhypothesis;
    @FXML
    Label myTextValid;
    @FXML
    CheckBox myregleMediumTerm, myregleLatus, myrNN, myrN, myrAA, myrPP, myrP, myrUU;

    public String language ;

    private final File languageFile = new File("language.json");

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

    @FXML
    public void switchToSyllogismeRedaction(ActionEvent event) throws IOException {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyllogismeRedaction.fxml"));
            Parent acceuilContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
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
    public void switchToTypeFigure(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TypeFigure.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Type Figure");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void recoverPremise1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise1 = menuItem.getText();
        textPremise1.setText(quantifPremise1);
    }

    @FXML
    public void recoverPremise2(ActionEvent event){
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise2 = menuItem.getText();
        textPremise2.setText(quantifPremise2);
    }

    @FXML
    public void recoverConclusion(ActionEvent event){
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifConclusion = menuItem.getText();
        textConclusion.setText(quantifConclusion);
    }


    @FXML
    public void actionVerif(ActionEvent event) throws IOException {
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

        reglelist.add(myregleMediumTerm.isSelected());
        reglelist.add(myregleLatus.isSelected());
        reglelist.add(myrNN.isSelected());
        reglelist.add(myrN.isSelected());
        reglelist.add(myrAA.isSelected());
        reglelist.add(myrPP.isSelected());
        reglelist.add(myrP.isSelected());
        reglelist.add(myrUU.isSelected());

        boolean q1univ = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifPremise1)) {
                q1univ = true;
                break;
            }
        }
        Quantificateur q1 = new Quantificateur(quantifPremise1, q1univ);

        boolean q2univ = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifPremise2)) {
                q2univ = true;
                break;
            }
        }
        Quantificateur q2 = new Quantificateur(quantifPremise2, q1univ);

        boolean qCuniv = false ;
        for (String quantif : quantiflistUniv){
            if (quantif.equals(quantifConclusion)) {
                qCuniv = true;
                break;
            }
        }
        Quantificateur qC = new Quantificateur(quantifConclusion, q1univ);

        Syllogisme syllo = new Syllogisme(q1,q2,qC,subject,predicatConclusion,mediumTerm,!negatifPremise1,!negatifPremise2,!negatifConclusion,typeFigure );

        Reponse r = syllo.valider();
        if (r.getConclusion() == null)
            myTextValid.setText(r.getMessage());
        else
            myTextValid.setText(r.getMessage() + " " + r.getConclusion());
        myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

    }

    public void setLabelsToFrench() {
        // Titre principal
        labelTitle.setText("Rédaction d'un syllogisme");

        // Premise 1
        textPremise1.setText("Quantificateur");
        // textPremise1.getItems().get(0).setText("Tous");
        //textPremise1.getItems().get(1).setText("Aucun");
        mynegatifPremise1.setText("Négatif");

        // Premise 2
        textPremise2.setText("Quantificateur");
        mynegatifPremise2.setText("Négatif");

        // Conclusion
        textConclusion.setText("Quantificateur");
        //textConclusion.getItems().get(0).setText("Tous");
        //textConclusion.getItems().get(1).setText("Aucun");
        mynegatifConclusion.setText("Négatif");

        // Autres éléments
        labelSubject.setText("Sujet");
        mypredicateConclusionLabel.setText("Prédicat de la conclusion");
        mymediumTermLabel.setText("Terme moyen");
        mytypeFigureLabel.setText("Type de la figure");

        // Boutons et autres cases à cocher
        actionVerif.setText("Vérification");
        myhypothesis.setText("Hypothèse d'existence");
        btnBack.setText("Retour");
        btnSwitch.setText("->");
        labelPremise1.setText("Premisse n°1");
        labelPremise2.setText("Premisse n°2");

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
