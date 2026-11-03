/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import base.SpecBase
import models.UserAnswers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{never, times, verify, when}
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository

import scala.concurrent.Future

class KeepAliveControllerSpec extends SpecBase with MockitoSugar {
  private abstract class Setup extends MockitoSugar {
    val givenUserAnswers: Option[UserAnswers]

    val mockSessionRepository: SessionRepository = mock
    when(mockSessionRepository.keepAlive(any())) thenReturn Future.successful(true)

    lazy val controllerUnderTest = new KeepAliveController(
      mockAppControllerComponents(givenUserAnswers),
      mockSessionRepository
    )
  }

  "keepAlive" - {

    "when the user has answered some questions" - {

      "must keep the answers alive and return OK" in new Setup {
        val givenUserAnswers: Option[UserAnswers] = Some(emptyUserAnswers)

        private val result = controllerUnderTest.keepAlive()(FakeRequest())

        status(result) mustEqual OK
        verify(mockSessionRepository, times(1)).keepAlive(emptyUserAnswers.id)
      }
    }

    "when the user has not answered any questions" - {

      "must return OK" in new Setup {
        val givenUserAnswers: Option[UserAnswers] = None

        private val result = controllerUnderTest.keepAlive()(FakeRequest())

        status(result) mustEqual OK
        verify(mockSessionRepository, never()).keepAlive(any())
      }
    }
  }
}
