package com.vrctech.aproundup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSH {

    public static String getString(JSONObject object, String tag){
        return getString(object, tag, "");
    }

    public static String getString(JSONObject object, String tag, String defaultValue){
        return object.optString(tag, defaultValue);
    }

    public static JSONObject getJSONObject(JSONObject object, String tag){
        if(!object.has(tag)){
            return new JSONObject();
        }
        JSONObject json = object.optJSONObject(tag);
        if(json == null){
            return new JSONObject();
        }
        return json;
    }

    public static JSONObject getJSONObject(JSONArray array, int index){
        if(array.isNull(index)){
            return new JSONObject();
        }
        JSONObject object = null;
        try {
            object = array.getJSONObject(index);
        } catch (JSONException e){
            return new JSONObject();
        }
        return object;
    }

    public static JSONArray getJSONArray(JSONObject object, String tag){
        try {
            if(!object.has(tag)){
                return new JSONArray("[]");
            }
            return object.getJSONArray(tag);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static boolean getBoolean(JSONObject object, String tag){
        return getBoolean(object, tag, false);
    }

    private static boolean getBoolean(JSONObject object, String tag, boolean defaultValue){
        return object.optBoolean(tag, defaultValue);
    }

}
