package com.dbsh.skup.tmpstructure.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "ServiceResult")
public class ResponseArrive {
    @Element(name = "msgHeader")
    ResponseArriveMsgHeader header;

    @Element(name = "msgBody")
    ResponseArriveMsgBody body;

    public ResponseArriveMsgHeader getHeader() {
        return header;
    }

    public ResponseArriveMsgBody getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ResponseArrive{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
