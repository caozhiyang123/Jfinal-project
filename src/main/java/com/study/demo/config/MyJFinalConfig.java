package com.study.demo.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.study.demo.interceptor.LoginInterceptor;
import com.study.demo.model._MappingKit;

public class MyJFinalConfig extends JFinalConfig {
    
    public void configRoute(Routes me) {
       me.setMappingSuperClass(false);
       me.add(new FrontRoutes());  // front route
       me.add(new AdminRoutes());  // server route
    }
    
    public void configConstant(Constants me) {
        // loading a little config,then can by method PropKit.get(...) get value
        PropKit.use("properties/application.properties");
        me.setDevMode(PropKit.getBoolean("DEV_MODE"));
        me.setError500View("/view/500.html");
        me.setError404View("/view/404.html");
        //use inject
        me.setInjectDependency(true);
        
        me.setJsonFactory(new FastJsonFactory());
        me.setJsonDatePattern("yyyy-MM-dd HH:mm:ss");
    }
    
    public void configEngine(Engine me) {
        me.setDevMode(true);
        me.addSharedFunction("/view/common/_layout.html");
    }
    
    public void configPlugin(Plugins me) {
     // C3p0 database connection pool plugin
        DruidPlugin core = createC3p0Plugin();
        core.setTestWhileIdle(true);
        me.add(core);

     //  ActiveRecord database access plugin
        ActiveRecordPlugin arp_core = new ActiveRecordPlugin("core", core);
        arp_core.setShowSql(true);
        
        Engine engine = arp_core.getEngine();
        // 上面的代码获取到了用于 sql 管理功能的 Engine 对象，接着就可以开始配置了
        engine.setToClassPathSourceFactory();
        engine.addSharedMethod(new StrKit());
        
        me.add(arp_core);
        
     // ActiveRecordPlugin mapping 2 db(VO)
        _MappingKit.mapping(arp_core);
    }
    
    public static DruidPlugin createC3p0Plugin() {
        return new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.user"), PropKit.get("jdbc.password").trim());
    }
    
    /**
     * global interceptor
     */
    public void configInterceptor(Interceptors me) {
        //global interceptor
        me.add(new LoginInterceptor());
    }
    
    /**
     * global handler ,name "contextPath" can be global project relative path root directory in js,html
     */
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("contextPath"));
    }
    
    public static void main(String[] args) {
        System.setProperty("WORKDIR", "logs");
        /**
         * JFinal 3.2V
         */
//        JFinal.start("src/main/webapp", 80, "/");
        /**
         * JFinal 3.6V
         */
        UndertowServer.start(MyJFinalConfig.class, 80, true);
    }
    
    /**
     * this method can be used after start up server,such as start a timing task
     */
    @Override
    public void onStart()
    {
       
    }
    
    /**
     * this method can be used befor shutDown server,such as save some data to db
     */
    @Override
    public void onStop()
    {
        
    }
}