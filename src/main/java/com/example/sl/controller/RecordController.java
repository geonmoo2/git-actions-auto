package com.example.sl.controller;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/record")
public class RecordController {

    @GetMapping("/teams")
    public String getTeamRankings(Model model) {
        String baseUrl = "http://www.statiz.co.kr";
        String url = baseUrl + "/stat.php?mid=stat&re=0&ys=1982&ye=2023&se=0&te=&tm=&ty=0&qu=auto&po=0&as=&ae=&hi=&un=&pl=&da=1&o1=WAR_ALL_ADJ&o2=TPA&de=1&tr=&cv=&ml=1&sn=30&pa=0&si=&cn=&lr=1";
        List<Map<String, String>> teamRankings = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements divs = doc.select("div.table_type03");
            log.debug("Number of .table_type03 divs found: " + divs.size());
            if (divs.size() >= 3) {
                Element div = divs.get(2);
                Elements tables = div.select("table");
                log.debug("Number of tables found in 3rd .table_type03 div: " + tables.size());
                if (!tables.isEmpty()) {
                    Element table = tables.first();
                    Elements rows = table.select("tbody tr");
                    Elements headers = table.select("thead tr th");

                    List<String> columnNames = new ArrayList<>();
                    headers.forEach(header -> columnNames.add(header.text()));
                    columnNames.add("이미지");

                    rows.forEach(row -> {
                        Elements columns = row.select("td");
                        if (!columns.isEmpty()) {
                            Map<String, String> rowData = new LinkedHashMap<>();
                            for (int i = 0; i < columns.size(); i++) {
                                if (i == 1) { // 두 번째 컬럼
                                    Elements divRankTeams = columns.get(i).select("div.rank_teams");
                                    if (!divRankTeams.isEmpty()) {
                                        Elements aTag = divRankTeams.select("a");
                                        if (!aTag.isEmpty()) {
                                            String teamName = aTag.text();
                                            Elements imgTag = aTag.select("img");
                                            if (!imgTag.isEmpty()) {
                                                String imgUrl = imgTag.attr("src");
                                                if (!imgUrl.startsWith("http")) {
                                                    imgUrl = "https://statiz.sporki.com" + imgUrl;
                                                }
                                                rowData.put("이미지", imgUrl);
                                                log.debug("Image URL: " + imgUrl); // 디버깅을 위한 로깅
                                            }
                                            rowData.put(columnNames.get(i), teamName);
                                        }
                                    }
                                } else {
                                    rowData.put(columnNames.get(i), columns.get(i).text());
                                }
                            }
                            log.debug("Row data: " + rowData);
                            teamRankings.add(rowData);
                        }
                    });
                }
            }
        } catch (IOException e) {
            log.error("Error fetching data from URL: " + url, e);
            model.addAttribute("errorMessage", "Failed to fetch data from the source website.");
        }

        model.addAttribute("teamRankings", teamRankings);
        return "record/teamrank";  // 템플릿 경로를 지정
    }
}