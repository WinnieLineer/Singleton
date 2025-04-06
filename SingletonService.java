package com.example.singleton;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author memorykghs
 * @date 2025/4/6
 */
public class SingletonService {
    Map<String, Singleton> singletonMap;

    /**
     * 解耦：非常乾淨，新增新的邏輯只需要建立新的 class 並實作 Singleton 介面的方法
     * 她也符合依賴反轉原則：程式應該要依賴高級模組（interface）而不是實例
     * 等同於 List 和 ArrayList / LinkedList
     * 一旦架構建立之後，後續維護很方便，debug 也方便
     *
     * 情境：有很多個根據不同情境處理邏輯的時候
     * 行動支付：根據不同的按鈕選項，決定要走 LINE Pay / 街口 / 台灣 Pay
     * 沒有順序性，且只會有一個對象處理邏輯
     *
     * n8n, Notion API, Zapier
     * @param singletonList
     */
    public SingletonService(List<Singleton> singletonList){
        singletonMap = singletonList.stream()
            .collect(Collectors.toMap(Singleton::getType, Function.identity()));
    }

    public void test(String type){
        Singleton service = singletonMap.get(type);
        service.test();
    }
}
