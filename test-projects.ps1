foreach ($dir in Get-ChildItem test-projects -Directory) {
  Set-Location $dir
  .\gradlew $args
  Set-Location -
}
