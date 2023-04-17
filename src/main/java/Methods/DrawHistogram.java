package Methods;

import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class DrawHistogram {
    public void barChart(Image defaultImage, XYChart<String, Integer> barChart) {
        barChart.getData().clear();
        int height = (int) defaultImage.getHeight();
        int width = (int) defaultImage.getWidth();
        PixelReader pixelReader = defaultImage.getPixelReader();
        //grayscale();
        //hisImage.setImage(writableImage1);
        //PixelWriter pixelWriter = writableImage1.getPixelWriter();

        int[] red = new int[256];
        int[] green = new int[256];
        int[] blue = new int[256];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                int red1 = (int) (color.getRed() * 255);
                int green1 = (int) (color.getGreen() * 255);
                int blue1 = (int) (color.getBlue() * 255);

                red[red1]++;
                green[green1]++;
                blue[blue1]++;
            }
        }

        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        series1.setName("Red");

        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        series2.setName("Green");

        XYChart.Series<String, Integer> series3 = new XYChart.Series<>();
        series3.setName("Blue");

        for (int i = 0; i < 256; i++) {
            series1.getData().add(new XYChart.Data<>(Integer.toString(i), red[i]));
            series2.getData().add(new XYChart.Data<>(Integer.toString(i), green[i]));
            series3.getData().add(new XYChart.Data<>(Integer.toString(i), blue[i]));
        }

        barChart.getData().addAll(series1, series2, series3);
    }

}
