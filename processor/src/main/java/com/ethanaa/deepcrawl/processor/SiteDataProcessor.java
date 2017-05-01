package com.ethanaa.deepcrawl.processor;


import com.ethanaa.deepcrawl.model.*;
import com.ethanaa.deepcrawl.processor.service.LinkProcessorService;
import com.ethanaa.deepcrawl.processor.service.PageAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Set;

@EnableBinding(Processor.class)
public class SiteDataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDataProcessor.class);

    private PageAnalysisService pageAnalysisService;
    private LinkProcessorService linkProcessorService;

    @Autowired
    public SiteDataProcessor(PageAnalysisService pageAnalysisService, LinkProcessorService linkProcessorService) {

        this.pageAnalysisService = pageAnalysisService;
        this.linkProcessorService = linkProcessorService;
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public ProcessedSiteData receive(SiteData siteData) {

        LOG.info("Received site data: " + siteData);

        Set<ContentSearchResult> contentSearchResults =
                pageAnalysisService.searchContent(siteData.getContent());

        PageRelevanceResult relevanceResult =
                pageAnalysisService.calculateRelevance(siteData.getUri(), contentSearchResults);

        Set<PrioritizedLink> prioritizedLinks =
                linkProcessorService.prioritizeLinks(siteData.getLinks(), contentSearchResults);

        return new ProcessedSiteData();
    }
}
