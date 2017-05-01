package com.ethanaa.deepcrawl.processor;


import org.junit.Test;

import java.net.URI;

public class UriTreeTests {

    @Test
    public void test() {

        UriTree uriTree = new UriTree();

        uriTree.add(URI.create("http://localhost:15672/#/queues/%2F/scrape-requests.anonymous.9aUmSGvBTkGAv4y7cLSpnA"), 2);
        uriTree.add(URI.create("http://www.google.com/images/mine.html"), 10);
        uriTree.add(URI.create("http://www.google.com/search/images?query=test"), 1);
        uriTree.add(URI.create("http://www.google.com/search/images?query=test2&asdf=true"), 2);
        uriTree.add(URI.create("http://www.google.com/search/text"), 6);
        uriTree.add(URI.create("http://www.google.com"), 0);

        System.out.println(uriTree);
        System.out.println(uriTree.getPrioritizedUris().toString().replaceAll(",", "\n"));
    }
}
