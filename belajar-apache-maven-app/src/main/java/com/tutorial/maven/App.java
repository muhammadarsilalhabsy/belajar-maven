package com.tutorial.maven;

import com.google.gson.Gson;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    Gson gson = new Gson();

    Person person = new Person();
    person.setName("Arsil");

    System.out.println(gson.toJson(person));
  }
}
