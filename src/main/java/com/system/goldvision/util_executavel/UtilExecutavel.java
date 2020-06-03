package com.system.goldvision.util_executavel;

import com.system.goldvision.util.ResponseUtil;

public class UtilExecutavel {

    public static void main(String[] args) {
        ResponseUtil util = new ResponseUtil();
        String password = "admin";
        System.out.println(util.gerarSenhaCriptografada(password));

    }

}
