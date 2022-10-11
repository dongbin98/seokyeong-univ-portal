package com.dbsh.skup.tmpstructure.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "ServiceResult")
public class ResponseStation {
    @Element(name = "msgHeader")
    ResponseStationMsgHeader header;

    @Element(name = "msgBody")
    ResponseStationMsgBody body;

    public ResponseStationMsgHeader getHeader() {
        return header;
    }

    public ResponseStationMsgBody getBody() {
        return body;
    }
}

