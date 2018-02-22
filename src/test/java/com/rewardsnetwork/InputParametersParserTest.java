package com.rewardsnetwork;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InputParametersParserTest {
    @Test
    public void shouldCorrectlyParseIgnoringBlanks() {

        String[] args = "hot, 8,6, 4, 2,1, 7,".split(" ");

        InputParametersParser parser = new InputParametersParser();
        parser.parse(args);

        assertEquals("hot", parser.getTemperature());
        assertEquals(Arrays.asList(8, 6, 4, 2, 1, 7), parser.getCommandIdsList());
    }
}
