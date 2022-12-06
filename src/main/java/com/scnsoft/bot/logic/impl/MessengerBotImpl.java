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
            ff3b5310-6c17-469c-bd1b-679464c80807
                """;
    }

    @Override
    public String getPublicKey() {
        return """
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuCoO6q5NEbFJyKpihupY
            SIFfEi4ovqJbMdA1Jv4qOmGj6IVMwNHzi/XNuwPeSo5TsQNHsGAPsQ6f0r9folW1
            f+v0HtUfojE8sa/9c4MalvZ+y2nnczzfAnDGEzckyEKAYJi7eUUaFG5gH/cqyrpA
            xJMSCaxzbUgCr0WSa09MY2pTy9OKDPo3kolD7ESkW8S7xjPEU+ZM7ha6veq9uTDf
            uml6z7hZZh+ATiS5bh+SQoh5me0dygcoH6akzT7o5xnQq13Q35ZFKJ7AS2e2N/SK
            UQFvgA8JUv11D4wUUaSGizj/34LitBzkQw7Ez0TBvJrXDWeRVqtb/3rq0htt25vc
            MwIDAQAB
            -----END PUBLIC KEY-----            
                """;
    }

    @Override
    public String getPrivateKey() {
        return """
            -----BEGIN RSA PRIVATE KEY-----
            MIIEogIBAAKCAQEAuCoO6q5NEbFJyKpihupYSIFfEi4ovqJbMdA1Jv4qOmGj6IVM
            wNHzi/XNuwPeSo5TsQNHsGAPsQ6f0r9folW1f+v0HtUfojE8sa/9c4MalvZ+y2nn
            czzfAnDGEzckyEKAYJi7eUUaFG5gH/cqyrpAxJMSCaxzbUgCr0WSa09MY2pTy9OK
            DPo3kolD7ESkW8S7xjPEU+ZM7ha6veq9uTDfuml6z7hZZh+ATiS5bh+SQoh5me0d
            ygcoH6akzT7o5xnQq13Q35ZFKJ7AS2e2N/SKUQFvgA8JUv11D4wUUaSGizj/34Li
            tBzkQw7Ez0TBvJrXDWeRVqtb/3rq0htt25vcMwIDAQABAoIBAAh2xmC6ygf6Ie37
            BrQ0qQIMEBaB0JWSWKadbxPvsnkka6gQGEr0W0EfdukVcG30xJvqDq1ofNAiX+4L
            pZWMYt8SNRIFsYJ+yM9Ry1A3wk0V3xXyNBTzwzwVesT/T810YPoPiu4z4tL1j61o
            aqsOjFN8fuO67UMkgBsWE3ijGFupPyzbXTTtO3Gb7BMVEKz4Klkl6VjDGHsjcvoS
            XG5JmuMI33qXTl1MvCHY2IqF/SYuri9abuHsLmLUJrUW8cB60euhSnxMfS58nfYX
            8K8AuXhiQjejECMQbFWKf2Ml0wtHpbpxXnFIH0/oU+fn7P9sZsphgAxGToZWwIhk
            vAhpc8ECgYEA+zmRBGgiDBYelGPg77a/oCsfTyMhwYfsh6Hxytmg3pufuDj73kbH
            +wiO00zNauYuWvNA1EdaYWxo45JlzyqyJaunS9u+DLS0KvzrZIbdJayCOOSU2Q/U
            n7deZX1xlRVBt7kp3CiW89MO68ZADmXP+NZO7tbaPqHwBXLpyuHfbCsCgYEAu6ou
            oNEsc6iqzCzW+iqO2uYxJLrCZibvi1+DcffeGTQ6E+NVY0XV3ncjdwMwLypj8p8k
            G5Vz+XC7Ew7Z4QJkEHFUxHR2hba8/Ruq/9kWrEhjHUnGXJ45ixA0EELtZv9tKrwD
            RuooZWLFY2JyGF3QHpLdReArT0lgJ71CQlNc5BkCgYBGrc101e8uQ1LbILZ5+ooy
            /67RTusCTcFn71HdZhjySApJhA6oXHWK1L+KBwOL3bKQ9/r3PhPZcOyZieiGvlZc
            bsykdPAlFvnRVlLLd33kSYJhiz2tS9zgCnDOUNqRvOc+TyPNwsWlV23NO3tuEYrP
            UUvnuh6wnFKw1cGcyKtY/QKBgF3cycOfu5cBtqO2jbPEU31DAzN2wcKsGMXh+PGo
            4wpCKAZIfy3PASBrAG8HRjNhBWqVlE7DmqUZcEgg9nLnajsbJCW6holsk+wYyZd7
            8i4ran98pd0vOmLGsPOtNeZ9PRHRxeXTOm8zTrzdwWlUisKv/EJ109OtRI27yIc6
            AM0JAoGAanWc+8x7oJ+0HJpkdrMDhaXdx87jGJfNgeRjaKE769KO9zdvgGlheSfh
            e+V7K/74VZS1yCoIPV4dFmdN+v0eODusrBy9le+f/QK2u+CbiXBaDXSHuxspg/Wa
            99vaPyrsDkCj/W1+d0tEGoPjrU+nfJcDG/3r/VL0Ulpon1xvecM=
            -----END RSA PRIVATE KEY-----
                """;
    }
    
}
