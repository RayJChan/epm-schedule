package com.midea.epm.schedule;

import com.midea.epm.common.util.CryptographyUtil;

public class Test {

    @org.junit.Test
    public void Md5(){
        String password = "123456";
        System.out.println(CryptographyUtil.md5(password, "xiaguangjun"));
    }

}
