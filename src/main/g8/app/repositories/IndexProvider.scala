package repositories

import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONSerializationPack
import reactivemongo.api.indexes.Index.Aux
import reactivemongo.api.indexes.Index
import reactivemongo.api.indexes.IndexType

object IndexProvider {

  def index(
             key: Seq[(String, IndexType)],
             name: Option[String],
             unique: Boolean = false,
             sparse: Boolean = false,
             background: Boolean = false,
             expireAfterSeconds: Option[Int] = None
           ): Aux[BSONSerializationPack.type] =
    Index(
      key = key,
      unique = unique,
      name = name,
      background = background,
      sparse = sparse,
      expireAfterSeconds = expireAfterSeconds,
      storageEngine = None,
      weights = None,
      defaultLanguage = None,
      languageOverride = None,
      textIndexVersion = None,
      sphereIndexVersion = None,
      bits = None,
      min = None,
      max = None,
      bucketSize = None,
      collation = None,
      wildcardProjection = None,
      version = None,
      partialFilter = None,
      options = BSONDocument.empty
    )
}
