package com.ethanaa.deepcrawl.processor.service;


import com.ethanaa.deepcrawl.model.ContentSearchResult;
import com.ethanaa.deepcrawl.model.PageRelevanceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PageAnalysisService {

    private static final Logger LOG = LoggerFactory.getLogger(PageAnalysisService.class);

    private static final Pattern SEARCH_PATTERN = Pattern.compile("trump", Pattern.CASE_INSENSITIVE);

    public Set<ContentSearchResult> searchContent(String content) {

        long matchCount = 0L;
        Set<ContentSearchResult> contentSearchResults = new LinkedHashSet<>();

        Matcher matcher = SEARCH_PATTERN.matcher(content);
        boolean found = false;

        while (matcher.find()) {
            matchCount++;
            found = true;
        }

        if (found) {
            contentSearchResults.add(new ContentSearchResult(SEARCH_PATTERN.pattern(), matchCount));
        }

        return contentSearchResults;
    }

    public PageRelevanceResult calculateRelevance(String uriString, Set<ContentSearchResult> contentSearchResults) {

        try {

            URI uri = new URI(uriString);

            return new PageRelevanceResult(1);

        } catch (URISyntaxException use) {
            LOG.error("Could not convert to URI: " + uriString, use);
        }

        return new PageRelevanceResult();
    }
}
