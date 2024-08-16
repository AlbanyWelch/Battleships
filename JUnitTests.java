package Battleships;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.*;

public class JUnitTests {
    public int testsCompleted;

    @BeforeAll
    public static void setupClass() {

    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void Test1() { // File Configuration
        System.out.println("Starting Test 1...");
        BSModel instance = new BSModel("y");
        instance.change(0, 0);
        instance.change(0, 1);
        instance.change(0, 2);
        instance.change(0, 3);
        instance.change(0, 4);
        Assertions.assertEquals(4, instance.shipsRemain);
        Assertions.assertEquals("H", instance.Cells[0][0].text);
        Assertions.assertEquals("H", instance.Cells[0][1].text);
        Assertions.assertEquals("H", instance.Cells[0][2].text);
        Assertions.assertEquals("H", instance.Cells[0][3].text);
        Assertions.assertEquals("H", instance.Cells[0][4].text);
    }
    // For File configuration, the model is given 'y' to load the file, and one of
    // the ships
    // is targeted and destroyed. Using assertions, we can check the text of the
    // cells and
    // that shipsRemain should equal 4.

    @Test
    public void Test2() { // End of game
        System.out.println("Starting Test 2...");
        BSModel instance = new BSModel("y");
        // Ship 1
        instance.change(1, 5);
        instance.change(2, 5);
        // Ship 2
        instance.change(3, 6);
        instance.change(4, 6);
        // Ship 3
        instance.change(8, 0);
        instance.change(8, 1);
        instance.change(8, 2);
        // Ship 4
        instance.change(6, 0);
        instance.change(6, 1);
        instance.change(6, 2);
        instance.change(6, 3);
        // Ship 5
        instance.change(0, 0);
        instance.change(0, 1);
        instance.change(0, 2);
        instance.change(0, 3);
        instance.change(0, 4);

        Assertions.assertEquals(0, instance.shipsRemain);
        Assertions.assertEquals(true, instance.win);
        Assertions.assertEquals(16, instance.hits);
        Assertions.assertEquals(16, instance.turns);
    }
    // Using the test data from file configuration, destroy all ships
    // and test end game variables

    @Test
    public void Test3() { // Initial configuration with ship position (not file loading)
        System.out.println("Starting Test 3...");
        BSModel instance = new BSModel("n");
        String coordinates = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                instance.change(i, j);
                if (instance.Cells[i][j].text != "" && instance.Cells[i][j].text != "M") {
                    coordinates += "(" + instance.Cells[i][j].getRow() + "," + instance.Cells[i][j].getCol() + ")";
                }
                if (instance.win) {
                    Assertions.assertEquals(0, instance.shipsRemain);
                }
            }
        }
        System.out.println(coordinates);
    }
    // Test searches through all cells and stops when all ships have been destroyed
    // A list of all the ships coordinates is printed into the console
}
