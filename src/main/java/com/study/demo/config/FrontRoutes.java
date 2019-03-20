package com.study.demo.config;

import com.jfinal.config.Routes;
import com.study.demo.controller.BlogController;
import com.study.demo.controller.IndexController;

public class FrontRoutes extends Routes {
    public void config() {
     // ��������ֻ�� FrontRoutes �µ�·����Ч������������������������
        setMappingSuperClass(true);
       add("/index", IndexController.class,"/");
       add("/log", BlogController.class,"/");
    }
}