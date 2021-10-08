package cn.misection.oaplatform.util.proputil;

import cn.misection.oaplatform.config.ResourceBundle;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName PropertiesProxy
 * @Description TODO
 * @CreateTime 2021年10月08日 20:55:00
 */
public class PropertiesProxy {

    private static final Map<ResourceBundle, PropertiesProxy> cache = new HashMap<>();

    private final ResourceBundle bundle;

    private final Properties properties = new Properties();

    private PropertiesProxy(ResourceBundle bundle) {
        this.bundle = bundle;
        init();
    }

    public static PropertiesProxy instanceWithBundle(ResourceBundle bundle) {
        if (cache.containsKey(bundle)) {
            return cache.get(bundle);
        }
        PropertiesProxy instance = new PropertiesProxy(bundle);
        cache.put(bundle, instance);
        return instance;
    }

    private void init() {
        try {
            properties.load(new FileReader(bundle.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getProperty(String key) {
        return properties.getProperty(key);
    }

    public void putAndSave(String key, String value) {
        properties.setProperty(key, value);
        try {
            FileOutputStream fos = new FileOutputStream(bundle.getPath());
            properties.store(fos, "TODO auto log");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
