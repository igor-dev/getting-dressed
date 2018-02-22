package com.rewardsnetwork.command;

import com.rewardsnetwork.common.Context;
import com.rewardsnetwork.common.Item;

import java.util.function.Consumer;

public class ThenAction {

    public static final ThenAction PUT_ON_HAT = buildAddAction(Item.HAT);
    public static final ThenAction PUT_ON_SUNGLASSES = buildAddAction(Item.SUNGLASSES);
    public static final ThenAction PUT_ON_SOCKS = buildAddAction(Item.SOCKS);
    public static final ThenAction PUT_ON_JACKET = buildAddAction(Item.JACKET);
    public static final ThenAction PUT_ON_SHIRT = buildAddAction(Item.SHIRT);
    public static final ThenAction PUT_ON_SANDALS = buildAddAction(Item.SANDALS);
    public static final ThenAction PUT_ON_BOOTS = buildAddAction(Item.BOOTS);
    public static final ThenAction PUT_ON_SHORTS = buildAddAction(Item.SHORTS);
    public static final ThenAction PUT_ON_PANTS = buildAddAction(Item.PANTS);


    public static final ThenAction FAILED = new ThenAction(Item.NONE, "fail", Context::setDone);
    public static final ThenAction LEAVE_HOUSE = new ThenAction(Item.NONE, "leaving house", Context::setDone);
    public static final ThenAction TAKE_OFF_PJ = new ThenAction(
            Item.NONE,
            "Removing PJs",
            ctx -> ctx.getItemsOn().remove(Item.PJ)
    );

    private Item item;
    private String textResponse;
    private Consumer<Context> actionProc;

    public ThenAction(Item item, String textResponse, Consumer<Context> actionProc) {
        this.item = item;
        this.textResponse = textResponse;
        this.actionProc = actionProc;
    }

    public Item getItem() {
        return item;
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void applyTo(Context ctx) {
        actionProc.accept(ctx);
    }


    private static ThenAction buildAddAction(Item item) {
        return new ThenAction(
                item,
                item.toString().toLowerCase(),
                ctx -> ctx.getItemsOn().add(item)
        );
    }
}
