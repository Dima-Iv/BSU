import javax.swing.*;

public class PazzleBlock {
    private int currentRow;
    private int currentCol;

    private final int CORRECTROW;
    private final int CORRECTCOL;

    private ImageIcon image;

    public PazzleBlock(int correctRow, int correctCol, ImageIcon img) {
        this.CORRECTROW = correctRow;
        this.CORRECTCOL = correctCol;
        this.image = img;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getCORRECTROW() {
        return CORRECTROW;
    }

    public int getCORRECTCOL() {
        return CORRECTCOL;
    }
}
