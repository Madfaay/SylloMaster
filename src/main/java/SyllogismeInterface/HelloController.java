package SyllogismeInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HelloController {
    public Label settings;
    public Label credits;
    public Label start;
    public Label whatsyllo;
    public Label quit;

    public Label syllogismeCours;
    public Label polysyllogismeCours;
    public Label propos;
    public Label reglescours;
    public Label qualiteCours;
    public Label quantiteCours;
    public Label FiguresCours;

    public Text sylloMaster;
    public Text sylloUnderstand;

    public String language ;

    public boolean effect ;

    @FXML
    public Label back ;

    @FXML
    private AnchorPane archor;

    @FXML
    private ImageView imageView;

    private final File languageFile = new File("language.json");


    /******     Pour les langues        *********/

    @FXML
    private ComboBox<String> languageComboBox = new ComboBox<>(); // ComboBox pour la sélection de la langue

    //Getters


    private void setBackgroundImage(String chemin) {
        Path imagePath = Paths.get(chemin);
        String imageUrl = imagePath.toUri().toString(); // Convertir le chemin en URL

        BackgroundSize backgroundSize = new BackgroundSize(
                100,  // largeur à 100%
                100,  // hauteur à 100%
                true, // couverture de la largeur
                true, // couverture de la hauteur
                false, // ne pas conserver les proportions
                true  // ne pas limiter aux dimensions du BorderPane
        );
        Image image = new Image(imageUrl); // Utiliser l'URL pour charger l'image
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,    // Pas de répétition horizontale
                BackgroundRepeat.NO_REPEAT,    // Pas de répétition verticale
                BackgroundPosition.CENTER,     // Position centrée
                backgroundSize                 // Applique la taille définie
        );
        Background background = new Background(backgroundImage);
        archor.setBackground(background);
    }



    protected void hideParams()
    {
        start.setVisible(false);
        settings.setVisible(false);
        quit.setVisible(false);
        whatsyllo.setVisible(false);
        credits.setVisible(false);

    }

    protected void hideTitle()
    {
        sylloMaster.setVisible(false);
        sylloUnderstand.setVisible(false);

    }


    @FXML
    protected void initialize() {
        //String path = "\\src\\IMAGES\\background.png";
        Path relativePath = Paths.get("src", "IMAGES", "background.jpg");
        setBackgroundImage(relativePath.toAbsolutePath().toString());
        loadLanguageFromJson();
        loadEffectFromJson();
        System.out.println(effect);
        if(!Objects.equals(this.language, "English")) {
            setLabelsToFrench();
            if(effect)
                updateEffectInJson();
        }
        else if(effect){
            setLabelsToEnglish();
            updateEffectInJson();}






        // Associer les labels à la méthode handleLabelClick pour les clics
        syllogismeCours.setVisible(false);
        polysyllogismeCours.setVisible(false);
        propos.setVisible(false);
        qualiteCours.setVisible(false);
        quantiteCours.setVisible(false);
        FiguresCours.setVisible(false);
        reglescours.setVisible(false);
        back.setVisible(false);

        back.setOnMouseClicked(this::handleLabelClick);
        start.setOnMouseClicked(this::handleLabelClick);
        settings.setOnMouseClicked(this::handleLabelClick);
        credits.setOnMouseClicked(this::handleLabelClick);
        whatsyllo.setOnMouseClicked(this::handleLabelClick);
        quit.setOnMouseClicked(this::handleLabelClick);
        syllogismeCours.setOnMouseClicked(this::handleLabelClick);
        polysyllogismeCours.setOnMouseClicked(this::handleLabelClick);
        propos.setOnMouseClicked(this::handleLabelClick);
        qualiteCours.setOnMouseClicked(this::handleLabelClick);
        quantiteCours.setOnMouseClicked(this::handleLabelClick);
        FiguresCours.setOnMouseClicked(this::handleLabelClick);
        reglescours.setOnMouseClicked(this::handleLabelClick);


        // Associer les labels aux méthodes pour l'effet de zoom
        addZoomEffect(start);
        addZoomEffect(settings);
        addZoomEffect(credits);
        addZoomEffect(whatsyllo);
        addZoomEffect(quit);
        addZoomEffect(syllogismeCours);
        addZoomEffect(polysyllogismeCours);
        addZoomEffect(propos);
        addZoomEffect(qualiteCours);
        addZoomEffect(quantiteCours);
        addZoomEffect(FiguresCours);
        addZoomEffect(reglescours);
        addZoomEffect(back);

        /*******        LANGUES            *******/

        /*
        // Configuration du ComboBox pour la sélection de la langue
        languageComboBox.getItems().addAll("English", "French");
        languageComboBox.setValue(LanguageManager.getLocale().getDisplayLanguage());

        // Appliquer la langue courante lors de l'initialisation
        LanguageManager.updateLabelsHello(this);

        languageComboBox.setOnAction(event -> {
            switchLanguage(languageComboBox.getValue());
        });
         */


    }



    // Méthode unique pour gérer tous les clics sur les labels
    private void handleLabelClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource(); // Obtenir le label cliqué

        Path relativePath;
        if (clickedLabel.equals(start)) {
            System.out.println("Start label clicked!");
            polyOrShow();
            // Action pour 'start'
        } else if (clickedLabel.equals(settings)) {
            System.out.println("Settings label clicked!");
            showSettingsInSamePane();
            // Action pour 'settings'
        } else if (clickedLabel.equals(credits)) {
            System.out.println("Credits label clicked!");
            this.hideParams();
            this.hideTitle();
            relativePath = Paths.get("src", "IMAGES", "Credits.jpg");
            this.setBackgroundImage(relativePath.toAbsolutePath().toString());
            back.setVisible(true);
        } else if (clickedLabel.equals(whatsyllo)) {



            relativePath = Paths.get("src", "IMAGES", "syllocours.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
            this.syllogismeCours.setVisible(true);
            this.polysyllogismeCours.setVisible(true);
            this.qualiteCours.setVisible(true);
            this.reglescours.setVisible(true);
            this.propos.setVisible(true);
            this.quantiteCours.setVisible(true);
            this.FiguresCours.setVisible(true);
            System.out.println("WhatSyllo label clicked!");
            this.back.setVisible(true);

            // Action pour 'whatsyllo'
        } else if (clickedLabel.equals(quit)) {
            System.out.println("Quit label clicked!");
            Platform.exit(); // Quitter l'application
        } else if (clickedLabel.equals(syllogismeCours)) {
            hideParams();
            hideTitle();
            System.out.println("syllogismeCours label clicked!");
            relativePath = Paths.get("src", "IMAGES","Cours", "Syllogisme.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());

        }
        else if (clickedLabel.equals(polysyllogismeCours)) {
            System.out.println("pollysylogisme label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "Polysyllogisme.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }

        else if (clickedLabel.equals(qualiteCours)) {
            System.out.println("syllogismeCours label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "Qualite.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }
        else if (clickedLabel.equals(quantiteCours)) {
            System.out.println("syllogismeCours label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "Quantificateurs.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }

        else if (clickedLabel.equals(reglescours)) {
            System.out.println("syllogismeCours label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "Regles.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }
        else if (clickedLabel.equals(propos)) {
            System.out.println("syllogismeCours label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "TypeDeprop.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }

        else if (clickedLabel.equals(FiguresCours)) {
            System.out.println("syllogismeCours label clicked!");
            hideParams();
            hideTitle();
            relativePath = Paths.get("src", "IMAGES","Cours", "quatreFigures.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
        }
        else if (clickedLabel.equals(back)) {
            System.out.println("back label clicked!");
            recoverAcceuil();
            // Action pour 'start'
        }
    }



    // Méthode pour ajouter l'effet de zoom lors du survol du curseur
    private void addZoomEffect(Label label) {
        // Quand la souris entre dans le label, on l'agrandit
        label.setOnMouseEntered(event -> {
            label.setScaleX(1.2); // Agrandir la taille sur l'axe X
            label.setScaleY(1.2); // Agrandir la taille sur l'axe Y
        });

        // Quand la souris quitte le label, on remet la taille d'origine
        label.setOnMouseExited(event -> {
            label.setScaleX(1); // Revenir à la taille normale
            label.setScaleY(1); // Revenir à la taille normale
        });
    }

    private void showSettingsInSamePane() {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quantificateurs.fxml"));
            Parent settingsContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            archor.getChildren().setAll(settingsContent);
            // Optionnel : ajustez la position et la taille du contenu ajouté
            AnchorPane.setTopAnchor(settingsContent, 0.0);
            AnchorPane.setBottomAnchor(settingsContent, 0.0);
            AnchorPane.setLeftAnchor(settingsContent, 0.0);
            AnchorPane.setRightAnchor(settingsContent, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void polyOrShow() {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("poly-or-syllogisme.fxml"));
            Parent polyorsylloContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            archor.getChildren().clear(); // Efface les éléments existants
            archor.getChildren().add(polyorsylloContent); // Ajoute l'interface des paramètres

            // Optionnel : ajustez la position et la taille du contenu ajouté
            AnchorPane.setTopAnchor(polyorsylloContent, 0.0);
            AnchorPane.setBottomAnchor(polyorsylloContent, 0.0);
            AnchorPane.setLeftAnchor(polyorsylloContent, 0.0);
            AnchorPane.setRightAnchor(polyorsylloContent, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recoverAcceuil()
    {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent acceuilContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            archor.getChildren().setAll(acceuilContent);

            // Optionnel : ajustez la position et la taille du contenu ajouté
            AnchorPane.setTopAnchor(acceuilContent, 0.0);
            AnchorPane.setBottomAnchor(acceuilContent, 0.0);
            AnchorPane.setLeftAnchor(acceuilContent, 0.0);
            AnchorPane.setRightAnchor(acceuilContent, 0.0);
            this.back.setVisible(false);


        } catch (IOException e) {
            e.printStackTrace();
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

    public void typewriterEffect(Label label, String text) {
        label.setText(""); // Efface le texte actuel
        final int[] index = {0}; // Pour suivre la position dans le texte

        // Vérifie que le texte n'est pas vide avant de commencer
        if (text == null || text.isEmpty()) {
            return; // Si le texte est vide, sort de la méthode
        }

        // Déclarer la variable timeline
        Timeline timeline = new Timeline();

        // Créer une nouvelle KeyFrame
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event -> {
            // Ajouter le prochain caractère au texte du label
            label.setText(label.getText() + text.charAt(index[0]));
            index[0]++;

            // Si on arrive à la fin du texte, arrêter l'animation
            if (index[0] >= text.length()) {
                timeline.stop(); // Utiliser l'instance timeline ici
                System.out.println("Animation stoppée");
            }
        });

        // Ajouter la KeyFrame à la Timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter jusqu'à l'arrêt manuel
        timeline.play(); // Démarrer l'animation
    }



    public void setLabelsToFrench() {
        applyLabelEffect(back, "Retour");
        applyLabelEffect(whatsyllo, "Qu'est-ce qu'un syllogisme");
        applyLabelEffect(start, "Commencer l'aventure");
        applyLabelEffect(settings, "Paramètres");
        applyLabelEffect(credits, "Crédits");
        applyLabelEffect(quit, "Quitter");
        applyLabelEffect(syllogismeCours, "Syllogisme");
        applyLabelEffect(polysyllogismeCours, "Polysyllogisme");
        applyLabelEffect(FiguresCours, "4 Figures");
        applyLabelEffect(quantiteCours, "Quantité");
        applyLabelEffect(qualiteCours, "Qualité");
        applyLabelEffect(reglescours, "Règles");
        applyLabelEffect(propos, "Propositions");
        sylloUnderstand.setText("Comprendre & Valider Syllogisme-Polysyllogisme");
        credits.setLayoutX(1050);
        quit.setLayoutX(1050);
    }

    public void setLabelsToEnglish() {
        applyLabelEffect(back, "Back");
        applyLabelEffect(whatsyllo, "What's a syllogism");
        applyLabelEffect(start, "Start the adventure");
        applyLabelEffect(settings, "Settings");
        applyLabelEffect(credits, "Credits");
        applyLabelEffect(quit, "Quit");
        applyLabelEffect(syllogismeCours, "Syllogism");
        applyLabelEffect(polysyllogismeCours, "PolySyllogism");
        applyLabelEffect(FiguresCours, "4 Figures");
        applyLabelEffect(quantiteCours, "Quantity");
        applyLabelEffect(qualiteCours, "Quality");
        applyLabelEffect(reglescours, "Rules");
        applyLabelEffect(propos, "Propositions");
    }

    // Helper method to apply typewriter effect conditionally
    private void applyLabelEffect(Label label, String text) {
        if (effect) {
            typewriterEffect(label, text);
        } else {
            label.setText(text);
        }
    }

    public void setEffect(boolean effect)
    {
        this.effect = effect;
    }

    private void loadEffectFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Lire les données du fichier JSON
            Map<String, Object> data = mapper.readValue(new File("language.json"), Map.class);
            if (data.containsKey("effect")) {
                effect = (Boolean) data.get("effect");
                // Utilisez la valeur de 'effect' selon vos besoins
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la lecture de l'effet depuis le fichier JSON.");
        }
    }

    public void updateEffectInJson() {
        ObjectMapper mapper = new ObjectMapper();
        File languageFile = new File("language.json");

        if (languageFile.exists()) {
            try {
                // Lire les données actuelles depuis le fichier JSON
                Map<String, Object> data = mapper.readValue(languageFile, Map.class);

                // Mettre à jour la valeur de "effect" à false
                data.put("effect", false);

                // Écrire les nouvelles données dans le fichier JSON
                mapper.writeValue(languageFile, data);
                System.out.println("Mise à jour de l'effet à false réussie.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la mise à jour de l'effet dans le fichier JSON.");
            }
        } else {
            System.out.println("Le fichier language.json n'existe pas.");
        }
    }
}