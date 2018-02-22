package com.rewardsnetwork.command;

import com.rewardsnetwork.common.Action;
import com.rewardsnetwork.common.ClothesType;
import com.rewardsnetwork.common.Context;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Command {

    @Getter
    private Integer id;

    @Getter
    private Action action;

    @Getter
    private ClothesType clothesType;

    @Getter
    private Map<Context.Temperature, ThenAction> thenActionMap;

    @Getter
    private List<BiFunction<Context, Command, Boolean>> conditions = new ArrayList<>();

    
    public Command(
            Integer id,
            Action action,
            ClothesType clothesType,
            Map<Context.Temperature, ThenAction> thenActionMap
    ) {
        this.id = id;
        this.action = action;
        this.clothesType = clothesType;
        this.thenActionMap = thenActionMap;
    }

    public void addCondition(BiFunction<Context, Command, Boolean> condition) {
        conditions.add(condition);
    }
}
