package com.xumingxing.webpack.userfunction.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2916:40
 * Description: Version 1.0
 * Location: webpack
 */
@Configuration
@EnableApolloConfig
public class BootConfig {

    @Value("${CONFIG.PlatFormCode}")
    private String PlatFormCode;

    public String getPlatFormCode() {
        return PlatFormCode;
    }

    public void setPlatFormCode(String platFormCode) {
        PlatFormCode = platFormCode;
    }
}
