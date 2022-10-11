package com.dbsh.skup.tmpstructure.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "msgBody")
public class ResponseArriveMsgBody {
    @Element(name = "itemList")
    ResponseArriveItem item;

    public ResponseArriveItem getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "ResponseArriveMsgBody{" +
                "item=" + item +
                '}';
    }
}
