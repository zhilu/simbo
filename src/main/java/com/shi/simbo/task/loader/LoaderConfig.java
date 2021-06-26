package com.shi.simbo.task.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoaderConfig {
    private String host;
    private String source;
    private Map<String,String> params;

    public void addParam(String key,String value){
        if(Objects.isNull(params)){
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    public String getParam(String key) {
        if(Objects.nonNull(params)){
            return params.get(key);
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


}
