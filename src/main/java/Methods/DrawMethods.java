package Methods;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;

public class DrawMethods {
       public static void drawCirclesAroundSpotsUsingXYMap(HashMap<String, HashMap<Integer, List<Integer>>> xyMap, WritableImage image, WritableImage image1, Color circleColor, int minSize) {
        PixelWriter writer = image.getPixelWriter();
        PixelWriter writer1 = image1.getPixelWriter();

        // Iterate over each spot in the xMap
        for (int root : xyMap.get("xMap").keySet()) {
            // Get the center point of the spot by finding the average of the x and y coordinates of each pixel in the spot
            List<Integer> xPixels = xyMap.get("xMap").get(root);
            List<Integer> yPixels = xyMap.get("yMap").get(root);
            if (xPixels.size() > minSize) {
                int sumX = 0;
                int sumY = 0;
                for (int i = 0; i < xPixels.size(); i++) { // Get the sum of all the x and y coordinates
                    double x = xPixels.get(i);
                    double y = yPixels.get(i);
                    sumX += x;
                    sumY += y;
                }
                int centerX = sumX / xPixels.size(); // Get the average of all the x coordinates
                int centerY = sumY / yPixels.size(); // Get the average of all the y coordinates
                int smallestX = Integer.MAX_VALUE;
                int smallestY = Integer.MAX_VALUE;
                int biggestX = Integer.MIN_VALUE;
                int biggestY = Integer.MIN_VALUE;
                int circleRadius;

                //this is getting the smallest and biggest x and y values
                for (int i = 0; i < xPixels.size(); i++) {
                    int x = xPixels.get(i);
                    int y = yPixels.get(i);
                    if (x < smallestX) {
                        smallestX = x;
                    }
                    if (y < smallestY) {
                        smallestY = y;
                    } if (x > biggestX) {
                        biggestX = x;
                    }
                    if (y > biggestY) {
                        biggestY = y;
                    }
                }

                //this is getting the radius of the circle, it is the distance from the center to the furthest point, the plus 5 is an offset
                int smallestXDistance = centerX - smallestX;
                int smallestYDistance = centerY - smallestY;
                int biggestXDistance = biggestX - centerX;
                int biggestYDistance = biggestY - centerY;

                int totalX = biggestX - smallestX;
                int totalY = biggestY - smallestY;

                if (totalX > totalY){
                    if (smallestXDistance > biggestXDistance){
                        circleRadius = smallestXDistance + 5;
                    } else {
                        circleRadius = biggestXDistance + 5;
                    }
                } else {
                    if (smallestYDistance > biggestYDistance){
                        circleRadius = smallestYDistance + 5;
                    } else {
                        circleRadius = biggestYDistance + 5;
                    }
                }

                // Draw an empty circle around the center point with the specified radius and color
                for (int x = centerX - circleRadius; x <= centerX + circleRadius; x++) {                                               // Loop through each x value in the circle
                    for (int y = centerY - circleRadius; y <= centerY + circleRadius; y++) {                                           // Loop through each y value in the circle
                        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {                                       // Make sure the x and y values are within the image
                            double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));                // Calculate the distance from the center of the circle
                            if (distance >= circleRadius - 1 && distance <= circleRadius) {
                                // On the edge of the circle, draw the outline in the circleColor
                                writer.setColor(x, y, circleColor);
                                writer1.setColor(x, y, circleColor);

                            } else if (distance < circleRadius && distance >= circleRadius - 3) {
                                // Within two pixel inside the circle, draw a transparent color
                                Color translucentColor = circleColor.deriveColor(0, 1, 1, 0.0); // alpha value of 0 for full transparency
                                writer.setColor(x, y, translucentColor);
                                writer1.setColor(x, y, translucentColor);
                            }
                        }
                    }
                }
            }
        }
    }



}
