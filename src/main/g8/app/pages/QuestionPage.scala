package pages

import queries.{Gettable, Settable}

trait QuestionPage[A] extends Page with Gettable[A] with Settable[A]
