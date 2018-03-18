package lab.cadl.lirui.study.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SudokuBoard {
    public static final int NONE = 0;
    public static final int CELL_COUNT = 81;

    public static class Cell {
        private int value = NONE;
        private Position position;
        private List<Cell> relatedCell = new ArrayList<>();
        private List<Integer> possibleValues = new ArrayList<>();

        private Cell(Position position) {
            this.position = position;
            for (int i = 1; i <= 9; i++) {
                possibleValues.add(i);
            }
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public List<Cell> getRelatedCell() {
            return relatedCell;
        }

        public List<Integer> getPossibleValues() {
            return possibleValues;
        }

        public Position getPosition() {
            return position;
        }

        public int getPos() {
            return position.toPos();
        }

        public String getLabelString() {
            return value == NONE ? "" : String.valueOf(value);
        }
    }

    public static class Position {
        int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int toPos() {
            return row * 9 + col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row &&
                    col == position.col;
        }

        @Override
        public int hashCode() {

            return Objects.hash(row, col);
        }
    }

    private final Cell[] cells = new Cell[81];

    public SudokuBoard() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(toPosition(i));
        }

        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            int row = i / 9;
            int col = i % 9;

            for (int j = 0; j < 9; j++) {
                if (j != col) {
                    cell.relatedCell.add(cells[row * 9 + j]);
                }

                if (j != row) {
                    cell.relatedCell.add(cells[j * 9 + col]);
                }
            }

            int rowLow = row / 3 * 3;
            int rowHigh = (row / 3 + 1) * 3;
            int colLow = col / 3 * 3;
            int colHigh = (col / 3 + 1) * 3;
            for (int m = rowLow; m < rowHigh; m++) {
                for (int k = colLow; k < colHigh; k++) {
                    int pos = m * 9 + k;
                    Cell relatedCell = cells[pos];
                    if (pos != i && !cell.relatedCell.contains(relatedCell)) {
                        cell.relatedCell.add(relatedCell);
                    }
                }
            }
        }
    }

    public Cell[] getCells() {
        return cells;
    }

    public Cell getCell(int pos) {
        return cells[pos];
    }

    public Cell getCell(Position position) {
        return cells[position.toPos()];
    }

    public String getDisplayString() {
        return "";
    }

    public static SudokuBoard createFromString(String s) {
        String[] numberStrings = s.split("\\s+");
        if (numberStrings.length != CELL_COUNT) {
            throw new IllegalArgumentException(String.format("size %d != %d", numberStrings.length, CELL_COUNT));
        }

        SudokuBoard board = new SudokuBoard();
        for (int i = 0; i < numberStrings.length; i++) {
            int number = Integer.valueOf(numberStrings[i]);
            if (number < 0 || number > 9) {
                throw new IllegalArgumentException(String.format("violation: 0 <= n <= 9, n = %d", number));
            }

            board.getCells()[i].setValue(number);
        }

        return board;
    }

    public static Position toPosition(int pos) {
        return new Position(pos / 9, pos % 9);
    }
}
