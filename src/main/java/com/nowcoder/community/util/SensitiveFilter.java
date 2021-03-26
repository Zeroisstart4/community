package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤器
 * @author zhou
 * @create 2021-3-26 14:51
 */


@Component
public class SensitiveFilter {

    // 日志对象
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    // 根节点
    private TrieNode rootNode = new TrieNode();


    // 前缀树初始化，在加载当前 bean 对象的构造器后进行初始化
    @PostConstruct
    public void init() {

        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ){
            String keyword = null;
            while ((keyword = reader.readLine()) != null) {
                // 添加如前缀树
                this.addKeyword(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败:" + e.getMessage());
        }
    }

    // 添加敏感词
    private void addKeyword(String keyword) {
        // 创建一个临时节点，用于添加子节点
        TrieNode tempNode = rootNode;
        // 遍历字符串
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            // 获取当前节点中 存放字符为 c 的子节点
            TrieNode subNode = tempNode.getSubNode(c);
            // 若存放字符为 c 的子节点为空，即不存在存放字符为 c 的子节点
            if(subNode == null) {
                // 创建该节点，并设置其为当前节点的子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // 指向子节点，进入下一轮循环
            tempNode = subNode;

            // 设置结束标识，表示这一串字符为一个敏感词
            if(i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 代检查敏感词文本
     * @return 过滤后的字符串
     */
    public String filter(String text) {
        // 判断文本是否为空
        if(StringUtils.isBlank(text)) {
            return null;
        }
        // 指针一,用于指向当前节点
        TrieNode tempNode = rootNode;
        // 指针二，用于记录检测字符串的起始下标
        int begin = 0;
        // 指针三，用于记录检测字符串的所检测到的字符的当前下标
        int position = 0;
        // 拼接字符串
        StringBuilder sb = new StringBuilder();
        // 若字符串未被检测完，遍历字符串
        while (position < text.length()) {
            // 获取字符
            char c = text.charAt(position);
            // 跳过符号
            if(isSymbol(c)) {
                // 若指针 1 处于根节点,将此符号计入结果,让指针 2 向下走一步
                if(tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或中间,指针 3 都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            }
            else if(tempNode.iskeywordEnd()) {
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                // 重新指向根节点
                tempNode = rootNode;
            }
            else {
                position++;
            }
        }
        // 将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    // 判断是否为敏感词
    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }
    // 前缀树类
    private class TrieNode {
        // 关键词结束标识
        private boolean iskeywordEnd = false;
        // 子节点(key是下级字符,value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean iskeywordEnd() {
            return iskeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            iskeywordEnd = keywordEnd;
        }
        // 添加子节点
        public void addSubNode(Character c , TrieNode node) {
            subNodes.put(c, node);
        }
        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

    }
}
