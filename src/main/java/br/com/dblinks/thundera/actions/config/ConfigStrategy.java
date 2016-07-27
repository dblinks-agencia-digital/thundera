package br.com.dblinks.thundera.actions.config;

public interface ConfigStrategy {

    public Boolean isEnabled();

    public void enable();

    public void disable();

}
