package com.example.singleton;

import com.example.singleton.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SingletonDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SingletonDemoApplication.class, args);
    }
}

// Singleton Implementations
class SynchronizedMethodSingleton implements Singleton{
    private static SynchronizedMethodSingleton instance;
    private SynchronizedMethodSingleton() {}
    public static synchronized SynchronizedMethodSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedMethodSingleton();
        }
        return instance;
    }

    @Override
    public void test() {
        System.out.println("SYNC");
    }

    @Override
    public String getType() {
        return TypeEnum.SYNC.getValue();
    }
}

class DCLSingleton implements Singleton{
    private static volatile DCLSingleton instance;
    private DCLSingleton() {}
    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    @Override
    public void test() {
        System.out.println("DCL");
    }

    @Override
    public String getType() {
        return TypeEnum.DCL.getValue();
    }
}

class StaticInnerClassSingleton implements Singleton{
    private StaticInnerClassSingleton() {}

    @Override
    public void test() {
        System.out.println("STATIC");
    }

    @Override
    public String getType() {
        return TypeEnum.STATIC.getValue();
    }

    private static class Holder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }
    public static StaticInnerClassSingleton getInstance() {
        return Holder.INSTANCE;
    }
}

enum EnumSingleton {
    INSTANCE;
}

@RestController
@RequestMapping("/singleton")
class SingletonController {

    @Autowired
    private SingletonService singletonService;

    // 頁面路由
    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/{type}")
    public String getSingleton(@PathVariable String type) {
        int hashCode;
        switch (type.toLowerCase()) {
            case "sync":
                hashCode = SynchronizedMethodSingleton.getInstance().hashCode();
                break;
            case "dcl":
                hashCode = DCLSingleton.getInstance().hashCode();
                break;
            case "static":
                hashCode = StaticInnerClassSingleton.getInstance().hashCode();
                break;
            case "enum":
                hashCode = EnumSingleton.INSTANCE.hashCode();
                break;
            default:
                return "Invalid Singleton type!";
        }
        return "Singleton Type: " + type + " | HashCode: " + hashCode;
    }

    @GetMapping("/{type}")
    public void test(@PathVariable String type) {
        singletonService.test(type);
    }
}
