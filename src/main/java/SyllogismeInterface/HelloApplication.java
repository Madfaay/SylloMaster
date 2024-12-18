package SyllogismeInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**Allows us to start application */
public class HelloApplication extends Application {
    private static PageManager pageManager;
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        pageManager = new PageManager(root);

        pageManager.loadPage("home", "hello-view.fxml");
        pageManager.loadPage("settings", "quantificateurs.fxml");
        pageManager.loadPage("selection", "poly-or-syllogisme.fxml");

        pageManager.loadPage("mode", "SyllogismeRedactionSimple.fxml");
        pageManager.loadPage("mode2", "SyllogismeRedaction.fxml");

        pageManager.loadPage("poly", "PremissePage.fxml");


        pageManager.goToPage("home");

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("SylloMaster");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static PageManager getPageManager() {
        return pageManager;
    }

    public static void main(String[] args) {
        launch();
    }
}