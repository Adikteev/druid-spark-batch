/*
 *  Licensed to Metamarkets Group Inc. (Metamarkets) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  Metamarkets licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.druid.indexer.spark

import com.google.inject.Binder
import com.google.inject.Key
import com.google.inject.Module
import com.google.inject.name.Names
import org.apache.druid.guice.GuiceInjectors
import org.apache.druid.guice.JsonConfigProvider
import org.apache.druid.guice.annotations.Self
import org.apache.druid.initialization.DruidModule
import org.apache.druid.initialization.Initialization
import org.apache.druid.server.DruidNode
import java.util.ServiceLoader
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import scala.collection.JavaConverters._

class TestSparkDruidIndexerModule extends FlatSpec with Matchers
{
  "SparkDruidIndexerModules" should "load properly" in {
    val loader: ServiceLoader[DruidModule] = ServiceLoader.load(classOf[DruidModule], classOf[TestSparkDruidIndexerModule].getClassLoader)
    val modules = loader.asScala
    val maybeModule = modules.find(_.getClass.getCanonicalName.startsWith("org.apache.druid.indexer.spark.SparkDruidIndexerModule"))
    maybeModule shouldNot be(None)
    Initialization.makeInjectorWithModules(
      GuiceInjectors.makeStartupInjector(), Seq(
        new Module()
        {
          override def configure(binder: Binder) = {
            JsonConfigProvider
              .bindInstance(
                binder,
                Key.get(classOf[DruidNode], classOf[Self]),
                new DruidNode("spark-indexer-test", null, false, null, null, true, false)
              )
            binder.bindConstant.annotatedWith(Names.named("servicePort")).to(0)
            binder.bindConstant.annotatedWith(Names.named("tlsServicePort")).to(-1)
          }
        },
        maybeModule.get
      ).asJava
    )
  }
}
