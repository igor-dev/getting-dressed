package com.rewardsnetwork.rule;

import com.google.common.collect.ImmutableList;
import com.rewardsnetwork.command.CommandRepository;
import com.rewardsnetwork.common.ClothesType;
import com.rewardsnetwork.common.Context;
import com.rewardsnetwork.common.Item;
import lombok.Getter;

import java.util.List;

import static com.rewardsnetwork.common.Action.*;
import static com.rewardsnetwork.common.ClothesType.*;
import static com.rewardsnetwork.common.Item.FULL_DRESSED_MAP;

public class RuleRepository {

    private static RuleRepository instance = new RuleRepository();

    @Getter
    private List<Rule> rules;

    private RuleRepository() {
        buildRulesList();
    }

    public static RuleRepository getInstance() {
        return instance;
    }


    private void buildRulesList() {
        rules = ImmutableList.<Rule>builder()
                //------------------------------------------------------------------------------------------------------
                // Pajamas must be taken off before anything else can be put on
                .add(
                        new Rule(PUT_ON, ANYTHING,
                                (ctx, command) -> !contains(ctx, Item.PJ))
                )

                //------------------------------------------------------------------------------------------------------
                // Only 1 piece of each type of clothing may be put on
                .add(
                        new Rule(PUT_ON, SOMETHING,
                                (ctx, command) -> !contains(ctx, command.getClothesType()))
                )

                //------------------------------------------------------------------------------------------------------
                // Socks must be put on before footwear
                .add(
                        new Rule(PUT_ON, SOCKS,
                                (ctx, command) -> !contains(ctx, FOOTWEAR))
                )

                //------------------------------------------------------------------------------------------------------
                // Pants must be put on before footwear
                .add(
                        new Rule(PUT_ON, PANTS,
                                (ctx, command) -> !contains(ctx, FOOTWEAR))
                )

                //------------------------------------------------------------------------------------------------------
                // The shirt must be put on before the headwear or jacket
                .add(
                        new Rule(PUT_ON, SHIRT,
                                (ctx, command) -> !contains(ctx, HEADWEAR) && !contains(ctx, JACKET))
                )

                //------------------------------------------------------------------------------------------------------
                // You cannot leave the house until all items of clothing are on
                // (except socks and a jacket when it’s hot)
                .add(
                        new Rule(LEAVE, HOUSE,
                                (ctx, command) ->
                                        ctx.getItemsOn()
                                                .containsAll(FULL_DRESSED_MAP.get(ctx.getTemperature())))
                )
                //------------------------------------------------------------------------------------------------------
                .add(
                        new Rule(TAKE_OFF, PJ,
                                (ctx, command) -> contains(ctx, Item.PJ))
                )
                //------------------------------------------------------------------------------------------------------
                .build();
    }

    private static boolean contains(Context ctx, ClothesType clothesType) {
        return ctx.getItemsOn().contains(getItemByActionTypeObj(ctx, clothesType));
    }

    private static boolean contains(Context ctx, Item item) {
        return ctx.getItemsOn().contains(item);
    }

    private static Item getItemByActionTypeObj(Context ctx, ClothesType clothesType) {
        return CommandRepository.getInstance()
                .getItemsMapping()
                .get(clothesType)
                .get(ctx.getTemperature())
                .getItem();
    }
}
