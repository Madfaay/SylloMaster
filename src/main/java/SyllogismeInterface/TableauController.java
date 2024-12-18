package SyllogismeInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TableauController {

    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML private TableColumn<ObservableList<String>, String> number;
    @FXML private TableColumn<ObservableList<String>, String> Figure;
    @FXML private TableColumn<ObservableList<String>, String> Form;
    @FXML private TableColumn<ObservableList<String>, String> MiddleTermRule;
    @FXML private TableColumn<ObservableList<String>, String> LatiusRule;
    @FXML private TableColumn<ObservableList<String>, String> rNN;
    @FXML private TableColumn<ObservableList<String>, String> rN;
    @FXML private TableColumn<ObservableList<String>, String> rAA;
    @FXML private TableColumn<ObservableList<String>, String> rPP;
    @FXML private TableColumn<ObservableList<String>, String> rP;
    @FXML private TableColumn<ObservableList<String>, String> Valide;
    @FXML private TableColumn<ObservableList<String>, String> Interssant;

    private final File languageFile = new File("language.json");
    public String language ;

    // Liste des données pour le TableView
    private final ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        loadLanguageFromJson();
        // Configurer les colonnes pour afficher les données par index
        number.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        Figure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        Form.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        MiddleTermRule.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        LatiusRule.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        rNN.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        rN.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        rAA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(7)));
        rPP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));
        rP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(9)));
        Valide.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(10)));
        Interssant.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(11)));

        // Lier les données au TableView
        tableView.setItems(data);

        if(!this.language.equals("English"))
            this.translateTableColumnsToFrench();

        // Ajouter toutes les colonnes au TableView

        // Ajouter des lignes de test
    }

    // Méthode pour ajouter des données de test

    /**
     * Adds a new row of values to the table.
     *
     * This method adds a new row to the `data` list, which is a list of observable lists representing the rows of the table.
     * It ensures that the new row contains exactly 12 values, corresponding to the 12 columns in the table. If the number of values
     * is not 12, an `IllegalArgumentException` is thrown.
     *
     * @param values An array of strings representing the values for the new row. This array must contain exactly 12 elements.
     *
     * @throws IllegalArgumentException if the number of values is not 12.
     */
    public void addRow(String[] values) {
        if (values.length != 12) { // 12 columns in the table
            throw new IllegalArgumentException("Chaque ligne doit contenir exactement 12 valeurs.");
        }
        data.add(FXCollections.observableArrayList(values));
    }


    /**
     * Adds a new row of values to the table.
     *
     * This method adds a new row to the `data` list, which is a list of observable lists representing the rows of the table.
     * It ensures that the new row contains exactly 12 values, corresponding to the 12 columns in the table. If the number of values
     * is not 12, an `IllegalArgumentException` is thrown.
     *
     * @param values A list of strings representing the values for the new row. This list must contain exactly 12 elements.
     *
     * @throws IllegalArgumentException if the number of values is not 12.
     */
    public void addRow(List<String> values) {
        if (values.size() != 12) { // 12 columns in the table
            throw new IllegalArgumentException("Chaque ligne doit contenir exactement 12 valeurs.");
        }
        data.add(FXCollections.observableArrayList(values));
    }


    /**
     * Loads the language setting from a JSON file.
     *
     * This method attempts to load the language preference from a JSON file (`language.json`). If the file exists, it reads the data
     * and retrieves the language setting. If the language is found in the file, it is stored in the `language` variable.
     * If an error occurs while reading the file, an error message is printed.
     *
     */
    private void loadLanguageFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        if (languageFile.exists()) {
            try {
                Map<String, String> data = mapper.readValue(languageFile, Map.class);
                String savedLanguage = data.get("language");

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


    /**
     * Translates the column headers of a table to French.
     *
     * This method iterates through all the columns of the table (`tableView`), retrieves the text of the column headers,
     * and translates them into French based on predefined cases. If a column header matches one of the specified values,
     * it is replaced with the corresponding French translation.
     *
     *
     * If the header text does not match any of the predefined values, no change is made.
     */
    public void translateTableColumnsToFrench() {
        for (TableColumn<?, ?> column : tableView.getColumns()) {
            String headerText = column.getText(); // Retrieve the column header text
            if (headerText != null) {
                switch (headerText) {
                    case "Number of syllo":
                        column.setText("Nombre de syllogismes");
                        break;
                    case "Figure":
                        column.setText("Figure");
                        break;
                    case "Form":
                        column.setText("Forme");
                        break;
                    case "MiddleTermRule":
                        column.setText("Règle du terme moyen");
                        break;
                    case "LatiusRule":
                        column.setText("Règle de Latius");
                        break;
                    case "rNN":
                        column.setText("rNN");
                        break;
                    case "rN":
                        column.setText("rN");
                        break;
                    case "rAA":
                        column.setText("rAA");
                        break;
                    case "rPP":
                        column.setText("rPP");
                        break;
                    case "rP":
                        column.setText("rP");
                        break;
                    case "Validation":
                        column.setText("Validation");
                        break;
                    case "Interessant":
                        column.setText("Intéressant");
                        break;
                    default:
                        // If no match, do nothing
                        break;
                }
            }
        }
    }

}
