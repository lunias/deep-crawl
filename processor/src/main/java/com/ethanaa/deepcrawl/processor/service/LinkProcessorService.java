package com.ethanaa.deepcrawl.processor.service;


import com.ethanaa.deepcrawl.model.ContentSearchResult;
import com.ethanaa.deepcrawl.model.PrioritizedLink;
import com.ethanaa.deepcrawl.processor.UriTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

@Service
public class LinkProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(LinkProcessorService.class);

    public Set<PrioritizedLink> prioritizeLinks(Set<String> links, Set<ContentSearchResult> contentSearchResults) {

        LOG.info("Got " + links.size() + " link(s)");

        Random random = new Random(System.nanoTime());

        long priority = contentSearchResults.iterator().next().getNumMatches();

        UriTree uriTree = new UriTree();
        for (String link : links) {
            uriTree.add(URI.create(link), Math.toIntExact(priority));
        }

        LOG.info(uriTree.toString());
        LOG.info(uriTree.getPrioritizedUris().toString().replace(",", "\n"));

        TreeSet<PrioritizedLink> prioritizedLinks = new TreeSet<>();
        for (String link : links) {

            prioritizedLinks.add(new PrioritizedLink(link, random.nextInt(256)));
        }

        return prioritizedLinks;
    }
}
