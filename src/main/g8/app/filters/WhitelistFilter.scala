package filters

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.Configuration
import play.api.mvc.Call
import uk.gov.hmrc.whitelist.AkamaiWhitelistFilter

class WhitelistFilter @Inject() (
                                  config: Configuration,
                                  override val mat: Materializer
                                ) extends AkamaiWhitelistFilter {

  override val whitelist: Seq[String] = {
    config
      .underlying
      .getString("filters.whitelist.ips")
      .split(",")
      .map(_.trim)
      .filter(_.nonEmpty)
  }

  override val destination: Call = {
    val path = config.underlying.getString("filters.whitelist.destination")
    Call("GET", path)
  }

  override val excludedPaths: Seq[Call] = {
    config.underlying.getString("filters.whitelist.excluded").split(",").map {
      path =>
        Call("GET", path.trim)
    }
  }
}
