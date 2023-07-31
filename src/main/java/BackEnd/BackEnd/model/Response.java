package BackEnd.BackEnd.model;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class Response {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;

    public Object ok(String message,String key, Object value) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);
        res.put("error",status = 0 );

        Map<Object, Object> data = new HashMap<>();

        data.put(key, value);
        res.put("data", data);

        return res;
    }

    public Object okLogin(String message,String key, Object value,String key2,Object value2,String profile,Object value3) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);
        res.put("error",status = 0 );

        Map<Object, Object> data = new HashMap<>();
        data.put(key, value);
        data.put(key2, value2);
        data.put(profile,value3);
        res.put("data", data);

        return res;
    }

    public Object success(String message) {

        Map<Object, Object> res = new HashMap<>();

        res.put("timestamp", timestamp);
        res.put("status", HttpStatus.OK.value());
        res.put("message", message);


        return res;


    }
}

