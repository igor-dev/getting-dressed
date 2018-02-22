package com.rewardsnetwork;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParametersParser {

    @Getter
    private String temperature;

    @Getter
    private List<Integer> commandIdsList;


    public void parse(String[] args) {
        temperature = args[0].replace(",", "");

        commandIdsList = Stream.of(args)
                .skip(1)
                .flatMap(e -> Arrays.stream(e.split(",")).filter(k -> !"".equals(k)))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
