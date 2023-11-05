package com.engzi.Utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateToTimestamp {
    public static Timestamp dateToTimestamp(Date time) {
        return new Timestamp(time.getTime());
    }

    public static Date timestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
}
