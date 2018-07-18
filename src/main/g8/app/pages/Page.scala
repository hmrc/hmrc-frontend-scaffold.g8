package pages

import scala.language.implicitConversions

trait Page

object Page {

  implicit def toString(page: Page): String =
    page.toString
}
