package com.stanleycen.facepunch.util;

import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPPost;
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.model.fp.FPUser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private static Pattern postcountPattern = Pattern.compile("(\\d|,)+");

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
            if (e.select("div.nothreads").size() != 0) { // either GMF or something
                break;
            }
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
            String name = head.get(0).text();
            _id = head.get(0).attr("href");
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
                thread.replies = Integer.parseInt(e.select("td.threadreplies a").text().replace(",", ""));
            }
            catch (Throwable t) {
            }
            try {
                thread.views = Integer.parseInt(e.select("td.threadviews span").text().replace(",", ""));
            } catch( Throwable t) {}
            try {
                thread.reading = Integer.parseInt(e.select("span.viewers").text().split(" ")[0]);
            } catch (Throwable t) {}
            thread.lastPoster = new FPUser();
            thread.lastPoster.name = e.select("td.threadlastpost dl dd a:not(.lastpostdate)").get(0).text();
            thread.lastPostTime = e.select("td.threadlastpost dl dd").get(0).text();
            thread.maxPages = 1;
            try {
                thread.maxPages = Integer.parseInt(e.select("span.threadpagenav a:last-of-type").text().replace(",","").split(" ")[0]);
            } catch (Throwable t) {}
            ret.threads.add(thread);
        }

        // subforums
        Elements divs = document.select("#content_inner div");
        if (!divs.isEmpty()) {
            if (divs.get(0).text().contains("Subforums:")) {
                Elements anchors = divs.get(0).select("a");
                for (Element e : anchors) {
                    String title = e.text();
                    int id = Integer.parseInt(e.attr("href").split("/")[2]);
                    ret.subforums.add(new FPForum(id, 1, title));
                }
            }
        }

        ret.fetched = true;
        return ret;
    }

    public static FPThread parseThread(String response, FPThread ret) {
        Document doc = Jsoup.parse(response);
        ret.posts.clear();
        Elements elements = doc.select("li.postbitlegacy");
        for (Element element: elements) {
            long id = Long.parseLong(element.attr("id").substring(5));
            String postDate = element.select("span.date").first().text();

            // Parse author
            String authorName = element.select("div.username_container").text();
            String authorIdText = element.select("div.username_container a").attr("href");
            Matcher authorIdMatcher = userIdPattern.matcher(authorIdText);
            int authorId = -1;
            if (authorIdMatcher.find()) {
                authorId = Integer.parseInt(authorIdMatcher.group());
            }
            String userGroupColor = element.select("div.username_container a font").attr("color");
            // Moderator check
            if (userGroupColor.equals("") && !element.select("div.username_container a span").attr("style").equals("")) {
                userGroupColor = "#00aa00";
            }
            String postcountText = element.select("div#userstats").first().childNode(2).toString();
            Matcher postcountMatcher = postcountPattern.matcher(postcountText);
            int postcount = -1;
            if (postcountMatcher.find()) {
                postcount = Integer.parseInt(postcountMatcher.group().replace(",", ""));
            }
            String joinDate = element.select("div#userstats").first().childNode(0).toString().substring(2);

            FPUser author = new FPUser(authorName, authorId);
            author.joinDate = joinDate;
            author.postCount = postcount;
            author.userGroupColor = userGroupColor;

            String message = element.select("blockquote.postcontent").html();

            FPPost post = new FPPost(id, author, postDate, message);

//            boolean old = element.hasClass("postbitold");
//            post.setOld(old);

            // Parse ratings
//            Map<String, Integer> ratingsMap = new HashMap<String, Integer>();
//            for (Element ratingElement: element.select("span.rating_results span")) {
//                String rating = ratingElement.select("img").attr("alt");
//                int ratingAmount = Integer.parseInt(ratingElement.select("strong").text());
//                ratingsMap.put(rating, ratingAmount);
//            }
//            post.setRatings(ratingsMap);

            ret.posts.add(post);

        }

        return ret;
    }
}
