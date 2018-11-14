package com.demo.controller;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class statsCollection {

  //  public static final StatsDClient statsd = new NonBlockingStatsDClient()

    public static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

}
