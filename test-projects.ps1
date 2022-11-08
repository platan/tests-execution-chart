foreach ($dir in Get-ChildItem examples) {
  Set-Location $dir
  .\gradlew $args
  Set-Location -
}
