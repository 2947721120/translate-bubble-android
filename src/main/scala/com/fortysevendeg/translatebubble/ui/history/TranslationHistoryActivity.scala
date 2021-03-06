/*
 * Copyright (C) 2015 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortysevendeg.translatebubble.ui.history

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.{GridLayoutManager, LinearLayoutManager}
import android.view.MenuItem
import com.fortysevendeg.macroid.extras.DeviceMediaQueries._
import com.fortysevendeg.macroid.extras.RecyclerViewTweaks._
import com.fortysevendeg.translatebubble.modules.ComponentRegistryImpl
import com.fortysevendeg.translatebubble.modules.repository.FetchAllTranslationHistoryRequest
import com.fortysevendeg.translatebubble.provider.TranslationHistoryEntity
import com.fortysevendeg.translatebubble.ui.commons.{HistoryItemDecorator, ListLayout}
import macroid.FullDsl._
import macroid.{ContextWrapper, Contexts, Ui}

import scala.concurrent.ExecutionContext.Implicits.global

class TranslationHistoryActivity
  extends Activity
  with Contexts[Activity]
  with ComponentRegistryImpl
  with ListLayout {

  override lazy val contextProvider: ContextWrapper = activityContextWrapper

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(content)

    getActionBar.setDisplayHomeAsUpEnabled(true)
    getActionBar.setHomeButtonEnabled(true)

    runUi(initializeUi ~
      loadTranslationHistory() ~
      (reloadButton <~ On.click(loadTranslationHistory())))
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean =
    item.getItemId match {
      case android.R.id.home => finish(); true
      case _ => super.onOptionsItemSelected(item)
    }

  def loadTranslationHistory(): Ui[_] = {
    val result = for {
      response <- repositoryServices.fetchAllTranslationHistory(FetchAllTranslationHistoryRequest())
    } yield response.result
    result mapUi {
      list =>
        reloadList(list)
    } recoverUi {
      case _ => failed()
    }
    loading()
  }

  def reloadList(translationHistoryItems: Seq[TranslationHistoryEntity]): Ui[_] = translationHistoryItems.length match {
    case 0 => empty()
    case _ => adapter(new TranslationHistoryAdapter(translationHistoryItems))
  }

}

