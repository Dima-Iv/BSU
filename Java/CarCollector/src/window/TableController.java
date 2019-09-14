package window;

import collector.Collector;
import handler.Handler;
import handler.SAXHandler;
import handler.Transform;
import handler.XSDHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.util.*;

public class TableController implements Initializable {
    private ObservableList<Collector> collectors;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label labelMax;

    @FXML
    private TableView<Collector> table;
    @FXML
    private TableColumn<Collector, String> brandColumn;
    @FXML
    private TableColumn<Collector, String> countryColumn;
    @FXML
    private TableColumn<Collector, Integer> priceColumn;
    @FXML
    private TableColumn<Collector, Integer> idColumn;

    @FXML
    private MenuItem openDOMMenuItem;
    @FXML
    private MenuItem openSAXMenuItem;
    @FXML
    private MenuItem openBinaryMenuItem;
    @FXML
    private MenuItem writeDOMMenuItem;
    @FXML
    private MenuItem saveBinaryMenuItem;
    @FXML
    private MenuItem checkMenu;
    @FXML
    private MenuItem htmlXsltMenuItem;
    @FXML
    private MenuItem txtXsltMenuItem;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TextField brandTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField priceTextField;

    private Handler handler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectors = FXCollections.observableArrayList();

        initTable();
        handler = new Handler();
        initOpenMenuItem();
        initSAXOpenMenuItem();
        initOpenBinaryMenuItem();
        initWriteMenuItem();
        initSaveBinaryMenuItem();
        initCheckMenuItem();
        initHtmlXsltMenuItem();
        initTxtXsltMenuItem();
        initAddButton();
        initDeleteButton();
    }


    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Collector, Integer>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Collector, String>("brand"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Collector, String>("country"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Collector, Integer>("price"));
    }

    private void initOpenMenuItem() {
        openDOMMenuItem.setOnAction(event -> {
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openFileChooser()) != null) {
                    Vector<Collector> vectorCollectors = handler.readXmlFile(file.getAbsolutePath());
                    collectors.clear();
                    collectors = FXCollections.observableArrayList(vectorCollectors);
                    table.setItems(collectors);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Exception!", 0);
            }
        });
    }

    private void initOpenBinaryMenuItem() {
        openBinaryMenuItem.setOnAction(event -> {
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openFileChooser()) != null) {
                    FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                    ObjectInputStream oin = new ObjectInputStream(fis);

                    Collector collector = null;
                    collectors.clear();
                    try {
                        while ((collector = (Collector) oin.readObject()) != null) {
                            collectors.add(collector);
                        }
                    } catch (EOFException ignored) {
                        // as expected
                    } finally {
                        if (fis != null)
                            fis.close();
                    }
                    table.setItems(collectors);
                }
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Exception!", 0);
            }
        });
    }

    private void initWriteMenuItem() {
        writeDOMMenuItem.setOnAction(event -> {
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openSaveFileChooser()) != null) {
                    Vector<Collector> vector = new Vector<>();
                    for (Collector collector : collectors) {
                        vector.add(collector);
                    }
                    handler.writeXmlFile(vector, file);
                }
            } catch (ParserConfigurationException | TransformerException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Exception!", 0);
            }
        });
    }

    private void initSaveBinaryMenuItem() {
        saveBinaryMenuItem.setOnAction(event -> {
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openSaveFileChooser()) != null) {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file.getAbsolutePath());

                    ObjectOutputStream oos = null;
                    oos = new ObjectOutputStream(fos);

                    for (Collector collector : collectors) {
                        oos.writeObject(collector);
                    }

                    oos.flush();
                    oos.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Exception!", 0);
            }
        });
    }

    private void initAddButton() {
        addButton.setOnAction(event -> {
            String brand = brandTextField.getText();
            String country = countryTextField.getText();
            int price = Integer.parseInt(priceTextField.getText());
            if (brand != null && country != null && price >= 0) {
                collectors.add(new Collector(collectors.size(), brand, country, price));
                table.setItems(collectors);
            } else {
                JOptionPane.showMessageDialog(null, "Error", "Exception!", 0);
            }
        });

    }

    private void initDeleteButton() {
        deleteButton.setOnAction(event -> {
            int row = table.getSelectionModel().getSelectedIndex();
            if (row >= 0)
                table.getItems().remove(row);
        });
    }

    private void initSAXOpenMenuItem() {
        openSAXMenuItem.setOnAction(event -> {
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openFileChooser()) != null) {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    SAXHandler saxHandler = new SAXHandler();
                    saxParser.parse(file, saxHandler);
                    labelMax.setText(Integer.toString(saxHandler.getMax()));
                    totalPriceLabel.setText(Integer.toString(saxHandler.getTotal()));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error", "Exception!", 0);
            }
        });
    }

    private void initCheckMenuItem() {
        checkMenu.setOnAction(event -> {
            XSDHandler xsdHandler = new XSDHandler();
            try {
                WindowLoader loaderWindow = new WindowLoader();

                File file;
                if ((file = loaderWindow.openFileChooser()) != null) {
                    InputStream is = getClass().getResourceAsStream("/checker.xsd");

                    if (xsdHandler.validateXMLByXSD(file, new StreamSource(is))) {
                        JOptionPane.showMessageDialog(null, "File checked", "Information", 1);
                    }
                }

            } catch (SAXException | IOException e) {
                JOptionPane.showMessageDialog(null, "File have mistake", "Exception!", 0);
            }
        });
    }

    private void initHtmlXsltMenuItem() {
        htmlXsltMenuItem.setOnAction(event -> {
            Transform transformer = new Transform();
            try {
                WindowLoader windowLoader = new WindowLoader();

                File file;
                if ((file = windowLoader.openFileChooser()) != null) {
                    File resultFile;
                    if ((resultFile = windowLoader.openSaveFileChooser()) != null) {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("transformHtml.xsl");
                        if (transformer.transformToHtml(new StreamSource(is), file.getAbsolutePath(), resultFile.getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "File transform", "Information", 1);
                        }
                    }
                }
            } catch (TransformerException e) {
                JOptionPane.showMessageDialog(null, "Error", "Exception!", 0);
            }
        });
    }

    private void initTxtXsltMenuItem() {
        txtXsltMenuItem.setOnAction(event -> {
            Transform transformer = new Transform();
            try {
                WindowLoader windowLoader = new WindowLoader();
                File file;
                if ((file = windowLoader.openFileChooser()) != null) {

                    File resultFile;
                    if ((resultFile = windowLoader.openSaveFileChooser()) != null) {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("transformTxt.xsl");
                        if (transformer.transformToTxt(new StreamSource(is), file.getAbsolutePath(), resultFile.getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "File transform", "Information", 1);
                        }
                    }
                }
            } catch (TransformerException e) {
                JOptionPane.showMessageDialog(null, "Error", "Exception!", 0);
            }
        });
    }
}