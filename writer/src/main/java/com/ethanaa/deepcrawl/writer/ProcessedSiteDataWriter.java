package com.ethanaa.deepcrawl.writer;

import com.ethanaa.deepcrawl.model.ProcessedSiteData;
import com.ethanaa.deepcrawl.writer.repository.ScrapedPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class ProcessedSiteDataWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessedSiteDataWriter.class);

    private ScrapedPageRepository scrapedPageRepository;

    @Autowired
    public ProcessedSiteDataWriter(ScrapedPageRepository scrapedPageRepository) {

        this.scrapedPageRepository = scrapedPageRepository;
    }

    public void write(ProcessedSiteData processedSiteData) {

        LOG.info("Received processed site data: " + processedSiteData);


    }
}
