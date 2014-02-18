package com.stanleycen.facepunch.util;

import com.stanleycen.facepunch.model.fp.FPForum;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public class ResponseParser {
    private static Pattern subforumIdPattern = Pattern.compile("(?<=forumdisplay\\.php\\?f=)\\d+"); // TODO stolen
    private static Pattern subforumPagecountPattern = Pattern.compile("(?<=&page=)\\d+(?=&)");

    public static ArrayList<FPForum> parseHome(String response) {
        ArrayList<FPForum> res = new ArrayList<>();

        Document document = Jsoup.parse(response);

        Elements elemSubforums = document.select("table.forums");

        for (Element elemSubforum : elemSubforums) {
            String title = elemSubforum.select("tr.forumhead td h2 a").text();
            FPForum forum = new FPForum(-1, 1, title);
            Elements elems = elemSubforum.select("div.forumdata");
            for (Element e : elems) {
                String sfTitle = e.select("h2.forumtitle a").text();
                String _sfId = e.select("h2.forumtitle a").attr("href");
                Elements ee = e.select("p.forumdescription");
                Matcher matcher = subforumIdPattern.matcher(_sfId);
                if (matcher.find()) {
                    int id = Integer.parseInt(matcher.group());
                    FPForum subforum = new FPForum(id, 1, sfTitle);
                    if (!ee.isEmpty()) {
                        subforum.desc = ee.text();
                    }
                    forum.subforums.add(subforum);
                }
            }
            res.add(forum);
        }

        return res;
    }

}
