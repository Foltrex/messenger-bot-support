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
            24611ad6-65c7-4cf4-9272-982a7d33cf10
                """;
    }

    @Override
    public String getPublicKey() {
        return """
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzvPvOFeHcrPM25tMtIDU
            4JXZ1RjbEuS8sjSX7DRImomIH6a4b1M2jmYoh4tZKZcowNWVD50rv41fYGvA+gjG
            dKzjdRehJX4gSG+D7H5JvzmDCX70AM9SJR8MpnOyBI7z2gKv3i+4HY/e9/RTHo++
            jUxFPcQlQSCQGCgN2pwTLxnNK8cPLvaE+gDSGyQrOBpuXooSKjYLsEhpCklwFO0r
            oLzhE3BZn8CyKSHG1GzkZHgmch4qVv2ow7HUhd5x+9hlkooZLj2f9tZRoNaK7M53
            1mixVbcFQj86eSyO0Vatev+tJyqk0BsEUIResOk1WIn1cCwKubO20RZxJGposzJV
            UQIDAQAB
            -----END PUBLIC KEY-----                                                  
                """;
    }

    @Override
    public String getPrivateKey() {
        return """
            -----BEGIN RSA PRIVATE KEY-----
            MIIEowIBAAKCAQEAzvPvOFeHcrPM25tMtIDU4JXZ1RjbEuS8sjSX7DRImomIH6a4
            b1M2jmYoh4tZKZcowNWVD50rv41fYGvA+gjGdKzjdRehJX4gSG+D7H5JvzmDCX70
            AM9SJR8MpnOyBI7z2gKv3i+4HY/e9/RTHo++jUxFPcQlQSCQGCgN2pwTLxnNK8cP
            LvaE+gDSGyQrOBpuXooSKjYLsEhpCklwFO0roLzhE3BZn8CyKSHG1GzkZHgmch4q
            Vv2ow7HUhd5x+9hlkooZLj2f9tZRoNaK7M531mixVbcFQj86eSyO0Vatev+tJyqk
            0BsEUIResOk1WIn1cCwKubO20RZxJGposzJVUQIDAQABAoIBAC5CNlGYtwz7han8
            z6t1NKAyvqs0UBusB0WqHd9NXIE2hrfUc1tm9UXlLeAGVNrID/LyiswfrJy3g/8w
            dsUs2vdPgdQ1gO65f4vRaWKeY+ubdl/UAcSAkvL7Or+mEr+UGIu9mAQJFJV/CC7S
            bRXiuYwp5TxGMLGbDrpnkZkw09Tt/4AyA+tvZFi8IWzrnTYd2Ayp9DJFyqFjI5PJ
            09Qz3jre10rZLLnQ5Xwrwt/OJEEy5yukp/fU4P1L5X5F6jVCur02sd85ToI/3b/E
            4BvoCv5pZjDqJReCn/dOALzsYcPGNJCToMwCKlI2l2GVinE43qwKPuLcmjv4zkLd
            OXsaOBUCgYEA8GismyE6H5RhIgP867Z88oompWf7/6aQykqgvQtN7vjcB+6SrW0E
            +dwiLtgWjtnCOjTylQ+u7FznRobiUT9Ov5SHdm0WEmCuehFt6vfXFRpDHC9jJTxN
            a18ucZYuhrNtRRqpSHOu/LtHHllD/avTu0GvhxBi1Z/VVZCQhGfDW2sCgYEA3F/Q
            wbmM76liw982lt8bz9frG5kbXI/4BK9h0zWqct6gzBzWIXEuIJyGihmkvwBIn70c
            s2Fmu6ob9az/7dyor2CrgD3bc5cyluyhTOG5GrpAqMTyn4/QgnjnM/NdcKVs0/bm
            8Jzj3xpVveJMUZPfoPmAdw9CkMJ5z9yPALjEHTMCgYEAnNlOM/AbwdrG/LBpZUgs
            gPlNeYE8UnFT6geCk5Q6hn1q4OnwXKHSK0pKKxHktdKw+9TrqAEReTir08HRCn0p
            XakEMGm/isH1U8LnWfPVKfl2a38nSnXrdPAmlwkK1etekUVxyMlhE5cEMXK//1MJ
            0xRp9s7kDMg5IZguEZS5Sn0CgYBj7fblwdQM4Uxt8Yv5ghZEe26bRyRHJumahkTf
            15gBGr7jj7fx1jbKhu/jM5mnGuPTu5eGKAbD1nFzSA2NuN+tFzLtph8bHVbNZWQH
            Ttv3KLolTZmPjVFJuLVus06NxBXDUFSMizgxdOmofzOIUnu3rSv50SuoJOfDwAoA
            k8gqTQKBgBsOsHATAyGy+tXe7c5HOcBcYKReFzLBkbJUV5G48Q/5tc2ELwhe9MOn
            3FURtUD+Oc5ppBJrOgtGAd4Jk9/k6UFBIPyG+X9IAZP47bwoNEkMkeFQQeHPFAnF
            JGman2Oifd9/HntRmVwUlIBBZ3ETd598htijPIB/ptRihuWfLheG
            -----END RSA PRIVATE KEY-----                                        
                """;
    }
    
}
