package com.rewardsnetwork.common;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Item {
    SANDALS,
    BOOTS,
    SUNGLASSES,
    HAT,
    SOCKS,
    SHIRT,
    JACKET,
    SHORTS,
    PANTS,
    PJ,
    NONE;

    public static final Map<Context.Temperature, List<Item>> FULL_DRESSED_MAP =
            ImmutableMap.<Context.Temperature, List<Item>>builder()
                    .put(
                            Context.Temperature.HOT,
                            Arrays.asList(
                                    SANDALS,
                                    SUNGLASSES,
                                    SHIRT,
                                    SHORTS
                            )
                    )
                    .put(
                            Context.Temperature.COLD,
                            Arrays.asList(
                                    BOOTS,
                                    HAT,
                                    SOCKS,
                                    SHIRT,
                                    JACKET,
                                    PANTS
                            )
                    )
                    .build();
}
