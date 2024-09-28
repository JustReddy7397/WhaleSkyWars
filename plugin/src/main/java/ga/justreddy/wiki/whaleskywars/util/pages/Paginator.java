package ga.justreddy.wiki.whaleskywars.util.pages;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author JustReddy
 */
public class Paginator<T> {

    private final UUID owner;
    private List<T> objects;
    private final int pageSize;
    private int currentPage;
    private int amountOfPages;

    public Paginator(UUID owner, T[] objects, int maxPerPage) {
        this(owner, Arrays.asList(objects), maxPerPage);
    }

    public Paginator(UUID owner, List<T> objects, int maxPerPage) {
        this.owner = owner;
        this.objects = objects;
        this.pageSize = maxPerPage;
    }

    public Paginator(UUID owner, Set<T> objects, int maxPerPage) {
        this(owner, new ArrayList<>(objects), maxPerPage);
    }


    public void setElements(List<T> objects) {
        this.objects = objects;
    }

    public boolean hasNext() {
        return currentPage < amountOfPages;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean hasPrev() {
        return currentPage > 1;
    }

    public int getNext() {
        return currentPage + 1;
    }

    public int getPrev() {
        return currentPage - 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<T> getFromPage(int pageNum) {
        List<T> page = new ArrayList<>();
        amountOfPages = getTotalPages();
        currentPage = pageNum;

        if (objects.isEmpty())
            return page;

        double startC = pageSize * (pageNum - 1);
        double finalC = startC + pageSize;

        for (; startC < finalC; startC++)
            if (startC < objects.size()) {
                page.add(objects.get((int) startC));
            }
        return page;

    }

    public int getTotalPages() {
        int page = 1;
        int count = 1;
        for (int i = 0; i < objects.size(); i++) {
            if (count > pageSize) {
                ++page;
                count = 0;
            }
            ++count;
        }
        return page;
    }

}
