package com.dbsh.skup.model;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "msgHeader")
public class ResponseStationMsgHeader {
    @PropertyElement(name = "headerCd")
    String headerCd;

    public String getHeaderCd() {
        return headerCd;
    }
}
