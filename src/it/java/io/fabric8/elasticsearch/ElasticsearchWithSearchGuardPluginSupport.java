/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.elasticsearch;

import java.util.Collection;

import com.floragunn.searchguard.SearchGuardPlugin;
import com.floragunn.searchguard.ssl.SearchGuardSSLPlugin;
import io.fabric8.elasticsearch.plugin.ConfigurationSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;

/**
 *
 */
public class ElasticsearchWithSearchGuardPluginSupport extends ESIntegTestCase {

    static {
      System.setProperty("tests.client.ratio", "1");
    }

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return pluginList(SearchGuardPlugin.class, SearchGuardSSLPlugin.class);
    }

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        Settings settings = Settings.builder()
          .put(super.nodeSettings(nodeOrdinal))
          //
          .put("http.cors.enabled", true)
          .put("node.local", false)
          .put("node.mode", false)
          .put("discovery.zen.minimum_master_nodes", 1)
          //
          .put("path.conf", this.getDataPath("/config"))
          //.put(ConfigurationSettings.SG_CLIENT_KS_PATH, "src/it/resources/sg_certs/kirk-keystore.jks")
          .put(ConfigurationSettings.SG_CLIENT_KS_PATH, "kirk-keystore.jks")
          //.put(ConfigurationSettings.SG_CLIENT_TS_PATH, "src/it/resources/sg_certs/truststore.jks")
          .put(ConfigurationSettings.SG_CLIENT_TS_PATH, "truststore.jks")
          //
          .put("searchguard.authcz.admin_dn", "CN=kirk,OU=client,O=client,L=Test,C=DE")
          .put("searchguard.config_index_name", "searchguard")
          //
          .put("searchguard.ssl.transport.enabled", true)
          .put("searchguard.ssl.transport.enforce_hostname_verification", false)
          .put("searchguard.ssl.transport.keystore_type", "JKS")
          //.put("searchguard.ssl.transport.keystore_filepath", "src/it/resources/sg_certs/transport/node-0-keystore.jks")
          .put("searchguard.ssl.transport.keystore_filepath", "transport-node-0-keystore.jks")
          .put("searchguard.ssl.transport.keystore_password", "changeit")
          .put("searchguard.ssl.transport.truststore_type", "JKS")
          //.put("searchguard.ssl.transport.truststore_filepath", "src/it/resources/sg_certs/truststore.jks")
          .put("searchguard.ssl.transport.truststore_filepath", "truststore.jks")
          .put("searchguard.ssl.transport.truststore_password", "changeit")
          .put("searchguard.ssl.transport.enable_openssl_if_available", false)
          //
          .put("searchguard.ssl.http.enabled", true)
          .put("searchguard.ssl.http.keystore_type", "JKS")
          //.put("searchguard.ssl.http.keystore_filepath", "src/it/resources/sg_certs/node-0-keystore.jks")
          .put("searchguard.ssl.http.keystore_filepath", "node-0-keystore.jks")
          .put("searchguard.ssl.http.keystore_password", "changeit")
          .put("searchguard.ssl.http.clientauth_mode", "OPTIONAL")
          .put("searchguard.ssl.http.truststore_type", "JKS")
          //.put("searchguard.ssl.http.truststore_filepath", "src/it/resources/sg_certs/truststore.jks")
          .put("searchguard.ssl.http.truststore_filepath", "truststore.jks")
          .put("searchguard.ssl.http.truststore_password", "changeit")
          .put("searchguard.ssl.http.enable_openssl_if_available", false)
          //
          .build();
        return settings;
    }
}
