/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link CallableOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CallableOfTest {

    @Test
    public void convertsFuncIntoCallable() throws Exception {
        MatcherAssert.assertThat(
            new CallableOf<>(
                input -> 1
            ).call(),
            Matchers.equalTo(1)
        );
    }

    @Test
    public void convertsRunnableIntoCallable() throws Exception {
        final AtomicBoolean flag = new AtomicBoolean(false);
        new CallableOf<>(
            () -> { flag.set(true); }
        ).call();
        MatcherAssert.assertThat(
            flag.get(),
            Matchers.is(true)
        );
    }

    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    @Test(expected = Exception.class)
    public void wrapsRuntimeErrorFromRunnable() throws Exception {
        new CallableOf<>(
            () -> { throw new RuntimeException(); }
        ).call();
    }

    @Test
    public void convertsProcIntoCallable() throws Exception {
        final AtomicBoolean flag = new AtomicBoolean(false);
        new CallableOf<>(
            unused -> { flag.set(true); }
        ).call();
        MatcherAssert.assertThat(
            flag.get(),
            Matchers.is(true)
        );
    }

    @Test
    public void convertsFuncWithInputIntoCallable() throws Exception {
        MatcherAssert.assertThat(
            new CallableOf<>(
                num -> num + 1,
                1
            ).call(),
            Matchers.equalTo(2)
        );
    }

}
