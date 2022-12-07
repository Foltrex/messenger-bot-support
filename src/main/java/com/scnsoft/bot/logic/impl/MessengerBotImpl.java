package com.scnsoft.bot.logic.impl;

import org.springframework.stereotype.Component;

import com.scnsoft.bot.logic.MessengerBot;
import com.scnsoft.bot.model.Message;

// TODO: only for test purposes, delete later
@Component
public class MessengerBotImpl implements MessengerBot {

    @Override
    public Message handleIncommingMessage(Message message) {
        return message;
    }

    @Override
    public String getBotId() {
        return """
            52505f15-d130-4d15-8ac7-d58eac0f9307
                """;
    }

    @Override
    public String getPublicKey() {
        return """
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiZLEeDs4hvYus+/fKjGL
            xkyz8V6yEzVFarEcIMv/ifH9iubfwVvd8Ihrkos4Qh0lHMdYywoxqPXMsKuFrZJo
            dJ1o5HhlzGk0dU+JtsB+7/vJqd/SZhj1d01DpnBp/EjxXD6v9hmikCa3D/ghcp2E
            reFUiBoXA/zZZV0UkyWH0viH3Yq4HsbjYBYP3xnaOI5EF/70FUa+n4XQL0iXtIFH
            Y8EeFxeKoJ8NKoQZbt1c6b5WpEFRDSyn609fKu8cgKBUlmIe9I257yPIitRh5WNL
            eAlb9WJWgGnfUIR1gvZYI8uH7Bl1kAL80wUSEM42FrbnHIlybiA55Ly/OA25b//R
            lwIDAQAB
            -----END PUBLIC KEY-----                                                            
                """;
    }

    @Override
    public String getPrivateKey() {
        return """
            -----BEGIN RSA PRIVATE KEY-----
            MIIEowIBAAKCAQEAiZLEeDs4hvYus+/fKjGLxkyz8V6yEzVFarEcIMv/ifH9iubf
            wVvd8Ihrkos4Qh0lHMdYywoxqPXMsKuFrZJodJ1o5HhlzGk0dU+JtsB+7/vJqd/S
            Zhj1d01DpnBp/EjxXD6v9hmikCa3D/ghcp2EreFUiBoXA/zZZV0UkyWH0viH3Yq4
            HsbjYBYP3xnaOI5EF/70FUa+n4XQL0iXtIFHY8EeFxeKoJ8NKoQZbt1c6b5WpEFR
            DSyn609fKu8cgKBUlmIe9I257yPIitRh5WNLeAlb9WJWgGnfUIR1gvZYI8uH7Bl1
            kAL80wUSEM42FrbnHIlybiA55Ly/OA25b//RlwIDAQABAoIBABwhh80yBuAKDQi8
            PvzovrUg0BbGD1HALd6gMqk+LZWuhvLVPOHUADEEJEMF9s4GGaWl2bFC916eTGm/
            2QjiDDv0SBOXzvEafiCS0A0vlYVLDjSPUP2Rkvf/M5sgF3wACmKbau2+1i/1yq8Y
            RPaj4RKRJfDirNwi4u1IOEHShdCMtl/lImYgGCpUXbKJiTpO151p6LeGA0SI292S
            gpSC16cdCJqc/eT6/xKeaviGL37IIixvlob159Y+rFyUk9hxNHvJkaIsp0VPbErV
            aAYEDOHwaObqLSHK8CvtO/d35nt5jlLUoqJ7gZRDTwA6dimrcsUrYNn+Fkcg9lzL
            us3Q2ykCgYEAzdHFb4J4eEANjXDZSAvQLZEH9ypJHxPP+u4muSV+WJiFAqLYxMRt
            rnV6nXZdHSKhzHWIWVf8STxESmqmhRo/e3ibdETEo4ZILpX6vHuICwHZvx5quozk
            Uw2BlUz6tHa9o1z6WL94lmOLxhEtzfs2NhLIB8awBUHIowHpIijkwQsCgYEAqx1p
            gcfyhvgiy3anMnPN9s+uG3l8TK9zHhYMOQLdB3nS1fHf5b5gBMIc/w6Uw/PR2QKK
            rn6QpzIx8bYVbOBLA8YI45SYrAGclog3hXIvGNeWPL7DHPRrDXo2F9g6TF4tUjjp
            Bd6d7BqZ4bLrQ5lXiQFitAp4Pioy1dJFOgGYoSUCgYATw2HW/Es/GL90ZnKmxWOf
            DH8cT7YnwspEyRt0ydHhBfw3rZVtB4OUrfkxJ413X7zzmhGWqqkyCxRvpOAKheO0
            onci6fuLzDggKQyjjqUYkXvsKyqKcT73bqOTx4mHTKOXDTWI0hHtNu/T/BBO6CFj
            fdgxTvXV+2QdqvBM9GpK0wKBgQCRD2AQG3cooFau8amQBchHQYwx0m1jfSE0EjDO
            a/UAj3AC2weZ9ifkIMMAHlZTP/FX7OWwQ69RbiYU6O2E3Sf6zPlgJOrAdqVOn6+h
            u0jd4HWGjZlOhI30YPRWhoH1l6kIGSLCTRTHwU1OnqT/K5GfncMVKgMBgDiUN6tk
            H1JK2QKBgBMh2+5Kky2anWNotTKTTQuJ6dhv+OloEptyp8fz+SsM/6Y71kZvR8oR
            MGXQP1+spb9QDhFtAy/fEPnaisqmSkmumwgeIrUDjIF8bzot3cznBGK8pKsoPOPj
            0SKuI3RD7KS9fSH6q9t6CoMgnLGTA1gf9ft0I2e+7dhv9Xgeb49H
            -----END RSA PRIVATE KEY-----                                                 
                """;
    }
    
}
