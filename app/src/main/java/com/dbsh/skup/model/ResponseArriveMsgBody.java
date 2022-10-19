package com.dbsh.skup.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "msgBody")
public class ResponseArriveMsgBody {
    @Element(name = "itemList")
    ResponseArriveItem item;

    public ResponseArriveItem getItem() {
        return item;
    }
}
