package cn.misection.oaplatform.config;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName BuildConfig
 * @Description TODO
 * @CreateTime 2021年06月13日 21:31:00
 */
public class BuildConfig {

    private BuildConfig() {
        throw new RuntimeException("here are no BuildConfig instance for you!");
    }

    public static final boolean DEBUG = true;

    public static final boolean RELEASE = false;
}
