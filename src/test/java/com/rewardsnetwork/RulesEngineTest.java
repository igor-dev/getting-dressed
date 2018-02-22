package com.rewardsnetwork;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RulesEngineTest {
    
    private RulesEngine rulesEngine = new RulesEngine();
    private Joiner commaJoiner = Joiner.on(", ");


    // Success cases ---------------------------------------------------------------------------------------------------

    @Test
    public void shouldLeaveHouseIfHot() {
        assertEquals(
                "Removing PJs, shorts, shirt, sunglasses, sandals, leaving house",
                commaJoiner.join(rulesEngine.run("hot", Arrays.asList(8, 6, 4, 2, 1, 7)))
        );
    }

    @Test
    public void shouldLeaveHouseIfCold() {
        assertEquals(
                "Removing PJs, pants, socks, shirt, hat, jacket, boots, leaving house",
                commaJoiner.join(rulesEngine.run("cold", Arrays.asList(8, 6, 3, 4, 2, 5, 1, 7)))
        );
        assertEquals(
                "Removing PJs, shirt, socks, pants, boots, jacket, hat, leaving house",
                commaJoiner.join(rulesEngine.run("cold", Arrays.asList(8, 4, 3, 6, 1, 5, 2, 7)))
        );
    }


    
    // Fail cases ------------------------------------------------------------------------------------------------------

    @Test
    public void shouldFailIfTemperatureInputIsIncorrect() {
        assertTrue(commaJoiner.join(rulesEngine.run("hots", Arrays.asList(8, 6, 4, 2, 1, 7))).startsWith("Incorrect"));
        assertTrue(
                commaJoiner.join(rulesEngine.run("cld", Arrays.asList(8, 6, 3, 4, 2, 5, 1, 7))).startsWith("Incorrect")
        );
    }

    @Test
    public void shouldFailIfCommandNumberDoesNotExist() {
        assertTrue(commaJoiner.join(rulesEngine.run("hot", Arrays.asList(8, 999, 4, 2))).startsWith("Incorrect"));
    }

    
    @Test
    public void shouldFailIfItemIsAlreadyOn() {
        assertEquals(
                "Removing PJs, shorts, fail",
                commaJoiner.join(rulesEngine.run("hot", Arrays.asList(8, 6, 6)))
        );
    }

    @Test
    public void shouldFailIfPuttingOnSocksWhenItIsHot() {
        assertEquals(
                "Removing PJs, shorts, fail",
                commaJoiner.join(rulesEngine.run("hot", Arrays.asList(8, 6, 3)))
        );
    }

    @Test
    public void shouldFailToLeaveHomeWithoutFootwear() {
        assertEquals(
                "Removing PJs, pants, socks, shirt, hat, jacket, fail",
                commaJoiner.join(rulesEngine.run("cold", Arrays.asList(8, 6, 3, 4, 2, 5, 7)))
        );
    }

    @Test
    public void shouldFailIfPuttingOnPantsWhilePajamasIsOn() {
        assertEquals(
                "fail",
                commaJoiner.join(rulesEngine.run("cold", Collections.singletonList(6)))
        );
    }

}
