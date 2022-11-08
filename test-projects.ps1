foreach ($dir in Get-ChildItem test-projects) {
  Set-Location $dir
  .\gradlew $args
  Set-Location -
}
