package com.ngcafai.QandA.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    /**
     * replacement for sensitive words
     */
    private static final String DEFAULT_REPLACEMENT = "****";

    /**
     * add the words in SensitiveWord.txt to the tree
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String newWord;
            while ((newWord = reader.readLine()) != null) {
                addWord(newWord.trim());
            }
        } catch (Exception e) {
            logger.error("读取敏感词文件失败  " + e.getMessage());
        }
    }

    private class TrieNode {
        // whether this node is the end of a keyword
        private boolean end = false;

        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return this.end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    // root node
    private TrieNode rootNode = new TrieNode();

    /**
     *
     * @param c
     * @return true if c is a symbol; false otherwise
     */
    public boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    /**
     * add a new word to the tree
     */
    private void addWord(String newWord) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < newWord.length(); i++){
            Character c = newWord.charAt(i);
            if (isSymbol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);

            if(node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if(i == newWord.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * filter the sensitive words in the text
     * @param text
     * @return
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    stringBuilder.append(c);
                    begin++;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if(tempNode == null) {
                stringBuilder.append(text.charAt(begin));
                position = ++begin;
                tempNode = rootNode;
            } else if(tempNode.isKeywordEnd()){
                // detect a sensitive word
                stringBuilder.append(DEFAULT_REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                position++;
            }
        }

        stringBuilder.append(text.substring(begin));
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWord("色情");
        sensitiveService.addWord("色情网站");
        System.out.println(sensitiveService.filter("   欢  迎收看色  情a网站色情网站anv"));

    }

}
