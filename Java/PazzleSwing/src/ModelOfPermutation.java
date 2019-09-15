import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModelOfPermutation {
    private List<List<PazzleBlock>> listOfBlocks;
    private static final int MAINSIZEH = 600, MAINSIZEW = 600;

    public ModelOfPermutation(BufferedImage original, int countOfPictH, int countOfPictW) {
        listOfBlocks = new ArrayList<>(countOfPictH);
        for (int i = 0; i < countOfPictH; i++) {
            listOfBlocks.add(new ArrayList<>());
        }

        double coordX = 0;
        double coordY = 0;
        double smallWidth = original.getWidth()/countOfPictW;
        double smallHeight = original.getHeight()/countOfPictH;
        double sizeW = MAINSIZEW/countOfPictW;
        double sizeH = MAINSIZEH/countOfPictH;

        for (int i = 0; i < countOfPictH; i++) {
            listOfBlocks.set(i, new ArrayList<>(countOfPictW));
            for (int j = 0; j < countOfPictW; j++) {
                BufferedImage bufImg = original.getSubimage((int)coordX, (int)coordY, (int)smallWidth, (int)smallHeight);
                Image img = bufImg.getScaledInstance((int)sizeW, (int)sizeH, Image.SCALE_DEFAULT);
                listOfBlocks.get(i).add(new PazzleBlock(i, j, new ImageIcon(img)));
                coordX += smallWidth;
            }
            coordY += smallHeight;
            coordX = 0;
        }
        shuffle(countOfPictW, countOfPictH);
    }

    private void shuffle(int width, int height) {
        List<PazzleBlock> src = new ArrayList<>();
        listOfBlocks.forEach(src::addAll);
        Collections.shuffle(src);
        listOfBlocks.forEach(List::clear);

        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PazzleBlock pb = src.get(index);
                pb.setCurrentRow(i);
                pb.setCurrentCol(j);
                listOfBlocks.get(i).add(pb);
                index++;
            }
        }
    }

    public ImageIcon getCell(int row, int col) {
        return listOfBlocks.get(row).get(col).getImage();
    }

    public boolean isSuccess() {
        AtomicBoolean res = new AtomicBoolean(true);
        listOfBlocks.forEach(row -> {
            row.forEach(col -> {
                if (col.getCurrentCol() != col.getCORRECTCOL() || col.getCurrentRow() != col.getCORRECTROW())
                    res.set(false);
            });
        });
        return res.get();
    }

    public void swap(int row1, int col1, int row2, int col2) {
        PazzleBlock pbl = listOfBlocks.get(row1).get(col1);
        PazzleBlock pb2 = listOfBlocks.get(row2).get(col2);
        listOfBlocks.get(row1).set(col1, pb2);
        listOfBlocks.get(row2).set(col2, pbl);

        setNewIdx(listOfBlocks.get(row1).get(col1), row1, col1);
        setNewIdx(listOfBlocks.get(row2).get(col2), row2, col2);
    }

    public void setNewIdx(PazzleBlock pb, int newRow, int newCol) {
        pb.setCurrentRow(newRow);
        pb.setCurrentCol(newCol);
    }
}
