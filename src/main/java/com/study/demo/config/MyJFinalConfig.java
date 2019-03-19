package com.study.demo.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

public class MyJFinalConfig extends JFinalConfig {
    
    public void configRoute(Routes me) {
       me.setMappingSuperClass(false);
       me.add(new FrontRoutes());  // ǰ��·��
       me.add(new AdminRoutes());  // ���·��
    }
    public void configConstant(Constants me) {
        me.setDevMode(true);
    }
    public void configEngine(Engine me) {}
    public void configPlugin(Plugins me) {}
    public void configInterceptor(Interceptors me) {}
    public void configHandler(Handlers me) {}
    
    public static void main(String[] args) {
        /**
         * �ر�ע�⣺IDEA ֮�½����������ʽ������ eclipse ֮���������һ������
         */
//        JFinal.start("src/main/webapp", 80, "/");
        UndertowServer.start(MyJFinalConfig.class, 80, true);
    }
}