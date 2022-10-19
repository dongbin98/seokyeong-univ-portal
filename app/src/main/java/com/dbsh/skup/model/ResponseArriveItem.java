package com.dbsh.skup.model;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "itemList")
public class ResponseArriveItem {
    @PropertyElement(name = "arrmsg1")
    String arrMsg1;         // 첫번째 도착정보

    @PropertyElement(name = "arrmsg2")
    String arrMsg2;       // 두번째 도착정보

    public String getArrMsg1() {
        return arrMsg1;
    }

    public String getArrMsg2() {
        return arrMsg2;
    }
}
