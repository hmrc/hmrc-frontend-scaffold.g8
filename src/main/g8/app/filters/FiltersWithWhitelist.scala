package filters

import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import uk.gov.hmrc.play.bootstrap.filters.FrontendFilters

class FiltersWithWhitelist @Inject()(
                                      frontendFilters: FrontendFilters,
                                      whitelistFilter: WhitelistFilter,
                                      sessionIdFilter: SessionIdFilter
                                    ) extends DefaultHttpFilters(frontendFilters.filters :+ whitelistFilter :+ sessionIdFilter: _*)
