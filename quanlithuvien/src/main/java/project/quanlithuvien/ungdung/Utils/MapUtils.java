package project.quanlithuvien.ungdung.Utils;

import java.util.Map;

public class MapUtils {
    public static <T> T getObject(Map<String,String> param ,String key,Class<T> tClass){
        Object tmp = param.getOrDefault(key, null);
        if(tmp != null){
            if(tClass.getTypeName().equals("java.lang.Integer")){
                tmp =tmp != "" ? Integer.valueOf(tmp.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.String")) {
				tmp = tmp.toString();
			}
        }
        return tClass.cast(tmp);
    }

    
}
