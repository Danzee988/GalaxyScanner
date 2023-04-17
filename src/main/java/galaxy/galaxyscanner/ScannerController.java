package galaxy.galaxyscanner;

import Methods.DrawHistogram;
import Methods.Merging;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;


public class ScannerController {

    //Side Panel
    @FXML
    private Button btnSignout;
    @FXML
    private Button btnMain;

    @FXML
    private Button btnProcessed;
    @FXML
    private Button btnHistogram;
    @FXML
    private Pane pnlMain;
    @FXML
    private Pane pnlProcess;
    @FXML
    private Pane pnlHistogram;

    @FXML
    private TextField minSelection;
    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {//side panel with buttons each button corresponds to displaying a panel
        if (actionEvent.getSource() == btnMain) {
            pnlMain.setVisible(true);
            pnlProcess.setVisible(false);
        }
        if (actionEvent.getSource() == btnProcessed) {
            pnlMain.setVisible(false);
            pnlProcess.setVisible(true);
        }
        if (actionEvent.getSource() == btnSignout) {
            Platform.exit();
        }
    }//handles all the buttons on the left side of the screen

    @FXML
    ImageView image;

    @FXML
    ImageView scanndedImage;

    private Image defaultImage;

    private WritableImage writableImage1;

    @FXML
    private ComboBox<String> img;

    @FXML
    private Slider slider; //slider for the threshold

    @FXML
    private TextField starNumber;

    public void Chooser() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
//        File file = new File("C:\\Users\\danze\\Desktop\\1.jpg");

        Image image1 = new Image(String.valueOf(file));
        image.setImage(image1);

        int height=(int)image1.getHeight();
        int width=(int)image1.getWidth();
        PixelReader pixelReader=image1.getPixelReader();
        WritableImage writableImage = new WritableImage(width,height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }

        defaultImage = image1;
        writableImage1 = writableImage;
    }

    @FXML
    void pickImg(MouseEvent event) throws IOException {
        Chooser();

    }
    public void blackAndWhite() throws IOException {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        merging.segmentImage(defaultImage,scanndedImage,imageWithCircle,image, Integer.parseInt(minSize),starDetails,slider.getValue(),0);
        merging.countSpotsText(totalStars, Integer.parseInt(minSize));
        merging.createDetailTree(starDetails,Integer.parseInt(minSize),false);
    }

    @FXML
    void reset(ActionEvent event) {
        slider.setValue(0.5);
    }

    @FXML
    void threshold(MouseEvent event) {

    }

    @FXML
    public void singleStarChange() throws IOException {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        String star = starNumber.getText();
        merging.segmentImage(defaultImage,scanndedImage,imageWithCircle,image, Integer.parseInt(minSize),starDetails,slider.getValue(),3);
    }

    @FXML
    public void multiChange() throws IOException {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        merging.segmentImage(defaultImage,scanndedImage,imageWithCircle,image, Integer.parseInt(minSize),starDetails,slider.getValue(),2);
    }
    public void highlight() throws IOException {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        merging.segmentImage(defaultImage,scanndedImage,imageWithCircle,image, Integer.parseInt(minSize),starDetails,slider.getValue(),1);

    }

//    //Process Panel ----------------------------------------------
    @FXML
    private TreeView<String> starDetails;
    @FXML
    ImageView imageWithCircle;

    @FXML
    private TextField totalStars;

    @FXML
    private TextField findStar;

    @FXML
    void selectStar(ActionEvent event) {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        String star = findStar.getText();
        merging.printSingleStar(starDetails,Integer.parseInt(minSize), Integer.parseInt(star));
    }

    @FXML
    void expandTree(ActionEvent event) {
        Merging merging = new Merging();
        String minSize = minSelection.getText();
        merging.createDetailTree(starDetails,Integer.parseInt(minSize),true);

    }




    //Histogram Panel ----------------------------------------------
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    ImageView hisImage;


    public void barChart() {
        DrawHistogram drawHistogram = new DrawHistogram();
        drawHistogram.barChart(defaultImage,barChart);
        hisImage.setImage(writableImage1);

    }
}