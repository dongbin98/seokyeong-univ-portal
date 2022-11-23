package com.dbsh.skup.dto;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "msgHeader")
public class ResponseArriveMsgHeader {
    @PropertyElement(name = "headerCd")
    String headerCd;

    public String getHeaderCd() {
        return headerCd;
    }
}
