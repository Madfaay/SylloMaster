package SyllogismeInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**Start adventure controller */

public class PolyOrSyllogismeController {

    /**
     * AnchorPane for organizing the main layout of the application.
     */
    public AnchorPane archor;

    /**
     * Label to display information or content related to the left part of the syllogism.
     */
    public Label syllogismeLeft;

    /**
     * Label to display information or content related to the right part of the syllogism.
     */
    public Label syllogismeRight;

    /**
     * Label for displaying or interacting with the polysyllogism section.
     */
    public Label poly;

    /**
     * Parent node for the mode selection view.
     */
    public Parent modeContent;

    /**
     * Parent node for the main welcome (acceuil) view.
     */
    public Parent acceuilContent;

    /**
     * Parent node for the syllogism-related content view.
     */
    public Parent syllogismeContent;

    /** Button to navigate back to the previous step, linked to FXML.*/

    @FXML
    public Label back;

    /**
     * Sets a background image for the anchor pane (`archor`).
     *
     * This method applies an image as the background, centered, and scaled to fit
     * the pane without repeating.
     *
     * @param chemin the file path to the image to use as the background.
     *
     * How it works:
     * 1. Converts the file path to a URI compatible with JavaFX.
     * 2. Creates a `BackgroundSize` object to ensure the image fully covers the pane.
     * 3. Loads the image using the `Image` class.
     * 4. Creates a `BackgroundImage` with no repeat, centered position, and custom size.
     * 5. Applies the `Background` to the `archor` pane.
     */
    private void setBackgroundImage(String chemin) {
        Path imagePath = Paths.get(chemin);
        String imageUrl = imagePath.toUri().toString(); // Convert path to URL

        BackgroundSize backgroundSize = new BackgroundSize(
                100,  // width 100%
                100,  // height 100%
                true, // cover width
                true, // cover height
                false, // do not keep proportions
                true  // do not limit to pane size
        );
        Image image = new Image(imageUrl); // Load the image
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,    // No horizontal repeat
                BackgroundRepeat.NO_REPEAT,    // No vertical repeat
                BackgroundPosition.CENTER,     // Centered position
                backgroundSize                 // Apply defined size
        );
        Background background = new Background(backgroundImage);
        archor.setBackground(background);
    }


    /**
     * Handles label click events and navigates to the corresponding view.
     *
     * Based on the label clicked, this method performs different actions:
     * - If the "back" label is clicked, it navigates to the main view.
     * - If the "syllogismeLeft" label is clicked, it navigates to the mode selection view.
     * - If the "syllogismeRight" label is clicked, it navigates to the syllogism view.
     *
     * @param event the mouse event triggered by clicking a label.
     */
    private void handleLabelClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource();

        if (clickedLabel.equals(back)) {
            recoverAcceuil();
        }

        if (clickedLabel.equals(this.syllogismeLeft)) {
            this.setMode();
        }
        if (clickedLabel.equals(this.syllogismeRight)) {
            this.setSyllogisme();
        }
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * This method performs the following tasks:
     * - Loads the FXML files for different views: "SyllogismeRedactionSimple.fxml", "PremissePage.fxml", and "hello-view.fxml".
     * - Sets up the background image for the application using a predefined file path.
     * - Adds a zoom effect to specific labels to enhance user interaction.
     * - Assigns click event handlers to the labels for navigation between views.
     */
    @FXML
    void initialize() {
        // Set the background image using the relative file path
        Path relativePath = Paths.get("src", "IMAGES", "backgroundlight.jpg");
        HelloApplication.getPageManager().setBackgroundImage(relativePath.toAbsolutePath().toString());

        // Add zoom effects to labels for hover animations
        addZoomEffect(this.syllogismeLeft);
        addZoomEffect(this.poly);
        addZoomEffect(this.syllogismeRight);
        addZoomEffect(this.back);

        // Set click event handlers for navigation
        back.setOnMouseClicked(this::handleLabelClick);
        syllogismeLeft.setOnMouseClicked(this::handleLabelClick);
        poly.setOnMouseClicked(this::handleLabelClick);
        syllogismeRight.setOnMouseClicked(this::handleLabelClick);
    }

    /**
     * Adds a zoom effect to a given label when the mouse hovers over it.
     *
     * This method enlarges the label when the mouse enters and restores it to its original size when the mouse exits.
     * For specific labels, it also adjusts the size of related labels to create a dynamic interaction effect.
     *
     * @param label The label to which the zoom effect will be applied.
     */
    private void addZoomEffect(Label label) {
        label.setOnMouseEntered(event -> {
            // Scale the label up when the mouse hovers over it
            label.setScaleX(1.2);
            label.setScaleY(1.2);

            // If the hovered label is "poly", also scale up "syllogismeRight"
            if (label.equals(this.poly)) {
                syllogismeRight.setScaleX(1.2);
                syllogismeRight.setScaleY(1.2);
            }

            // If the hovered label is "syllogismeRight", also scale up "poly"
            if (label.equals(this.syllogismeRight)) {
                poly.setScaleX(1.2);
                poly.setScaleY(1.2);
            }
        });

        label.setOnMouseExited(event -> {
            // Restore the label's original size when the mouse exits
            label.setScaleX(1);
            label.setScaleY(1);

            // If the exited label is "poly", also restore "syllogismeRight" size
            if (label.equals(this.poly)) {
                syllogismeRight.setScaleX(1);
                syllogismeRight.setScaleY(1);
            }

            // If the exited label is "syllogismeRight", also restore "poly" size
            if (label.equals(this.syllogismeRight)) {
                poly.setScaleX(1);
                poly.setScaleY(1);
            }
        });
    }

    /**
     * Restores the main interface by loading the home content into the `AnchorPane`.
     *
     * This method clears the current content of the `AnchorPane` and replaces it with the preloaded
     * home content (`acceuilContent`). The content is adjusted to fit the pane entirely by setting
     * all anchor constraints to 0.
     */
    private void recoverAcceuil() {
        HelloApplication.getPageManager().goBack();
    }

    /**
     * Switches the displayed content to the mode selection interface.
     *
     * This method replaces the current content of the `AnchorPane` with the preloaded
     * mode selection interface (`modeContent`). The content is resized to fill the entire
     * `AnchorPane` by setting all anchor constraints to 0.
     */
    private void setMode() {
        HelloApplication.getPageManager().goToPage("mode");
    }

    /**
     * Switches the displayed content to the syllogism interface.
     *
     * This method replaces the current content of the `AnchorPane` with the preloaded
     * syllogism interface (`syllogismeContent`). The content is resized to fill the entire
     * `AnchorPane` by setting all anchor constraints to 0.
     */
    private void setSyllogisme() {
        HelloApplication.getPageManager().goToPage("poly");
    }
}
