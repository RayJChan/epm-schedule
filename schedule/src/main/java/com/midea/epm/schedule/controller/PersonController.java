package com.midea.epm.schedule.controller;

import com.midea.epm.common.entity.JWTResult;
import com.midea.epm.system.entity.Person;
import com.midea.epm.system.service.PersonService;
import com.midea.epm.common.util.JWTUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/getPerson")
    public Person getList() throws Exception {

        return personService.getById("46411778957f4cd5947c98b7a32d6169");
    }

    @GetMapping("/getToken")
    public JWTResult getToken(){
        String token = JWTUtils.getInstance().getToken("ex_xiagj");
        JWTResult jwtResult = JWTUtils.getInstance().checkToken("eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJleF94aWFnaiIsImV4cCI6MTU2OTY0MzQ2MX0.pTr__kQR6mz_huphVnkyKju1feFvCLH_GSUKOj_JOV8r0oMo27ytHVofat5ywX7Q6DxLVsIyZCaNC9o3sSqmEnQxB-LEMBeXyyKOn-YjYMbH-xYekMDvgps1dabaA4J_aUE2Lvs5abFXL-jq8G2TH-2wB6uh6yf-1nvitFytaSB");
        System.out.println(token);
        return jwtResult;
    }

}
