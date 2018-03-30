package lab.cadl.lirui.study.sudoku;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SudokuPane extends GridPane {
    private static final double CELL_SIZE = 50;

    private CellControl[] cellLabels = new CellControl[81];
    private SudokuBoard board;

    private enum CellControlSelectionState {
        NONE("none"),
        RELATED("related"),
        SELECTED("selected");

        String styleClass;

        CellControlSelectionState(String styleClass) {
            this.styleClass = styleClass;
        }
    }

    private class CellControl extends Label {
        private SudokuBoard.Cell cell;
        private CellControlSelectionState selectionState;

        CellControl(SudokuBoard.Cell cell) {
            super(cell.getLabelString());
            setPrefSize(CELL_SIZE, CELL_SIZE);
            setAlignment(Pos.CENTER);
            setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.cell = cell;
            final CellControl self = this;

            setSelectionState(CellControlSelectionState.NONE);

            setOnMouseClicked(e -> {
                setSelectionState(CellControlSelectionState.SELECTED);

                List<CellControl> relatedCellControls = new ArrayList<>();
                for (SudokuBoard.Cell relatedCell : cell.getRelatedCell()) {
                    relatedCellControls.add(cellLabels[relatedCell.getPos()]);
                }

                for (CellControl cellControl : cellLabels) {
                    if (cellControl == self) {
                        continue;
                    }

                    cellControl.setSelectionState(relatedCellControls.contains(cellControl) ?
                            CellControlSelectionState.RELATED : CellControlSelectionState.NONE);
                }
            });
        }

        void setSelectionState(CellControlSelectionState selectionState) {
            this.selectionState = selectionState;

            getStyleClass().setAll(selectionState.styleClass);
        }
    }

    public SudokuPane() {
        setPadding(new Insets(5));

        this.board = SudokuBoard.createFromString("0 1 0 0 6 0 0 0 4 " +
                "0 0 4 0 1 5 0 6 0 " +
                "0 6 0 0 3 0 8 0 0 " +
                "5 0 9 7 0 0 0 0 0 " +
                "0 0 3 0 5 0 0 0 0 " +
                "7 0 0 2 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 3 1 " +
                "9 0 0 0 0 0 0 0 0 " +
                "0 0 8 0 0 0 0 4 0 ");

        for (SudokuBoard.Cell cell : board.getCells()) {
            CellControl cellLabel = new CellControl(cell);
            setConstraints(cellLabel, cell.getPosition().getRow(), cell.getPosition().getCol());
            getChildren().add(cellLabel);
            cellLabels[cell.getPos()] = cellLabel;
        }
    }

    public CellControl[] getCellLabels() {
        return cellLabels;
    }

    public static Scene createScene() {
        Scene scene = new Scene(new SudokuPane(), CELL_SIZE * 9 + 10, CELL_SIZE * 9 + 10);
        scene.getStylesheets().add(SudokuPane.class.getResource("styles.css").toExternalForm());
        return scene;
    }
}
