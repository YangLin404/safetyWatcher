package be.linyang.safetyWatcher.controllers;

import be.linyang.safetyWatcher.Services.LocalService;
import be.linyang.safetyWatcher.model.Local;
import be.linyang.safetyWatcher.model.LocalSafety;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Window;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.HashSet;


/**
 * Created by yanglin on 28/05/17.
 */

@Controller
public class MainController {

    @FXML
    private SplitPane mainPane;

    private ListView<String> listView;

    private ImageView imageView;

    private MasterDetailPane masterDetailPane;

    private TableView<LocalSafety> tableView;

    private Text titleText;

    private VBox vBox;

    @Autowired
    private LocalService localService;

    @FXML
    public void initialize()
    {
        ObservableList<String> items = FXCollections.observableArrayList(localService.getAllLocalName());
        listView = new ListView<>(items);

        Image splash = new Image("/pictures/plattegrond.jpg");
        imageView = new ImageView(splash);
        imageView.setFitWidth(650);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        setupDetailView();

        masterDetailPane = new MasterDetailPane(Side.RIGHT,listView,vBox,false);
        masterDetailPane.setDividerPosition(0.2);

        mainPane.getItems().add(masterDetailPane);
        mainPane.getItems().add(imageView);

        registerListeners();

    }

    private void registerListeners() {
        listView.getSelectionModel().selectedItemProperty().addListener(event -> {
            String selectLocal = listView.getSelectionModel().getSelectedItem();
            Local local = localService.getLocalByName(selectLocal);
            changeDetailView(local);
        });

    }

    private void setupDetailView()
    {
        titleText = new Text("text");

        TextFlow textFlow = new TextFlow(titleText);
        textFlow.getStyleClass().add("h1");

        //titleText.getStyleClass().addAll("lbl", "lbl-primary");

        tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.sort();

        TableRowExpanderColumn<LocalSafety> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);

        TableColumn<LocalSafety,String> nameCol = new TableColumn<>("Beschrijving");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<LocalSafety,Boolean> availableCol = new TableColumn<>("Aanwezig?");
        availableCol.setCellValueFactory(param -> {
            LocalSafety safety = param.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().setValue(safety.isAvailable());
            return new SimpleObjectProperty(checkBox);
        });

        TableColumn<LocalSafety, LocalDate> expiredDateCol = new TableColumn<>("Verval datum");
        expiredDateCol.setCellValueFactory(
                new PropertyValueFactory<>("expiryDate"));

        TableColumn<LocalSafety, String> deleteCol = new TableColumn<>("delete");
        deleteCol.setCellValueFactory(param -> {
            LocalSafety safety = param.getValue();
            Button button = new Button("Verwijderen");
            button.getStyleClass().addAll("btn", "btn-danger");
            button.setOnAction(event -> {
                Window onwer = tableView.getScene().getWindow();
                String msg = "Wil je " + safety.getName() + " verwijderen?";
                showConfirmDialog(msg, onwer).showAndWait().ifPresent(result -> {
                    if (result.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                        changeDetailView(localService.removeSafety(safety));
                    }
                });
            });
            return new SimpleObjectProperty(button);
        });

        tableView.getColumns().addAll(expanderColumn,nameCol,availableCol,expiredDateCol, deleteCol);

        Button addButton = new Button("voeg toe");
        addButton.getStyleClass().addAll("btn", "btn-success");

        addButton.setOnAction(event -> {
            addNewSafetyWizard(tableView.getScene().getWindow());
        });

        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(textFlow,tableView, addButton);
    }

    private void addNewSafetyWizard(Window owner) {
        Wizard wizard = new Wizard(owner);
        wizard.setTitle("Voeg nieuwe veiligheidsaspect toe");

        // Page 1
        WizardPane page1 = new WizardPane() {
            ValidationSupport vs = new ValidationSupport();
            {
                vs.initInitialDecoration();

                int row = 0;

                GridPane page1Grid = new GridPane();
                page1Grid.setVgap(10);
                page1Grid.setHgap(10);

                page1Grid.add(new Label("Name: "), 0, row);
                TextField txName = createTextField("name");
                vs.registerValidator(txName, Validator.createEmptyValidator("name mag niet leeg zijn!"));
                page1Grid.add(txName, 1, row++);

                page1Grid.add(new Label("Aanwezig: "), 0, row);
                CheckBox checkBoxAvailable = createCheckBox("available");
                checkBoxAvailable.setSelected(true);
                page1Grid.add(checkBoxAvailable, 1, row++);

                page1Grid.add(new Label("Verval datum: "), 1, row);
                DatePicker datePicker = new DatePicker();
                datePicker.setId("expiredDate");
                page1Grid.add(datePicker, 1, row);

                setContent(page1Grid);
            }

            @Override
            public void onEnteringPage(Wizard wizard) {
                wizard.invalidProperty().unbind();
                wizard.invalidProperty().bind(vs.invalidProperty());
            }
        };

        // Page 2
        WizardPane page2 = new WizardPane() {
            ValidationSupport vs = new ValidationSupport();
            {
                vs.initInitialDecoration();

                int row = 0;

                GridPane page2Grid = new GridPane();
                page2Grid.setVgap(10);
                page2Grid.setHgap(10);

                page2Grid.add(new Label("druk op finish"),0,row);

                setContent(page2Grid);
            }

            @Override
            public void onEnteringPage(Wizard wizard) {
                wizard.invalidProperty().unbind();
                wizard.invalidProperty().bind(vs.invalidProperty());
            }
        };


        // create wizard
        wizard.setFlow(new Wizard.LinearFlow(page1,page2));

        // show wizard and wait for response
        wizard.showAndWait().ifPresent(result -> {
            if (result == ButtonType.FINISH) {
                Local currentLocal = localService.getLocalByName(listView.getSelectionModel().getSelectedItem());
                String name = (String) wizard.getSettings().get("name");
                LocalDate date = (LocalDate) wizard.getSettings().get("expiredDate");
                boolean available = (Boolean) wizard.getSettings().get("available");
                LocalSafety localSafety = new LocalSafety(name,date,currentLocal,available);
                changeDetailView(localService.addSafety(localSafety));
            }
        });
    }

    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<LocalSafety> param) {
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        LocalSafety localSafety = param.getValue();

        CheckBox available = new CheckBox();
        available.setSelected(localSafety.isAvailable());

        editor.addRow(0,new Label("Aanwezig?: "), available);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(localSafety.getExpiryDate()==null?LocalDate.now():localSafety.getExpiryDate());

        editor.addRow(1, new Label("vervaldatum: "), datePicker);

        Button saveButton = new Button("Save");
        saveButton.getStyleClass().addAll("btn", "btn-primary");
        saveButton.setOnAction(event -> {

            if (!localSafety.isAvailable() == available.isSelected())
                localSafety.setAvailable(available.isSelected());

            if (!localSafety.isAvailable())
                localSafety.setExpiryDate(null);
            else {
                if (localSafety.getExpiryDate() == null || !localSafety.getExpiryDate().isEqual(datePicker.getValue()))
                    localSafety.setExpiryDate(datePicker.getValue());
            }
            localService.updateSafety(localSafety);
            param.toggleExpanded();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> param.toggleExpanded());
        cancelButton.getStyleClass().addAll("btn","btn-default");
        editor.addRow(2, saveButton, cancelButton);

        return editor;

    }

    private void changeDetailView(Local local) {
        HashSet<LocalSafety> safeties = new HashSet<>(local.getSafeties());

        ObservableList<LocalSafety> data = FXCollections.observableArrayList(safeties);

        tableView.setItems(data);
        titleText.setText(local.getName());
        masterDetailPane.setShowDetailNode(true);
    }

    private Alert showConfirmDialog(String msg, Window owner)
    {
        Alert dlg = createAlert(Alert.AlertType.CONFIRMATION, owner);
        dlg.setTitle("verwijderen?");
        dlg.getDialogPane().setContentText(msg);
        return dlg;

    }

    private Alert createAlert(Alert.AlertType type,Window owner) {
        Alert dlg = new Alert(type);
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.initOwner(owner);

        return dlg;
    }

    private TextField createTextField(String id) {
        TextField textField = new TextField();
        textField.setId(id);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        return textField;
    }

    private CheckBox createCheckBox(String id) {
        CheckBox checkBox = new CheckBox();
        checkBox.setId(id);
        GridPane.setHgrow(checkBox, Priority.ALWAYS);
        return checkBox;
    }
}
