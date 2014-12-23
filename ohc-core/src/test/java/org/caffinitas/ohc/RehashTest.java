/*
 *      Copyright (C) 2014 Robert Stupp, Koeln, Germany, robert-stupp.de
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.caffinitas.ohc;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class RehashTest
{
    @AfterMethod(alwaysRun = true)
    public void deinit()
    {
        HashEntries.memBufferClear();
        Uns.clearUnsDebugForTest();
    }

    @Test
    public void testRehash() throws IOException
    {
        try (OHCache<Integer, String> cache = OHCacheBuilder.<Integer, String>newBuilder()
                                                            .keySerializer(TestUtils.intSerializer)
                                                            .valueSerializer(TestUtils.stringSerializer)
                                                            .hashTableSize(64)
                                                            .segmentCount(4)
                                                            .build())
        {
            for (int i = 0; i < 1000000; i++)
                cache.put(i, Integer.toOctalString(i));

            assertTrue(cache.stats().getRehashCount() > 0);
        }
    }
}
