/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;


import ninja.Result;
import ninja.Results;

import com.google.inject.Inject;

import dao.ArticleDao;
import dao.SetupDao;

public class ApplicationController {

    @Inject
    ArticleDao articleDao;

    @Inject
    SetupDao setupDao;

    public ApplicationController() {

    }

    /**
     * Method to put initial data in the db...
     * 
     * @return
     */
    public Result setup() {

        setupDao.setup();

        return Results.ok();

    }

    public Result index() {

//        Article frontPost = articleDao.getFirstArticleForFrontPage();
//
//        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();
//
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("frontArticle", frontPost);
//        map.put("olderArticles", olderPosts);

    	return Results.notFound().json().render("404 Not Found");
    }
    
    public Result indexOptions() {
    	return Results.json().addHeader("Content-Type", "application/json").addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").addHeader("Access-Control-Allow-Credentials", "true").addHeader("Access-Control-Allow-Origin", "http://localhost:4200").addHeader("Access-Control-Allow-Headers", "content-type").render("cors accepted...");
    	
    }
}
