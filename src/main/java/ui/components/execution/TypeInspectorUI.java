package ui.components.execution;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Maurice Amon
 */

public class TypeInspectorUI extends TableView<SPLVariable> {

    public ObservableList<SPLVariable> splVariables = FXCollections.observableArrayList();

    public TypeInspectorUI() {
        TableColumn<SPLVariable, String> identifierColumn = new TableColumn<>("Variable Name");
        identifierColumn.setCellValueFactory(cellData -> cellData.getValue().getIdentifierProperty());
        TableColumn<SPLVariable, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        TableColumn<SPLVariable, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        this.getColumns().addAll(identifierColumn, typeColumn, valueColumn);
        this.setItems(splVariables);
        this.setVisible(true);
        this.setEditable(false);
        this.setWidth(250);
        this.setPrefWidth(250);
        this.setMaxWidth(250);
    }

}
