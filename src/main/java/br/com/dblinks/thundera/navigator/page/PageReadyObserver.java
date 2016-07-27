package br.com.dblinks.thundera.navigator.page;

public class PageReadyObserver {

    private final Page page;

    public PageReadyObserver(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

}
