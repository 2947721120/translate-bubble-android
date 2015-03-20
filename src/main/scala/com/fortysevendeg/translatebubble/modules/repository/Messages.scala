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

package com.fortysevendeg.translatebubble.modules.repository

import com.fortysevendeg.translatebubble.provider.{TranslationHistoryEntity, TranslationHistoryEntityData}
import com.fortysevendeg.translatebubble.utils.LanguageType.LanguageType

case class AddTranslationHistoryRequest(data: TranslationHistoryEntityData)

case class AddTranslationHistoryResponse(
    success: Boolean,
    translationHistoryEntity: Option[TranslationHistoryEntity])

case class DeleteTranslationHistoryRequest(entity: TranslationHistoryEntity)

case class DeleteTranslationHistoryResponse(success: Boolean)

case class FetchTranslationHistoryRequest(
    from: LanguageType,
    to: LanguageType,
    originalText: String)

case class FetchTranslationHistoryResponse(result: Option[TranslationHistoryEntity])

case class FetchAllTranslationHistoryRequest()

case class FetchAllTranslationHistoryResponse(result: Seq[TranslationHistoryEntity])