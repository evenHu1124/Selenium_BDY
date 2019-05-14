package com.falcon.entity;

import java.util.List;

/**
 * @author even
 * @create 2019/5/13-20-54
 */
public class TreeLevel {
    private Integer currentIndex; //当前处理的目录集合的索引
    private List<String> levelCatalog; //遍历层次的所有目录节点m名称

    public TreeLevel(Integer currentIndex, List<String> levelCatalog) {
        this.currentIndex = currentIndex;
        this.levelCatalog = levelCatalog;
    }

    public TreeLevel() {
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<String> getLevelCatalog() {
        return levelCatalog;
    }

    public void setLevelCatalog(List<String> levelCatalog) {
        this.levelCatalog = levelCatalog;
    }
}
