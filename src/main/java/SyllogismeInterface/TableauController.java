package SyllogismeInterface;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableauController {

    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML
    private TableColumn<ObservableList<String>, String> Universal1, Affirmative1, Major, Universal2, Affirmative2, Minor, UniversalC, AffirmativeC, Conclusion, Figure, RMT, RLH, RNN, RN, RAA, RPP, RP, Valide, RUU, RI;

    // Liste des données pour le TableView
    private final ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        // Configurer les colonnes pour afficher les données par index
        Universal1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        Affirmative1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        Major.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        Universal2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        Affirmative2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        Minor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        UniversalC.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        AffirmativeC.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(7)));
        Conclusion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));
        Figure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(9)));
        RMT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(10)));
        RLH.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(11)));
        RNN.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(12)));
        RN.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(13)));
        RAA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(14)));
        RPP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(15)));
        RP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(16)));
        Valide.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(17)));
        RUU.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(18)));
        RI.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(19)));

        // Lier les données au TableView
        tableView.setItems(data);

        // Ajouter des lignes de test

    }


    // Méthode pour ajouter une ligne
    public void addRow(String[] values) {
        if (values.length != 20) {
            throw new IllegalArgumentException("Chaque ligne doit contenir exactement 20 valeurs.");
        }
        data.add(FXCollections.observableArrayList(values));
    }
}
