package Methods;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

import static Methods.DrawMethods.drawCirclesAroundSpotsUsingXYMap;
import static Methods.HashMaps.*;


public class Merging {
    //------------------------------------------------------------------------------------------
    // Initialize the hashmap to keep track of labeled spots
    public static int[] pixelArray; // Array to store the pixel values of the image (used for union-find)
    public static int redArray[]; // Array to store the red values of each pixel (used for union-find)
    public static int greenArray[]; // Array to store the green values of each pixel (used for union-find)
    public static int blueArray[]; // Array to store the blue values of each pixel (used for union-find)

    public int xArray[]; // Array to store the x values of each pixel (used for union-find)

    public int yArray[]; // Array to store the y values of each pixel (used for union-find)

    public void segmentImage(Image defaultImage, ImageView mainImage, ImageView blackWhiteImage , ImageView treeImage, int minSize, double threshold, int changeNow, int starNumber) {
        // Get the height and width of the image
        int height = (int) defaultImage.getHeight();
        int width = (int) defaultImage.getWidth();

        // Create a PixelReader object to read the pixel values of the defaultImage
        PixelReader pixelReader = defaultImage.getPixelReader();

        // Create a new WritableImage object with the same height and width as the defaultImage
        WritableImage labelImage = new WritableImage(width, height);
        WritableImage circleImage = new WritableImage(width, height);

        // Create a PixelWriter object to write label values to the new WritableImage
        PixelWriter labelWriter = labelImage.getPixelWriter();
        PixelWriter circleWriter = circleImage.getPixelWriter();

        pixelArray = new int[ width * height];                                                          // Array to store the pixel values of the image (used for union-find)

        redArray = new int[ width * height];
        blueArray = new int[ width * height];
        greenArray = new int[ width * height];

        xArray = new int[ width * height];
        yArray = new int[ width * height];
        
        Color changeColor = Color.WHITE;
        
        if (changeNow == 0) {
            changeColor = Color.WHITE;
        }
         else if (changeNow == 1) {
            changeColor = Color.YELLOW;
        }

        for (int y = 0; y < height; y++) { //row
            for (int x = 0; x < width; x++) { //column
                // Get the color of the current pixel
                Color color = pixelReader.getColor(x, y);

                // Calculate the grayscale value of the pixel using a weighted sum of the color channels
                double gray = color.getRed() * 0.2989 + color.getGreen() * 0.5870 + color.getBlue() * 0.1140;

                Color black = Color.BLACK;                                                              // Create a new Color object for black
                Color white = changeColor;                                                              // Create a new Color object for white

                // Set the new color of the pixel based on the grayscale value and threshold
                Color newColor = (gray > threshold) ? white : black;

                if (newColor == Color.BLACK) {
                    // This pixel belongs to the background
                    pixelArray[y * width + x] = -1;                                                     // Set the pixel value to -1 (background)
                    redArray[y * width + x] = -1;
                    greenArray[y * width + x] = -1;
                    blueArray[y * width + x] = -1;
                    xArray[y * width + x] = -1;                                                                     // Add the x value of the pixel to the xValues ArrayList
                    yArray[y * width + x] = -1;
                    labelWriter.setColor(x, y, Color.BLACK);                                            // Set the color of the pixel in the new image to black (background)
                    circleWriter.setColor(x, y, pixelReader.getColor(x, y));
                } else {
                    pixelArray[y * width + x] = y* width + x;                                           // Set the pixel value to the index of the pixel in the array (foreground)
                    redArray[y * width + x] = (int) (color.getRed() * 255);                            // Set the red value of the pixel to the red value of the pixel in the array (foreground)
                    greenArray[y * width + x] = (int) (color.getGreen() * 255);                        // Set the green value of the pixel to the green value of the pixel in the array (foreground)
                    blueArray[y * width + x] = (int) (color.getBlue() * 255);                          // Set the blue value of the pixel to the blue value of the pixel in the array (foreground)
                    xArray[y * width + x] = x;                                                         // Add the x value of the pixel to the xValues ArrayList
                    yArray[y * width + x] = y;                                                         // Add the y value of the pixel to the yValues ArrayList
                    labelWriter.setColor(x, y, changeColor);                                          // Set the color of the pixel in the new image to white (foreground)
                    circleWriter.setColor(x, y, pixelReader.getColor(x, y));
                }
            }
        }

        mergeUnionArray(width,height, pixelArray, minSize, labelWriter);

        HashMap<String, HashMap<Integer, List<Integer>>> xyMap = createXYMap(xArray, yArray, pixelArray);

        drawCirclesAroundSpotsUsingXYMap(xyMap, circleImage, labelImage, Color.BLUE, minSize);

        if (changeNow == 2){
            differentColorStars(pixelArray,minSize,labelWriter,xArray,yArray);
        }
        if (changeNow == 3){
            oneStarChange(pixelArray,minSize,labelWriter,xArray,yArray,starNumber);
        }

        //--------------------------------------------------------------
        //printUnionArray(width);
        //printRedArray(width);
        //--------------------------------------------------------------


        // Set the new image to the ImageView
        mainImage.setImage(labelImage);
        blackWhiteImage.setImage(circleImage);
        treeImage.setImage(circleImage);

        HashMap<String, HashMap<Integer, List<Integer>>> spotMap = createStarMap(pixelArray);

        List<Map.Entry<Integer, Integer>> sortedSpotList = sortSpotsBySize(minSize);

        // Iterate over each spot in the spot map
        int counter = 1;
        for (Map.Entry<Integer, Integer> entry : sortedSpotList) {
            int root = entry.getKey();
            // Check if the spot size is greater than or equal to minSize
            if (spotMap.get("valueMap").get(root).size() >= minSize) {

                HashMap<Integer, List<Integer>> xMap = xyMap.get("xMap");
                HashMap<Integer, List<Integer>> yMap = xyMap.get("yMap");

                    List<Integer> xValues = xMap.get(root);
                    List<Integer> yValues = yMap.get(root);
                int lastIndex = xValues.size() - 1; // get the index of the last x and y values for the root
                int x = xValues.get(lastIndex) - 20; // get the last x value for the root
                int y = yValues.get(lastIndex) + 20; // get the last y value for the root

                // Create a Canvas object with the same dimensions as the ImageView
                Canvas canvas = new Canvas(mainImage.getImage().getWidth(), mainImage.getImage().getHeight());
                Canvas canvas2 = new Canvas(blackWhiteImage.getImage().getWidth(), blackWhiteImage.getImage().getHeight());

                // Get the GraphicsContext of the Canvas
                GraphicsContext gc = canvas.getGraphicsContext2D();
                GraphicsContext gc2 = canvas2.getGraphicsContext2D();

                // Set the font and color for the number
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                gc2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                gc.setFill(Color.GREEN);
                gc2.setFill(Color.GREEN);

                // Draw the number on the Canvas at the specified x and y coordinates
                gc.fillText(String.valueOf(counter), x, y);
                gc2.fillText(String.valueOf(counter), x, y);
                counter++;

                // Create an ImageView with the same image as the original ImageView
                ImageView numberedImage = new ImageView(mainImage.getImage());
                ImageView numberedImage2 = new ImageView(blackWhiteImage.getImage());

                // Add the Canvas to a Group and set it as the content of the new ImageView
                Group group = new Group(numberedImage, canvas);
                Group group2 = new Group(numberedImage2, canvas2);
                numberedImage = new ImageView(group.snapshot(null, null));
                numberedImage2 = new ImageView(group2.snapshot(null, null));

                // Set the new ImageView as the content of the parent Pane
                mainImage.setImage(numberedImage.getImage());
                blackWhiteImage.setImage(numberedImage2.getImage());
                treeImage.setImage(numberedImage2.getImage());

            }
        }
    }

    public static void countSpotsText(TextField totalSpots, int minSize) {
        HashMap<Integer, List<Integer>> valueMap = createStarMap(pixelArray).get("valueMap");

        int count = 0;
        for (List<Integer> spot : valueMap.values()) {     // For each spot in the labelMap
            if (!spot.isEmpty()) {                        // If the list is not empty
                if (spot.size() > minSize){                 // If the spot is larger than the minimum size
                count++;
                }
            }
        }
        totalSpots.setText(String.valueOf(count));        // Set the text of the totalSpots TextField to the spot count
    }

    public static int countSpotsTest(int minSize,HashMap<Integer, List<Integer>> valueMap) {
        int count = 0;
        for (List<Integer> spot : valueMap.values()) {     // For each spot in the labelMap
            if (!spot.isEmpty()) {                        // If the list is not empty
                if (spot.size() > minSize){                 // If the spot is larger than the minimum size
                    count++;
                }
            }
        }
        return count;
    }

    public void mergeUnionArray( int width, int height, int[] array, int minSize,PixelWriter pixelWriter){
        for (int i = 0; i < array.length; i++) {                                           //for each pixel in the image
            if ( array[i] != -1) {                                                         //if pixel is not background
                if(i+1 < array.length && array[i+1] != -1 && i % width != width - 1)       //if pixel is not on the right edge of the image
                   unionFind.union(array, i, i+1);                                      //merge with pixel to the right

                if(i+width < array.length && array[i+width] != -1)                         //if pixel is not on the bottom edge of the image
                    unionFind.union(array, i, i+width);                                 //merge with pixel below
            }
        }
        // Get the size of each star
        HashMap<Integer, Integer> sizeMap = createSizeMap(array);                                 // Create a HashMap to store the size of each spot

        // Set the color of pixels in small stars to black
        for (int y = 0; y < height; y++) {                                                        // Iterate over the rows
            for (int x = 0; x < width; x++) {                                                     // Iterate over the columns
                int root = unionFind.find(array, y * width + x);                               // Get the root of the current pixel
                if (root != -1 && sizeMap.containsKey(root) && sizeMap.get(root) <= minSize) {     // If the root is not -1 and the size of the star is less than the minimum size
                    Color black = Color.BLACK;                                                    // Set the color to black
                    pixelWriter.setColor(x, y, black);
            }
        }
    }
    }

    public static void differentColorStars(int[] pixelArray, int minSize, PixelWriter pixelWriter, int [] xArray, int [] yArray) {
        // Create the spot map and XY map
        HashMap<String, HashMap<Integer, List<Integer>>> spotMap = createStarMap(pixelArray);
        HashMap<String, HashMap<Integer, List<Integer>>> xyMap = createXYMap(xArray, yArray,pixelArray);

        // Iterate over each spot in the spot map
        for (int root : spotMap.get("valueMap").keySet()) { // For each root in the spot map
            // Check if the spot size is greater than or equal to minSize
            if (spotMap.get("valueMap").get(root).size() >= minSize) {
                // Generate a random color
                Color randomColor = Color.rgb((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));

                // Get the x and y coordinates for the pixels in the spot
                List<Integer> xCoords = xyMap.get("xMap").get(root);
                List<Integer> yCoords = xyMap.get("yMap").get(root);

                // Set the color of each pixel in the spot to the random color
                for (int i = 0; i < xCoords.size(); i++) {
                    int x = xCoords.get(i);
                    int y = yCoords.get(i);
                    pixelWriter.setColor(x, y, randomColor);
                }
            }
        }
    }

    public static void oneStarChange(int[] pixelArray, int minSize, PixelWriter pixelWriter, int [] xArray, int [] yArray,int starNumber) {
        // Create the spot map and XY map
        HashMap<String, HashMap<Integer, List<Integer>>> spotMap = createStarMap(pixelArray);
        HashMap<String, HashMap<Integer, List<Integer>>> xyMap = createXYMap(xArray, yArray, pixelArray);

        // Get a list of stars with sizes greater than or equal to minSize
        List<Integer> largeStars = new ArrayList<>();
        List<Map.Entry<Integer, Integer>> sortedSpotList = sortSpotsBySize(minSize);

        for (int root : spotMap.get("valueMap").keySet()) {
            if (spotMap.get("valueMap").get(root).size() > minSize) {
                largeStars.add(root);
            }
        }

        // Generate a random color
        Color randomColor = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));

        // Choose a random star from the list of large stars
        if (!sortedSpotList.isEmpty()) {
            if (starNumber == 0) {
                int randomRoot = largeStars.get((int) (Math.random() * largeStars.size()));

                List<Integer> xRandom = xyMap.get("xMap").get(randomRoot);
                List<Integer> yRandom = xyMap.get("yMap").get(randomRoot);

                for (int i = 0; i < xRandom.size(); i++) {
                    int x = xRandom.get(i);
                    int y = yRandom.get(i);
                    pixelWriter.setColor(x, y, randomColor);
                }
            }

            // Set the color of each pixel in the star to the random color
            else {
                int index = 0;
                int root = sortedSpotList.get(starNumber - 1).getKey();

                // Get the x and y coordinates for the pixels in the star
                List<Integer> xSorted = xyMap.get("xMap").get(root);
                List<Integer> ySorted = xyMap.get("yMap").get(root);

                for (Map.Entry<Integer, Integer> entry : sortedSpotList) {
                    index++;
                    if (starNumber == index) {
                        System.out.println("index: " + index);
                        for (int i = 0; i < xSorted.size(); i++) {
                            int x = xSorted.get(i);
                            int y = ySorted.get(i);
                            pixelWriter.setColor(x, y, randomColor);
                        }
                    }
                }

            }
        }
    }


    public static List<Map.Entry<Integer, Integer>> sortSpotsBySize(int minSize) {
        HashMap<Integer, Integer> sizeMap = createSizeMap(pixelArray);         // Create a HashMap to store the size of each spot
        int[] roots = new int[sizeMap.size()];                                 // Create an array to store the roots
        int[] sizes = new int[sizeMap.size()];                                 // Create an array to store the sizes

        int index = 0;
        for (int root : sizeMap.keySet()) {                                    // Iterate over the roots in the size map
            roots[index] = root;
            sizes[index] = sizeMap.get(root);
            index++;
        }

        Sorting.quickSort(roots, sizes, 0, roots.length - 1);         // Sort the arrays using quick sort

        List<Map.Entry<Integer, Integer>> sortedSpotList  = new ArrayList<>();
        for (int i = 0; i < roots.length; i++) {                               // Iterate over the sorted arrays in reverse order
            if (sizes[i] > minSize) {                                          // If the size of the spot is greater than 5
                int root = roots[i];
                int size = sizes[i];
                sortedSpotList.add(new AbstractMap.SimpleEntry<>(root, size));  // Add the root and size to the sorted spot list
            }
        }
        return sortedSpotList;
    }

    public void printUnionArray(int width) {
        for (int i = 0; i < pixelArray.length; i++) {   //for each pixel in the image
            if (i % width == 0) {                       //if pixel is on the left edge of the image
                System.out.println();                   //print a new line
            }
            System.out.print(pixelArray[i] + " ");      //print the pixel value
        }
    }
    public void printRedArray(int width) {
        for (int i = 0; i < redArray.length; i++) {   //for each pixel in the image
            if (i % width == 0) {                       //if pixel is on the left edge of the image
                System.out.println();                   //print a new line
            }
            System.out.print(redArray[i] + " ");      //print the pixel value
        }
    }

    public void createDetailTree (TreeView<String> treeView,int minSize,boolean expandAll){
        HashMap<Integer, Integer> sizeMap = createSizeMap(pixelArray);

        HashMap<Integer, List<Integer>> redMap = createRedMap(pixelArray, redArray);
        HashMap<Integer, Double> redAverage = HashMaps.calculateRedAverages(redMap);
        HashMap<Integer, List<Integer>> greenMap = createRedMap(pixelArray, greenArray);
        HashMap<Integer, Double> greenAverage = HashMaps.calculateRedAverages(greenMap);
        HashMap<Integer, List<Integer>> blueMap = createRedMap(pixelArray, blueArray);
        HashMap<Integer, Double> blueAverage = HashMaps.calculateRedAverages(blueMap);

        List<Map.Entry<Integer, Integer>> sortedSpotList = sortSpotsBySize(minSize);

        TreeItem<String> rootTree = new TreeItem<>("Celestial Stars"); // Create a root item for the TreeView
        rootTree.setExpanded(true); // Expand the root item by default
        treeView.setRoot(rootTree); // Set the root of the TreeView to the root item

        // Iterates over the spotMap to create a new TreeItem for each spot
        int index = 1;
        for (Map.Entry<Integer, Integer> entry : sortedSpotList) {
            int root = entry.getKey(); // Get the spot ID

            // Creates a new TreeItem for the spot
            TreeItem<String> spotItem = new TreeItem<>("Celestial Object number " + index++);
            if (expandAll == true) {
                spotItem.setExpanded(true);
            } else {
                spotItem.setExpanded(false);
            }
                rootTree.getChildren().add(spotItem); // Add the spot item to the root item

                // Creates a new TreeItem for the average red value of the spot
                TreeItem<String> redItem = new TreeItem<>("Estimated Sulphur: " + (redAverage.get(root)));
                spotItem.getChildren().add(redItem); // Add the red item to the spot item

                // Creates a new TreeItem for the average green value of the spot
                TreeItem<String> greenItem = new TreeItem<>("Estimated Hydrogen: " + (greenAverage.get(root)));
                spotItem.getChildren().add(greenItem); // Add the red item to the spot item

                // Creates a new TreeItem for the average blue value of the spot
                TreeItem<String> blueItem = new TreeItem<>("Estimated oxygen: " + (blueAverage.get(root)));
                spotItem.getChildren().add(blueItem); // Add the red item to the spot item

                // Creates a new TreeItem for the size of the spot
                TreeItem<String> sizeItem = new TreeItem<>("Estimated Size (pixel units): " + sizeMap.get(root));
                spotItem.getChildren().add(sizeItem); // Add the size item to the spot item
            }
        }

    public void printSingleStar (TreeView<String> treeView,int minSize,int starNumber){
        HashMap<Integer, Integer> sizeMap = createSizeMap(pixelArray);

        HashMap<Integer, List<Integer>> redMap = createRedMap(pixelArray, redArray);
        HashMap<Integer, Double> redAverage = HashMaps.calculateRedAverages(redMap);
        HashMap<Integer, List<Integer>> greenMap = createRedMap(pixelArray, greenArray);
        HashMap<Integer, Double> greenAverage = HashMaps.calculateRedAverages(greenMap);
        HashMap<Integer, List<Integer>> blueMap = createRedMap(pixelArray, blueArray);
        HashMap<Integer, Double> blueAverage = HashMaps.calculateRedAverages(blueMap);

        List<Map.Entry<Integer, Integer>> sortedSpotList = sortSpotsBySize(minSize);

        TreeItem<String> rootTree = new TreeItem<>("Celestial Stars"); // Create a root item for the TreeView
        rootTree.setExpanded(true); // Expand the root item by default
        treeView.setRoot(rootTree); // Set the root of the TreeView to the root item

        // Iterates over the spotMap to create a new TreeItem for each spot
        int index = 1;
        starNumber = starNumber + 1;
        for (Map.Entry<Integer, Integer> entry : sortedSpotList) {
            int root = entry.getKey(); // Get the spot ID

            // Creates a new TreeItem for the spot
            TreeItem<String> spotItem = new TreeItem<>("Celestial Object number " + index++);
            spotItem.setExpanded(true);
            if (starNumber == index) {
                System.out.println("Star number " + starNumber + " found");
                rootTree.getChildren().add(spotItem); // Add the spot item to the root item

                // Creates a new TreeItem for the average red value of the spot
                TreeItem<String> redItem = new TreeItem<>("Estimated Sulphur: " + (redAverage.get(root)));
                spotItem.getChildren().add(redItem); // Add the red item to the spot item

                // Creates a new TreeItem for the average green value of the spot
                TreeItem<String> greenItem = new TreeItem<>("Estimated Hydrogen: " + (greenAverage.get(root)));
                spotItem.getChildren().add(greenItem); // Add the red item to the spot item

                // Creates a new TreeItem for the average blue value of the spot
                TreeItem<String> blueItem = new TreeItem<>("Estimated oxygen: " + (blueAverage.get(root)));
                spotItem.getChildren().add(blueItem); // Add the red item to the spot item

                // Creates a new TreeItem for the size of the spot
                TreeItem<String> sizeItem = new TreeItem<>("Estimated Size (pixel units): " + sizeMap.get(root));
                spotItem.getChildren().add(sizeItem); // Add the size item to the spot item
            }
        }
        }
    }
