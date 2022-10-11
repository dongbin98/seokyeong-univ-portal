package com.dbsh.skup.tmpstructure.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "msgBody")
public class ResponseStationMsgBody {
    @Element(name = "itemList")
    List<ResponseStationItem> items;

    public List<ResponseStationItem> getItems() {
        return items;
    }
}
