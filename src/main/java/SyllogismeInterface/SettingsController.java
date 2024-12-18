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
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**Controller to add quantifiers and changing language*/

public class SettingsController {
    /**
     * Text element for displaying the title.
     */
    public Text titleText;

    /**
     * Menu for selecting quantifiers.
     */
    public Menu quantiMenu;

    /**
     * ComboBox for selecting a language, linked to FXML.
     */
    @FXML
    private ComboBox<String> languageComboBox;

    /**
     * TextField for user input.
     */
    public TextField champ;

    /**
     * Button for performing an action, such as validation or submission.
     */
    public Button button;

    /**
     * RadioButton for selecting the "universal" option.
     */
    public RadioButton universel;

    /**
     * RadioButton for selecting the "existential" option.
     */
    public RadioButton existentiel;

    /**
     * GridPane for organizing UI components in a grid layout.
     */
    public GridPane grid;

    /**
     * Menu for selecting the language, linked to FXML.
     */
    @FXML
    private Menu languageMenu;

    /**
     * Menu for accessing help options, linked to FXML.
     */
    @FXML
    private Menu helpMenu;

    /**
     * Label for displaying output or feedback, linked to FXML.
     */
    @FXML
    private Label sortie;

    /**
     * Button for deleting an item, labeled "Delete" in the UI.
     */
    @FXML
    private Button deleteButton;

    /**
     * Label for navigating back in the application, linked to FXML.
     */
    @FXML
    public Label back;

    /**
     * AnchorPane for organizing the main layout.
     */
    public AnchorPane anchorPane;

    /**
     * GridPane for managing dynamic grid movements or layouts.
     */
    public GridPane gridmove;

    /**
     * Label for displaying a new element or status.
     */
    public Label nouveau;

    /**
     * ImageView for displaying an ear icon or related image.
     */
    public ImageView ear;

    /**
     * Audio clip for playing a heartbeat sound.
     */
    private Clip heartbeatClip;

    /**
     * String representing the selected quantifier.
     */
    private String selectedQuantif;

    /**
     * Counter for rows associated with existential quantifiers, initialized to 1.
     */
    int rowCountExist = 1;

    /**
     * Counter for rows associated with universal quantifiers, initialized to 1.
     */
    int rowCountUniv = 1;

    /**
     * String representing the current language.
     */
    private String langue;

    /**
     * ToggleGroup for managing radio button selections, linked to FXML.
     */
    @FXML
    private ToggleGroup group;

    /**
     * File object pointing to the language configuration file (language.json).
     */
    private final File languageFile = new File("language.json");

    private void reloadFPages() {

        try {
            HelloApplication.getPageManager().reloadPage("mode", "SyllogismeRedactionSimple.fxml");
            HelloApplication.getPageManager().reloadPage("mode2", "SyllogismeRedaction.fxml");
            HelloApplication.getPageManager().reloadPage("poly", "PremissePage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Sets the background image for the anchorPane.
     *
     * @param relativePath The relative path to the image file in the project (e.g., "background.jpg").
     */
    private void setBackgroundImage(String relativePath) {
        // Construct the image URL from the relative path
        String imageUrl = Paths.get("src", "IMAGES", relativePath).toUri().toString();

        // Create an Image object with the image URL
        Image image = new Image(imageUrl);

        // Set up the background image properties (no repeat, centered, full size)
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        // Apply the background image to the anchorPane
        anchorPane.setBackground(new Background(backgroundImage));
    }

    /**
     * Sets an image for the given ImageView.
     *
     * @param imageView The ImageView to display the image.
     * @param relativePath The relative path to the image file (e.g., "image.jpg").
     */
    private void setImageView(ImageView imageView, String relativePath) {
        // Construct the image URL from the relative path
        String imageUrl = Paths.get("src", "IMAGES", relativePath).toUri().toString();

        // Set the image for the provided ImageView
        imageView.setImage(new Image(imageUrl));
    }

    /**
     * Initializes the user interface elements when the scene is loaded.
     * This method is called automatically by the FXMLLoader when the FXML file is loaded.
     */
    @FXML
    void initialize() {
        // Load the language settings from a JSON file
        loadLanguageFromJson();

        // Hide the "sortie" element (probably a button or label)
        sortie.setVisible(false);

        // Load the "ear.png" image into the ear ImageView
        setImageView(this.ear, "ear.png");
        startHeartbeatAnimation();

        // Optionally, start a heartbeat animation (commented out)
        // startHeartbeatAnimation();

        // Hide the "nouveau" element (probably a button or label)
        nouveau.setVisible(false);

        // Retrieve and classify data (could be for processing or displaying information)
        retrieveAndClassifyData();

        // Configure button event handlers
        configureButtonEvents();

        // Configure the behavior of radio buttons
        configureRadioButtons();

        // Set the background image of the UI
        setBackgroundImage("backgroundSettings.jpg");

        // If the language is set to "English", translate labels to English
        if ("English".equals(this.langue)) {
            translateLabelsToEnglish();
        }

        // Add a listener to the language combo box to detect language changes
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(this.langue)) {
                saveLanguageToJson(newValue);
            }
        });
    }

    /**
     * Configures the event handlers for button interactions.
     * This method sets up mouse click events for buttons and applies visual effects.
     */
    private void configureButtonEvents() {
        // Set the click event handler for the "button" element
        button.setOnMouseClicked(this::handleLabelClick);

        // Set the click event handler for the "back" element
        back.setOnMouseClicked(this::handleLabelClick);

        // Set the click event handler for the "deleteButton" element
        deleteButton.setOnMouseClicked(this::handleLabelClick);

        // Apply a zoom effect to the "back" button for visual feedback on interaction
        addZoomEffect(back);
    }

    /**
     * Configures the radio button group and sets up a listener to track the selected option.
     * This method ensures that only one radio button is selected at a time and updates the selected value.
     */
    private void configureRadioButtons() {
        // Create a new ToggleGroup to group the radio buttons together
        group = new ToggleGroup();

        // Assign the radio buttons to the same ToggleGroup
        existentiel.setToggleGroup(group);
        universel.setToggleGroup(group);

        // Add a listener to detect when the selected radio button changes
        group.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            // If a new radio button is selected, update the selectedQuantif variable with its text
            if (newToggle != null) {
                selectedQuantif = ((RadioButton) newToggle).getText();
            }
        });
    }

    /**
     * Handles the deletion action when the "Supprimer" button is clicked.
     * This method clears certain fields and triggers additional deletion logic.
     *
     * @param delete The identifier or value to be deleted (e.g., an item name or ID).
     */
    private void handleSupprimer(String delete) {
        // Print a message indicating that the delete button was clicked
        System.out.println("Supprimer bouton cliqué");

        // Clear the content of the "champ" text field
        champ.clear();

        // Set the "nouveau" label text to an empty string
        nouveau.setText("");

        // Call the deleteQuantif method to perform the specific deletion logic
        this.deleteQuantif(delete);

        // Add any additional specific logic for deletion here if necessary
    }

    /**
     * Saves the selected language and effect status to a JSON file.
     * This method stores the language preference and a fixed "effect" value into a JSON file.
     *
     * @param language The selected language to be saved (e.g., "English", "French").
     */
    private void saveLanguageToJson(String language) {
        // Create an ObjectMapper to work with JSON data
        ObjectMapper mapper = new ObjectMapper();

        // Create a map to hold the language and effect data
        Map<String, Object> data = new HashMap<>();
        data.put("language", language);  // Store the selected language
        data.put("effect", true);        // Store the effect value as true (could represent some setting)

        try {
            // Write the data to a JSON file
            mapper.writeValue(languageFile, data);

            // Print confirmation that the language and effect were saved successfully
            System.out.println("Langue et effet enregistrés dans language.json : " + language + ", " + true);
        } catch (IOException e) {
            // Handle any errors that occur during file writing
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde de la langue et de l'effet.");
        }

        Map<String, String> fxmlPaths = new HashMap<>();
        fxmlPaths.put("home", "hello-view.fxml");
        fxmlPaths.put("settings", "quantificateurs.fxml");
        fxmlPaths.put("selection", "poly-or-syllogisme.fxml");
        fxmlPaths.put("first_redaction", "SyllogismeRedactionSimple.fxml");
        fxmlPaths.put("mode", "SyllogismeRedactionSimple.fxml");
        fxmlPaths.put("mode2", "SyllogismeRedaction.fxml");
        fxmlPaths.put("poly", "PremissePage.fxml");

        try {
            // Rechargement de toutes les pages en passant le HashMap contenant les chemins FXML
            HelloApplication.getPageManager().reloadAllPages(fxmlPaths);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call a method to perform additional actions after saving the data
        recoverAcceuil();
    }

    /**
     * Loads the language preference from a JSON file and sets it as the default value in the ComboBox.
     * This method reads the language stored in the JSON file and updates the ComboBox and internal language variable.
     */
    private void loadLanguageFromJson() {
        // Create an ObjectMapper to work with JSON data
        ObjectMapper mapper = new ObjectMapper();

        // Check if the language file exists before attempting to read it
        if (languageFile.exists()) {
            try {
                // Read the language data from the JSON file into a Map
                Map<String, String> data = mapper.readValue(languageFile, Map.class);

                // Retrieve the saved language from the map
                String savedLanguage = data.get("language");

                // If a language was found in the file, set it as the default value for the ComboBox
                if (savedLanguage != null) {
                    languageComboBox.setValue(savedLanguage);  // Set ComboBox value to the saved language
                    this.langue = savedLanguage;               // Update the internal language variable
                    System.out.println("Langue chargée depuis language.json : " + savedLanguage);
                }
            } catch (IOException e) {
                // Handle any errors during the file reading process
                e.printStackTrace();
                System.out.println("Erreur lors du chargement de la langue.");
            }
        }
    }

    /**
     * Adds a zoom effect to a given label when the mouse hovers over it.
     * The label will scale up when the mouse enters and return to its original size when the mouse exits.
     *
     * @param label The label to which the zoom effect will be applied.
     */
    private void addZoomEffect(Label label) {
        // When the mouse enters the label, scale it up
        label.setOnMouseEntered(event -> {
            label.setScaleX(1.2); // Scale up the label's width by 1.2 times
            label.setScaleY(1.2); // Scale up the label's height by 1.2 times
        });

        // When the mouse exits the label, reset the size to normal
        label.setOnMouseExited(event -> {
            label.setScaleX(1); // Reset the label's width to original size
            label.setScaleY(1); // Reset the label's height to original size
        });
    }

    /**
     * Handles mouse click events on labels and buttons.
     * It performs different actions based on the clicked element (either a label or a button).
     * For labels, it checks if the "back" label was clicked to handle the return action.
     * For buttons, it performs different actions such as deleting or saving data based on the button clicked.
     *
     * @param event The mouse event triggered by the user interaction.
     */
    private void handleLabelClick(MouseEvent event) {
        // Check if the clicked source is either a Label or a Button
        if (!(event.getSource() instanceof Label || event.getSource() instanceof Button)) {
            return;  // Ignore if it's neither a Label nor a Button
        }

        // Get the text from the champ (input field) to determine the data to be added or removed
        String quantif = this.champ.getText();
        System.out.println("le champ à ajouter ou supprimer " + quantif);

        // If the clicked element is a Label (e.g., "back"), handle the back event
        if (event.getSource() instanceof Label label) {
            if (label.equals(back)) {  // If the "back" label is clicked
                System.out.println("on est la");
                recoverAcceuil();  // Perform the return action
                return;  // Exit the method after handling the back action
            }
        }

        // If the clicked element is a Button, handle the corresponding button action
        if (event.getSource() instanceof Button) {
            Button but = (Button) event.getSource();

            if (but.equals(deleteButton)) {  // If the "delete" button is clicked
                handleSupprimer(quantif);  // Call the method to handle deletion
            } else {  // For other buttons
                // Check if a quantifier is selected and if the input field is not empty
                if (this.selectedQuantif == null || quantif.isEmpty()) {
                    System.out.println("Erreur : Veuillez sélectionner un quantificateur et remplir le champ.");
                    return;  // Exit if the quantifier is not selected or the field is empty
                }

                // Create a Map to store the selected quantifier and input text
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("selectedQuantif", this.selectedQuantif);  // Store selected quantifier
                dataMap.put("quantif", quantif);  // Store user input

                System.out.println("Données à sauvegarder : " + dataMap);

                // Try to save the data to a JSON file
                try {
                    saveDataToJson(dataMap, quantif);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Saves the provided data to a JSON file after checking for duplicates.
     * This method ensures that the data (quantifier and value) does not already exist in the file.
     * If it doesn't exist, the new data is added, and the file is updated.
     *
     * @param data A map containing the data to be saved (including the selected quantifier and value).
     * @param quantif The value (quantifier) to be added or checked for duplication.
     * @throws IOException If there is an error during file reading or writing.
     */
    private void saveDataToJson(Map<String, String> data, String quantif) throws IOException {
        // Create an ObjectMapper for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("data.json");

        // Load existing data from the file if it exists and is not empty
        List<Map<String, String>> dataList = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            dataList = mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        }

        // Check if the quantifier already exists in the data
        boolean exists = dataList.stream()
                .anyMatch(existingData ->
                        existingData.get("selectedQuantif").equals(this.selectedQuantif) &&
                                existingData.get("quantif").equals(quantif)
                );

        // If the quantifier exists, print an error message and stop
        if (exists) {
            System.out.println("Erreur : Le quantificateur '" + quantif + "' existe déjà pour le type '" + this.selectedQuantif + "'.");
            return;  // Stop the method if the quantifier already exists
        }

        // Add the new data to the list after confirming there are no duplicates
        dataList.add(data);

        // Call different methods depending on the quantifier type
        if ("Universal".equals(selectedQuantif) || "Universel".equals(selectedQuantif)) {
            System.out.println("laaaaaaaaaaaaaa 0" + selectedQuantif);
            addData(0, quantif, this.rowCountUniv);
        } else {
            System.out.println("laaaaaaaaaaaaaa 1 " + selectedQuantif);
            addData(1, quantif, this.rowCountExist);
        }

        // Save the updated list back to the file
        mapper.writeValue(file, dataList);
        reloadFPages();


        // Update the UI with the new data
        this.nouveau.setText(quantif);  // Set the new quantifier text in the label
        this.nouveau.setVisible(true);  // Make the label visible
        moveLabelAcrossGrid();  // Trigger label movement or animation (assumed to be defined elsewhere)
    }

    /**
     * Retrieves data from a JSON file and classifies it into two categories: "Universal" and "Existential".
     * The data is separated based on the selected quantifier and the current language setting.
     * The method processes each data entry and classifies it into the appropriate list.
     *
     * If the language is set to English, it looks for "Universal" and "Existential" quantifiers.
     * If the language is set to a different language (e.g., French), it looks for "Universel" and "Existentiel" quantifiers.
     *
     * After classifying the data, it prints the categorized data and updates the UI as needed.
     */
    @FXML
    private void retrieveAndClassifyData() {
        try {
            // Read data from the JSON file
            List<Map<String, String>> dataList = loadData();

            // Two lists to store classified data
            List<Map<String, String>> universelData = new ArrayList<>();
            List<Map<String, String>> existentielData = new ArrayList<>();

            // Iterate through the data and classify it
            for (Map<String, String> data : dataList) {
                String selectedQuantif = data.get("selectedQuantif");
                String quantif = data.get("quantif");

                // Check the language setting and classify based on the selected quantifier
                if(this.langue.equals("English")) {
                    // For English, look for "Universal" and "Existential"
                    if ("Universal".equals(selectedQuantif)) {
                        System.out.println("l339");
                        universelData.add(data);
                        addData(0, quantif, this.rowCountUniv);
                    }
                    if ("Existential".equals(selectedQuantif)) {
                        existentielData.add(data);
                        addData(1, quantif, this.rowCountExist);
                    }
                } else {
                    // For other languages, look for "Universel" and "Existentiel"
                    if ("Universel".equals(selectedQuantif)) {
                        universelData.add(data);
                        addData(0, quantif, this.rowCountUniv);
                    }
                    if ("Existentiel".equals(selectedQuantif)) {
                        existentielData.add(data);
                        addData(1, quantif, this.rowCountExist);
                    }
                }
            }

            // Print the classified data
            System.out.println("Données Universel : " + universelData);
            System.out.println("Données Existentiel : " + existentielData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data from a JSON file and returns it as a list of maps.
     * The method reads data from the "data.json" file and converts it into a list of maps,
     * where each map contains key-value pairs representing the data.
     * If the file doesn't exist or is empty, it returns an empty list.
     *
     * @return A list of maps representing the data loaded from the JSON file.
     *         Each map contains string keys and values (e.g., quantifier and value).
     * @throws IOException If there is an error reading the file or converting it to the required format.
     */
    private List<Map<String, String>> loadData() throws IOException {
        File file = new File("data.json");
        ObjectMapper mapper = new ObjectMapper();

        // Check if the file exists and is not empty
        if (file.exists() && file.length() > 0) {
            // Read the data from the file if it contains data
            return mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        } else {
            // Return an empty list if the file doesn't exist or is empty
            return new ArrayList<>();
        }
    }

    /**
     * Adds a new label containing a quantifier to a specified column and row in the grid layout.
     * The label is created with the provided quantifier text and styled with a font size of 18 and black text color.
     * The method also increments the row counter for the appropriate column based on the provided `colonne` value.
     *
     * @param colonne The column index where the label will be placed (0 for "Universal", 1 for "Existential").
     * @param quantif The quantifier text to be displayed in the label.
     * @param rowCount The row index where the label will be placed in the grid.
     */
    private void addData(int colonne, String quantif, int rowCount) {
        // Create a new label for the quantifier
        Label quantifLabel = new Label(quantif);
        quantifLabel.setStyle("-fx-font-size: 18; -fx-text-fill: #000000;");

        // Add the label to the specified column and row in the grid
        this.grid.add(quantifLabel, colonne, rowCount);

        // Increment the row counter for the corresponding column
        if (colonne == 0) {
            this.rowCountUniv++; // Increment for the "Universal" column
            System.out.println("le row countUnive : " + this.rowCountUniv);
        } else {
            this.rowCountExist++; // Increment for the "Existential" column
            System.out.println("le rowCountExist : " + this.rowCountExist);
        }
    }

    /**
     * Moves the 'nouveau' label across the grid with a sequence of horizontal and vertical movements.
     * The label is animated across a grid layout in several steps, starting from the top-left corner,
     * moving horizontally to the left, vertically down, then horizontally to the right before becoming invisible.
     *
     * The method ensures that the label (`nouveau`) is added to the grid (`gridmove`) if not already present,
     * and it sets up the necessary keyframes to animate the movement using a `Timeline`.
     */
    public void moveLabelAcrossGrid() {
        // Ensure that `nouveau` is added to `gridmove` before starting the animation
        if (!gridmove.getChildren().contains(nouveau)) {
            gridmove.add(nouveau, 7, 0); // Add the label to `gridmove` if not already added
        }

        // Reset the initial position of `nouveau` to column 7, row 0
        GridPane.setColumnIndex(nouveau, 7);
        GridPane.setRowIndex(nouveau, 0);
        nouveau.setVisible(true); // Make the label visible at the beginning

        Timeline timeline = new Timeline();
        int durationMillis = 100; // Duration in milliseconds between each movement

        // Initial horizontal movement to the left (from column 7 to 0)
        for (int col = 7; col >= 0; col--) {
            int finalCol = col;
            KeyFrame moveAcross = new KeyFrame(Duration.millis((7 - col) * durationMillis), e -> {
                GridPane.setColumnIndex(nouveau, finalCol);
                nouveau.setRotate(0);  // Horizontal movement
            });
            timeline.getKeyFrames().add(moveAcross);
        }

        // Vertical movement down to row 17
        for (int row = 0; row <= 17; row++) { // Note: row limit is increased to 17
            int finalRow = row;
            KeyFrame moveDown = new KeyFrame(Duration.millis((7 + row + 1) * durationMillis), e -> {
                GridPane.setRowIndex(nouveau, finalRow);
                nouveau.setRotate(90);  // Rotate 90 degrees for vertical movement
            });
            timeline.getKeyFrames().add(moveDown);
        }

        // Reset the rotation back to horizontal (0 degrees) once the label reaches position (0, 17)
        KeyFrame resetRotation = new KeyFrame(Duration.millis((7 + 17 + 1) * durationMillis), e -> {
            nouveau.setRotate(0); // Reset rotation to horizontal
        });
        timeline.getKeyFrames().add(resetRotation);

        // Horizontal movement to the right (from column 0 to 3) at the bottom
        for (int col = 0; col <= 4; col++) {
            int finalCol = col;
            KeyFrame moveRight = new KeyFrame(Duration.millis((7 + 17 + col + 2) * durationMillis), e -> {
                GridPane.setColumnIndex(nouveau, finalCol);
            });
            timeline.getKeyFrames().add(moveRight);
        }

        // Make the label invisible at the end of the animation
        KeyFrame hideLabel = new KeyFrame(Duration.millis((7 + 17 + 4) * durationMillis), e -> {
            nouveau.setVisible(false); // Hide the label after the final movement
        });
        timeline.getKeyFrames().add(hideLabel);

        // Start the animation
        timeline.setCycleCount(1); // Run the animation once
        timeline.play();
    }


    /**
     * Starts an infinite heartbeat animation on the `ear` image.
     * The image will scale up and down to create a heartbeat effect.
     * The animation continuously enlarges and shrinks the image at a regular interval.
     *
     * The scale of the image increases slightly (by 10%) and then returns to the original size, simulating a heartbeat.
     * This animation is executed using a `Timeline` and repeats indefinitely.
     */
    private void startHeartbeatAnimation() {
        // Variables for base and enlarged dimensions
        double initialScale = 1.0; // Initial scale (original size)
        double beatScale = 1.1;    // Slightly larger scale for the heartbeat effect
        int beatDuration = 500;    // Duration of one heartbeat (500ms = 0.5s for enlarging, 0.5s for shrinking)

        // Timeline for heartbeat animation
        Timeline heartbeat = new Timeline(
                // Step to enlarge the image
                new KeyFrame(Duration.millis(beatDuration), e -> {
                    ear.setScaleX(beatScale);  // Increase scale on the X-axis
                    ear.setScaleY(beatScale);  // Increase scale on the Y-axis
                }),
                // Step to return to the original size
                new KeyFrame(Duration.millis(beatDuration * 2), e -> {
                    ear.setScaleX(initialScale);  // Reset scale on the X-axis
                    ear.setScaleY(initialScale);  // Reset scale on the Y-axis
                })
        );

        // Set the animation to repeat indefinitely
        heartbeat.setCycleCount(Timeline.INDEFINITE);
        heartbeat.play();  // Start the animation
    }

    /**
     * Plays the heartbeat sound continuously.
     * This method loads the heartbeat sound file from the specified path and starts playing it in a loop.
     * The sound file should be located in the "src/son" directory with the name "hb.wav".
     */
    private void playHeartbeatSound() {
        try {
            // Path to the heartbeat sound file
            Path relativePath = Paths.get("src", "son", "hb.wav");
            File audioFile = new File(relativePath.toAbsolutePath().toString());

            // Open the audio input stream for the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            heartbeatClip = AudioSystem.getClip();  // Initialize the clip variable
            heartbeatClip.open(audioStream);  // Load the audio data into the clip

            // Start playing the sound from the beginning
            heartbeatClip.setFramePosition(0);  // Set the clip to start from the beginning
            heartbeatClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the sound continuously
        } catch (Exception e) {
            e.printStackTrace();  // Print error if something goes wrong
        }
    }

    /**
     * This method handles the transition to the home screen (acceuil).
     * It stops the heartbeat sound (if playing), loads the FXML for the home screen,
     * and sets up the interface with the appropriate layout.
     */
    private void recoverAcceuil() {
            // Stop the heartbeat sound before switching to the new screen
            if (heartbeatClip != null && heartbeatClip.isRunning()) {
                heartbeatClip.stop();  // Stop the heartbeat audio clip if it is running
            }

            HelloApplication.getPageManager().goBack();
    }

    /**
     * This method deletes a quantifier from the data file and updates the UI.
     * It removes the quantifier from the list in the JSON file and from the displayed grid.
     *
     * @param quantifToDelete The quantifier (string) to be deleted.
     */
    public void deleteQuantif(String quantifToDelete) {
        try {
            // Load the existing data from the JSON file
            List<Map<String, String>> dataList = loadData();

            // Remove the entry with the specified quantifier
            dataList.removeIf(data ->
                    data.get("quantif").equals(quantifToDelete)
            );

            // Save the updated list back to the JSON file
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("data.json"), dataList);
            reloadFPages();

            // Update the UI by removing the quantifier label from the grid
            removeQuantifFromGrid(quantifToDelete);

            // Log success message
            System.out.println("Quantificateur '" + quantifToDelete + "' supprimé avec succès.");

        } catch (IOException e) {
            // Log error if there's an issue during file handling
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du quantificateur.");
        }
    }

    /**
     * This method removes a quantifier label from the grid and animates the deletion.
     * It searches for the label corresponding to the specified quantifier and removes it from the `GridPane`.
     * Additionally, it triggers a visible "output" message and runs an animation.
     *
     * @param quantifToDelete The quantifier (string) to be removed from the grid.
     */
    private void removeQuantifFromGrid(String quantifToDelete) {
        // Loop through all nodes in the grid to find the label matching the quantifier
        for (Node node : grid.getChildren()) {
            // Check if the node is a Label and its text matches the quantifier to delete
            if (node instanceof Label label && label.getText().equals(quantifToDelete)) {

                // Set the output label with the deleted quantifier and make it visible
                this.sortie.setText(quantifToDelete);
                this.sortie.setVisible(true);

                // Remove the label from the grid
                grid.getChildren().remove(label);

                // Start the animation for the deletion process
                this.animateLabel();

                // Call the deleteQuantif method to delete the quantifier from the data (already handled in grid update)
                deleteQuantif(quantifToDelete);

                // Break the loop after finding and removing the first matching label
                break;
            }
        }
    }

    /**
     * This method animates the "sortie" label by moving it diagonally upwards and then making it fall down,
     * simulating an animation of the label being "thrown" across the screen.
     */
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

                // Cacher le label jusqu'à ce qu'il soit ajouté à la grille
                sortie.setVisible(false);

                // Ajouter le nouveau label au GridPane
                gridmove.getChildren().add(sortie);
            });

            fall.play(); // Démarrer l'animation de chute
        });

        moveUp.play(); // Démarrer l'animation de montée
    }

    /**
     * Translates UI components' text from English to French.
     * This method updates the text of labels, buttons, menus, and ComboBox items in the user interface to their French equivalents.
     *
     * It is assumed that the UI components (such as labels and buttons) are already initialized before calling this method.
     *
     * No parameters or return values.
     */
    public void translateLabelsToFrench() {
        // Translate text for labels
        back.setText("Retour");
        sortie.setText("Sortie");
        button.setText("Ajouter");
        deleteButton.setText("Supprimer");
        helpMenu.setText("Aide");
        quantiMenu.setText("Quantificateurs");
        languageMenu.setText("Langues");

        universel.setText("Universel");
        existentiel.setText("Existentiel");

        titleText.setText("Ajouter un quantificateur");

        // Translate ComboBox items
        for (String menuItem : languageComboBox.getItems()) {
            if (menuItem.equals("Close")) {
                menuItem = ("Fermer");
            } else if (menuItem.equals("About")) {
                menuItem = ("À propos");
            }
        }
    }

    /**
     * Translates UI components' text from French to English.
     * This method updates the text of labels, buttons, menus, and ComboBox items in the user interface to their English equivalents.
     *
     * It is assumed that the UI components (such as labels and buttons) are already initialized before calling this method.
     *
     * No parameters or return values.
     */
    public void translateLabelsToEnglish() {
        // Translate text for labels
        back.setText("Back");
        sortie.setText("Exit");
        button.setText("Add");
        deleteButton.setText("Delete");
        helpMenu.setText("Help");
        quantiMenu.setText("Quantifiers");
        languageMenu.setText("Languages");

        universel.setText("Universal");
        existentiel.setText("Existential");

        titleText.setText("Add a Quantifier");

        // Translate ComboBox items
        for (int i = 0; i < languageComboBox.getItems().size(); i++) {
            String menuItem = languageComboBox.getItems().get(i);
            if (menuItem.equals("Fermer")) {
                languageComboBox.getItems().set(i, "Close");
            } else if (menuItem.equals("À propos")) {
                languageComboBox.getItems().set(i, "About");
            }
        }
    }
}
