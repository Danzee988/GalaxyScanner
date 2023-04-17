import Methods.HashMaps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Methods.HashMaps.createSizeMap;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class HashMapTests {

    public static int[] pixelArray;
    public static HashMap<String, HashMap<Integer, List<Integer>>> result;

    @BeforeEach
    public void setUp() {
        // Initialize the pixel array
        pixelArray = new int[]{0, 1, 1, 0, -1, -1, 2, 2, -1, 3, 3, -1};
        result = HashMaps.createStarMap(pixelArray);

    }

    @AfterEach
    public void tearDown() {
        // Clean up resources used by the test
        pixelArray = null;
        result = null;
    }
    @Test
    public void testCreateStarMap() {

        // Call the method being tested
        result = HashMaps.createStarMap(pixelArray);

        // Check that the result is not null
        assertNotNull(result);

        // Check that the spotMap and valueMap are not null
        HashMap<Integer, List<Integer>> spotMap = result.get("spotMap");
        HashMap<Integer, List<Integer>> valueMap = result.get("valueMap");
        assertNotNull(spotMap);
        assertNotNull(valueMap);

        // Check that the spotMap contains the expected keys and values
        System.out.println("SpotMap : " + spotMap);
        System.out.println("valueMap : " +valueMap);
        assertEquals(2, spotMap.size());
        assertTrue(spotMap.containsKey(0));
        assertTrue(spotMap.containsKey(1));
        assertFalse(spotMap.containsKey(3));
        List<Integer> expectedSpotMapValues0 = new ArrayList<Integer>();
        expectedSpotMapValues0.add(0);
        expectedSpotMapValues0.add(3);
        assertEquals(expectedSpotMapValues0, spotMap.get(0));
        List<Integer> expectedSpotMapValues2 = new ArrayList<Integer>();
        expectedSpotMapValues2.add(1);
        expectedSpotMapValues2.add(2);
        assertEquals(expectedSpotMapValues2, spotMap.get(1));

        // Check that the valueMap contains the expected keys and values
        assertEquals(2, valueMap.size());
        assertTrue(valueMap.containsKey(0));
        assertTrue(valueMap.containsKey(1));
        assertTrue(valueMap.get(1).contains(1));
    }

    @Test
    public void testCreateSizeMap(){
        HashMap<Integer, Integer> sizeMap = createSizeMap(pixelArray);         // Create a HashMap to store the size of each spot
        HashMap<String, HashMap<Integer, List<Integer>>> result = HashMaps.createStarMap(pixelArray);
        HashMap<Integer, List<Integer>> valueMap = result.get("valueMap");

        System.out.println("SizeMap : " + sizeMap);
        System.out.println("valueMap : " + valueMap);
        assertEquals(2, valueMap.size());

        assertEquals(2, sizeMap.size());
        assertTrue(sizeMap.containsKey(0));
        assertTrue(sizeMap.containsKey(1));

        assertTrue(sizeMap.get(0) == 4);
        assertTrue(valueMap.get(1).contains(2));
    }

    @Test
    public void testSpotCount(){

    }
}
