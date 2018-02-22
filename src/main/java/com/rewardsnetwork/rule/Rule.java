package com.rewardsnetwork.rule;

import com.rewardsnetwork.command.Command;
import com.rewardsnetwork.common.Action;
import com.rewardsnetwork.common.ClothesType;
import com.rewardsnetwork.common.Context;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@AllArgsConstructor
public class Rule {

    @Getter
    private Action action;

    @Getter
    private ClothesType clothesType;

    @Getter
    private BiFunction<Context, Command, Boolean> condition;
}
