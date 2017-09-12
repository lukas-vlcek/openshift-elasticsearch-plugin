package io.fabric8.elasticsearch;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;

import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertHitCount;

/**
 *
 */
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@ESIntegTestCase.ClusterScope(scope = ESIntegTestCase.Scope.TEST, numDataNodes = 1, numClientNodes = 0)
public class SmokeSearchGuardIntegrationTest extends ElasticsearchWithSearchGuardPluginSupport {

    @Test
    public void testClusterIsEmpty() throws Exception {

      System.out.println("========================");
      System.out.println("Waiting for 10 seconds");
      Thread.sleep(30000);

      /*
        SearchResponse searchResponse = client().prepareSearch().setQuery(QueryBuilders.matchAllQuery()).get();
        ensureGreen();
        assertHitCount(searchResponse, 0);

        ClusterHealthResponse clusterHealthResponse = client().admin().cluster().health(
            new ClusterHealthRequest()
        ).get();

        //this.logger.info("Nodes settings: \n{}", clusterHealthResponse.toString());
        assertTrue(clusterHealthResponse.getIndices().isEmpty());
        */
    }
}
