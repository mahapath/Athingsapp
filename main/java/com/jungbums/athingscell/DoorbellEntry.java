package com.jungbums.athingscell;

import java.util.Map;

/**
 * Created by 75151 on 2017-09-03.
 */

public class DoorbellEntry {
    Long timestamp;
    String image;

    public DoorbellEntry() {
    }
    public DoorbellEntry(Long timestamp,String image){
        this.timestamp=timestamp;
        this.image=image;
    }
    public Long getTimestamp(){
        return timestamp;
    }
    public String getImage(){
        return image;
    }

}
