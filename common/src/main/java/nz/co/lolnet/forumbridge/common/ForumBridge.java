/*
 * Copyright 2018 lolnet.co.nz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.co.lolnet.forumbridge.common;

import nz.co.lolnet.forumbridge.common.configuration.Config;
import nz.co.lolnet.forumbridge.common.configuration.Configuration;
import nz.co.lolnet.forumbridge.common.manager.IntegrationManager;
import nz.co.lolnet.forumbridge.common.util.Logger;
import nz.co.lolnet.forumbridge.common.util.Reference;

import java.util.Optional;

public class ForumBridge {
    
    private static ForumBridge instance;
    private final Platform platform;
    private final Logger logger;
    private final Configuration configuration;
    
    public ForumBridge(Platform platform) {
        instance = this;
        this.platform = platform;
        this.logger = new Logger();
        this.configuration = new Configuration();
    }
    
    public void loadForumBridge() {
        getConfiguration().loadConfiguration();
        IntegrationManager.buildNodes();
        getConfiguration().saveConfiguration();
        getLogger().info("{} v{} loaded", Reference.NAME, Reference.VERSION);
    }
    
    public static ForumBridge getInstance() {
        return instance;
    }
    
    public Platform getPlatform() {
        return platform;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<Config> getConfig() {
        if (getConfiguration() != null) {
            return Optional.ofNullable(getConfiguration().getConfig());
        }
        
        return Optional.empty();
    }
}