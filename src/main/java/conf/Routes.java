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
 * Copyright (C) 2012-2020 the original author or authors.
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

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import controllers.AdminController;
import controllers.ApplicationController;
import controllers.BidController;
import controllers.FeedbackController;
import controllers.AuthController;
import controllers.ProductController;
import filters.AdminFilter;
import filters.CorsHandlerFilter;
import filters.ValidateUserFilter;

public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @SuppressWarnings("unchecked")
	@Override
    public void init(Router router) {  
        
        // puts test data into db:
        if (!ninjaProperties.isProd()) {
            router.GET().route("/setup").with(ApplicationController::setup);
        }
        
        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/api/login").with(AuthController::login);
        router.POST().route("/api/login").filters(CorsHandlerFilter.class).with(AuthController::loginPost);
        router.GET().route("/api/logout").filters(CorsHandlerFilter.class).with(AuthController::logout);
        
        router.POST().route("/api/register").filters(CorsHandlerFilter.class).with(AuthController::registerUser);
        
        router.PUT().route("/api/updateUser").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(AuthController::updateUser);
        router.GET().route("/api/user/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(AuthController::getUser);
        router.GET().route("/api/me").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(AuthController::getMyProfile);
        
        router.POST().route("/api/addProduct").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::addProduct);
        router.DELETE().route("/api/deleteProduct").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::deleteProduct);
        router.PUT().route("/api/updateProduct/{pid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::updateProduct);
        
        
        router.GET().route("/api/product").filters(CorsHandlerFilter.class).with(ProductController::getAllProducts);
        router.GET().route("/api/product/totalCount").filters(CorsHandlerFilter.class).with(ProductController::totalProductCount);
        router.GET().route("/api/product/totalCountByMaxPrice").filters(CorsHandlerFilter.class).with(ProductController::totalProductCountByMaxPrice);
        router.GET().route("/api/product/{pid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::getSingleProduct);
        router.PUT().route("/api/product/productBidStatus/{pid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(BidController::changeBidStatus);
        router.GET().route("/api/product/getByTitle/{title}").filters(CorsHandlerFilter.class).with(ProductController::getProductByTitle);
        router.GET().route("/api/product/getById/{pid}").filters(CorsHandlerFilter.class).with(ProductController::getProductByPid);
        router.GET().route("/api/product/getByMaxPrice/{maxPrice}").filters(CorsHandlerFilter.class).with(ProductController::getProductByMaxPrice);
        router.GET().route("/api/product/getByUid/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::getProductByUid);
        router.GET().route("/api/product/getBySoldTo/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::getProductBySoldTo);
        router.GET().route("/api/product/getByTitleAndUid/{title}/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(ProductController::getProductByTitleAndUid);
        
        router.POST().route("/api/bid/{pid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(BidController::bidOnProduct);
        router.GET().route("/api/getBid/{pid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(BidController::getAllBidsByPid);
        router.GET().route("/api/getBidByUid/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(BidController::getAllBidsByUid);
        router.POST().route("/api/feedback/{uid}").filters(ValidateUserFilter.class, CorsHandlerFilter.class).with(FeedbackController::addFeedback);
        
        router.POST().route("/api/admin/deleteUser/{uid}").filters(ValidateUserFilter.class, AdminFilter.class, CorsHandlerFilter.class).with(AdminController::deleteUser);
        router.POST().route("/api/admin/deleteProduct/{pid}").filters(ValidateUserFilter.class, AdminFilter.class, CorsHandlerFilter.class).with(AdminController::deleteProduct);
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController::index);
        router.OPTIONS().route("/.*").with(ApplicationController::indexOptions);
    }

}
