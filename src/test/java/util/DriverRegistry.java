package util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.openqa.selenium.WebDriver;

/**
 * Created by nikita on 24.05.16.
 */
public class DriverRegistry {
    private static final Map<Long, WebDriver> DRIVER_MAP =
            new ConcurrentHashMap<>();


    public static void setDriver(WebDriver driver) {
        DRIVER_MAP.putIfAbsent(Thread.currentThread().getId(), driver);
    }

    public static  WebDriver getDriver() {
        return DRIVER_MAP.get(Thread.currentThread().getId());
    }

    public static void remove() {
        DRIVER_MAP.remove(Thread.currentThread().getId());
    }

}
