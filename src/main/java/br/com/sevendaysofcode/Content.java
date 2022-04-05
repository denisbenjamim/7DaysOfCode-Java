package br.com.sevendaysofcode;

public interface Content extends Comparable<Content> {
    String title();
    String urlImage();
    String rating();
    String year();
    String type();
}
