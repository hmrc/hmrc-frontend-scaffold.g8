package controllers

import uk.gov.hmrc.http.cache.client.CacheMap
import base.SpecBase
import controllers.actions.FakeDataRetrievalAction

trait ControllerSpecBase extends SpecBase {

  val cacheMapId = "id"

  def emptyCacheMap = CacheMap(cacheMapId, Map())

  def getEmptyCacheMap = new FakeDataRetrievalAction(Some(emptyCacheMap))

  def dontGetAnyData = new FakeDataRetrievalAction(None)
}
