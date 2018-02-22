package com.rewardsnetwork;

import com.google.common.base.Joiner;
import com.rewardsnetwork.command.Command;
import com.rewardsnetwork.command.CommandRepository;
import com.rewardsnetwork.command.ThenAction;
import com.rewardsnetwork.common.Context;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RulesEngine {

    private Map<Integer, Command> allCommandsMap;

    public RulesEngine() {
        allCommandsMap = CommandRepository.getInstance()
                .preProcessCommands()
                .getCommandsMap();
    }

    public List<String> run(String temperatureAsString, List<Integer> commandIdsList) {

        if (inputParametersAreIncorrect(temperatureAsString, commandIdsList)) {
            return errorMessage();
        }

        Context context = new Context(Context.Temperature.getByName(temperatureAsString));

        List<Command> commands = commandIdsList.stream()
                .map(allCommandsMap::get)
                .collect(Collectors.toList());

        return commands.stream()
                .map(
                        command -> {

                            String textResponse = null;

                            if (!context.isDone()) {

                                ThenAction thenAction = ThenAction.FAILED;

                                if (command.getConditions().stream()
                                        .allMatch(condition -> condition.apply(context, command))) {

                                    thenAction = command.getThenActionMap().get(context.getTemperature());
                                    thenAction = thenAction != null
                                            ? thenAction
                                            : ThenAction.FAILED;
                                }

                                thenAction.applyTo(context);
                                textResponse = thenAction.getTextResponse();
                            }

                            return textResponse;
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    //------------------------------------------------------------------------------------------------------------------
    private boolean inputParametersAreIncorrect(String temperatureAsString, List<Integer> commandIdsList) {
        return Context.Temperature.getByName(temperatureAsString) == null
                || commandIdsList.stream().anyMatch(n -> !allCommandsMap.keySet().contains(n));
    }

    private List<String> errorMessage() {
        return Collections.singletonList(
                "Incorrect input parameters\n" +
                        "Usage: [" + Joiner.on("|").join(Context.Temperature.values()) + "] (" +
                        Joiner.on(", ").join(allCommandsMap.keySet()) + ")"
        );
    }
}
