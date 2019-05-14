package com.falcon.main;

import com.falcon.entity.Article;
import com.falcon.entity.TreeLevel;
import com.falcon.util.DateUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

/**
 * @author even
 * @create 2019/5/14-19-26
 */
public class GenTreeInfo {
    public void gen(Article article){
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        Map<String,Object> preferences=new HashMap<String,Object>();
        ChromeOptions options=new ChromeOptions();
        preferences.put("profile.managed_default_content_settings.images",2);
        options.setExperimentalOption("prefs",preferences);
        WebDriver driver=new ChromeDriver(options);
        driver.get(article.getShare_url());

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.tagName("body")).isDisplayed();
                return loadcomplete;
            }
        });

        boolean hasPassword=false; // 是否有密码
        String title = driver.getTitle();
        if("百度网盘 请输入提取码".equals(title)){
            hasPassword=true;
        }
        if(hasPassword){
            WebElement pInput = driver.findElement(By.cssSelector(".QKKaIE.LxgeIt"));
            WebElement btn = driver.findElement(By.cssSelector(".g-button-right"));
            pInput.sendKeys(article.getPassword());
            btn.click();
        }

        try {
            genPageData(driver,article);
            // 生成资源结构信息
            S3Level.currentLevel=1;
            S3Level.forward=true;
            S3Level.treeInfo=null;
            S3Level.allTreeInfo=new HashMap<String, TreeLevel>();
            S3Level.gen3Level(driver,article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(article);
        driver.close(); // 浏览器关闭
        driver.quit(); // 释放资源
    }

    /**
     * 生成基本数据
     * @param driver
     * @param article
     * @throws Exception
     */
    public static void genPageData(WebDriver driver, Article article)throws Exception{
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }

        WebElement fileNameEle = driver.findElement(By.cssSelector(".file-name"));
        article.setName(fileNameEle.getText());
        WebElement shareDateEle = driver.findElement(By.cssSelector(".share-file-info span"));
        article.setShare_date(shareDateEle.getText());
        WebElement shareUserEle = driver.findElement(By.cssSelector(".share-person-data-top a.share-person-username.global-ellipsis"));
        article.setShare_user(shareUserEle.getAttribute("textContent"));
        article.setContent(fileNameEle.getText());  // 预先设置
        article.setState(1);
        article.setInclude_date(DateUtil.getCurrentDateStr());

    }
}
