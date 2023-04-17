package Methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMaps {
    public static HashMap<String, HashMap<Integer, List<Integer>>> createStarMap(int[] pixelArray) {
        HashMap<Integer, List<Integer>> spotMap = new HashMap<>();           // Create a new HashMap to store a list of values for each root
        HashMap<Integer, List<Integer>> valueMap = new HashMap<>();          // Create a new HashMap to store a set of values for each root

        for (int i = 0; i < pixelArray.length; i++) {                        // For each pixel in the image
            if (pixelArray[i] != -1) {                                       // If the pixel is not background
                int root = unionFind.find(pixelArray, i);                    // Get the root of the current spot
                if (!spotMap.containsKey(root)) {                            // If the root is not already in the HashMap, create a new List and Set for it
                    spotMap.put(root, new ArrayList<Integer>());             // Create a new List for the root
                    valueMap.put(root, new ArrayList<Integer>());
                    // Create a new Set for the root
                }
                valueMap.get(root).add(pixelArray[i]);                       // Add the value to the Set for the root
                if (!spotMap.get(root).contains(pixelArray[i])) {            // If the List for the root does not already contain the value
                    spotMap.get(root).add(pixelArray[i]);                    // Add the value to the List for the root
                }
            }
        }

        HashMap<String, HashMap<Integer, List<Integer>>> resultMap = new HashMap<>();   // Create a new HashMap to store both spotMap and valueMap
        resultMap.put("spotMap", spotMap);                                              // Put spotMap in resultMap with key "spotMap"
        resultMap.put("valueMap", valueMap);                                            // Put valueMap in resultMap with key "valueMap"
        return resultMap;
    }

    public static HashMap<String, HashMap<Integer, List<Integer>>> createXYMap(int[] xArray, int[] yArray, int[] pixelArray) {
        HashMap<Integer, List<Integer>> xMap = new HashMap<>(); // Create a new HashMap to store a list of x values for each root
        HashMap<Integer, List<Integer>> yMap = new HashMap<>(); // Create a new HashMap to store a list of y values for each root

        for (int i = 0; i < xArray.length; i++) {                        // For each pixel in the image
            if (xArray[i] != -1) {                                       // If the pixel is not background
                int root = unionFind.find(pixelArray, i);                    // Get the root of the current spot
                if (!xMap.containsKey(root)) {                            // If the root is not already in the HashMap, create a new List and Set for it
                    xMap.put(root, new ArrayList<Integer>());             // Create a new List for the root
                    yMap.put(root, new ArrayList<Integer>());
                    // Create a new Set for the root
                }
                xMap.get(root).add(xArray[i]);                       // Add the value to the Set for the root
                yMap.get(root).add(yArray[i]);

            }
        }
        HashMap<String, HashMap<Integer, List<Integer>>> resultMap = new HashMap<>(); // Create a new HashMap to store both spotMap and valueMap
        resultMap.put("xMap", xMap); // Put spotMap in resultMap with key "xMap"
        resultMap.put("yMap", yMap); // Put valueMap in resultMap with key "yMap"
        return resultMap;
    }



    public static HashMap<Integer, List<Integer>> createRedMap(int[] pixelArray, int[] redArray) {
        HashMap<Integer, List<Integer>> redMap = new HashMap<>();   // Create a new HashMap to store the red value of each spot
        for (int i = 0; i < redArray.length; i++) {                  // For each pixel in the image
            if (redArray[i] != -1) {                                 // If the pixel is not background
                int root = unionFind.find(pixelArray, i);            // Get the root of the current spot
                if (!redMap.containsKey(root)) {                     // If the root is not already in the HashMap, create a new List for it
                    redMap.put(root, new ArrayList<Integer>());      // Create a new List for the root
                }
                redMap.get(root).add(redArray[i]);                   // Add the value to the List for the root
            }
        }
        return redMap;
    }

    public static HashMap<Integer, Double> calculateRedAverages(HashMap<Integer, List<Integer>> map) {
        HashMap<Integer, Double> redAverages = new HashMap<>();  // Create a new HashMap to store the average red value of each spot

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) { // For each spot in the redMap HashMap
            int spot = entry.getKey(); // Get the spot ID
            List<Integer> values = entry.getValue(); // Get the list of red values for the spot
            double sum = 0.0;
            for (int value : values) {
                sum += value;
            }
            double average = sum / values.size() / 255; //Normalize value to 0-1 scale
            average = Math.round(average * 10000.0) / 10000.0; // Round the average to 4 decimal places
            redAverages.put(spot, average); // Add the average red value to the redAverages HashMap
        }

        return redAverages;
    }



    public static HashMap<Integer, Integer> createSizeMap(int pixelArray[]) {
        HashMap<Integer, List<Integer>> spotMap = new HashMap<>();           // Create a new HashMap to store a list of values for each root
        HashMap<Integer, Integer> sizeMap = new HashMap<>();                 // Create a new HashMap to store the size of each spot

        for (int i = 0; i < pixelArray.length; i++) {                        // For each pixel in the image
            if (pixelArray[i] != -1) {                                       // If the pixel is not background
                int root = unionFind.find(pixelArray, i);                    // Get the root of the current spot
                if (!spotMap.containsKey(root)) {                            // If the root is not already in the HashMap, create a new List and Set for it
                    spotMap.put(root, new ArrayList<Integer>());             // Create a new List for the root
                    sizeMap.put(root, 0);
                }
                spotMap.get(root).add(pixelArray[i]);                        // Add the value to the Set for the root
                sizeMap.put(root, sizeMap.get(root) + 1);                    // Increment the size of the spot
            }
        }
        return sizeMap;
    }
}
