package com.kirilo.ws;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TutorialService {
    private static Map<String, Item> tutorialRepository = new ConcurrentHashMap<>();

    static {
        tutorialRepository.put("1", new Item("TomEE Tutorial", 134, "2019-05-27 15:27:16.878", false));
    }

    public List<Item> findAllItems() {
        return new ArrayList<>(tutorialRepository.values());
    }

    public Item findItemById(String id) {
        return tutorialRepository.get(String.valueOf(id));
    }
}
