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

public class PolyOrSyllogismeController {

    public AnchorPane archor;
    public Label syllogismeLeft;
    public Label syllogismeRight;
    public Label poly;

    Parent modeContent;
    Parent acceuilContent;
    Parent syllogismeContent;

    @FXML
    public Label back;

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


    @FXML
    void initialize() {
        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SyllogismeRedactionSimple.fxml"));
            modeContent = fxmlLoader.load();
            fxmlLoader = new FXMLLoader(getClass().getResource("PremissePage.fxml"));
            syllogismeContent = fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Charge le fichier FXML de l'interface des paramètres
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            acceuilContent = fxmlLoader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Path relativePath = Paths.get("src", "IMAGES", "backgroundlight.jpg");
        setBackgroundImage(relativePath.toAbsolutePath().toString());
        addZoomEffect(this.syllogismeLeft);
        addZoomEffect(this.poly);
        addZoomEffect(this.syllogismeRight);
        addZoomEffect(this.back);
        back.setOnMouseClicked(this::handleLabelClick);
        syllogismeLeft.setOnMouseClicked(this::handleLabelClick);
        poly.setOnMouseClicked(this::handleLabelClick);
        syllogismeRight.setOnMouseClicked(this::handleLabelClick);


    }

    private void addZoomEffect(Label label) {
        label.setOnMouseEntered(event -> {
            label.setScaleX(1.2);
            label.setScaleY(1.2);

            // Si c'est "poly", on agrandit également "syllogismeLeft"
            if (label.equals(this.poly)) {
                syllogismeRight.setScaleX(1.2);
                syllogismeRight.setScaleY(1.2);
            }

            // Si c'est "syllogismeLeft", on agrandit également "poly"
            if (label.equals(this.syllogismeRight)) {
                poly.setScaleX(1.2);
                poly.setScaleY(1.2);
            }
        });

        label.setOnMouseExited(event -> {
            label.setScaleX(1);
            label.setScaleY(1);

            // Si c'est "poly", on remet "syllogismeLeft" à sa taille normale
            if (label.equals(this.poly)) {
                syllogismeRight.setScaleX(1);
                syllogismeRight.setScaleY(1);
            }

            // Si c'est "syllogismeLeft", on remet "poly" à sa taille normale
            if (label.equals(this.syllogismeRight)) {
                poly.setScaleX(1);
                poly.setScaleY(1);
            }

        });
    }

    private void recoverAcceuil() {

        // Efface le contenu actuel de l'AnchorPane et ajoute le contenu des paramètres
        archor.getChildren().setAll(acceuilContent); // Ajoute l'interface des paramètres

        // Optionnel : ajustez la position et la taille du contenu ajouté
        AnchorPane.setTopAnchor(acceuilContent, 0.0);
        AnchorPane.setBottomAnchor(acceuilContent, 0.0);
        AnchorPane.setLeftAnchor(acceuilContent, 0.0);
        AnchorPane.setRightAnchor(acceuilContent, 0.0);


    }




    private void setMode() {

        archor.getChildren().setAll(modeContent);

        // Optionnel : ajustez la position et la taille du contenu ajouté
        AnchorPane.setTopAnchor(modeContent, 0.0);
        AnchorPane.setBottomAnchor(modeContent, 0.0);
        AnchorPane.setLeftAnchor(modeContent, 0.0);
        AnchorPane.setRightAnchor(modeContent, 0.0);

    }


    private void setSyllogisme() {

        archor.getChildren().setAll(syllogismeContent);

        // Optionnel : ajustez la position et la taille du contenu ajouté
        AnchorPane.setTopAnchor(modeContent, 0.0);
        AnchorPane.setBottomAnchor(modeContent, 0.0);
        AnchorPane.setLeftAnchor(modeContent, 0.0);
        AnchorPane.setRightAnchor(modeContent, 0.0);

    }


}
