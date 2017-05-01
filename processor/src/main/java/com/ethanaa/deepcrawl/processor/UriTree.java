package com.ethanaa.deepcrawl.processor;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class UriTree {

    private static final Logger LOG = LoggerFactory.getLogger(UriTree.class);

    private final Map<String, UriTree> branches = new ConcurrentHashMap<>();
    private final Map<String, Integer> priorities = new ConcurrentHashMap<>();

    private static final Comparator<Entry<String, Integer>> PRIORITY_COMPARATOR =
            (Entry<String, Integer> e1, Entry<String, Integer> e2) -> {

                int priorityComparison = -(e1.getValue().compareTo(e2.getValue()));
                if (priorityComparison == 0) {
                    return e1.getKey().compareTo(e2.getKey());
                }

                return priorityComparison;
            };

    public UriTree() {}

    public UriTree(URI uri) {

        add(uri);
    }

    public void add(URI uri) {

        add(uri, 0);
    }

    public void add(URI uri, int priority) {

        add(tokenize(uri), 0, priority);
    }

    protected void add(String[] tokens, int depth, int priority) {

        if (depth >= tokens.length) {
            return;
        }

        String key = tokens[depth];

        UriTree branch = this.branches.computeIfAbsent(key,
                (k) -> new UriTree());

        this.branches.computeIfPresent(key,
                (k, v) -> {
                    this.priorities.compute(k,
                            (pk, pv) -> pv == null ? priority
                                    : pv + priority);
                    return v;
                });

        branch.add(tokens, depth + 1, priority);
    }

    public static void getPrioritizedUris(UriTree uriTree, StringBuilder sb, List<URI> prioritizedUris) {

        if (uriTree.priorities.entrySet().isEmpty()) {
            return;
        }

        SortedSet<Entry<String, Integer>> prioritizedTokenEntries =
                new TreeSet<>(PRIORITY_COMPARATOR);

        prioritizedTokenEntries.addAll(uriTree.priorities.entrySet());

        StringBuilder levelSb = new StringBuilder(sb);

        for (Entry<String, Integer> entry : prioritizedTokenEntries) {

            String token = entry.getKey();
            if (token.contains("=")) {
                levelSb.replace(levelSb.length() - 1, levelSb.length(), "").append("?").append(token);
            } else {
                levelSb.append(token).append("/");
            }

            try {
                prioritizedUris.add(new URI(levelSb.toString()));
            } catch (URISyntaxException use) {
                LOG.error("Could not convert to URI: " + levelSb.toString(), use);
            }

            getPrioritizedUris(uriTree.branches.get(token), levelSb, prioritizedUris);

            levelSb = new StringBuilder(sb);
        }
    }

    public List<URI> getPrioritizedUris() {

        List<URI> uris = new ArrayList<>();
        getPrioritizedUris(this, new StringBuilder(), uris);

        return uris;
    }

    private String[] tokenize(URI uri) {

        int port = uri.getPort();

        String uriHost = uri.getHost();
        if (uriHost.startsWith("www.")) {
            uriHost = uriHost.substring(4);
        }

        String host = uriHost + (port != -1 ? ":" + port : "");
        String path = uri.getPath();
        String query = uri.getQuery();
        String fragment = uri.getFragment();

        path = path.replaceAll("^/", "");
        path = path.replaceAll("/$", "");

        String[] pathTokens;
        if (!path.isEmpty()) {
            pathTokens = path.split("/");
        } else {
            pathTokens = new String[]{};
        }

        boolean hasFragment = fragment != null && !fragment.isEmpty();
        boolean hasQuery = query != null && !query.isEmpty();

        String[] tokens = new String[pathTokens.length + (hasFragment ? 1 : 0) + (hasQuery ? 2 : 1)];

        tokens[0] = host;
        for (int i = 0; i < pathTokens.length; i++) {
            tokens[i + 1] = pathTokens[i];
        }

        if (hasFragment) {
            tokens[tokens.length - (1 + (hasQuery ? 1 : 0))] = "#" + fragment;
        }

        if (hasQuery) {
            tokens[tokens.length - 1] = query;
        }

        return tokens;
    }

    private Map<String, UriTree> getBranches() {

        return branches;
    }

    private int getPriority(String token) {

        return priorities.getOrDefault(token, -1);
    }

    public static String stringify(UriTree uriTree, StringBuilder sb, int depth) {

        if (uriTree == null) {
            return "";
        }

        Set<String> tokens = uriTree.getBranches().keySet();

        for (String token : tokens) {

            String printToken = token;
            int priority = uriTree.getPriority(token);

            if (token.contains("=")) {
                printToken = "?" + token;
            } else if (!token.contains("localhost") && !token.contains(".")) {
                printToken += "/";
            }

            printToken += " (" + priority + ")";

            String keyString = Strings.repeat("-", depth * 2) + (depth > 0 ? "> " : ". ") + printToken;
            if (depth > 0) {
                keyString = "|" + keyString;
            }

            sb.append(keyString).append("\n");
            sb.append(stringify(uriTree.getBranches().get(token), sb, depth + 1));
        }

        return depth == 0 ? sb.toString() : "";
    }

    @Override
    public String toString() {

        return stringify(this, new StringBuilder(), 0);
    }
}