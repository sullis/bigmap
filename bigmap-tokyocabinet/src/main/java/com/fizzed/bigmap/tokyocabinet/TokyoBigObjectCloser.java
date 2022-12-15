/*
 * Copyright 2019 Fizzed, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fizzed.bigmap.tokyocabinet;

import com.fizzed.bigmap.impl.AbstractBigObjectCloser;
import tokyocabinet.BDB;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class TokyoBigObjectCloser extends AbstractBigObjectCloser {

    private final BDB bdb;

    public TokyoBigObjectCloser(
            UUID id,
            boolean persistent,
            Path directory,
            BDB bdb) {
        
        super(id, persistent, directory);
        this.bdb = bdb;
    }

    @Override
    public void doClose() throws IOException {
        this.bdb.close();
    }

}