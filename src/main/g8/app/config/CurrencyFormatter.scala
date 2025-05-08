package config

trait CurrencyFormatter {
  def currencyFormat(amt: BigDecimal): String = f"£\$amt%,1.2f".replace(".00","")
}

object CurrencyFormatter extends CurrencyFormatter
