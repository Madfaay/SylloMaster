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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyllogismeRedactionController {
    public Label labelTitle;
    public Button btnBack;
    public Button btnSwitch;
    public Label premisse1;
    @FXML
    private AnchorPane pane;

    String quantifPremise1;
    String subjectPremise1;
    String predicatPremise1;
    Boolean negatifPremise1;

    String quantifPremise2;
    String subjectPremise2;
    String predicatPremise2;
    Boolean negatifPremise2;

    String quantifConclusion;
    String subjectConclusion;
    String predicatConclusion;
    Boolean negatifConclusion;

    RadioButton buttonSubject;
    RadioButton buttonPredicat;

    Boolean hypothesis;

    List<String> quantiflistExist = new ArrayList<>();
    List<String> quantiflistUniv = new ArrayList<>();
    List<String> reglelist = new ArrayList<>();

    @FXML
    MenuButton myquantifPremise1;
    @FXML
    TextField mysubjectPremise1;
    @FXML
    TextField mypredicatPremise1;
    @FXML
    CheckBox mynegatifPremise1;
    @FXML
    HBox myHBoxsubjectPremise1;
    @FXML
    HBox myHBoxpredicatPremise1;

    @FXML
    Label myTextValid;

    @FXML
    MenuButton myquantifPremise2;
    @FXML
    RadioButton mysubjectPremise2Subject;
    @FXML
    RadioButton mysubjectPremise2Predicat;
    @FXML
    RadioButton mysubjectPremise2New;
    @FXML
    TextField mysubjectPremise2;
    @FXML
    RadioButton mypredicatPremise2Subject;
    @FXML
    RadioButton mypredicatPremise2Predicat;
    @FXML
    RadioButton mypredicatPremise2New;
    @FXML
    TextField mypredicatPremise2;
    @FXML
    CheckBox mynegatifPremise2;
    @FXML
    HBox myHBoxquantifPremise2;
    @FXML
    HBox myHBoxsubjectPremise2;
    @FXML
    HBox myHBoxpredicatPremise2;
    @FXML
    ToggleGroup subject;
    @FXML
    ToggleGroup predicat;

    @FXML
    MenuButton myquantifConclusion;
    @FXML
    TextField mysubjectConclusion;
    @FXML
    TextField mypredicatConclusion;
    @FXML
    CheckBox mynegatifConclusion;
    @FXML
    HBox myHBoxquantifConclusion;
    @FXML
    HBox myHBoxsubjectConclusion;
    @FXML
    HBox myHBoxpredicatConclusion;

    @FXML
    CheckBox myhypothesis;
    @FXML
    Button myverif;
    @FXML
    CheckBox myregleMediumTerm, myregleLatus, myrNN, myrN, myrAA, myrPP, myrP, myrUU;

    private final File languageFile = new File("language.json");

    private String language ;

    @FXML
    void initialize()
    {
        loadLanguageFromJson();
        if(!this.language.equals("English")) {
            System.out.println("laaaaaaaaaaa");
            setLabelsToFrench();
        }

        retrieve();

        int taille = quantiflistExist.size();
        for (int i = 0; i < taille; i++) {
            MenuItem quantif = new MenuItem(quantiflistExist.get(i));
            quantif.setOnAction(this::recoverPremise1);
            MenuItem quantif2 = new MenuItem(quantiflistExist.get(i));
            quantif2.setOnAction(this::recoverPremise2);
            MenuItem quantif3 = new MenuItem(quantiflistExist.get(i));
            quantif3.setOnAction(this::recoverConclusion);
            myquantifPremise1.getItems().add(quantif);
            myquantifPremise2.getItems().add(quantif2);
            myquantifConclusion.getItems().add(quantif3);
        }
        int t = quantiflistUniv.size();
        for (int i = 0; i < t; i++) {
            MenuItem quantif = new MenuItem(quantiflistUniv.get(i));
            quantif.setOnAction(this::recoverPremise1);
            MenuItem quantif2 = new MenuItem(quantiflistUniv.get(i));
            quantif2.setOnAction(this::recoverPremise2);
            MenuItem quantif3 = new MenuItem(quantiflistUniv.get(i));
            quantif3.setOnAction(this::recoverConclusion);
            myquantifPremise1.getItems().add(quantif);
            myquantifPremise2.getItems().add(quantif2);
            myquantifConclusion.getItems().add(quantif3);
        }


    }

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
    public void recoverPremise1(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise1 = menuItem.getText();
        myquantifPremise1.setText(quantifPremise1);
        myHBoxsubjectPremise1.setDisable(false);
    }

    @FXML
    public void nextSubjectPremise1(ActionEvent event) throws IOException {
        myHBoxpredicatPremise1.setDisable(false);
        subjectPremise1 = mysubjectPremise1.getText();
    }

    @FXML
    public void nextPredicatPremise1(ActionEvent event) throws IOException {
        myHBoxquantifPremise2.setDisable(false);
        predicatPremise1 = mypredicatPremise1.getText();
    }

    @FXML
    public void recoverPremise2(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifPremise2 = menuItem.getText();
        myquantifPremise2.setText(quantifPremise2);
        myHBoxsubjectPremise2.setDisable(false);
    }

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

    @FXML
    public void nextSubjectPremise2(ActionEvent event) throws IOException {
        myHBoxpredicatPremise2.setDisable(false);
        mysubjectConclusion.setText(mysubjectPremise2.getText());
        subjectConclusion = mysubjectConclusion.getText();
        subjectPremise2 = mysubjectPremise2.getText();
    }

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

    @FXML
    public void nextPredicatPremise2(ActionEvent event) throws IOException {
        myHBoxquantifConclusion.setDisable(false);
        mypredicatConclusion.setText(mypredicatPremise2.getText());
        predicatConclusion = mypredicatPremise2.getText();
        predicatPremise2 = mypredicatPremise2.getText();
    }

    @FXML
    public void recoverConclusion(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        quantifConclusion = menuItem.getText();
        myquantifConclusion.setText(quantifConclusion);

        myHBoxsubjectConclusion.setDisable(false);
        myHBoxpredicatConclusion.setDisable(false);
        myverif.setDisable(false);

    }

    @FXML
    public void actionVerif(){
        negatifPremise1 = mynegatifPremise1.isSelected();
        negatifPremise2 = mynegatifPremise2.isSelected();
        negatifConclusion = mynegatifConclusion.isSelected();

        hypothesis = myhypothesis.isSelected();

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

        Syllogisme syllo = new Syllogisme(  q1,subjectPremise1,predicatPremise1, !negatifPremise1,
                q2,subjectPremise2,predicatPremise2, !negatifPremise2,
                qC, subjectConclusion, predicatConclusion, !negatifConclusion );

        Reponse r = syllo.valider();
        if (r.getConclusion() == null)
            myTextValid.setText(r.getMessage());
        else
            myTextValid.setText(r.getMessage() + " " + r.getConclusion());
        myTextValid.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
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
        ((Label) myHBoxsubjectPremise2.getChildren().get(0)).setText("Sujet");
        mysubjectPremise2Subject.setText("Sujet");
        mysubjectPremise2Predicat.setText("Prédicat");
        mysubjectPremise2New.setText("Nouveau");
        ((Label) myHBoxpredicatPremise2.getChildren().get(0)).setText("Prédicat");
        mypredicatPremise2Subject.setText("Sujet");
        mypredicatPremise2Predicat.setText("Prédicat");
        mypredicatPremise2New.setText("Nouveau");

        // Conclusion
        myquantifConclusion.setText("Quantificateur");
        mynegatifConclusion.setText("Négatif");

        // Sujet et prédicat pour la conclusion
        ((Label) myHBoxsubjectConclusion.getChildren().get(0)).setText("Sujet");
        ((Label) myHBoxpredicatConclusion.getChildren().get(0)).setText("Prédicat");

        // Boutons et autres éléments
        myverif.setText("Vérification");
        myhypothesis.setText("Hypothèse d'existence");
        btnBack.setText("Retour");
        btnSwitch.setText("->");
        premisse1.setText("Premisse n°1");
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
