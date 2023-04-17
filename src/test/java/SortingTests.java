import Methods.HashMaps;
import Methods.Merging;
import Methods.Sorting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static Methods.HashMaps.createSizeMap;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortingTests {

    private int[] pixelArray;
    private HashMap<String, HashMap<Integer, List<Integer>>> result;

    @BeforeEach
    public void setUp() {
        // Initialize the pixel array
        pixelArray = new int[]{0, 1, 1, 0, 0, -1, -1, 2, 2, -1, 3, 3, -1};
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources used by the test
        pixelArray = null;
        result = null;
    }
    public static List<Map.Entry<Integer, Integer>> sortSpotsBySize(int minSize, int[] pixelArray) {
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
    @Test
    public void testSortBySize(){
        HashMap<Integer, Integer> sizeMap = createSizeMap(pixelArray);         // Create a HashMap to store the size of each spot
        result = HashMaps.createStarMap(pixelArray);
        HashMap<Integer, List<Integer>> valueMap = result.get("valueMap");

        System.out.println("SizeMap : " + sizeMap);
        System.out.println("valueMap : " + valueMap);

        Merging merging = new Merging();
        List<Map.Entry<Integer, Integer>> sortedSpotList = sortSpotsBySize(2, pixelArray);
        System.out.println("SortedSpotList : " + sortedSpotList);

        assertEquals(2, sortedSpotList.size());
        assertTrue(sortedSpotList.get(0).getKey() == 0);
        assertTrue(sortedSpotList.get(1).getKey() == 1);

        assertTrue(valueMap.get(1).size() == 4);
        assertTrue(valueMap.get(0).size() == 5);


    }
}
