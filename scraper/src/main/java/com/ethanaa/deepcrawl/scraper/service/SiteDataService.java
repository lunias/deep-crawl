package com.ethanaa.deepcrawl.scraper.service;

import com.ethanaa.deepcrawl.model.ScrapeRequest;
import com.ethanaa.deepcrawl.model.SiteData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@Service
public class SiteDataService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDataService.class);

    private static final String USER_AGENT = "Mozilla/5.0 Firefox/40.1";
    private static final String REFERRER = "http://www.google.com";
    private static final int TIMEOUT_MILLIS = 5_000;
    private static final String CHARSET = "ISO-8859-15";

    public SiteData getSiteData(ScrapeRequest scrapeRequest) {

        String uri = scrapeRequest.getUri();

        Document document;
        try {
            document = getDocument(uri);
        } catch (IOException ioe) {
            LOG.error("Could not get document: " + uri, ioe);
            return null;
        }

        String content = getContent(document);

        Set<URI> links = getLinks(document);
        Set<String> cleanedLinks = cleanLinks(links);

        SiteData siteData = new SiteData(uri, content, cleanedLinks);

        LOG.debug("Got site data: " + siteData);

        return siteData;
    }

    private Document getDocument(String uri) throws IOException {

        Connection connection = Jsoup.connect(uri)
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .followRedirects(true)
                .timeout(TIMEOUT_MILLIS);

        Connection.Response response = connection.execute();

        response.charset(CHARSET);

        return response.parse();
    }

    private String getContent(Document document) {

        Element body = document.body();

        if (body == null) {
            return "";
        }

        return body.text();
    }

    private Set<URI> getLinks(Document document) {

        Elements links = document.select(
                "a[href]:not([href~=(?i)\\.(jpe?g|png|gif|bmp|svg|pdf)$])");

        if (links == null) {
            return new HashSet<>();
        }

        Set<URI> uris = new HashSet<>(links.size());
        for (Element link : links) {

            String absHref = link.attr("abs:href");
            if (absHref == null || absHref.isEmpty()) {
                continue;
            }

            try {

                URI uri = new URI(absHref);
                uris.add(uri);

            } catch (URISyntaxException use) {
                LOG.error("Could not convert to URI: " + absHref, use);
            }
        }

        return uris;
    }

    private Set<String> cleanLinks(Set<URI> links) {

        Set<String> cleanedLinks = new HashSet<>();
        for (URI link : links) {
            if (!shouldRemove(link)) {
                cleanedLinks.add(link.toString());
            }
        }

        return cleanedLinks;
    }

    private boolean shouldRemove(URI link) {

        boolean remove = link.isOpaque();

        if (remove) {
            LOG.debug("Removing link: " + link);
        }

        return remove;
    }
}
