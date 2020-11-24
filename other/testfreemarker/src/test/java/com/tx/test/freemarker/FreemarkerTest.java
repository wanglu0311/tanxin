package com.tx.test.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName FreemarkerTest
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 13:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    @Autowired
    private RestTemplate restTemplate;

    // 测试静态化  基于ftl模板文件生成html文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {


        // 定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion()) ;

        // 定义模板
        // 得到clsspath的路径
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File("H:\\IdeaSpeace\\tx_project\\other\\testfreemarker\\src\\test\\java\\resources\\templates\\"));

        // 得到模板文件的内容
        Template template = configuration.getTemplate("test1.ftl");

        // 定义数据模型
        Map map = getMap();

        // 静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        System.out.println(content);

        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream("h:/test/one.html");

        IOUtils.copy(inputStream,outputStream);

        inputStream.close();
        outputStream.close();


    }


    //基于模板文件的内容(字符串)生成html文件
    @Test
    public void bbb()throws Exception{

        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        //定义模板
        //模板内容
        String temp
                = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Hello world!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "${name}\n" +
                "</body>\n" +
                "</html>";
        //使用模块加载器变为模块
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("templates",temp);

        //在配置中设置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);

        //模板内容
        Template templates = configuration.getTemplate("templates", "utf-8");


        // 定义数据模型
        Map map = getMap();

        // 静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(templates, map);

        System.out.println(content);

        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream("h:/test/two.html");

        IOUtils.copy(inputStream,outputStream);

        inputStream.close();
        outputStream.close();



    }


    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> forEntity =
                restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f",
                        Map.class);
        System.out.println(forEntity);
    }





    public Map getMap(){
        Map map = new HashMap<>();

        map.put("name", "贪心科技");

        return map;

    }

}
