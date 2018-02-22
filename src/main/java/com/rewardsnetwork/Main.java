package com.rewardsnetwork;

import com.google.common.base.Joiner;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        InputParametersParser parser = new InputParametersParser();
        parser.parse(args);

        List<String> responses = new RulesEngine()
                .run(
                        parser.getTemperature(),
                        parser.getCommandIdsList()
                );

        System.out.println(Joiner.on(", ").join(responses));
    }
}
