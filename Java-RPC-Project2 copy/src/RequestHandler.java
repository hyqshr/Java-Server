import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestHandler implements Callable<String> {
    private final String message;
    private Map<String, String> map;
    private static Logger logger = Logger.getLogger(RequestHandler.class.getName());

    // constructor
    public RequestHandler(String msg, Map<String, String> map) {
        this.message = msg;
        this.map = map;
    }
    @Override
    public String call() {
        String threadName = Thread.currentThread().getName();
        logger.info(System.currentTimeMillis() + " " + threadName + "handling message: " + message);

        try {
            String[] splitedMsg = message.split(",");
            String method = splitedMsg[0];
            // extract method, key, (value if method is PUT) from the client msg
            if (method.equals("PUT")){
                String[] putMsg = splitedMsg[1].split("-");
                String putKey = putMsg[0];
                String putVal = putMsg[1];
                logger.info("System time: " + System.currentTimeMillis() + " PUT received, key: " + putKey + " value: " + putVal);
                return putHandler(putKey, putVal);
            } else if(method.equals("GET")){
                String key = splitedMsg[1];
                logger.info("System time: " + System.currentTimeMillis() + " " + method + "received, key: " + key);
                return getHandler(key);
            }else{
                String key = splitedMsg[1];
                logger.info("System time: " + System.currentTimeMillis() + " " + method + "received, key: " + key);
                return deleteHandler(key);
            }

        } catch (RuntimeException e){
            logger.log(Level.WARNING, System.currentTimeMillis() + " message format invalid");
        }
        return "";
    }

    // handle put request
    public String putHandler(String key, String val){

        try {
            map.put(key, val);
            logger.info("System time: " + System.currentTimeMillis() + "key-val: " + key + val + map);
            logger.info("System time: " + System.currentTimeMillis() + " PUT successfully handled! Current map: "+ map);
            return "success! current map: " + map;
        } catch (Exception e){
            System.out.println(e);
            logger.log(Level.SEVERE, System.currentTimeMillis() + "Error with put request with key: value: " + key + " " + val);
        }

        return "";
    }

    // handle get request
    public String getHandler(String msg){
        try {
            String result = map.get(msg);
            logger.info(System.currentTimeMillis() + " GET successfully handled! get result" + msg + " with key: "+ msg);
            return result;
        } catch (RuntimeException e){
            logger.log(Level.SEVERE, System.currentTimeMillis() + "Error with get request with key: " + msg);
        }
        return "";
    }

    // handle delete request
    public String deleteHandler(String msg){
        try {
            String result = map.remove(msg);
            logger.info(System.currentTimeMillis() + " DELETE successfully handled! delete with key: "+ msg);
            return result;
        } catch (RuntimeException e){
            logger.log(Level.SEVERE, System.currentTimeMillis() + "Error with DELETE with key: " + msg);
        }

        return "";
    }
}