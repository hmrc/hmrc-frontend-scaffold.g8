package viewmodels

abstract class WithName(name: String) {
  override val toString: String = name
}
