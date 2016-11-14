package org.shawn.tutorials.jsf.security;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by jiangliang on 2016/4/19.
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
public @interface SpringRequestScoped {}
