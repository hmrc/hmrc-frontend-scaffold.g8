package controllers.actions

import base.SpecBase
import models.UserAnswers
import models.requests.{IdentifierRequest, OptionalDataRequest}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import repositories.SessionRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataRetrievalActionSpec extends SpecBase with MockitoSugar {

  class Harness(sessionRepository: SessionRepository) extends DataRetrievalActionImpl(sessionRepository) {
    def callTransform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] = transform(request)
  }

  "Data Retrieval Action" when {

    "there is no data in the cache" must {

      "set userAnswers to 'None' in the request" in {

        val sessionRepository = mock[SessionRepository]
        when(sessionRepository.get("id")) thenReturn Future(None)
        val action = new Harness(sessionRepository)

        val futureResult = action.callTransform(IdentifierRequest(fakeRequest, "id"))

        whenReady(futureResult) { result =>
          result.userAnswers.isEmpty mustBe true
        }
      }
    }

    "there is data in the cache" must {

      "build a userAnswers object and add it to the request" in {

        val sessionRepository = mock[SessionRepository]
        when(sessionRepository.get("id")) thenReturn Future(Some(UserAnswers("id")))
        val action = new Harness(sessionRepository)

        val futureResult = action.callTransform(new IdentifierRequest(fakeRequest, "id"))

        whenReady(futureResult) { result =>
          result.userAnswers.isDefined mustBe true
        }
      }
    }
  }
}
