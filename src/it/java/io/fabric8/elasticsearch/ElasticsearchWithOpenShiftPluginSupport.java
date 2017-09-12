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

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;

import io.fabric8.elasticsearch.plugin.ConfigurationSettings;

/**
 * This starts Elasticsearch nodes with installed OpenShift plugin
 * which also installs SearchGuard plugin due to transitive dependency.
 */
public class ElasticsearchWithOpenShiftPluginSupport extends ESIntegTestCase {

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return pluginList(io.fabric8.elasticsearch.plugin.OpenShiftElasticSearchPlugin.class);
    }

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {

      String baseLocation = this.getClass().getResource("").getPath();
      baseLocation += "../../../../..";
      baseLocation = Paths.get(baseLocation).normalize().toUri().getPath();

      Settings settings = Settings.builder()
            .put(super.nodeSettings(nodeOrdinal))
            .put("path.conf", this.getDataPath("/config"))
            //
            .put("node.master", true)
            .put("node.data", true)
//            .put("network.host", "0.0.0.0")
//            .put("http.port", "9200-9300")
//            .put("transport.tcp.port", "9300-9400")
//            .put("node.mode", "network")
            //
            // set to false to completely disable Searchguard plugin functionality, this should result into failed tests?
            // is true by default, no?
            //.put("searchguard.enabled", true)
            //
            // Disabling ssl should fail, though it seems to be overridden somewhere...
            //.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENABLED, false)
            //.put("searchguard.ssl.http.enabled", false)
            //
//            .put(ConfigurationSettings.SG_CLIENT_KS_PATH, "src/it/resources/sg_certs/kirk-keystore.jks")
//            .put(ConfigurationSettings.SG_CLIENT_KS_PATH, "kirk-keystore.jks")
//            .put(ConfigurationSettings.SG_CLIENT_TS_PATH, "src/it/resources/sg_certs/truststore.jks")
//            .put(ConfigurationSettings.SG_CLIENT_TS_PATH, "truststore.jks")
            //
            //.put("searchguard.authcz.admin_dn", "CN=system.admin,OU=OpenShift,O=Logging")
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
            .put("io.fabric8.elasticsearch.kibana.mapping.app",   baseLocation + "samples/applications_mapping.json")
            .put("io.fabric8.elasticsearch.kibana.mapping.ops",   baseLocation + "samples/applications_mapping.json")
            .put("io.fabric8.elasticsearch.kibana.mapping.empty", baseLocation + "samples/applications_mapping.json")
            //
            .build();
        return settings;
    }
}
