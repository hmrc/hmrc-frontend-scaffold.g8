package filters

import com.google.inject.Inject
import play.api.http.DefaultHttpFilters
import uk.gov.hmrc.play.bootstrap.filters.FrontendFilters

class Filters @Inject() (
                          sessionIdFilter: SessionIdFilter,
                          frontendFilters: FrontendFilters
                        ) extends DefaultHttpFilters(frontendFilters.filters :+ sessionIdFilter: _*)
