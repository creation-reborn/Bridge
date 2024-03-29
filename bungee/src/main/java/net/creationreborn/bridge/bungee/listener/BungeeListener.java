/*
 * Copyright 2019 creationreborn.net
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

package net.creationreborn.bridge.bungee.listener;

import net.creationreborn.bridge.bungee.BungeePlugin;
import net.creationreborn.bridge.bungee.event.PaymentEventImpl;
import net.creationreborn.bridge.bungee.event.RegistrationEventImpl;
import net.creationreborn.bridge.common.manager.IntegrationManager;
import net.creationreborn.bridge.common.util.StringUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {
    
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        BungeePlugin.getInstance().getProxy().getScheduler().runAsync(BungeePlugin.getInstance(), () -> {
            IntegrationManager.updateUser(event.getPlayer().getUniqueId(), event.getPlayer().getName());
            IntegrationManager.updateGroups(event.getPlayer().getUniqueId());
        });
    }
    
    @EventHandler
    public void onPayment(PaymentEventImpl event) {
        if (event.getModel().getMinecraftUniqueId() == null) {
            return;
        }
        
        ProxiedPlayer player = BungeePlugin.getInstance().getProxy().getPlayer(event.getModel().getMinecraftUniqueId());
        if (player == null || !player.isConnected()) {
            return;
        }
        
        IntegrationManager.updateGroups(player.getUniqueId());
    }
    
    @EventHandler
    public void onRegistration(RegistrationEventImpl event) {
        if (StringUtils.isBlank(event.getModel().getMinecraftUsername())) {
            return;
        }
        
        ProxiedPlayer player = BungeePlugin.getInstance().getProxy().getPlayer(event.getModel().getMinecraftUsername());
        if (player == null || !player.isConnected()) {
            return;
        }
        
        IntegrationManager.updateUser(player.getUniqueId(), player.getName());
        IntegrationManager.updateGroups(player.getUniqueId());
    }
}