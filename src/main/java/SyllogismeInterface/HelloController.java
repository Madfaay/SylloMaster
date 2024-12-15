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

    /**
     * Sets the background image for the AnchorPane.
     *
     * This method:
     * 1. Converts the image path into a URI format.
     * 2. Creates an Image object from the URI.
     * 3. Sets the background size to cover the entire AnchorPane.
     * 4. Ensures the image doesn't repeat and is centered.
     * 5. Applies the background to the AnchorPane.
     *
     * @param chemin The file path of the image to be used as the background.
     */
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

    /**
     * Hides the UI elements related to the application parameters.
     */
    protected void hideParams() {
        start.setVisible(false);
        settings.setVisible(false);
        quit.setVisible(false);
        whatsyllo.setVisible(false);
        credits.setVisible(false);

    }

    /**
     * Hides the title-related UI elements.
     *
     * This method hides the following title elements:
     * - Syllogism Master button
     * - Syllogism Understand button
     */
    protected void hideTitle() {
        sylloMaster.setVisible(false);
        sylloUnderstand.setVisible(false);

    }

    /**
     * Initializes the application interface when the class is loaded.
     *
     * This method:
     * - Sets the background image from a relative path.
     * - Loads language and effect settings from a JSON file.
     * - Updates UI labels based on the selected language (French or English).
     * - Sets up click event handlers for several UI elements (e.g., buttons, labels).
     * - Adds a zoom effect to several UI elements for visual feedback.
     */
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
    }

    /**
     * Handles the click event for various labels in the user interface.
     * Depending on the label clicked, different actions are performed, such as:
     * - Changing the background image.
     * - Showing or hiding UI elements.
     * - Navigating to different parts of the app.
     *
     * @param event The MouseEvent triggered by the label click.
     */
    private void handleLabelClick(MouseEvent event) {
        // Get the label that was clicked
        Label clickedLabel = (Label) event.getSource();

        Path relativePath;

        // Handle the 'start' label click
        if (clickedLabel.equals(start)) {
            System.out.println("Start label clicked!");
            polyOrShow(); // Show the poly or syllogism options
        }
        // Handle the 'settings' label click
        else if (clickedLabel.equals(settings)) {
            System.out.println("Settings label clicked!");
            showSettingsInSamePane(); // Show settings in the same pane
        }
        // Handle the 'credits' label click
        else if (clickedLabel.equals(credits)) {
            System.out.println("Credits label clicked!");
            hideParams(); // Hide the parameters
            hideTitle();  // Hide the title
            // Set the background image for credits
            relativePath = Paths.get("src", "IMAGES", "Credits.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
            back.setVisible(true); // Show the back button
        }
        // Handle the 'whatsyllo' label click
        else if (clickedLabel.equals(whatsyllo)) {
            System.out.println("WhatSyllo label clicked!");
            relativePath = Paths.get("src", "IMAGES", "syllocours.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString());
            // Show various course-related elements
            syllogismeCours.setVisible(true);
            polysyllogismeCours.setVisible(true);
            qualiteCours.setVisible(true);
            reglescours.setVisible(true);
            propos.setVisible(true);
            quantiteCours.setVisible(true);
            FiguresCours.setVisible(true);
            back.setVisible(true); // Show the back button
        }
        // Handle the 'quit' label click
        else if (clickedLabel.equals(quit)) {
            System.out.println("Quit label clicked!");
            Platform.exit(); // Exit the application
        }
        // Handle the 'syllogismeCours' label click
        else if (clickedLabel.equals(syllogismeCours)) {
            System.out.println("SyllogismeCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "syllo.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours", "Syllogisme.jpg");

            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for syllogism course
        }
        // Handle the 'polysyllogismeCours' label click
        else if (clickedLabel.equals(polysyllogismeCours)) {
            System.out.println("PolysyllogismeCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn","poly.jpg");
            else
                relativePath = Paths.get("src", "IMAGES","Cours","Polysyllogisme.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for polysyllogism course
        }
        // Handle the 'qualiteCours' label click
        else if (clickedLabel.equals(qualiteCours)) {
            System.out.println("QualiteCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "Qualite.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours" ,"Qualite.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for quality course
        }
        // Handle the 'quantiteCours' label click
        else if (clickedLabel.equals(quantiteCours)) {
            System.out.println("QuantiteCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "Quantificateurs.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours", "Quantificateurs.jpg");

            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for quantity course
        }
        // Handle the 'reglescours' label click
        else if (clickedLabel.equals(reglescours)) {
            System.out.println("ReglesCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "Regles.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours", "Regles.jpg");

            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for rules course
        }
        // Handle the 'propos' label click
        else if (clickedLabel.equals(propos)) {
            System.out.println("Propos label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title]
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "TypeDeprop.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours", "TypeDeprop.jpg");
            setBackgroundImage(relativePath.toAbsolutePath().toString()); // Set background for propositions course
        }
        // Handle the 'FiguresCours' label click
        else if (clickedLabel.equals(FiguresCours)) {
            System.out.println("FiguresCours label clicked!");
            hideParams(); // Hide parameters
            hideTitle();  // Hide title
            if(this.language.equals("English"))
                relativePath = Paths.get("src", "IMAGES", "CoursEn", "quatreFigures.jpg");
            else
                relativePath = Paths.get("src", "IMAGES", "Cours", "quatreFigures.jpg");

            setBackgroundImage(relativePath.toAbsolutePath().toString());
            // Set background for figures course
        }
        // Handle the 'back' label click
        else if (clickedLabel.equals(back)) {
            System.out.println("Back label clicked!");
            recoverAcceuil(); // Go back to the home screen
        }
    }

    /**
     * Adds a zoom effect to a label when the mouse hovers over it.
     * The label will scale up when the mouse enters, and return to its normal size when the mouse exits.
     *
     * @param label The label to which the zoom effect will be applied.
     */
    private void addZoomEffect(Label label) {
        // When the mouse enters the label, increase its size (zoom in)
        label.setOnMouseEntered(event -> {
            label.setScaleX(1.2); // Increase the size on the X axis
            label.setScaleY(1.2); // Increase the size on the Y axis
        });

        // When the mouse exits the label, reset its size to normal
        label.setOnMouseExited(event -> {
            label.setScaleX(1); // Reset the size on the X axis
            label.setScaleY(1); // Reset the size on the Y axis
        });
    }

    private void showSettingsInSamePane() {
        HelloApplication.getPageManager().goToPage("settings");
    }

    private void polyOrShow() {
        HelloApplication.getPageManager().goToPage("selection");
    }

    private void recoverAcceuil()
    {
        // Reset background image
        Path relativePath = Paths.get("src", "IMAGES", "background.jpg");
        setBackgroundImage(relativePath.toAbsolutePath().toString());

        // Set all menu's button visible
        start.setVisible(true);
        settings.setVisible(true);
        quit.setVisible(true);
        whatsyllo.setVisible(true);
        credits.setVisible(true);

        // Set visible title
        sylloMaster.setVisible(true);
        sylloUnderstand.setVisible(true);

        // Hide courses element
        syllogismeCours.setVisible(false);
        polysyllogismeCours.setVisible(false);
        propos.setVisible(false);
        reglescours.setVisible(false);
        qualiteCours.setVisible(false);
        quantiteCours.setVisible(false);
        FiguresCours.setVisible(false);

        // Hide back button
        back.setVisible(false);

        System.out.println("Retour à l'accueil effectué.");
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
        reglescours.setText("Rules");
        polysyllogismeCours.setText("PolySyllogism");
        syllogismeCours.setText("Syllogism");
        qualiteCours.setText("Quality");
        quantiteCours.setText("Quantity");

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