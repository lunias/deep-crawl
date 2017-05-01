package com.ethanaa.deepcrawl.scraper;


import com.ethanaa.deepcrawl.model.SiteData;
import com.ethanaa.deepcrawl.model.ScrapeRequest;
import com.ethanaa.deepcrawl.scraper.service.SiteDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.IOException;

@EnableBinding(Processor.class)
public class ScrapeRequestProcessor {

    // {"uri":"http://www.cnn.com"}

    private static final Logger LOG = LoggerFactory.getLogger(ScrapeRequestProcessor.class);

    private SiteDataService siteDataService;

    @Autowired
    public ScrapeRequestProcessor(SiteDataService siteDataService) {

        this.siteDataService = siteDataService;
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public SiteData receive(ScrapeRequest scrapeRequest) throws IOException {

        LOG.info("Received scrape request: " + scrapeRequest);

        return siteDataService.getSiteData(scrapeRequest);
    }
}
