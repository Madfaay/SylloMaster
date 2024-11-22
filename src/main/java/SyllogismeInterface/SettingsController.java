package SyllogismeInterface;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class SettingsController {
    public Text titleText;
    public Menu quantiMenu;
    @FXML
    private ComboBox<String> languageComboBox;
    public TextField champ;
    public Button button;
    public RadioButton universel;
    public RadioButton existentiel;
    public GridPane grid;
    @FXML
    private Menu languageMenu;
    @FXML
    private Menu helpMenu;




    @FXML
    private Label sortie ;
    @FXML
    private Button deleteButton; // Bouton Supprimer

    @FXML
    public Label back ;

    public AnchorPane anchorPane ;
    public GridPane gridmove;
    public Label nouveau;
    public ImageView ear;
    private Clip heartbeatClip;

    private String selectedQuantif;

    int rowCountExist = 1 ;
    int rowCountUniv = 1 ;

    private String langue ;


    @FXML
    private ToggleGroup group;

    private final File languageFile = new File("language.json");


    private void setBackgroundImage(String relativePath) {
        String imageUrl = Paths.get("src", "IMAGES", relativePath).toUri().toString();
        Image image = new Image(imageUrl);

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void setImageView(ImageView imageView, String relativePath) {
        String imageUrl = Paths.get("src", "IMAGES", relativePath).toUri().toString();
        imageView.setImage(new Image(imageUrl));
    }

    @FXML
    void initialize() {
        loadLanguageFromJson();
        sortie.setVisible(false);

        setImageView(this.ear, "ear.png"); // Chargement simplifié de l'image d'oreille
        playHeartbeatSound();
        // startHeartbeatAnimation();

        nouveau.setVisible(false);
        retrieveAndClassifyData();
        configureButtonEvents();
        configureRadioButtons();
        setBackgroundImage("backgroundSettings.jpg"); // Définir l'image de fond

        if ("English".equals(this.langue)) {
            translateLabelsToEnglish();
        }

        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(this.langue)) {
                saveLanguageToJson(newValue);
            }
        });
    }

    private void configureButtonEvents() {
        button.setOnMouseClicked(this::handleLabelClick);
        back.setOnMouseClicked(this::handleLabelClick);
        deleteButton.setOnMouseClicked(this::handleLabelClick);
        addZoomEffect(back);
    }

    private void configureRadioButtons() {
        group = new ToggleGroup();
        existentiel.setToggleGroup(group);
        universel.setToggleGroup(group);
        group.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                selectedQuantif = ((RadioButton) newToggle).getText();
            }
        });
    }


    private void handleSupprimer(String delete) {
        System.out.println("Supprimer bouton cliqué");

        // Exemple : Efface le contenu du champ de texte et du label
        champ.clear();
        nouveau.setText("");
        this.deleteQuantif(delete);
        // Ajoutez ici la logique spécifique de suppression si nécessaire

    }

    private void saveLanguageToJson(String language) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("language", language);
        data.put("effect", true); // Add effect to the JSON data

        try {
            // Écriture de la langue et de l'effet dans le fichier JSON
            mapper.writeValue(languageFile, data);
            System.out.println("Langue et effet enregistrés dans language.json : " + language + ", " + true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde de la langue et de l'effet.");
        }

        recoverAcceuil();
    }
    /**
     * Méthode pour charger la langue enregistrée depuis le fichier JSON.
     * Définit la langue de la ComboBox si une langue est enregistrée.
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
                    languageComboBox.setValue(savedLanguage);
                    this.langue = savedLanguage;
                    System.out.println("Langue chargée depuis language.json : " + savedLanguage);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors du chargement de la langue.");
            }
        }
    }


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

    // Méthode appelée lors du clic sur le bouton
    private void handleLabelClick(MouseEvent event) {
        // Vérifiez d'abord le type d'objet cliqué
        if (!(event.getSource() instanceof Label || event.getSource() instanceof Button)) {
            return;  // Ignore si ce n'est ni un Label ni un Button
        }

        String quantif = this.champ.getText();
        System.out.println("le champ à ajouter ou supprimer " + quantif);

        // Si le label cliqué est "back", gérer l'événement de retour et quitter la méthode
        if (event.getSource() instanceof Label label) {
            // Cas où c'est un Label, par exemple `back`
            if (label.equals(back)) {
                System.out.println("on est la");
                recoverAcceuil();
                return;
            }
        }

        if(event.getSource() instanceof Button) {


            Button but = (Button) event.getSource();
            if(but.equals(deleteButton)) {
                handleSupprimer(quantif);
            }
            else {
                // Vérifier si un quantificateur a été sélectionné et si le champ n'est pas vide
                if (this.selectedQuantif == null || quantif.isEmpty()) {
                    System.out.println("Erreur : Veuillez sélectionner un quantificateur et remplir le champ.");
                    return;
                }


                // Créer une Map pour stocker les deux valeurs
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("selectedQuantif", this.selectedQuantif);  // Quantificateur sélectionné
                dataMap.put("quantif", quantif);  // Texte entré par l'utilisateur

                System.out.println("Données à sauvegarder : " + dataMap);

                // Appeler une méthode pour sauvegarder dans un fichier JSON
                try {
                    saveDataToJson(dataMap, quantif);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    }



    // Méthode pour sauvegarder les données dans un fichier JSON
    private void saveDataToJson(Map<String, String> data, String quantif) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("data.json");

        // Charger les données existantes du fichier
        List<Map<String, String>> dataList = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            dataList = mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        }

        // Vérifier si le quantificateur est déjà présent
        boolean exists = dataList.stream()
                .anyMatch(existingData ->
                        existingData.get("selectedQuantif").equals(this.selectedQuantif) &&
                                existingData.get("quantif").equals(quantif)
                );

        if (exists) {
            System.out.println("Erreur : Le quantificateur '" + quantif + "' existe déjà pour le type '" + this.selectedQuantif + "'.");
            return;  // Arrêtez la méthode si le quantificateur existe déjà
        }

        // Ajouter les nouvelles données après vérification de l'absence de doublon
        dataList.add(data);
        if ("Universal".equals(selectedQuantif) || "Universel".equals(selectedQuantif)) {
            System.out.println("laaaaaaaaaaaaaa 0" + selectedQuantif);
            addData(0, quantif, this.rowCountUniv);
        } else {
            System.out.println("laaaaaaaaaaaaaa 1 "  + selectedQuantif);
            addData(1, quantif, this.rowCountExist);
        }

        // Sauvegarder la liste complète dans le fichier
        mapper.writeValue(file, dataList);
        this.nouveau.setText(quantif);
        this.nouveau.setVisible(true);
        moveLabelAcrossGrid();
    }

    // Méthode pour récupérer et classer les données selon Universel ou Existentiel
    @FXML
    private void retrieveAndClassifyData() {
        try {
            // Lire les données depuis le fichier JSON
            List<Map<String, String>> dataList = loadData();

            // Deux listes pour stocker les données classées
            List<Map<String, String>> universelData = new ArrayList<>();
            List<Map<String, String>> existentielData = new ArrayList<>();

            // Parcourir les données et les classer
            for (Map<String, String> data : dataList) {
                String selectedQuantif = data.get("selectedQuantif");
                String quantif = data.get("quantif");

                if(this.langue.equals("English")) {
                    if ("Universal".equals(selectedQuantif)) {
                        System.out.println("l339");
                        universelData.add(data);
                        addData(0, quantif, this.rowCountUniv);
                    }  if ("Existential".equals(selectedQuantif)) {
                        existentielData.add(data);
                        addData(1, quantif, this.rowCountExist);

                    }
                }

                else {
                    if ("Universel".equals(selectedQuantif)) {
                        universelData.add(data);
                        addData(0, quantif, this.rowCountUniv);
                    }  if ("Existentiel".equals(selectedQuantif)) {
                        existentielData.add(data);
                        addData(1, quantif, this.rowCountExist);

                    }
                }

            }

            // Afficher les données classées
            System.out.println("Données Universel : " + universelData);
            System.out.println("Données Existentiel : " + existentielData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les données depuis le fichier JSON
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


    private void addData(int colonne, String quantif, int rowCount) {
        // Créer un nouveau label pour le quantificateur
        Label quantifLabel = new Label(quantif);
        quantifLabel.setStyle("-fx-font-size: 18; -fx-text-fill: #000000;");

        // Ajouter le label dans la bonne colonne et à la ligne indiquée par rowCount
        this.grid.add(quantifLabel, colonne, rowCount);

        // Incrémenter le compteur de lignes selon la colonne (0 pour Universel, 1 pour Existentiel)
        if (colonne == 0) {
            this.rowCountUniv++;
            System.out.println("le row countUnive : " + this.rowCountUniv);
        } else {
            this.rowCountExist++;
            System.out.println("le rowCountExist : " + this.rowCountExist);

        }
    }


    public void moveLabelAcrossGrid() {
        // Assurez-vous que `nouveau` est bien ajouté à `gridmove` avant de démarrer l'animation
        if (!gridmove.getChildren().contains(nouveau)) {
            gridmove.add(nouveau, 7, 0); // Ajouter le label à `gridmove` si ce n'est pas déjà fait
        }

        // Réinitialiser la position initiale de `nouveau` à la colonne 7, ligne 0
        GridPane.setColumnIndex(nouveau, 7);
        GridPane.setRowIndex(nouveau, 0);
        nouveau.setVisible(true); // Rendre le label visible au début

        Timeline timeline = new Timeline();
        int durationMillis = 100; // Temps d'attente en millisecondes entre chaque mouvement

        // Mouvement horizontal initial vers la colonne 0
        for (int col = 7; col >= 0; col--) {
            int finalCol = col;
            KeyFrame moveAcross = new KeyFrame(Duration.millis((7 - col) * durationMillis), e -> {
                GridPane.setColumnIndex(nouveau, finalCol);
                nouveau.setRotate(0);  // Horizontal pendant le mouvement vers la gauche
            });
            timeline.getKeyFrames().add(moveAcross);
        }

        // Mouvement vertical vers le bas jusqu'à la ligne 17 (une ligne de plus)
        for (int row = 0; row <= 17; row++) { // Notez que nous avons augmenté la limite à 17
            int finalRow = row;
            KeyFrame moveDown = new KeyFrame(Duration.millis((7 + row + 1) * durationMillis), e -> {
                GridPane.setRowIndex(nouveau, finalRow);
                nouveau.setRotate(90);  // Tourner de 90 degrés pour le mouvement vertical
            });
            timeline.getKeyFrames().add(moveDown);
        }

        // Retourner à l'orientation normale (0°) une fois arrivé en (0,17)
        KeyFrame resetRotation = new KeyFrame(Duration.millis((7 + 17 + 1) * durationMillis), e -> {
            nouveau.setRotate(0); // Remettre à l'horizontale
        });
        timeline.getKeyFrames().add(resetRotation);

        // Mouvement horizontal vers la droite jusqu'à la colonne 3, en bas
        for (int col = 0; col <= 4; col++) {
            int finalCol = col;
            KeyFrame moveRight = new KeyFrame(Duration.millis((7 + 17 + col + 2) * durationMillis), e -> {
                GridPane.setColumnIndex(nouveau, finalCol);
            });
            timeline.getKeyFrames().add(moveRight);
        }

        // Rendre le label invisible à la fin de l'animation
        KeyFrame hideLabel = new KeyFrame(Duration.millis((7 + 17 + 4) * durationMillis), e -> {
            nouveau.setVisible(false); // Cacher le label après le dernier mouvement
        });
        timeline.getKeyFrames().add(hideLabel);

        // Lancer l'animation
        timeline.setCycleCount(1); // Exécuter l'animation une seule fois
        timeline.play();
    }




    private void startHeartbeatAnimation() {
        // Variables pour les dimensions de base et d'agrandissement
        double initialScale = 1.0;
        double beatScale = 1.1; // Taille légèrement plus grande pour l'effet de battement
        int beatDuration = 500; // Durée d'un battement en millisecondes (500ms = 0.5s pour agrandir, 0.5s pour réduire)

        // Timeline pour l'animation de battement
        Timeline heartbeat = new Timeline(
                // Etape pour agrandir l'image
                new KeyFrame(Duration.millis(beatDuration), e -> {
                    ear.setScaleX(beatScale);
                    ear.setScaleY(beatScale);
                }),
                // Etape pour réduire l'image
                new KeyFrame(Duration.millis(beatDuration * 2), e -> {
                    ear.setScaleX(initialScale);
                    ear.setScaleY(initialScale);
                })
        );

        // Animation continue
        heartbeat.setCycleCount(Timeline.INDEFINITE);
        heartbeat.play();
    }


    private void playHeartbeatSound() {
        try {
            // Chemin du fichier audio de battement de cœur
            Path relativePath = Paths.get("src", "son", "hb.wav");
            File audioFile = new File(relativePath.toAbsolutePath().toString());

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            heartbeatClip = AudioSystem.getClip(); // Initialiser la variable d'instance
            heartbeatClip.open(audioStream);

            // Jouer le son depuis le début
            heartbeatClip.setFramePosition(0);
            heartbeatClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recoverAcceuil() {
        try {
            // Arrêter le son avant de changer d'écran
            if (heartbeatClip != null && heartbeatClip.isRunning()) {
                heartbeatClip.stop(); // Arrêter le clip audio
            }

            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent acceuilContent = fxmlLoader.load();

            // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
            anchorPane.getChildren().setAll(acceuilContent);

            HelloController controller = fxmlLoader.getController();
            controller.setEffect(true);


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


    public void deleteQuantif(String quantifToDelete) {
        try {
            // Charger les données existantes depuis le fichier JSON
            List<Map<String, String>> dataList = loadData();

            // Filtrer les données pour exclure le quantificateur spécifié
            dataList.removeIf(data ->
                    data.get("quantif").equals(quantifToDelete)
            );

            // Sauvegarder la liste mise à jour dans le fichier JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("data.json"), dataList);

            // Mettre à jour l'interface utilisateur en supprimant le label du quantificateur
            removeQuantifFromGrid(quantifToDelete);
            System.out.println("Quantificateur '" + quantifToDelete + "' supprimé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du quantificateur.");
        }
    }

    // Méthode pour supprimer le label du quantificateur de la GridPane
    private void removeQuantifFromGrid(String quantifToDelete) {
        // Trouver et supprimer le label correspondant au quantificateur dans la GridPane
        for (Node node : grid.getChildren()) {
            if (node instanceof Label label && label.getText().equals(quantifToDelete)) {
                this.sortie.setText(quantifToDelete);
                this.sortie.setVisible(true);
                grid.getChildren().remove(label);// Ajouter le label à `gridmove` si ce n'est pas déjà fait
                this.animateLabel();
                deleteQuantif(quantifToDelete);
                break;  // Arrêter après la première suppression pour éviter des erreurs de modification de liste
            }
        }
    }


    // Méthode pour animer le label 'sortie'
    public void animateLabel() {
        // Enregistrer la position initiale du label
        double initialX = sortie.getLayoutX();
        double initialY = sortie.getLayoutY();

        // Étape de mouvement
        double stepX = 3.0; // Ajuster ce paramètre pour un mouvement plus à droite
        double stepY = 9.0; // Ajuster ce paramètre pour un mouvement plus haut ou plus bas
        int steps = 50; // Nombre d'étapes pour le mouvement diagonal

        // Calcul des positions cibles
        double targetX = initialX + (steps * stepX); // Mouvement vers la droite
        double targetY = initialY - (steps * stepY); // Mouvement vers le haut

        // Animation diagonale vers le haut et la droite
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), sortie);
        moveUp.setToX(targetX); // Déplacer à droite
        moveUp.setToY(targetY); // Déplacer vers le haut

        // Action à exécuter après la fin de l'animation de montée
        moveUp.setOnFinished(event -> {
            // Animation de chute
            TranslateTransition fall = new TranslateTransition(Duration.seconds(1), sortie);
            fall.setToY(initialY + 400); // Chute vers le bas

            // Action à exécuter après la chute
            fall.setOnFinished(fallEvent -> {
                // Créer un nouveau label
                sortie = new Label("Sortie");
                GridPane.setColumnIndex(sortie, 3);
                GridPane.setRowIndex(sortie, 14);
                sortie.setStyle("-fx-text-fill: #f20000; -fx-font-size: 15px;");
                sortie.setVisible(false);

                // Ajouter le nouveau label au GridPane
                gridmove.getChildren().add(sortie);
            });

            fall.play(); // Démarrer l'animation de chute
        });

        moveUp.play(); // Démarrer l'animation de montée
    }


    public void translateLabelsToFrench() {
        back.setText("Retour");
        sortie.setText("Sortie");
        button.setText("Ajouter");
        deleteButton.setText("Supprimer");
        helpMenu.setText("aide");
        quantiMenu.setText("Quantificateurs");
        languageMenu.setText("Langues");

        universel.setText("Universel");
        existentiel.setText("Existentiel");
        // Assuming you have a title text element
        titleText.setText("Ajouter un quantificateur");

        // Translate menu items
        for (String menuItem : languageComboBox.getItems()) {
            if (menuItem.equals("Close")) {
                menuItem = ("Fermer");
            } else if (menuItem.equals("About")) {
                menuItem = ("À propos");
            }
        }

        // If you have other labels or buttons to translate, continue here...
    }

    public void translateLabelsToEnglish() {
        back.setText("Back");
        sortie.setText("Exit");
        button.setText("Add");
        deleteButton.setText("Delete");
        helpMenu.setText("Help");
        quantiMenu.setText("Quantifiers");
        languageMenu.setText("Languages");
        universel.setText("Universal");
        existentiel.setText("Existential");

        // Assuming you have a title text element
        titleText.setText("Add a Quantifier");

        // Translate menu items
        for (int i = 0; i < languageComboBox.getItems().size(); i++) {
            String menuItem = languageComboBox.getItems().get(i);
            if (menuItem.equals("Fermer")) {
                languageComboBox.getItems().set(i, "Close");
            } else if (menuItem.equals("À propos")) {
                languageComboBox.getItems().set(i, "About");
            }
        }

        // If you have other labels or buttons to translate, continue here...
    }



}
