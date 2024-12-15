package SyllogismeInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The PageManager class is responsible for managing the loading, reloading, navigation, and history of pages
 * within a JavaFX application.
 */
public class PageManager {

    // The main Pane where the pages are displayed
    private AnchorPane mainPane;

    // A map that contains the page name and the loaded Parent for each page
    private Map<String, Parent> pagesMap = new HashMap<>();

    // A map that contains the page name and its associated controller
    private Map<String, Object> controllersMap = new HashMap<>();

    // A stack that keeps track of the history of visited pages
    private Stack<String> history = new Stack<>();

    // The name of the currently displayed page
    private String currentPage;

    /**
     * Constructor: Initializes with the main Pane to allow page transitions.
     * @param mainPane the main container (e.g., an AnchorPane) where the pages are displayed.
     */
    public PageManager(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * Loads and registers a page if not already loaded.
     * @param pageName the logical name of the page (e.g., "page1", "page2")
     * @param fxmlPath the path to the FXML file (e.g., "/fxml/Page1.fxml")
     * @throws IOException if an error occurs while loading the FXML file
     */
    public void loadPage(String pageName, String fxmlPath) throws IOException {
        if (!pagesMap.containsKey(pageName)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController(); // Retrieve the controller
            pagesMap.put(pageName, root);
            controllersMap.put(pageName, controller); // Store the controller
        }
    }

    /**
     * Reloads an existing page in the pagesMap.
     * If the page is not already loaded, it will be added.
     * @param pageName the logical name of the page
     * @param fxmlPath the path to the FXML file
     * @throws IOException if an error occurs while loading the FXML file
     */
    public void reloadPage(String pageName, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Object controller = loader.getController(); // Retrieve the controller
        pagesMap.put(pageName, root); // Replace the existing page in the map
        controllersMap.put(pageName, controller); // Replace the controller in the map
    }

    /**
     * Reloads all pages in the pagesMap you need to give every page because if you forget to
     * add one it will not be in the pageManger.
     * @param fxmlPaths a map containing the page names and their corresponding FXML paths.
     * @throws IOException if an error occurs while reloading the FXML files.
     */
    public void reloadAllPages(Map<String, String> fxmlPaths) throws IOException {
        Map<String, Parent> reloadedPagesMap = new HashMap<>();

        // Reload each page
        for (Map.Entry<String, String> entry : fxmlPaths.entrySet()) {
            String pageName = entry.getKey();
            String fxmlPath = entry.getValue();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent reloadedPage = loader.load();
            reloadedPagesMap.put(pageName, reloadedPage);

            Object controller = loader.getController();
            controllersMap.put(pageName, controller);
        }


        pagesMap = reloadedPagesMap;


        if (currentPage != null && pagesMap.containsKey(currentPage)) {
            showPage(currentPage);
        }
    }

    /**
     * Retrieves the controller associated with a given page.
     * @param pageName the name of the page for which to retrieve the controller
     * @return the controller of the page, or null if the page is not loaded
     */
    public Object getController(String pageName) {
        return controllersMap.get(pageName);
    }

    /**
     * Displays the requested page and updates the history.
     * @param pageName the name of the page to display
     */
    public void goToPage(String pageName) {
        if (pageName.equals(currentPage)) {
            return; // Avoid reloading the same page unnecessarily
        }

        // If a page is already displayed, push it to history
        if (currentPage != null) {
            history.push(currentPage);
        }
        showPage(pageName);
    }

    /**
     * Displays the requested page without updating history.
     * @param pageName the name of the page to display
     */
    public void goToPageNoHistory(String pageName) {
        if (pageName.equals(currentPage)) {
            return;
        }

        showPage(pageName);
    }

    /**
     * Goes back to the previous page in the history.
     */
    public void goBack() {
        if (!history.isEmpty()) {
            String previousPage = history.pop();
            showPage(previousPage);
        }
    }

    /**
     * The PageManager class is responsible for managing page transitions within the application.
     * This method is used to display the specified page in the main container.
     *
     * If the page is not found in the pages map, it throws an {@link IllegalArgumentException} to notify the caller
     * that the page was not properly loaded or does not exist.
     */
    private void showPage(String pageName) {
        Parent page = pagesMap.get(pageName);
        if (page != null) {
            mainPane.getChildren().setAll(page);
            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 0.0);
            AnchorPane.setLeftAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);
            currentPage = pageName;
        } else {
            throw new IllegalArgumentException("The page '" + pageName + "' is not loaded. Please ensure loadPage was called beforehand.");
        }
    }

    /**
     * Clears the history if needed.
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * Returns the name of the current page.
     * @return the name of the current page, or null if no page is displayed.
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * Unloads a page from memory if it is no longer needed.
     * @param pageName the name of the page to unload
     */
    public void unloadPage(String pageName) {
        pagesMap.remove(pageName);
        controllersMap.remove(pageName); // Also remove the controller from the map
    }

    public Parent getPage(String pageName) {
        return pagesMap.get(pageName);
    }

    /**
     * Sets a background image for the mainPane.
     *
     * @param path the path to the image file (e.g., "images/background.jpg").
     */
    public void setBackgroundImage(String path) {
        Path imagePath = Paths.get(path);
        String imageUrl = imagePath.toUri().toString();

        BackgroundSize backgroundSize = new BackgroundSize(
                100,
                100,
                true,
                true,
                false,
                true
        );
        Image image = new Image(imageUrl);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        Background background = new Background(backgroundImage);
        mainPane.setBackground(background);
    }

    /**
     * Returns the history stack.
     * @return the history stack
     */
    public Stack<String> getHistory() {
        return history;
    }
}
