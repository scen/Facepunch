package com.stanleycen.facepunch.util;

import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.model.fp.FPUser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/17/14.
 */
public class ResponseParser {
    private static Pattern subforumIdPattern = Pattern.compile("(?<=forumdisplay\\.php\\?f=)\\d+"); // TODO stolen
    private static Pattern threadIdPattern = Pattern.compile("(?<=showthread\\.php\\?t=)\\d+");
    private static Pattern userIdPattern = Pattern.compile("(?<=member\\.php\\?u=)\\d+");
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

    public static FPForum parseSubforum(String response, FPForum ret) {
        if (ret == null) return ret;

        ret.threads.clear();
        ret.subforums.clear();

        Document document = Jsoup.parse(response);

        //threads
        Elements elems = document.select("tr.threadbit");
        for (Element e : elems) {

            Elements head = e.select("h3.threadtitle a.title");

            int id = -1;
            String title = head.text();

            String _id = head.attr("href");
            Matcher m = threadIdPattern.matcher(_id);
            if (m.find()) {
                id = Integer.parseInt(m.group());
            }

            FPThread thread = new FPThread(id, 1, title);

            //author
            head = e.select("div.author a");
            String name = head.text();
            _id = head.attr("href");
            id = -1;
            m = userIdPattern.matcher(_id);
            if (m.find()) {
                id = Integer.parseInt(m.group());
            }
            thread.author = new FPUser(name, id);
            Set<String> classes = e.classNames();
            thread.locked = classes.contains("lock");
            thread.sticky = classes.contains("sticky");
            try {
                thread.replies = Integer.parseInt(e.select("td.threadreplies a").text());
            }
            catch (Throwable t) {
            }
            try {
                thread.views = Integer.parseInt(e.select("td.threadviews span").text());
            } catch( Throwable t) {}
            try {
                if (e.select("span.viewers").size() != 0) {
                    thread.reading = Integer.parseInt(e.select("span.viewers").text().split(" ")[0]);
                }
            } catch (Throwable t) {}

            ret.threads.add(thread);
        }

        ret.fetched = true;
        return ret;
    }
}
