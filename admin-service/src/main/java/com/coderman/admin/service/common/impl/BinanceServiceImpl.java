package com.coderman.admin.service.common.impl;

import com.coderman.admin.service.common.BinanceService;
import com.coderman.admin.utils.HttpClientUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author ：zhangyukang
 * @date ：2025/02/08 14:10
 */
@Service
public class BinanceServiceImpl implements BinanceService {


    public static void main(String[] args) throws IOException {

        String symbolsParam = "[\"BTCUSDT\",\"TRUMPUSDT\",\"DOGEUSDT\",\"BIOUSDT\"]";
        String encodedSymbols = URLEncoder.encode(symbolsParam, StandardCharsets.UTF_8.toString());

        String url = "https://api.binance.com/api/v3/ticker/price?symbols=" + encodedSymbols;

        String s = HttpClientUtil.doGet(url, Maps.newHashMap());
        System.out.println(s);


    }
}
