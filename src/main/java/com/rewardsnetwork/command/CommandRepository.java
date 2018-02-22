package com.rewardsnetwork.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.rewardsnetwork.common.ClothesType;
import com.rewardsnetwork.common.Context;
import com.rewardsnetwork.rule.Rule;
import com.rewardsnetwork.rule.RuleRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rewardsnetwork.command.ThenAction.*;
import static com.rewardsnetwork.common.Action.*;
import static com.rewardsnetwork.common.ClothesType.*;
import static com.rewardsnetwork.common.Context.Temperature.COLD;
import static com.rewardsnetwork.common.Context.Temperature.HOT;

public class CommandRepository {

    private static CommandRepository instance = new CommandRepository();

    private List<Command> commands;

    private Map<Integer, Command> commandsMap;
    private Map<ClothesType, Map<Context.Temperature, ThenAction>> itemsMapping;


    private CommandRepository() {
        buildCommandsList();
    }

    public static CommandRepository getInstance() {
        return instance;
    }


    // Grouped by id
    public Map<Integer, Command> getCommandsMap() {

        if (commandsMap == null) {
            commandsMap = commands.stream()
                    .collect(Collectors.toMap(Command::getId, c -> c));
        }

        return commandsMap;
    }


    // Items grouped by ClothesType
    public Map<ClothesType, Map<Context.Temperature, ThenAction>> getItemsMapping() {

        if (itemsMapping == null) {
            itemsMapping = commands.stream()
                    .collect(Collectors.toMap(Command::getClothesType, Command::getThenActionMap));
        }

        return itemsMapping;
    }

    // Add compatible conditions to command
    public CommandRepository preProcessCommands() {
        List<Rule> rules = RuleRepository.getInstance().getRules();

        commands.forEach(
                (Command command) -> rules.stream()
                        .filter(rule -> rule.getAction().equals(command.getAction()))
                        .filter(rule -> {
                            ClothesType ruleClothesType = rule.getClothesType();

                            return ruleClothesType.equals(command.getClothesType())
                                    || ruleClothesType.equals(ClothesType.SOMETHING)
                                    || ruleClothesType.equals(ClothesType.ANYTHING);
                        })
                        .forEach(rule -> command.addCondition(rule.getCondition()))
        );

        return this;
    }

    private void buildCommandsList() {
        commands = ImmutableList.<Command>builder()
                .add(
                        new Command(
                                1,
                                PUT_ON, FOOTWEAR,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, PUT_ON_SANDALS)
                                        .put(COLD, PUT_ON_BOOTS)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                2,
                                PUT_ON, HEADWEAR,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, PUT_ON_SUNGLASSES)
                                        .put(COLD, PUT_ON_HAT)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                3,
                                PUT_ON, SOCKS,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, FAILED)
                                        .put(COLD, PUT_ON_SOCKS)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                4,
                                PUT_ON, SHIRT,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, PUT_ON_SHIRT)
                                        .put(COLD, PUT_ON_SHIRT)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                5,
                                PUT_ON, JACKET,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, FAILED)
                                        .put(COLD, PUT_ON_JACKET)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                6,
                                PUT_ON, PANTS,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, PUT_ON_SHORTS)
                                        .put(COLD, PUT_ON_PANTS)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                7,
                                LEAVE, HOUSE,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, LEAVE_HOUSE)
                                        .put(COLD, LEAVE_HOUSE)
                                        .build()
                        )
                )

                .add(
                        new Command(
                                8,
                                TAKE_OFF, PJ,
                                ImmutableMap.<Context.Temperature, ThenAction>builder()
                                        .put(HOT, TAKE_OFF_PJ)
                                        .put(COLD, TAKE_OFF_PJ)
                                        .build()
                        )
                )
                .build();
    }
}
