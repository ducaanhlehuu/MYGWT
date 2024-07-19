package com.hello.server;

import javax.servlet.annotation.WebFilter;

import com.googlecode.objectify.ObjectifyFilter;

@WebFilter(urlPatterns = {"/*"})
public class ObjectifyFilterServlet extends ObjectifyFilter {
}