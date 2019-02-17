package com.agileengine.analyzer.services;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;

import static com.agileengine.analyzer.domain.Constants.TAG;
import static com.agileengine.analyzer.domain.Constants.TEXT;
import static com.agileengine.analyzer.utils.HTMLUtils.findAllElements;
import static java.util.Collections.singletonList;

public class AnalyzeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzeService.class);

    public Optional<String> getElementPathInFile(Element element, File changedHTML) {
        return findAllElements(changedHTML).flatMap(elements ->
                getMatchesMap(element, elements).entrySet().stream()
                        .max(Comparator.comparing(o -> ((Integer) o.getValue().size())))
                        .map(entry -> {
                            showOverlap(entry);
                            return buildStringOutput(entry.getKey());
                        }));
    }

    private Map<Element, List<Attribute>> getMatchesMap(Element originalElement, Elements elementsFromChangedHTML) {
        Map<Element, List<Attribute>> matches = new HashMap<>();
        BiConsumer<Element, Attribute> filler = (Element element, Attribute attribute) -> {
            if (matches.get(element) == null) {
                matches.put(element, new ArrayList<>(singletonList(attribute)));
            } else {
                matches.get(element).add(attribute);
            }
        };

        elementsFromChangedHTML.forEach(element -> {
            if (element.tag().equals(originalElement.tag())) {
                filler.accept(element, new Attribute(TAG, element.tagName()));
            }
            if (element.text().equals(originalElement.text())) {
                filler.accept(element, new Attribute(TEXT, element.text()));
            }
            element.attributes().asList().forEach(attribute -> {
                if (originalElement.attributes().asList().contains(attribute)) {
                    filler.accept(element, attribute);
                }
            });
        });
        return matches;
    }


    private String buildStringOutput(Element element) {
        return String.join(" > ", buildPath(element));
    }

    private List<String> buildPath(Element element) {
        List<String> path = new ArrayList<>();
        do {
            path.add(element.tagName());
            element = element.parent();
        } while (element.hasParent());
        Collections.reverse(path);
        return path;
    }

    private void showOverlap(Map.Entry<Element, List<Attribute>> entry) {
        List<Attribute> attributes = entry.getValue();
        LOGGER.info("Found element has [{}] matches", attributes.size());
        attributes.forEach(attribute -> LOGGER.info("Overlap on [{}] value [{}] ", attribute.getKey(), attribute.getValue()));
    }
}
